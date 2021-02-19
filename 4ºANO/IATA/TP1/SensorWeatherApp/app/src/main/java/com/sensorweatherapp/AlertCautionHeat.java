package com.sensorweatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.AppCompatDialogFragment;

import androidx.appcompat.app.AppCompatDialogFragment;

public class AlertCautionHeat extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String titleText = "Temperature: Caution";
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.rgb(255,219,88));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        String message = "Possibility of fatigue after prolonged exposure and activity...";
        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.BLACK);
        SpannableStringBuilder ssBuilder1 = new SpannableStringBuilder(message);
        ssBuilder1.setSpan(
                foregroundColorSpan1,
                0,
                message.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        builder.setTitle(ssBuilder)
                .setMessage(ssBuilder1)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }

}
