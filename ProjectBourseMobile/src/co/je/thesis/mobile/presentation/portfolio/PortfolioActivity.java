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
import co.je.thesis.mobile.R;

/**
 * This class extends an Android Activity. This activity provides a menu for the different portfolio
 * related functionalities.
 * 
 * @author Julian Espinel
 */
public class PortfolioActivity extends Activity implements OnClickListener {

	public static final String TAG = "PortfolioActivity";

	private Button btnCreatePortfolio;
	private Button btnViewPortfolio;
	private Button btnDeletePortfolio;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_portfolio);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		btnCreatePortfolio = (Button) findViewById(R.id.createPortfolio);
		btnViewPortfolio = (Button) findViewById(R.id.viewPortfolio);
		btnDeletePortfolio = (Button) findViewById(R.id.deletePortfolio);

		btnCreatePortfolio.setOnClickListener(this);
		btnViewPortfolio.setOnClickListener(this);
		btnDeletePortfolio.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_portfolio, menu);
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
			Intent createPortfolioIntent = new Intent(this, CreatePortfolioActivity.class);
			startActivity(createPortfolioIntent);
			this.finish();

		} else if (target == btnViewPortfolio) {

			Log.i(TAG, "btnViewPortfolio");
			Intent viewPortfolioIntent = new Intent(this, ViewPortfolioActivity.class);
			startActivity(viewPortfolioIntent);
			this.finish();

		} else if (target == btnDeletePortfolio) {

			Log.i(TAG, "btnDeletePortfolio");
			Intent deletePortfolioIntent = new Intent(this, DeletePortfolioActivity.class);
			startActivity(deletePortfolioIntent);
			this.finish();
		}
	}
}