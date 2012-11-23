package co.je.thesis.mobile.presentation.analysis;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import co.je.thesis.mobile.R;
import co.je.thesis.mobile.logic.analysisManager.AnalysisManager;

public class ShowPendingAnalysis extends Activity implements OnItemClickListener {

	private ArrayAdapter<String> pendingAnalysisArrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_pending_analysis);

		AnalysisManager analysisManager = new AnalysisManager(this);

		ArrayList<String> pendingAnalysisDates = analysisManager.getAllPendingAnalysisDates();
		ArrayList<String> sortedArrayList = getSortedArrayList(pendingAnalysisDates);
		
		pendingAnalysisArrayAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,
				sortedArrayList);

		ListView listView = (ListView) findViewById(R.id.listViewPendingAnalysis);
		listView.setOnItemClickListener(this);
		listView.setAdapter(pendingAnalysisArrayAdapter);
	}

	private ArrayList<String> getSortedArrayList(ArrayList<String> pendingAnalysisDates) {
		
		String[] tempArray = new String[pendingAnalysisDates.size()];
		String[] array = pendingAnalysisDates.toArray(tempArray);
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
		
		getMenuInflater().inflate(R.menu.activity_show_pending_analysis, menu);
		return true;
	}

	private void sendToGetAnalysisResultsAntivity(String uuid) {

		Bundle bundle = new Bundle();
		String key = "uuid";
		bundle.putString(key, uuid);

		Intent showAnalysisResultsIntent = new Intent(this, ShowAnalysisResults.class);
		showAnalysisResultsIntent.putExtras(bundle);
		startActivity(showAnalysisResultsIntent);
		this.finish();
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (position >= 0) {

			AnalysisManager analysisManager = new AnalysisManager(this);

			String pendingAnalysisDate = pendingAnalysisArrayAdapter.getItem(position);
			String uuid = analysisManager.getUuidByDate(pendingAnalysisDate);
			analysisManager.deletePendingAnalysis(uuid);
			
			sendToGetAnalysisResultsAntivity(uuid);
		}
	}
}