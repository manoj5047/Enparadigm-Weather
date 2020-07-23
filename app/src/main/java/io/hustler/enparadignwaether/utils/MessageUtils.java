package io.hustler.enparadignwaether.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import io.hustler.enparadignwaether.R;

import java.util.Objects;


public class MessageUtils {

    public static void showShortToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void showShortToastContext(Context activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void showDismissableSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.setAction(view.getContext().getString(R.string.ok), v -> snackbar.dismiss());
        snackbar.setActionTextColor(ContextCompat.getColor(Objects.requireNonNull(view.getContext()), R.color.colorAccent));
        snackbar.show();
    }



}


