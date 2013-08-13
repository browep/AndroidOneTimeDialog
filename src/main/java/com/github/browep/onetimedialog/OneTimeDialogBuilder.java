package com.github.browep.onetimedialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * a dialog that will only once per key.
 */
public class OneTimeDialogBuilder extends AlertDialog.Builder {

    private static String TAG = OneTimeDialogBuilder.class.getCanonicalName();

    private Context mContext;

    private String mKey;

    public OneTimeDialogBuilder(Context context, String key) {
        super(context);
        mContext = context;
        mKey = key;
    }

    @Override
    public AlertDialog create() {
        // check to see if we have already shown this dialog, if so return useless dialog, else regular
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("onetimedialog-keys", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(mKey, false)) {
            return new NoOpDialog(mContext);
        } else {
            sharedPreferences.edit().putBoolean(mKey, true).commit();
            return super.create();
        }
    }

    public static class NoOpDialog extends AlertDialog {

        protected NoOpDialog(Context context) {
            super(context);
        }

        protected NoOpDialog(Context context, boolean cancelable,
                OnCancelListener cancelListener) {
            super(context, cancelable, cancelListener);
        }

        protected NoOpDialog(Context context, int theme) {
            super(context, theme);
        }

        /**
         * do nothing on show
         */
        @Override
        public void show() {

        }
    }
}
