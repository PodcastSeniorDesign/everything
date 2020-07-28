package me.rooshi.podcastapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PhotoChooserDialogFragment extends DialogFragment {

    public interface PhotoChooserDialogListener {
        public void onOptionClick(DialogFragment dialog, int i);
    }

    PhotoChooserDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PhotoChooserDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement PhotoChooserDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title)
                .setItems(R.array.photo_dialog_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onOptionClick(PhotoChooserDialogFragment.this, i);
                    }
                });
        return builder.create();
    }
}