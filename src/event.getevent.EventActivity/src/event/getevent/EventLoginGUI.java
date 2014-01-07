package event.getevent;

import java.io.FileNotFoundException;

import SQLite.OfflineDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import eventModel.Model;

public class EventLoginGUI extends Activity{
	private EditText eventIdText;
	private EditText eventPasswordText;
	private Button backButt;
	private Button getButt;
	private Model model = Model.getModel();
	OfflineDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_login);

		eventIdText = (EditText) findViewById(R.id.eventId);
		eventPasswordText = (EditText) findViewById(R.id.eventPassword);
		getButt = (Button) findViewById(R.id.btnGet);
		backButt = (Button) findViewById(R.id.btnBack);
		db = new OfflineDatabase(this);
		
		
		getButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				getEve(v);
				
				
			}
		});
		
		backButt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("register","start");
				Intent myIntent = new Intent(v.getContext(),
						EventGUI.class);
				startActivityForResult(myIntent, 0);
			}
		});

	}

	
	private void getEve(View v){
		int id = Integer.parseInt(eventIdText.getText().toString().trim());
		if(model.isEvent(id, eventPasswordText.getText().toString().trim())){
			model.download(id);
			try {
				model.loadFile(id);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Toast.makeText(this, "Get event successfully", Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(v.getContext(),
					EventGUI.class);
			startActivityForResult(myIntent, Toast.LENGTH_SHORT);
			
		}else{
			Toast.makeText(this, "Get event fail", Toast.LENGTH_LONG).show();
			eventIdText.setText("");
			eventPasswordText.setText("");
		}
	}
	



}
