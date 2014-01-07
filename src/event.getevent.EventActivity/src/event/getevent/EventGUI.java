package event.getevent;

import eventModel.AppStatus;
import eventModel.Event;
import eventModel.Model;
import SQLite.OfflineDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EventGUI extends Activity {

	private Button newButt;
	private Button logoutButt;
	private Button getEventButt;
	private ListView listEvent;
	private Model model;
	private AppStatus app = AppStatus.getInstance(this);
	private EditText inputSearch;
	private ArrayAdapter<Event> adapter;
	private OfflineDatabase db;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);

		db = new OfflineDatabase(this);
		model = Model.getModel();
		newButt = (Button) findViewById(R.id.eventDetail_newevent_button);
		logoutButt = (Button) findViewById(R.id.eventDetail_logout_button);
		listEvent = (ListView) findViewById(R.id.eventList_list);
		getEventButt = (Button) findViewById(R.id.eventDetail_get_event_button);
		inputSearch = (EditText) findViewById(R.id.eventList_inputSearch);

		if (!app.isOnline()) {
			getEventButt.setEnabled(false);
			newButt.setEnabled(false);
		}

		adapter = new ArrayAdapter<Event>(this,
				android.R.layout.simple_list_item_2, android.R.id.text1,
				model.getEventSet());
		listEvent.setAdapter(adapter);

		getEventButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				getEvent(v);
			}
		});

		newButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newEvent(v);
			}
		});

		logoutButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logOut(v);
			}
		});

		listEvent.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Log.i("delte position2", model.getEventSet().get(position).getId()+"");
//				db.delete(model.getCurrentLoginName(), model.getEventSet().get(position).getId());

				model.removeEvent(model.getEventSet().get(position));
				Log.i("delte event size", model.getEventSet().size()+"");
				Intent myIntent = new Intent(v.getContext(), EventGUI.class);
				startActivityForResult(myIntent, 0);
				return true;
			}
		});

		listEvent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {

				model.setCurrentEvent(position);
				Log.i("position", position + "");
				Intent myIntent = new Intent(v.getContext(), EventTabHost.class);
				startActivityForResult(myIntent, 0);
			}
		});

		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				EventGUI.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void newEvent(View v) {
		Intent myIntent = new Intent(v.getContext(), NewEventGUI.class);
		startActivityForResult(myIntent, 0);
	}

	private void getEvent(View v) {
		Intent myIntent = new Intent(v.getContext(), EventLoginGUI.class);
		startActivityForResult(myIntent, 0);
	}

	private void logOut(View v) {
		Log.e("before remove all event", model.getEventSet().size()+"");
		for (int i = 0; i < model.getEventSet().size(); i++) {
			if (model.getEventSet().get(i).getId() >= 0) {
				db.setEventFile(model.getCurrentLoginName(), model
						.getEventSet().get(i).getId());
			}
		}
		Log.e("Before remove all event", model.getEventSet().size()+"");

		model.removeAllEvent();
		
	
		Log.e("After remove all event", model.getEventSet().size()+"");
		Intent myIntent = new Intent(v.getContext(), EventActivity.class);
		startActivityForResult(myIntent, 0);
		
	}

}
