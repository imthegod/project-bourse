package co.je.thesis.mobile.presentation.analysis;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import co.je.thesis.common.constants.DSLCategories;
import co.je.thesis.common.domainObjects.ValidRule;
import co.je.thesis.common.dtos.analysis.AnalysisDTO;
import co.je.thesis.common.dtos.dsl.DSLDataTransferObject;
import co.je.thesis.common.dtos.dsl.DSLElementDTO;
import co.je.thesis.common.dtos.rules.RuleDTO;
import co.je.thesis.common.verifiers.RuleVerifier;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.communication.analysis.AnalysisServicesConsumer;
import co.je.thesis.mobile.communication.dsl.DSLServicesConsumer;
import co.je.thesis.mobile.communication.rules.RuleServicesConsumer;
import co.je.thesis.mobile.logic.analysisManager.AnalysisManager;
import co.je.thesis.mobile.presentation.UIUtils;
import co.je.thesis.mobile.presentation.dialogs.CreateDialogActivity;
import co.je.thesis.mobile.presentation.dialogs.DatePickerFragment;

public class CreateAnalysisActivity extends Activity implements OnClickListener,
OnItemSelectedListener, TextWatcher, OnEditorActionListener, CreateDialogActivity {

	private static final String FIRST_ITEM = "Select one";

	private DSLDataTransferObject dsl;
	private ArrayList<ValidRule> validRules;

	private RuleDTO ruleDTO;
	private AnalysisDTO analysisDTO;

	private Button btnCancel;
	private Button btnAddRule;
	private Button btnSendAnalysis;

	/**
	 * Field to store the id of an active editText component
	 */
	private int editTextId;

	/**
	 * Field to store the text of an active editText component
	 */
	private String editTextValue;

	/**
	 * Attribute to count and assign an ID to each UI component that is added
	 * programmatically
	 */
	private int numberOfWidgets;

	private void getDSLAndValidRules() {

		DSLServicesConsumer dslServicesConsumer = new DSLServicesConsumer();
		dsl = dslServicesConsumer.getDSL();

		RuleServicesConsumer ruleServicesConsumer = new RuleServicesConsumer();
		validRules = ruleServicesConsumer.getValidRules();

		System.out.println("CreateAnalysisActivity: validRules.size():" + validRules.size());
	}
	
	private void initCustomElements() {
		
		ruleDTO = new RuleDTO();
		
		AnalysisManager analysisManager = new AnalysisManager(this);
		String userName = analysisManager.getUserName();
		
		analysisDTO = new AnalysisDTO();
		analysisDTO.setOwnerUserName(userName);
		
		editTextId = -1;
		editTextValue = "";
		
		numberOfWidgets = 0;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_create_analysis);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		getDSLAndValidRules();

		initCustomElements();

		addFirstSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_create_analysis, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void removeWidgetAt(int linearLayoutIndex) {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		linearLayout.removeViewAt(linearLayoutIndex);
	}

	public void onClick(View view) {

		if (view == btnCancel) {

			// Remove widgets from UI
			for (int i = numberOfWidgets; i >= 1; i--) {
				removeWidgetAt(i);
			}

			// new ruleDTO
			initCustomElements();

			// Adds the first spinner again
			addFirstSpinner();

		} else if (view == btnAddRule) {
			
			String category = DSLCategories.LOGICAL_OPERATOR;
			String value = "AND";
			DSLElementDTO logicalOperator = new DSLElementDTO(category, value);
			
			analysisDTO.addRuleDTO(ruleDTO);
			analysisDTO.addLogicalOperator(logicalOperator);
			
			Bundle bundle = new Bundle();
			String key = "analysisDTO";
			bundle.putSerializable(key, analysisDTO);
			Intent addRuleToAnalysisActivityIntent = new Intent(this, AddRuleToAnalysisActivity.class);
			
			addRuleToAnalysisActivityIntent.putExtras(bundle);
			startActivity(addRuleToAnalysisActivityIntent);
			
			this.finish();

		} else if (view == btnSendAnalysis) {
			
			analysisDTO.addRuleDTO(ruleDTO);
			AnalysisServicesConsumer analysisServicesConsumer = new AnalysisServicesConsumer();
			analysisServicesConsumer.sendAnalysisRequest(analysisDTO);
			
			AnalysisManager analysisManager = new AnalysisManager(this);
			analysisManager.addPendingAnalysis(analysisDTO.getUuid());
			
			String toastMessage = "The analysis has been sent.";
			UIUtils.showToast(this, toastMessage);
			
			this.finish();
		}
	}

	private void addFirstSpinner() {

		numberOfWidgets = 1;
		Spinner firstSpinner = new Spinner(this);
		firstSpinner.setId(numberOfWidgets);
		
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_list_item);
		
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.add(FIRST_ITEM);
		spinnerArrayAdapter.add("acciones");

		firstSpinner.setAdapter(spinnerArrayAdapter);
		firstSpinner.setOnItemSelectedListener(this);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		linearLayout.addView(firstSpinner);
	}

	private void addSpinner(ArrayList<String> spinnerItems) {

		numberOfWidgets++;
		Spinner spinner = new Spinner(this);
		spinner.setId(numberOfWidgets);

		spinner.setOnItemSelectedListener(this);

		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_list_item);
		
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerArrayAdapter.add(FIRST_ITEM);

		for (int i = 0; i < spinnerItems.size(); i++) {

			String item = spinnerItems.get(i);
			spinnerArrayAdapter.add(item);
		}

		spinner.setAdapter(spinnerArrayAdapter);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		linearLayout.addView(spinner);
	}

	/**
	 * Adds an EditText widget to the activity.
	 * 
	 * @return Returns the id of the editText added to the activity.
	 */
	private void addEditTextForNumber() {

		numberOfWidgets++;
		EditText editText = new EditText(this);
		editText.setId(numberOfWidgets);

		editTextId = editText.getId();

		editText.addTextChangedListener(this);
		editText.setOnEditorActionListener(this);

		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText.setGravity(Gravity.CENTER_HORIZONTAL);
		editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		editText.setHint("A number please");

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		linearLayout.addView(editText);
		
		editText.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
	}

	private ArrayList<String> getNextSpinnerItems(DSLElementDTO dslElementDTO) {

		ruleDTO.addElement(dslElementDTO);

		RuleVerifier ruleVerifier = new RuleVerifier(validRules);
		ArrayList<String> nextCategories = ruleVerifier.getNextValidDSLElementsCategories(ruleDTO);

		ArrayList<String> spinnerItems = new ArrayList<String>();

		for (int i = 0; i < nextCategories.size(); i++) {

			String category = nextCategories.get(i);
			ArrayList<String> tempValues = dsl.getValuesOfAGivenCategory(category);
			spinnerItems.addAll(tempValues);
		}

		return spinnerItems;
	}

	private void addButtons() {

		String cancelText = "Cancel";
		btnCancel = new Button(this);
		btnCancel.setOnClickListener(this);
		btnCancel.setText(cancelText);

		String addRuleText = "Add rule";
		btnAddRule = new Button(this);
		btnAddRule.setOnClickListener(this);
		btnAddRule.setText(addRuleText);

		String sendAnalysisText = "Send analysis";
		btnSendAnalysis = new Button(this);
		btnSendAnalysis.setOnClickListener(this);
		btnSendAnalysis.setText(sendAnalysisText);

		numberOfWidgets++;
		LinearLayout horizontalLayout = new LinearLayout(this);

		horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
		horizontalLayout.setGravity(Gravity.CENTER_HORIZONTAL);

		horizontalLayout.addView(btnCancel);
		horizontalLayout.addView(btnAddRule);
		horizontalLayout.addView(btnSendAnalysis);

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		linearLayout.addView(horizontalLayout);
	}

	public void setDialogAnswer(String dialogAnswer) {

		if (!dialogAnswer.isEmpty()) {

			DSLElementDTO dslElementDTO = new DSLElementDTO(DSLCategories.TIME_FRAME, dialogAnswer);
			ArrayList<String> spinnerItems = getNextSpinnerItems(dslElementDTO);

			if (spinnerItems.size() > 0) {

				addSpinner(spinnerItems);

			} else {

				addButtons();
			}
		}
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

		String selectedItem = parent.getItemAtPosition(position).toString();

		if (!selectedItem.equalsIgnoreCase(FIRST_ITEM)) {

			parent.setEnabled(false);
		}

		System.out.println("Selected item: " + selectedItem);

		if (!selectedItem.equals(FIRST_ITEM) && !selectedItem.equals(DSLCategories.TIME_FRAME)
				&& !selectedItem.equalsIgnoreCase(DSLCategories.NUMBER)) {

			DSLElementDTO dslElementDTO = dsl.getDSLElementFromStringValue(selectedItem);
			ArrayList<String> spinnerItems = getNextSpinnerItems(dslElementDTO);

			if (spinnerItems.size() > 0) {

				addSpinner(spinnerItems);

			} else {

				addButtons();
			}

		} else if (selectedItem.equalsIgnoreCase(DSLCategories.TIME_FRAME)) {

			DatePickerFragment datePickerFragment = new DatePickerFragment();
			datePickerFragment.show(getFragmentManager(), "DatePickerFragment");

		} else if (selectedItem.equalsIgnoreCase(DSLCategories.NUMBER)) {

			addEditTextForNumber();
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
	}

	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {

		editTextValue = s.toString();
	}

	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		boolean isDoneKeyPressed = (actionId == EditorInfo.IME_ACTION_DONE);

		if (isDoneKeyPressed && !editTextValue.isEmpty()) {

			EditText editText = (EditText) findViewById(editTextId);
			editText.setEnabled(false);

			DSLElementDTO dslElementDTO = new DSLElementDTO(DSLCategories.NUMBER, editTextValue);
			ArrayList<String> spinnerItems = getNextSpinnerItems(dslElementDTO);

			if (spinnerItems.size() > 0) {

				addSpinner(spinnerItems);

			} else {

				addButtons();
			}
		}

		return false;
	}
}