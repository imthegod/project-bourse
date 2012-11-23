package co.je.thesis.mobile.presentation;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UIUtils {

	public static final String APP_NAME = "Project Bourse";
	
	// Main menu options
	
	public static final String PORTFOLIO = "Portfolio";
	public static final String ANALYSIS = "Analysis";
	public static final String STATISTICS = "Statistics";
	
	// Portfolio options
	
	public static final String CREATE_PORTFOLIO = "Create portfolio";
	public static final String VIEW_PORTFOLIO = "View portfolio";
	public static final String UPDATE_PORTFOLIO = "Update portfolio";
	public static final String DELETE_PORTFOLIO = "Delete portfolio";
	
	public static final String CREATE = "Create";
	public static final String READ = "Read";
	public static final String UPDATE = "Update";
	public static final String DELETE = "Delete";
	
	public static final String ADD_STOCK = "Add stock";
	
	public static final String MENU = "Menu";

	//
	
	public static final int TITLE_FONT_SIZE = 37;
	public static final int SUB_TITLE_FONT_SIZE = 32;
	public static final int MAIN_BUTTON_FONT_SIZE = 27;
	
	public static final int REGULAR_FONT_SIZE = 20;
	
	public static void showAlertDialog(Context context, String dialogMessage) {
		
		AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.setMessage(dialogMessage);
		dialog.show();
	}
	
	public static void showToast(Context context, String toastMessage) {
		
		Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
	}
	
	public static void configureTextViewHeader(TextView textView) {
		
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		textView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
	}

	public static void configureTittle(TextView textView) {

		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(UIUtils.TITLE_FONT_SIZE);
	}
	
	public static void configureSubTittle(TextView textView) {

		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(UIUtils.SUB_TITLE_FONT_SIZE);
	}
	
	public static void configureLabel(TextView textLabel) {
		
		textLabel.setGravity(Gravity.CENTER);
		textLabel.setTextSize(UIUtils.REGULAR_FONT_SIZE);
	}

	public static void configureMainButton(Button button) {

		button.setGravity(Gravity.CENTER);
		button.setTextSize(UIUtils.MAIN_BUTTON_FONT_SIZE);
		button.setWidth(200);
		button.setHeight(100);
	}
	
	public static void configureRegularButton(Button button) {

		button.setGravity(Gravity.CENTER);
		button.setTextSize(UIUtils.REGULAR_FONT_SIZE);
		button.setWidth(100);
		button.setHeight(40);
	}
}
