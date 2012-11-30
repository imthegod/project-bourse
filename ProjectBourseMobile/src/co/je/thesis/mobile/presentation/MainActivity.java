package co.je.thesis.mobile.presentation;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.communication.utils.TestServerIsAliveAsyncTask;
import co.je.thesis.mobile.logic.stockController.AlarmBroadcastReceiver;
import co.je.thesis.mobile.persistence.userPersistence.UserPersistence;
import co.je.thesis.mobile.presentation.analysis.AnalysisActivity;
import co.je.thesis.mobile.presentation.dialogs.ICreateDialogActivity;
import co.je.thesis.mobile.presentation.dialogs.GetUserNameDialog;
import co.je.thesis.mobile.presentation.portfolio.PortfolioActivity;
import co.je.thesis.mobile.presentation.statistics.SelectPortfolioActivity;

public class MainActivity extends Activity implements OnClickListener, ICreateDialogActivity {

	public static final String TAG = "MainActivity";

	private Button btnPortfolio;
	private Button btnAnalysis;
	private Button btnStatistics;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnPortfolio = (Button) findViewById(R.id.portfolio);
		btnAnalysis = (Button) findViewById(R.id.analysis);
		btnStatistics = (Button) findViewById(R.id.statistics);

		btnPortfolio.setOnClickListener(this);
		btnAnalysis.setOnClickListener(this);
		btnStatistics.setOnClickListener(this);

		if (hasInternetConnection()) {

			System.out.println("hasInternetConnection()");
			
			setAlarm();
			
			if (!isServerAlive()) {

				setUIForServerDown();
			}

		} else {
			
			setUIForNoInternetConnection();
		}
	}

	private void setUIForNoInternetConnection() {
		
		// If doesn't have Internet access, or the server is down. we can not send
		// new analysis requests or view updated statistics

		btnAnalysis.setEnabled(false);
		btnStatistics.setEnabled(false);
		
		String dialogMessage = "Analysis and Statistics are not available because the device does not have internet access";
		UIUtils.showAlertDialog(this, dialogMessage);
	}
	
	private void setUIForServerDown() {
		
		btnAnalysis.setEnabled(false);
		
		String dialogMessage = "Analysis is not available because the server is down";
		UIUtils.showAlertDialog(this, dialogMessage);
	}

	private void setAlarm() {

		System.out.println("MainActivity: setAlarm()");

		Context context = this;

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent alarmReceiverIntent = new Intent(context, AlarmBroadcastReceiver.class);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmReceiverIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		int oneSecond = 1000;
		int oneMinute = (oneSecond * 60);
		int oneHour = oneMinute * 60;

		int alarmType = AlarmManager.ELAPSED_REALTIME;
		long timeToGoOff = (oneSecond * 75);
		long intervalRepetitions = (timeToGoOff + 5000);

		alarmManager.setInexactRepeating(alarmType, timeToGoOff, intervalRepetitions, pendingIntent);
	}

	private boolean hasInternetConnection() {

		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork.isConnectedOrConnecting();

		return isConnected;
	}

	private boolean isServerAlive() {

		TestServerIsAliveAsyncTask testServerIsAliveAsyncTask = new TestServerIsAliveAsyncTask();

		String[] emptyParams = {};
		testServerIsAliveAsyncTask.execute(emptyParams);
		boolean isAlive = false;

		try {

			isAlive = testServerIsAliveAsyncTask.get();

		} catch (InterruptedException e) {

			e.printStackTrace();

		} catch (ExecutionException e) {

			e.printStackTrace();
		}

		return isAlive;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View target) {

		if (target == btnPortfolio) {

			Log.i(TAG, "btnPortfolio");
			Intent portfolioIntent = new Intent(this, PortfolioActivity.class);
			startActivity(portfolioIntent);

		} else if (target == btnAnalysis) {

			Log.i(TAG, "btnAnalysis");

			UserPersistence userPersistence = new UserPersistence(this);

			if (!userPersistence.userNameHasBeenSet()) {

				GetUserNameDialog getUserNameDialog = new GetUserNameDialog();
				getUserNameDialog.show(getFragmentManager(), "GetUserNameDialog");

			} else {

				Intent analysisIntent = new Intent(this, AnalysisActivity.class);
				startActivity(analysisIntent);
			}

		} else if (target == btnStatistics) {

			Log.i(TAG, "btnStatistics");

			Intent selectPortfolioActivityIntent = new Intent(this, SelectPortfolioActivity.class);
			startActivity(selectPortfolioActivityIntent);
		}
	}

	public void setDialogAnswer(String dialogAnswer) {

		if (dialogAnswer != null) {
			
			if (!dialogAnswer.isEmpty()) {
				
				UserPersistence userPersistence = new UserPersistence(this);
				userPersistence.setUserName(dialogAnswer);

				Intent analysisIntent = new Intent(this, AnalysisActivity.class);
				startActivity(analysisIntent);
			}
		}
	}
}