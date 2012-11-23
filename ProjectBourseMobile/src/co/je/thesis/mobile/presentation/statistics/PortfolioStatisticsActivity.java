package co.je.thesis.mobile.presentation.statistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
import co.je.thesis.mobile.logic.stockController.DataRetriever;
import co.je.thesis.mobile.presentation.MainActivity;
import co.je.thesis.mobile.presentation.UIUtils;
import co.je.thesis.mobile.presentation.portfolio.EditStockActivity;

public class PortfolioStatisticsActivity extends Activity implements OnClickListener, OnItemClickListener {

	public static final String TAG = "PortfolioStatisticsActivity";

	private PortfolioManager portfolioManager;
	
	private String portfolioName;

	// UI array adapter

	private ArrayAdapter<String> symbolArrayAdapter;
	private ArrayAdapter<String> percentageEarningsArrayAdapter;
	private ArrayAdapter<String> netEarningsArrayAdapter;

	// UI Buttons
	
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
	
	private double getStockLastTradePrice(String stockSymbol) {

		double lastTradePice = -1;

		DataRetriever dataRetriever = new DataRetriever();
		try {

			String[] params = { stockSymbol };
			dataRetriever.execute(params);
			lastTradePice = dataRetriever.get();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (ExecutionException e) {

			e.printStackTrace();
		}

		return lastTradePice;
	}
	
	private String getFormattedDouble(double doubleNumber) {
		
		String formatPattern = "0.00";
		DecimalFormat formatter = new DecimalFormat(formatPattern);
		String formattedDouble = formatter.format(doubleNumber);
		
		return formattedDouble;
	}
	
	private double getStockPercentageEarnings(Stock stock) {
		
		String stockSymbol = stock.getSymbol();
		double lastTradePrice = getStockLastTradePrice(stockSymbol);
		
		double numerator = (lastTradePrice * 100);
		double percentgeEarnings = numerator/stock.getBasePrice();
		
		return percentgeEarnings;
	}
	
	private double getStockNetEarnings(Stock stock) {
		
		String stockSymbol = stock.getSymbol();
		double lastTradePrice = getStockLastTradePrice(stockSymbol);
		
		int numberOfShares = stock.getNumberOfShares();
		
		double baseInvestedValue = (numberOfShares * stock.getBasePrice());
		double currentInvestedValue = (numberOfShares * lastTradePrice);
		
		double netEarnings = currentInvestedValue - baseInvestedValue;
		
		return netEarnings;
	}
	
	private void initializePercentageEarningsArrayAdapter(ArrayList<Stock> portfolioStocks) {

		ArrayList<String> stockPercentageEarnings = new ArrayList<String>();

		for (int i = 0; i < portfolioStocks.size(); i++) {

			Stock stock = portfolioStocks.get(i);
			double percentageEarnings = getStockPercentageEarnings(stock);
			String percentageEarningsString = getFormattedDouble(percentageEarnings);
			stockPercentageEarnings.add(percentageEarningsString);
			
		}

		percentageEarningsArrayAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_list_item, stockPercentageEarnings);
	}

	private void initializeNetEarningsArrayAdapter(ArrayList<Stock> portfolioStocks) {

		ArrayList<String> stockNetEarnings = new ArrayList<String>();

		for (int i = 0; i < portfolioStocks.size(); i++) {

			Stock stock = portfolioStocks.get(i);
			double netEarnings = getStockNetEarnings(stock);
			String netEarningsString = getFormattedDouble(netEarnings);
			stockNetEarnings.add(netEarningsString);
		}

		netEarningsArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,
				stockNetEarnings);
	}

	private void initializeArrayAdapters(ArrayList<Stock> portfolioStocks) {

		initializeSymbolArrayAdapter(portfolioStocks);
		initializePercentageEarningsArrayAdapter(portfolioStocks);
		initializeNetEarningsArrayAdapter(portfolioStocks);
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
		setContentView(R.layout.activity_portfolio_statistics);

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

		ListView percentageEarningsListView = (ListView) findViewById(R.id.percentageEarnings);
		percentageEarningsListView.setOnItemClickListener(this);
		
		TextView percentageEarningsHeader = new TextView(this);
		percentageEarningsHeader.setText("Earnings (%)");
		UIUtils.configureTextViewHeader(percentageEarningsHeader);
		percentageEarningsListView.addHeaderView(percentageEarningsHeader);
		percentageEarningsListView.setAdapter(percentageEarningsArrayAdapter);

		// Stocks base price

		ListView netEarningsListView = (ListView) findViewById(R.id.netEarnings);
		netEarningsListView.setOnItemClickListener(this);
		
		TextView netEarningsHeader = new TextView(this);
		netEarningsHeader.setText("Earnings (USD)");
		UIUtils.configureTextViewHeader(netEarningsHeader);
		netEarningsListView.addHeaderView(netEarningsHeader);
		netEarningsListView.setAdapter(netEarningsArrayAdapter);

		// Buttons
		
		btnGoToMenu = (Button) findViewById(R.id.goToMenu);
		btnGoToMenu.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_portfolio_and_stocks, menu);
		return true;
	}

	public void onClick(View target) {
		
		if (target == btnGoToMenu) {
			
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