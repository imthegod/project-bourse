package co.je.thesis.mobile.presentation.dialogs;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SelectPortfolioDialog extends DialogFragment {

	public static final String NEW_PORTFOLIO_ITEM = "New portfolio";
	
	private String[] portfolioNames;
	private String selectedPortfolioName;

	public SelectPortfolioDialog(ArrayList<String> portfolioNamesArrayList) {

		if (portfolioNamesArrayList != null) {

			int numberOfNames = portfolioNamesArrayList.size() + 1;
			portfolioNames = portfolioNamesArrayList.toArray(new String[numberOfNames]);
			portfolioNames[numberOfNames - 1] = NEW_PORTFOLIO_ITEM;

		} else {

			portfolioNames = new String[0];
		}

		selectedPortfolioName = "";
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Pick a portfolio");

		builder.setItems(portfolioNames, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// The 'which' argument contains the index position
				// of the selected item

				selectedPortfolioName = portfolioNames[which];
				
				ICreateDialogActivity hostActivity = (ICreateDialogActivity) getActivity();
				hostActivity.setDialogAnswer(selectedPortfolioName);
			}
		});

		return builder.create();
	}
}
