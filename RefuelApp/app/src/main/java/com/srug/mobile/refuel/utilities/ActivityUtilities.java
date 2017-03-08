package com.srug.mobile.refuel.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public final class ActivityUtilities {

    private ActivityUtilities() {
    }

    public static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showCustomActionAlertDialog(Context context, String title, String message,
                                                   String positiveButtonName, String negativeButtonName,
                                                   DialogInterface.OnClickListener onClickPositiveButtonListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(positiveButtonName, onClickPositiveButtonListener)
                .setNegativeButton(negativeButtonName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public static void showYesCancelAlertDialog(Context context, String title, String message,
                                                DialogInterface.OnClickListener onClickPositiveButtonListener) {
        String strYes = context.getString(android.R.string.yes);
        String strCancel = context.getString(android.R.string.cancel);
        showCustomActionAlertDialog(context, title, message, strYes, strCancel, onClickPositiveButtonListener);
    }
}
