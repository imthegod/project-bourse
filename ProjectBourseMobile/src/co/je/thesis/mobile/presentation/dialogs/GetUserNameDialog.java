package co.je.thesis.mobile.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetUserNameDialog extends DialogFragment implements OnClickListener {
	
	private String userName;
	
	private EditText editText;
	private Button btnOk;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		userName = "";

		TextView title = new TextView(getActivity());
		title.setText("Username");
		title.setGravity(Gravity.CENTER_HORIZONTAL);

		editText = new EditText(getActivity());
		editText.setInputType(InputType.TYPE_CLASS_TEXT);

		btnOk = new Button(getActivity());
		btnOk.setOnClickListener(this);
		btnOk.setText("Ok");

		LinearLayout verticalLayout = new LinearLayout(getActivity());
		verticalLayout.setOrientation(LinearLayout.VERTICAL);
		verticalLayout.setGravity(Gravity.CENTER_HORIZONTAL);

		verticalLayout.addView(title);
		verticalLayout.addView(editText);
		verticalLayout.addView(btnOk);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(verticalLayout);
		AlertDialog alertDialog = builder.create();

		return alertDialog;
	}

	public void onClick(View view) {
		
		if (view == btnOk) {
			
			if (userName != null) {
				
				userName = editText.getText().toString();
				this.dismiss();
			}
		}
	}
	
	@Override
	public void onDestroy() {
		
		ICreateDialogActivity mainActivity = (ICreateDialogActivity) getActivity();
		mainActivity.setDialogAnswer(userName);
		super.onDestroy();
	}
}