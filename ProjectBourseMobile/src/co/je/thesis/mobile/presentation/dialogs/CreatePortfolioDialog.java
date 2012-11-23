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

public class CreatePortfolioDialog extends DialogFragment implements OnClickListener {
	
	private String portfolioName;
	private EditText editText;
	private Button btnCreate;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		portfolioName = "";

		TextView title = new TextView(getActivity());
		title.setText("New portfolio name");
		title.setGravity(Gravity.CENTER_HORIZONTAL);

		editText = new EditText(getActivity());
		editText.setInputType(InputType.TYPE_CLASS_TEXT);

		btnCreate = new Button(getActivity());
		btnCreate.setOnClickListener(this);
		btnCreate.setText("Create");

		LinearLayout verticalLayout = new LinearLayout(getActivity());
		verticalLayout.setOrientation(LinearLayout.VERTICAL);
		verticalLayout.setGravity(Gravity.CENTER_HORIZONTAL);

		verticalLayout.addView(title);
		verticalLayout.addView(editText);
		verticalLayout.addView(btnCreate);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(verticalLayout);
		AlertDialog alertDialog = builder.create();

		return alertDialog;
	}

	public void onClick(View view) {
		
		if (view == btnCreate) {
			
			if (portfolioName != null) {
				
				portfolioName = editText.getText().toString();
				this.dismiss();
			}
		}
	}
	
	@Override
	public void onDestroy() {
		
		CreateDialogActivity hostActivity = (CreateDialogActivity) getActivity();
		hostActivity.setDialogAnswer(portfolioName);
		super.onDestroy();
	}
}