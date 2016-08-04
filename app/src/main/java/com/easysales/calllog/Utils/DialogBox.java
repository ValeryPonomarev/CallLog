package com.easysales.calllog.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by drmiller on 25.07.2016.
 */
public final class DialogBox {

    public static enum DialogButton {
        Yes, No, Ok, Cancel
    }

    public static void Show(Context context, String title, DialogButton positiveButton, DialogButton negativeButton, DialogButton neutralButton)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        /*

                        break;
                        /**/
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;

                    case DialogInterface.BUTTON_NEUTRAL:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(title);
        if(positiveButton != null) {
            String namePositiveButton = positiveButton.toString();
            builder.setPositiveButton(namePositiveButton, dialogClickListener);
        }

        if(negativeButton != null){
            String nameNegativeButton = negativeButton.toString();
            builder.setPositiveButton(nameNegativeButton, dialogClickListener);
        }

        if(neutralButton != null){
            String nameNeutralButton = neutralButton.toString();
            builder.setPositiveButton(nameNeutralButton, dialogClickListener);
        }
    }
}
