package com.joseluisng.dbroom;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.joseluisng.dbroom.db.entity.NotaEntity;

public class NuevaNotaDialogFragment extends DialogFragment {



    public static NuevaNotaDialogFragment newInstance() {
        return new NuevaNotaDialogFragment();
    }

    private View view;
    private EditText etTitulo, etContendio;
    private RadioGroup rgColor;
    private Switch swNotaFavorita;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Nueva nota");
        builder.setMessage("Introduzca los datos de la nueva nota")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Fire ze missiles!
                        String titulo = etTitulo.getText().toString();
                        String contenido = etContendio.getText().toString();
                        String color = "azul";
                        switch(rgColor.getCheckedRadioButtonId()){
                            case R.id.radioButtonColorRojo:
                                color = "rojo"; break;
                            case R.id.radioButtonColorVerde:
                                color = "verde"; break;
                        }

                        boolean esFavorita = swNotaFavorita.isChecked();

                        // Comunicar el ViewModel el nuevo dato
                        NuevaNotaDialogViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NuevaNotaDialogViewModel.class);
                        mViewModel.insertarNota(new NotaEntity(titulo, contenido, esFavorita, color));
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.nueva_nota_dialog_fragment, null);

        etTitulo = view.findViewById(R.id.editTextContenido);
        etContendio = view.findViewById(R.id.editTextContenido);
        rgColor = view.findViewById(R.id.radioGroupColor);
        swNotaFavorita = view.findViewById(R.id.switchNotaFavorita);

        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
