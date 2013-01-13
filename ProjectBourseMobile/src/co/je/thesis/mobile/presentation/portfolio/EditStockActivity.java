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
 * Edit a stock that belongs to a specific portolio.
 * 
 * @author Julian Espinel
 */
public class EditStockActivity extends Activity implements OnClickListener {

	public static final String TAG = "EditStockActivity";

	private PortfolioManager portfolioManager;

	private String portfolioName;
	private String stockSymbol;

	private Button btnEdit;
	private Button btnDelete;
	private Button btnGoToMenu;

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

	private void fillStockFields(Stock stock) {

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

		String symbol = stock.getSymbol();
		inputSymbol.setText(symbol);
		inputSymbol.setEnabled(false);

		String name = stock.getName();
		inputName.setText(name);

		String numberOfShares = stock.getNumberOfShares() + "";
		inputNumberOfShares.setText(numberOfShares);

		String basePrice = stock.getBasePrice() + "";
		inputBasePrice.setText(basePrice);

		String stopLoss1 = stock.getStopLoss1() + "";
		inputStopLoss1.setText(stopLoss1);

		String stopLoss2 = stock.getStopLoss2() + "";
		inputStopLoss2.setText(stopLoss2);

		String stopLoss3 = stock.getStopLoss3() + "";
		inputStopLoss3.setText(stopLoss3);

		String takeProfit1 = stock.getTakeProfit1() + "";
		inputTakeProfit1.setText(takeProfit1);

		String takeProfit2 = stock.getTakeProfit2() + "";
		inputTakeProfit2.setText(takeProfit2);

		String takeProfit3 = stock.getTakeProfit3() + "";
		inputTakeProfit3.setText(takeProfit3);
	}
	
	private void goToMenu() {
		
		Intent mainActivityIntent = new Intent(this, MainActivity.class);
		startActivity(mainActivityIntent);
		this.finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_stock);

		String key1 = "portfolioName";
		String key2 = "selectedStockSymbol";

		Bundle bundle = getIntent().getExtras();
		portfolioName = bundle.getString(key1);
		stockSymbol = bundle.getString(key2);

		// Set the portfolio name as title and stock symbol as subtitle

		TextView title = (TextView) findViewById(R.id.portfolioName);
		title.setText(portfolioName);

		TextView subTitle = (TextView) findViewById(R.id.stockSymbol);
		subTitle.setText(stockSymbol);

		// Fill the stock fields with real data

		portfolioManager = new PortfolioManager(this);
		Stock stock = portfolioManager.getSingleStock(portfolioName, stockSymbol);
		fillStockFields(stock);

		// Handle the buttons

		btnEdit = (Button) findViewById(R.id.edit);
		btnEdit.setOnClickListener(this);
		
		btnDelete = (Button) findViewById(R.id.delete);
		btnDelete.setOnClickListener(this);

		btnGoToMenu = (Button) findViewById(R.id.goToMenu);
		btnGoToMenu.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_stock_details, menu);
		return true;
	}

	public void onClick(View target) {

		if (target == btnEdit) {

			Log.i(TAG, "btnEdit");

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

					portfolioManager.updateStockFromPortfolio(portfolioName, stock);

					String dialogMessage = "The stock has been successfully updated into portfolio "
							+ portfolioName;
					UIUtils.showAlertDialog(this, dialogMessage);

					goToMenu();

				} catch (Exception e) {

					String exceptionMessage = e.getMessage();
					UIUtils.showAlertDialog(this, exceptionMessage);
				}

			} else {

				String dialogMessage = "Please complete all the stocks fields.";
				UIUtils.showAlertDialog(this, dialogMessage);
			}
			
		} else if (target == btnDelete) {
			
			Log.i(TAG, "btnDelete");

			EditText inputSymbol = (EditText) findViewById(R.id.symbol);
			String symbol = inputSymbol.getText().toString();
			
			if (!fieldIsEmptyOrNull(symbol)) {
				
				portfolioManager.deleteStockFromPortfolio(portfolioName, symbol);
				
				String dialogMessage = "The stock has been successfully deleted from the portfolio "
						+ portfolioName;
				UIUtils.showAlertDialog(this, dialogMessage);
				
				goToMenu();
			}

		} else if (target == btnGoToMenu) {

			Log.i(TAG, "btnGoToMainMenu");
			
			goToMenu();
		}
	}
}
