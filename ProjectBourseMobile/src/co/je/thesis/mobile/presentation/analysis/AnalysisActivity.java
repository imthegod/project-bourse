package co.je.thesis.mobile.presentation.analysis;

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
import co.je.thesis.mobile.logic.analysisManager.AnalysisManager;
import co.je.thesis.mobile.presentation.UIUtils;

public class AnalysisActivity extends Activity implements OnClickListener {

	public static final String TAG = "AnalysisActivity";

	private Button btnCreateAnalysis;
	private Button btnGetAnalysisResults;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_analysis);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		btnCreateAnalysis = (Button) findViewById(R.id.createAnalysis);
		btnCreateAnalysis.setOnClickListener(this);

		btnGetAnalysisResults = (Button) findViewById(R.id.getAnalysisResults);
		btnGetAnalysisResults.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_analysis, menu);
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

		if (target == btnCreateAnalysis) {

			Log.i(TAG, "btnCreateAnalysis");

			Intent createAnalysisIntent = new Intent(this, CreateAnalysisActivity.class);
			startActivity(createAnalysisIntent);
			this.finish();

		} else if (target == btnGetAnalysisResults) {

			Log.i(TAG, "btnGetAnalysisResults");

			AnalysisManager analysisManager = new AnalysisManager(this);
			
			if (analysisManager.existsPengingAnalysis()) {

				Intent showPendingAnalysisIntent = new Intent(this, ShowPendingAnalysis.class);
				startActivity(showPendingAnalysisIntent);
				this.finish();

			} else {

				String dialogMessage = "There are not pending analysis to show its results.";
				UIUtils.showAlertDialog(this, dialogMessage);
			}
		}
	}
}