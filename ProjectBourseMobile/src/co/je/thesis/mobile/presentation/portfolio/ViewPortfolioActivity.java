package co.je.thesis.mobile.presentation.portfolio;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.logic.businessObjects.Portfolio;
import co.je.thesis.mobile.logic.portfolioManager.PortfolioManager;
import co.je.thesis.mobile.presentation.UIUtils;

/**
 * This class extends an Android Activity. This activity supports the following functionality: 
 * Show general information of the investor's portfolios.
 * 
 * @author Julian Espinel
 */
public class ViewPortfolioActivity extends Activity implements OnItemClickListener {

	public static final String TAG = "ViewPortfolioActivity";

	private PortfolioManager portfolioManager;

	// UI array adapter

	private ArrayAdapter<String> namesArrayAdapter;
	private ArrayAdapter<String> numberOfStockssArrayAdapter;

	private void initializePortfoliosNamesArrayAdapter(ArrayList<Portfolio> portfolios) {

		ArrayList<String> portfoliosName = new ArrayList<String>();

		for (int i = 0; i < portfolios.size(); i++) {

			Portfolio portfolio = portfolios.get(i);
			String portfolioName = portfolio.getName();
			portfoliosName.add(portfolioName);
		}

		namesArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,
				portfoliosName);
	}

	private void initializeNumberOfStocksArrayAdapter(ArrayList<Portfolio> portfolios) {

		ArrayList<String> portfoliosName = new ArrayList<String>();

		for (int i = 0; i < portfolios.size(); i++) {

			Portfolio portfolio = portfolios.get(i);
			String portfolioNumberOfStocks = portfolio.getNumberOfStocks() + "";
			portfoliosName.add(portfolioNumberOfStocks);
		}

		numberOfStockssArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,
				portfoliosName);
	}

	private void initializeArrayAdapters(ArrayList<Portfolio> portfolios) {

		initializePortfoliosNamesArrayAdapter(portfolios);
		initializeNumberOfStocksArrayAdapter(portfolios);
	}

	private void goToPortfolioAndStocksActivity(String portfolioName) {
		
		Bundle bundle = new Bundle();
		String key = "selectedPortfolioName";
		bundle.putString(key, portfolioName);
		Intent portfolioAndStocksActivityIntent = new Intent(this, PortfolioAndStocksActivity.class);
		portfolioAndStocksActivityIntent.putExtras(bundle);
		startActivity(portfolioAndStocksActivityIntent);
		this.finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_portfolio);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		portfolioManager = new PortfolioManager(this);
		ArrayList<Portfolio> portfolios = portfolioManager.getAllPortfolios();
		initializeArrayAdapters(portfolios);

		// Portfolios names listview
		
		ListView portfolioNameListView = (ListView) findViewById(R.id.portfolioNamesListView);
		portfolioNameListView.setOnItemClickListener(this);
		
		TextView namesHeader = new TextView(this);
		namesHeader.setText("Portfolio name");
		UIUtils.configureTextViewHeader(namesHeader);
		portfolioNameListView.addHeaderView(namesHeader);
		portfolioNameListView.setAdapter(namesArrayAdapter);

		// Portfolios number of stocks listview
		
		ListView numberOfStocksListView = (ListView) findViewById(R.id.portfolioNumberOfStocksListView);
		numberOfStocksListView.setOnItemClickListener(this);
		
		TextView stocksHeader = new TextView(this);
		stocksHeader.setText("Number of stocks");
		UIUtils.configureTextViewHeader(stocksHeader);
		numberOfStocksListView.addHeaderView(stocksHeader);
		numberOfStocksListView.setAdapter(numberOfStockssArrayAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_view_portfolio, menu);
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

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		if (position != 0) {

			/*
			 * Corrects the position because header is position = 0 in
			 * the listView but items into the arrayAdapters begin at 0.
			 */
			position -= 1;
			String portfolioName = namesArrayAdapter.getItem(position);
			goToPortfolioAndStocksActivity(portfolioName);
		}
	}
}