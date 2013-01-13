package co.je.thesis.mobile.presentation.portfolio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.logic.portfolioManager.PortfolioManager;
import co.je.thesis.mobile.presentation.MainActivity;
import co.je.thesis.mobile.presentation.UIUtils;

/**
 * This class extends an Android Activity. This activity supports the following functionality: 
 * Create a portfolio.
 * 
 * @author Julian Espinel
 */
public class CreatePortfolioActivity extends Activity implements OnClickListener {

	public static final String TAG = "CreatePortfolioActivity";

	private Button btnCreatePortfolio;
	private Button btnAddStock;
	private Button btnToMainMenu;

	private String portfolioName;
	
	private PortfolioManager portfolioManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_create_portfolio);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		btnCreatePortfolio = (Button) findViewById(R.id.createPortfolio);
		btnAddStock = (Button) findViewById(R.id.addStock);
		btnToMainMenu = (Button) findViewById(R.id.goToMenu);

		btnCreatePortfolio.setOnClickListener(this);

		btnAddStock.setOnClickListener(this);
		btnAddStock.setEnabled(false);

		btnToMainMenu.setOnClickListener(this);

		portfolioManager = new PortfolioManager(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_create_portfolio, menu);
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

	public void onClick(View target) {

		if (target == btnCreatePortfolio) {

			Log.i(TAG, "btnCreatePortfolio");

			EditText inputPortfolioName = (EditText) findViewById(R.id.portfolioName);
			portfolioName = inputPortfolioName.getText().toString();

			try {

				portfolioManager.createPortfolio(portfolioName);

				String dialogMessage = "The portfolio has been successfully created";
				UIUtils.showAlertDialog(this, dialogMessage);

				// Set the potrfolio name input and the create button to false,
				// to prevent the user to change the portfolio name.
				inputPortfolioName.setEnabled(false);
				btnCreatePortfolio.setEnabled(false);
				btnAddStock.setEnabled(true);

			} catch (Exception e) {

				String exceptionMessage = e.getMessage();
				UIUtils.showAlertDialog(this, exceptionMessage);
			}

		} else if (target == btnAddStock) {
			
			Log.i(TAG, "btnAddStock");
			
			Bundle bundle = new Bundle();
			String key = "portfolioName";
			bundle.putString(key, portfolioName);
			Intent addStockActivityIntent = new Intent(this, AddStockActivity.class);
			addStockActivityIntent.putExtras(bundle);
			startActivity(addStockActivityIntent);
			this.finish();

		} else if (target == btnToMainMenu) {

			Log.i(TAG, "btnGoToMainMenu");
			
			Intent mainActivityIntent = new Intent(this, MainActivity.class);
			startActivity(mainActivityIntent);
			this.finish();
		}
	}
}