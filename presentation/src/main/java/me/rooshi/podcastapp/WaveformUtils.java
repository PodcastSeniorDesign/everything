package me.rooshi.podcastapp;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public final class WaveformUtils {

    public static String getStringFromTextInputLayout(TextInputLayout textInputLayout) {
        EditText editText = textInputLayout.getEditText();
        String s = null;
        if (editText != null) {
            s = editText.getText().toString();
        }
        if (s == null) {
            return "";
        } else {
            return s;
        }
    }

    public static boolean isValidEmailAddress(String emailAddress) {
        //TODO
        return true;
    }

    public static boolean isValidPassword(String password) {
        //TODO
        return true;
    }
}
