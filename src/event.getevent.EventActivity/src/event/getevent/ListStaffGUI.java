package event.getevent;

import eventModel.Model;
import eventModel.Staff;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ListStaffGUI extends Activity {

	private ListView listStaff;
	private Model model = Model.getModel();
	private Button addManualy;
	private Button addFromServer;
	private Button back;
	private EditText inputSearch;
	ArrayAdapter<Staff> adapter;

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_staff_list);

		inputSearch = (EditText) findViewById(R.id.staffList_inputSearch);
		listStaff = (ListView) findViewById(R.id.staffList_list);
		addManualy = (Button) findViewById(R.id.staffList_addManually);
		addFromServer = (Button) findViewById(R.id.staffList_addFromServer);
		back = (Button) findViewById(R.id.staffList_back);

		adapter = new ArrayAdapter<Staff>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, model
						.getCurrentEvent().getStaffSet());

		listStaff.setAdapter(adapter);

		listStaff.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Staff s = (Staff) listStaff.getSelectedItem();
				model.getCurrentTask().addTeamMember(s);
				Intent myIntent = new Intent(v.getContext(), EventTabHost.class);
				startActivityForResult(myIntent, 0);
			}
		});

		addManualy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), AddStaffGUI.class);
				startActivityForResult(myIntent, 0);
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), EventTabHost.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		addFromServer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), ListStaffServerGUI.class);
				startActivityForResult(myIntent, 0);
			}
		});
		
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				ListStaffGUI.this.adapter.getFilter().filter(cs);
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

}
