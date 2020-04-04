package com.joseluisng.dbroom.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.joseluisng.dbroom.NuevaNotaDialogFragment;
import com.joseluisng.dbroom.NuevaNotaDialogViewModel;
import com.joseluisng.dbroom.R;
import com.joseluisng.dbroom.db.entity.NotaEntity;

import java.util.ArrayList;
import java.util.List;


public class NotaFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;

    private List<NotaEntity> notaEntityList;
    private MyNotaRecyclerViewAdapter adapterNotas;
    private NuevaNotaDialogViewModel notaViewModel;


    public NotaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NotaFragment newInstance(int columnCount) {
        NotaFragment fragment = new NotaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Indicamos que el Fragment tiene un menú de opciones propio.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (view.getId() == R.id.listPortrait) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                //Con displayMetrics calculamos lo ancho de la pantalla y podemos hacer que si es muy ancha la pantalla
                // mustre la lista en mas de 2 columnas
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
                int numeroColumnas = (int) (dpWidth / 180);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumnCount, StaggeredGridLayoutManager.VERTICAL));
            }

            notaEntityList = new ArrayList<>();
            //notaEntityList.add(new NotaEntity("lista de la compra", "comprar pan tostado", true, android.R.color.holo_blue_light));
            //notaEntityList.add(new NotaEntity("Rocardar", "He aparcado el coche en Hidalgo, no olvidar de pagar en el parquimetro", false, android.R.color.holo_green_light));
            //notaEntityList.add(new NotaEntity("Cumpleaños (fiesta)", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ", false, android.R.color.holo_orange_light));


            adapterNotas = new MyNotaRecyclerViewAdapter(notaEntityList, getActivity());
            recyclerView.setAdapter(adapterNotas);
            
            lanzarViewModel();
        }
        return view;
    }

    private void lanzarViewModel() {
        notaViewModel = ViewModelProviders.of(getActivity())
                .get(NuevaNotaDialogViewModel.class);

        notaViewModel.getAllNotas().observe(getActivity(), new Observer<List<NotaEntity>>() {
            @Override
            public void onChanged(@Nullable List<NotaEntity> notaEntities) {
                adapterNotas.setNuevasNotas(notaEntities);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_nota:
                mostrarDialogoNuevaNota();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarDialogoNuevaNota() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        NuevaNotaDialogFragment dialogNuevaNota = new NuevaNotaDialogFragment();
        dialogNuevaNota.show(fm, "NuevaNotaDialogFragment");
    }


}
