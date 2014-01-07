package event.getevent;

import eventModel.Model;
import eventModel.Staff;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ContactListGUI extends Activity {

	private ListView listStaff;
	private Model model;
	private AlertDialog.Builder phoneCall;
	private ArrayAdapter<Staff> adapters;
	private EditText inputSearch;


	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_list);
		
		Log.e("contact list", "start");
		
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		listStaff = (ListView) findViewById(R.id.list_view);
		phoneCall = new AlertDialog.Builder(this);
		phoneCall.setTitle("Phone Call");
		phoneCall.setMessage("Do you want to make phone call?");
		model = Model.getModel();

		Log.e("model event", model.getCurrentEvent()+"");

		adapters = new ArrayAdapter<Staff>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, model
						.getCurrentEvent().getStaffSet());
		

		listStaff.setAdapter(adapters);
		
		
		listStaff.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				phoneCall.show();
			}
		});
		
		phoneCall.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dio, int arg1) {
						// TODO Auto-generated method stub
						Staff staff = (Staff) listStaff.getSelectedItem();
						
						call(staff.getPhoneNumber());
						 dio.dismiss();
					}

				});
		
		phoneCall.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dio, int arg1) {
						// TODO Auto-generated method stub
						 dio.dismiss();
					}

				});
		
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				ContactListGUI.this.adapters.getFilter().filter(cs);
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
	
	private void call(String number){
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
		Log.d("phone call to ", Uri.parse(number)+"");
        startActivity(callIntent);
	}

}
