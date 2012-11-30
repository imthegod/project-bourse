package co.je.thesis.mobile.presentation.dialogs;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	private String resultDate;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		resultDate = "";
		
		Calendar calendar = Calendar.getInstance();
		
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		
		DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, dayOfMonth);
		
		return datePickerDialog;
	}
	
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		
		// Corrects moth number
		// (November is the moth 11, not 10)
		monthOfYear += 1;
		
		String correctedDayOfMonth = "";
		
		if (dayOfMonth < 10) {
			
			correctedDayOfMonth = "0" + dayOfMonth;
			
		} else {
			
			correctedDayOfMonth = "" + dayOfMonth;
		}
		
		resultDate = correctedDayOfMonth + "-" + monthOfYear + "-" + year;
	}
	
	@Override
	public void onDestroy() {
		
		ICreateDialogActivity createAnalysisActivity = (ICreateDialogActivity) getActivity();
		createAnalysisActivity.setDialogAnswer(resultDate);
		super.onDestroy();
	}
}
