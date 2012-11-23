package co.je.thesis.mobile.presentation.portfolio;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.logic.businessObjects.Stock;
import co.je.thesis.mobile.logic.portfolioManager.PortfolioManager;
import co.je.thesis.mobile.presentation.MainActivity;
import co.je.thesis.mobile.presentation.UIUtils;

public class PortfolioAndStocksActivity extends Activity implements OnClickListener, OnItemClickListener {

	public static final String TAG = "PortfolioAndStocksActivity";

	private PortfolioManager portfolioManager;
	
	private String portfolioName;

	// UI array adapter

	private ArrayAdapter<String> symbolArrayAdapter;
	private ArrayAdapter<String> numberOfSharesArrayAdapter;
	private ArrayAdapter<String> basePriceArrayAdapter;

	// UI Buttons

	private Button btnAddStock;
	private Button btnGoToMenu;

	private void initializeSymbolArrayAdapter(ArrayList<Stock> portfolioStocks) {

		ArrayList<String> stockSymbols = new ArrayList<String>();

		for (int i = 0; i < portfolioStocks.size(); i++) {

			Stock stock = portfolioStocks.get(i);
			String symbol = stock.getSymbol();
			stockSymbols.add(symbol);
		}

		symbolArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,
				stockSymbols);
	}

	private void initializeNumberOfSharesArrayAdapter(ArrayList<Stock> portfolioStocks) {

		ArrayList<String> stockNumberOfShares = new ArrayList<String>();

		for (int i = 0; i < portfolioStocks.size(); i++) {

			Stock stock = portfolioStocks.get(i);
			String numberOfShares = stock.getNumberOfShares() + "";
			stockNumberOfShares.add(numberOfShares);
		}

		numberOfSharesArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_list_item, stockNumberOfShares);
	}

	private void initializeBasePriceArrayAdapter(ArrayList<Stock> portfolioStocks) {

		ArrayList<String> stockBasePrice = new ArrayList<String>();

		for (int i = 0; i < portfolioStocks.size(); i++) {

			Stock stock = portfolioStocks.get(i);
			String basePrice = stock.getBasePrice() + "";
			stockBasePrice.add(basePrice);
		}

		basePriceArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,
				stockBasePrice);
	}

	private void initializeArrayAdapters(ArrayList<Stock> portfolioStocks) {

		initializeSymbolArrayAdapter(portfolioStocks);
		initializeNumberOfSharesArrayAdapter(portfolioStocks);
		initializeBasePriceArrayAdapter(portfolioStocks);
	}
	
	private void goToEditStockActivity(String stockSymbol) {
		
		Bundle bundle = new Bundle();
		
		String key1 = "portfolioName";
		bundle.putString(key1, portfolioName);
		
		String key2 = "selectedStockSymbol";
		bundle.putString(key2, stockSymbol);
		
		Intent stockDetalisActivityIntent = new Intent(this, EditStockActivity.class);
		stockDetalisActivityIntent.putExtras(bundle);
		startActivity(stockDetalisActivityIntent);
		this.finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_portfolio_and_stocks);

		String key = "selectedPortfolioName";
		Bundle bundle = getIntent().getExtras();
		portfolioName = bundle.getString(key);

		// Set the portfolio names as title

		TextView title = (TextView) findViewById(R.id.portfolioName);
		title.setText(portfolioName);

		portfolioManager = new PortfolioManager(this);
		
		ArrayList<Stock> portfolioStocks = portfolioManager
				.getMultipleStocks(portfolioName);
		initializeArrayAdapters(portfolioStocks);

		// Stocks symbols

		ListView symbolListView = (ListView) findViewById(R.id.symbolListView);
		symbolListView.setOnItemClickListener(this);
		
		TextView symbolsHeader = new TextView(this);
		symbolsHeader.setText("Symbol");
		UIUtils.configureTextViewHeader(symbolsHeader);
		symbolListView.addHeaderView(symbolsHeader);
		symbolListView.setAdapter(symbolArrayAdapter);

		// Stocks number of shares

		ListView numberOfSharesListView = (ListView) findViewById(R.id.numberOfSharesListView);
		numberOfSharesListView.setOnItemClickListener(this);
		
		TextView numberOfSharesHeader = new TextView(this);
		numberOfSharesHeader.setText("Number of shares");
		UIUtils.configureTextViewHeader(numberOfSharesHeader);
		numberOfSharesListView.addHeaderView(numberOfSharesHeader);
		numberOfSharesListView.setAdapter(numberOfSharesArrayAdapter);

		// Stocks base price

		ListView basePriceListView = (ListView) findViewById(R.id.basePriceListView);
		basePriceListView.setOnItemClickListener(this);
		
		TextView basePriceHeader = new TextView(this);
		basePriceHeader.setText("Base price");
		UIUtils.configureTextViewHeader(basePriceHeader);
		basePriceListView.addHeaderView(basePriceHeader);
		basePriceListView.setAdapter(basePriceArrayAdapter);

		// Buttons

		btnAddStock = (Button) findViewById(R.id.addStock);
		btnAddStock.setOnClickListener(this);
		
		btnGoToMenu = (Button) findViewById(R.id.goToMenu);
		btnGoToMenu.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_portfolio_and_stocks, menu);
		return true;
	}

	public void onClick(View target) {
		
		if (target == btnAddStock) {
			
			Log.i(TAG, "btnAddStock");
			
			Bundle bundle = new Bundle();
			String key = "portfolioName";
			bundle.putString(key, portfolioName);
			Intent addStockActivityIntent = new Intent(this, AddStockActivity.class);
			addStockActivityIntent.putExtras(bundle);
			startActivity(addStockActivityIntent);
			this.finish();
			
		} else if (target == btnGoToMenu) {
			
			Log.i(TAG, "btnToMainMenu");
			
			Intent mainActivityIntent = new Intent(this, MainActivity.class);
			startActivity(mainActivityIntent);
			this.finish();
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		if (position != 0) {

			/*
			 * Corrects the position because header is position = 0 in
			 * the listView but items into the arrayAdapters begin at 0.
			 */
			position -= 1;
			String stockSymbol = symbolArrayAdapter.getItem(position);
			goToEditStockActivity(stockSymbol);
		}
	}
}