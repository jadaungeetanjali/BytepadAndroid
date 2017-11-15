package in.silive.bo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import in.silive.bo.R;


/**
 * Created by Shobhit-pc on 3/28/2017.
 */

public class AlertDialog {

    public void alertDialog(final Context mContext) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        TextView alerttext = (TextView) dialog.findViewById(R.id.textViewalertdialog);
        alerttext.setText("Network unavailable. Please try again later");
        Button yesButton = (Button) dialog.findViewById(R.id.buttonYesAlertDialog);
        yesButton.setText("OK");
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        dialog.show();
    }
}
