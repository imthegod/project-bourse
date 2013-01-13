package co.je.thesis.mobile.presentation.portfolio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.logic.portfolioManager.PortfolioManager;
import co.je.thesis.mobile.presentation.MainActivity;
import co.je.thesis.mobile.presentation.UIUtils;

/**
 * This class extends an Android Activity. This activity supports the following functionality: 
 * Add a stock to a portfolio.
 * 
 * @author Julian Espinel
 */
public class AddStockActivity extends Activity implements OnClickListener {
	
	public static final String TAG = "AddStockActivity";

	private PortfolioManager portfolioManager;

	private String portfolioName;
	
	private Button btnAddStock;
	private Button btnGoToMainMenu;
	
	private void cleanStockInputTextFields() {

		EditText inputSymbol = (EditText) findViewById(R.id.symbol);
		EditText inputName = (EditText) findViewById(R.id.name);
		EditText inputNumberOfShares = (EditText) findViewById(R.id.numberOfShares);
		
		EditText inputBasePrice = (EditText) findViewById(R.id.basePrice);

		EditText inputStopLoss1 = (EditText) findViewById(R.id.stopLoss1);
		EditText inputStopLoss2 = (EditText) findViewById(R.id.stopLoss2);
		EditText inputStopLoss3 = (EditText) findViewById(R.id.stopLoss3);
		EditText inputTakeProfit1 = (EditText) findViewById(R.id.takeProfit1);
		EditText inputTakeProfit2 = (EditText) findViewById(R.id.takeProfit2);
		EditText inputTakeProfit3 = (EditText) findViewById(R.id.takeProfit3);

		inputSymbol.setText("");
		inputName.setText("");
		inputNumberOfShares.setText("");
		inputBasePrice.setText("");

		inputStopLoss1.setText("");
		inputStopLoss2.setText("");
		inputStopLoss3.setText("");

		inputTakeProfit1.setText("");
		inputTakeProfit2.setText("");
		inputTakeProfit3.setText("");
	}

	private boolean fieldIsEmptyOrNull(String field) {

		boolean answer = false;

		if (field == null) {
			answer = true;
		} else if (field.isEmpty()) {
			answer = true;
		}

		return answer;
	}

	private boolean stocksFieldsAreAllValid(String symbol, String name,
			String numberOfSharesStr, String basePriceStr, String stopLoss1Str,
			String stopLoss2Str, String stopLoss3Str, String takeProfit1Str,
			String takeProfit2Str, String takeProfit3Str) {

		boolean symbolIsValid = !fieldIsEmptyOrNull(symbol);
		boolean nameIsValid = !fieldIsEmptyOrNull(name);
		boolean numberOfSharesIsValid = !fieldIsEmptyOrNull(numberOfSharesStr);
		boolean basePriceIsValid = !fieldIsEmptyOrNull(basePriceStr);
		boolean stopLoss1IsValid = !fieldIsEmptyOrNull(stopLoss1Str);
		boolean stopLoss2IsValid = !fieldIsEmptyOrNull(stopLoss2Str);
		boolean stopLoss3IsValid = !fieldIsEmptyOrNull(stopLoss3Str);
		boolean takeProfit1IsValid = !fieldIsEmptyOrNull(takeProfit1Str);
		boolean takeProfit2IsValid = !fieldIsEmptyOrNull(takeProfit2Str);
		boolean takeProfit3IsValid = !fieldIsEmptyOrNull(takeProfit3Str);

		boolean answer = symbolIsValid && nameIsValid && numberOfSharesIsValid
				&& basePriceIsValid && stopLoss1IsValid && stopLoss2IsValid
				&& stopLoss3IsValid && takeProfit1IsValid && takeProfit2IsValid
				&& takeProfit3IsValid;

		return answer;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_stock);
		
		portfolioManager = new PortfolioManager(this);

		String key = "portfolioName";
		Bundle bundle = getIntent().getExtras();
		portfolioName = bundle.getString(key);

		// Set the portfolio name as tittle

		TextView title = (TextView) findViewById(R.id.portfolioName);
		title.setText(portfolioName);
		
		btnAddStock = (Button) findViewById(R.id.add);
		btnGoToMainMenu = (Button) findViewById(R.id.goToMenu);

		btnAddStock.setOnClickListener(this);
		btnGoToMainMenu.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_add_stock, menu);
		return true;
	}

	public void onClick(View target) {
		
		if (target == btnAddStock) {
			
			Log.i(TAG, "btnAddStock");

			EditText inputSymbol = (EditText) findViewById(R.id.symbol);
			String symbol = inputSymbol.getText().toString();

			EditText inputName = (EditText) findViewById(R.id.name);
			String name = inputName.getText().toString();

			EditText inputNumberOfShares = (EditText) findViewById(R.id.numberOfShares);
			String numberOfSharesStr = inputNumberOfShares.getText().toString();

			EditText inputBasePrice = (EditText) findViewById(R.id.basePrice);
			String basePriceStr = inputBasePrice.getText().toString();

			EditText inputStopLoss1 = (EditText) findViewById(R.id.stopLoss1);
			String stopLoss1Str = inputStopLoss1.getText().toString();			

			EditText inputStopLoss2 = (EditText) findViewById(R.id.stopLoss2);
			String stopLoss2Str = inputStopLoss2.getText().toString();

			EditText inputStopLoss3 = (EditText) findViewById(R.id.stopLoss3);
			String stopLoss3Str = inputStopLoss3.getText().toString();

			EditText inputTakeProfit1 = (EditText) findViewById(R.id.takeProfit1);
			String takeProfit1Str = inputTakeProfit1.getText().toString();

			EditText inputTakeProfit2 = (EditText) findViewById(R.id.takeProfit2);
			String takeProfit2Str = inputTakeProfit2.getText().toString();

			EditText inputTakeProfit3 = (EditText) findViewById(R.id.takeProfit3);
			String takeProfit3Str = inputTakeProfit3.getText().toString();

			if (stocksFieldsAreAllValid(symbol, name, numberOfSharesStr, basePriceStr,
					stopLoss1Str, stopLoss2Str, stopLoss3Str, takeProfit1Str,
					takeProfit2Str, takeProfit3Str)) {
				
				int numberOfShares = Integer.parseInt(numberOfSharesStr);
				double basePrice = Double.parseDouble(basePriceStr);
				
				double stopLoss1 = Double.parseDouble(stopLoss1Str);
				double stopLoss2 = Double.parseDouble(stopLoss2Str);
				double stopLoss3 = Double.parseDouble(stopLoss3Str);
				
				double takeProfit1 = Double.parseDouble(takeProfit1Str);
				double takeProfit2 = Double.parseDouble(takeProfit2Str);
				double takeProfit3 = Double.parseDouble(takeProfit3Str);

				Stock stock = new Stock(symbol, name, numberOfShares, portfolioName,
						basePrice, stopLoss1, stopLoss2, stopLoss3, takeProfit1,
						takeProfit2, takeProfit3);
				try {

					portfolioManager.addStockToPortfolio(portfolioName, stock);

					String dialogMessage = "The stock has been successfully added into portfolio "
							+ portfolioName;
					UIUtils.showAlertDialog(this, dialogMessage);

					cleanStockInputTextFields();

				} catch (Exception e) {

					String exceptionMessage = e.getMessage();
					UIUtils.showAlertDialog(this, exceptionMessage);
				}

			} else {

				String dialogMessage = "Please complete all the stocks fields.";
				UIUtils.showAlertDialog(this, dialogMessage);
			}
			
		} else if (target == btnGoToMainMenu) {
			
			Log.i(TAG, "btnGoToMainMenu");
			
			Intent mainActivityIntent = new Intent(this, MainActivity.class);
			startActivity(mainActivityIntent);
			this.finish();
		}
	}
}