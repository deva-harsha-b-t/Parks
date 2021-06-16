package com.dtl.parks.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.dtl.parks.R;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;
    public LoadingDialog(Activity activity){
        this.activity = activity;
    }
    public void startLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_layout,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
    public void stopLoading(){
        dialog.dismiss();
    }
}
