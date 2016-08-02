package com.example.shitij.railway.interfaces;

import android.os.Bundle;

/**
 * Created by Shitij on 18/11/15.
 */
public interface DialogCallbackInterface {

    void onDialogPositivePressed(Bundle bundle, String info);
    void onDialogNegativePressed(Bundle bundle, String info);
}
