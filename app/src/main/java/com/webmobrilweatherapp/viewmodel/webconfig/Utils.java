package com.webmobrilweatherapp.viewmodel.webconfig;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }

    public static ProgressDialog createProgress(Context mContext) {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        return null;
    }

    public static void showProgress(Context mContext) {
        if (createProgress(mContext) != null) {
            createProgress(mContext).show();
        }
    }

    public static void dismissProgress(Context mContext) {
        if (createProgress(mContext) != null) {
            createProgress(mContext).dismiss();
        }
    }

}
