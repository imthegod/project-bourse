package co.je.thesis.mobile.presentation.analysis;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import co.je.thesis.common.dtos.analysis.AnalysisResultsStorageDTO;
import co.je.thesis.common.dtos.stocks.BaseStock;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.communication.analysis.AnalysisServicesConsumer;
import co.je.thesis.mobile.logic.analysisManager.AnalysisManager;
import co.je.thesis.mobile.logic.portfolioManager.PortfolioManager;
import co.je.thesis.mobile.presentation.dialogs.CreateDialogActivity;
import co.je.thesis.mobile.presentation.dialogs.CreatePortfolioDialog;
import co.je.thesis.mobile.presentation.dialogs.SelectPortfolioDialog;
import co.je.thesis.mobile.presentation.portfolio.AddSpecificStockActivity;
import co.je.thesis.mobile.presentation.portfolio.EditStockActivity;

public class ShowAnalysisResults extends Activity implements OnItemClickListener,
CreateDialogActivity {

	private ArrayAdapter<String> baseStocksArrayAdapter;

	private String selectedStockSymbol;
	private String selectedStockName;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_show_analysis_results);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		String key = "uuid";
		Bundle bundle = getIntent().getExtras();
		String uuidFromLastActivity = bundle.getString(key);

		AnalysisManager analysisManager = new AnalysisManager(this);
		String ownerUserName = analysisManager.getUserName();

		AnalysisServicesConsumer analysisServicesConsumer = new AnalysisServicesConsumer();
		AnalysisResultsStorageDTO analysisResultsStorageDTO = analysisServicesConsumer
				.getAnalysisResults(ownerUserName, uuidFromLastActivity);

		ArrayList<BaseStock> resultStocks = analysisResultsStorageDTO.getResultStocks();

		int numberOfResults = resultStocks.size();
		String message = "There are " + numberOfResults + " results";
		TextView messageTextView = (TextView) findViewById(R.id.textViewAnalysisResultsMessage);
		messageTextView.setText(message);
		messageTextView.setGravity(Gravity.CENTER_HORIZONTAL);

		ArrayList<String> baseStocksToStringArray = getStockResultsToStringArray(resultStocks);
		ArrayList<String> sortedResults = getSortedArrayList(baseStocksToStringArray);

		baseStocksArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,
				sortedResults);

		ListView stocksListView = (ListView) findViewById(R.id.listViewAnalysisResults);
		stocksListView.setAdapter(baseStocksArrayAdapter);
		stocksListView.setOnItemClickListener(this);

		selectedStockSymbol = "";
	}

	private ArrayList<String> getStockResultsToStringArray(ArrayList<BaseStock> resultStocks) {

		ArrayList<String> baseStocksToStringArray = new ArrayList<String>();

		for (int i = 0; i < resultStocks.size(); i++) {

			BaseStock baseStock = resultStocks.get(i);
			String baseStockString = baseStock.toString();
			baseStocksToStringArray.add(baseStockString);
		}

		return baseStocksToStringArray;
	}

	private ArrayList<String> getSortedArrayList(ArrayList<String> baseStocksToStringArray) {

		String[] tempArray = new String[baseStocksToStringArray.size()];
		String[] array = baseStocksToStringArray.toArray(tempArray);
		Arrays.sort(array);

		ArrayList<String> sortedArrayList = new ArrayList<String>();

		for (int i = 0; i < array.length; i++) {

			String stock = array[i];
			sortedArrayList.add(stock);
		}

		return sortedArrayList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_show_analysis_results, menu);
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

		if (position >= 0) {

			String selectedStockString = baseStocksArrayAdapter.getItem(position);
			String[] splitArray = selectedStockString.split(BaseStock.SEPARATOR);
			selectedStockSymbol = splitArray[0];
			selectedStockName = splitArray[1];

			PortfolioManager portfolioManager = new PortfolioManager(this);
			ArrayList<String> portfolioNames = new ArrayList<String>();

			if (portfolioManager.investorHasThisStock(selectedStockSymbol)) {

				// Edit or Delete from portfolio
				// Shows the portfolios where this stock is in, and the possibility of create a
				// new portfolio.
				
				portfolioNames = portfolioManager.getPortfolioNamesWhereThisStockIsPresent(selectedStockSymbol);

			} else {

				// Add to an existing portfolio or create a new one
				// Shows all the portfolios available, and the possibility of create a
				// new portfolio.
				
				portfolioNames = portfolioManager.getAllPortfolioNames();
			}
			
			SelectPortfolioDialog selectPortfolioDialog = new SelectPortfolioDialog(portfolioNames);
			selectPortfolioDialog.show(getFragmentManager(), "SelectPortfolioDialog");
		}
	}

	private void goToEditStockActivity(String portfolioName, String stockSymbol) {

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

	private void goToAddSpecificStockActivity(String portfolioName, String stockSymbol,
			String stockName) {

		Bundle bundle = new Bundle();

		String key1 = "portfolioName";
		bundle.putString(key1, portfolioName);

		String key2 = "stockSymbol";
		bundle.putString(key2, stockSymbol);

		String key3 = "stockName";
		bundle.putString(key3, stockName);

		Intent addSpecificStockActivityIntent = new Intent(this, AddSpecificStockActivity.class);
		addSpecificStockActivityIntent.putExtras(bundle);
		startActivity(addSpecificStockActivityIntent);
		this.finish();
	}

	public void setDialogAnswer(String dialogAnswer) {

		System.out.println("ShowAnalysisResults: setDialogAnswer(): " + dialogAnswer);

		if (dialogAnswer != null) {

			if (dialogAnswer.equalsIgnoreCase(SelectPortfolioDialog.NEW_PORTFOLIO_ITEM)) {

				CreatePortfolioDialog createPortfolioDialog = new CreatePortfolioDialog();
				createPortfolioDialog.show(getFragmentManager(), "CreatePortfolioDialog");

			} else {

				if (selectedStockSymbol != null) {

					if (!dialogAnswer.isEmpty() && !selectedStockSymbol.isEmpty()) {

						PortfolioManager portfolioManager = new PortfolioManager(this);

						if (portfolioManager.portfolioAlreadyExists(dialogAnswer)) {
							
							if (portfolioManager.isStockInPortfolio(dialogAnswer, selectedStockSymbol)) {
								
								goToEditStockActivity(dialogAnswer, selectedStockSymbol);
								
							} else {
								
								goToAddSpecificStockActivity(dialogAnswer, selectedStockSymbol, selectedStockName);
							}

						} else {

							if (selectedStockName != null) {

								if (!selectedStockName.isEmpty()) {

									// Creates the new portfolio and adds the specific stock
									portfolioManager.createPortfolio(dialogAnswer);
									goToAddSpecificStockActivity(dialogAnswer, selectedStockSymbol,
											selectedStockName);
								}
							}
						}
					}
				}
			}
		}
	}
}