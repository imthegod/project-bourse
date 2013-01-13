package co.je.thesis.mobile.presentation.portfolio;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.TypedValue;
import android.view.Gravity;
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
 * Delete a portfolio.
 * 
 * @author Julian Espinel
 */
public class DeletePortfolioActivity extends Activity {

	public static final String TAG = "DeletePortfolioActivity";

	private PortfolioManager portfolioManager;

	// UI array adapter
	
	private ArrayAdapter<String> namesArrayAdapter;
	private ArrayAdapter<String> stocksArrayAdapter;

	// UI List view
	
	private ListView portfolioNameListView;
	private ListView portfolioNumberOfStocksListView;

	private ArrayAdapter<String> buildPortfoliosNamesArrayAdapter(
			ArrayList<Portfolio> portfolios) {

		ArrayList<String> portfoliosName = new ArrayList<String>();

		for (int i = 0; i < portfolios.size(); i++) {

			Portfolio portfolio = portfolios.get(i);
			String portfolioName = portfolio.getName();
			portfoliosName.add(portfolioName);
		}

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_list_item, portfoliosName);
		return arrayAdapter;
	}

	private ArrayAdapter<String> buildPortfoliosNumberOfStocksArrayAdapter(
			ArrayList<Portfolio> portfolios) {

		ArrayList<String> portfoliosName = new ArrayList<String>();

		for (int i = 0; i < portfolios.size(); i++) {

			Portfolio portfolio = portfolios.get(i);
			String portfolioNumberOfStocks = portfolio.getNumberOfStocks() + "";
			portfoliosName.add(portfolioNumberOfStocks);
		}

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				R.layout.simple_list_item, portfoliosName);
		return arrayAdapter;
	}

	private void updateListViews(int selectedPortfolioIndex) {

		String portfolioName = namesArrayAdapter.getItem(selectedPortfolioIndex);
		namesArrayAdapter.remove(portfolioName);
		namesArrayAdapter.notifyDataSetChanged();

		String portfolioNumberOfStocks = stocksArrayAdapter
				.getItem(selectedPortfolioIndex);
		stocksArrayAdapter.remove(portfolioNumberOfStocks);
		stocksArrayAdapter.notifyDataSetChanged();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_portfolio);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		portfolioManager = new PortfolioManager(this);
		ArrayList<Portfolio> portfolios = portfolioManager.getAllPortfolios();
		namesArrayAdapter = buildPortfoliosNamesArrayAdapter(portfolios);
		stocksArrayAdapter = buildPortfoliosNumberOfStocksArrayAdapter(portfolios);

		portfolioNameListView = (ListView) findViewById(R.id.portfolioNamesListView);
		
		TextView namesHeader = new TextView(this);
		namesHeader.setText("Portfolio name");
		namesHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		namesHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		namesHeader.setGravity(Gravity.CENTER_HORIZONTAL);
		portfolioNameListView.addHeaderView(namesHeader);
		portfolioNameListView.setAdapter(namesArrayAdapter);

		portfolioNumberOfStocksListView = (ListView) findViewById(R.id.portfolioNumberOfStocksListView);
		
		TextView stocksHeader = new TextView(this);
		stocksHeader.setText("Number of stocks");
		stocksHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
		stocksHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		stocksHeader.setGravity(Gravity.CENTER_HORIZONTAL);
		portfolioNumberOfStocksListView.addHeaderView(stocksHeader);
		portfolioNumberOfStocksListView.setAdapter(stocksArrayAdapter);

		portfolioNameListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				/* 
				 * Corrects the position because header is position = 0 in the listView
				 * but items into the arrayAdapters begin at 0.
				 */
				position -= 1;
				String portfolioName = namesArrayAdapter.getItem(position);
				portfolioManager.deletePortfolio(portfolioName);
				
				String toastMessage = "The portfolio " + portfolioName
						+ " has been deleted.";
				
				UIUtils.showToast(getApplicationContext(), toastMessage);

				updateListViews(position);
			}
		});
		
		portfolioNumberOfStocksListView.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {

				/* 
				 * Corrects the position because header is position = 0 in the listView
				 * but items into the arrayAdapters begin at 0.
				 */
				position -= 1;
				String portfolioName = namesArrayAdapter.getItem(position);
				portfolioManager.deletePortfolio(portfolioName);
				
				String toastMessage = "The portfolio " + portfolioName
						+ " has been deleted.";
				
				UIUtils.showToast(getApplicationContext(), toastMessage);
				
				updateListViews(position);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_delete_portfolio, menu);
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
}