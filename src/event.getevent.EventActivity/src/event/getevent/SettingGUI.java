package event.getevent;

import java.io.File;
import java.io.FileNotFoundException;

import eventModel.AppStatus;
import eventModel.Model;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SettingGUI extends Activity {

	private Model model;
	private Button uploadEventButt;
	private Button updateEventButt;
	private Button backButt;
	private Button uploadStaffButt;
	private AppStatus app;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		app = AppStatus.getInstance(this);
		model = Model.getModel();
		updateEventButt = (Button) findViewById(R.id.update_event_button);
		uploadEventButt = (Button) findViewById(R.id.upload_event_button);
		backButt = (Button) findViewById(R.id.back_event_list_button);
		uploadStaffButt = (Button) findViewById(R.id.upload_staff_button);
		
		if(model.getCurrentLoginName().equalsIgnoreCase(model.getCurrentEvent().getCreator())){
				updateEventButt.setEnabled(false);
		}else{
			uploadEventButt.setEnabled(false);
			uploadStaffButt.setEnabled(false);
		}
		
		if(!app.isOnline()){
			updateEventButt.setEnabled(false);
			uploadEventButt.setEnabled(false);
			uploadStaffButt.setEnabled(false);
		}

		uploadEventButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uploadEvent();
			}
		});

		updateEventButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateEvent(v);
			}
		});

		uploadStaffButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uploadStaff();
			}
		});

		backButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back(v);
			}
		});

	}

	private void uploadEvent() {
		model.getCurrentEvent().uploadEvent();
		Toast.makeText(this, "Your event id: " +model.getCurrentEvent().getId(), Toast.LENGTH_SHORT).show();
	}

	private void updateEvent(View v) {
		int id=model.getCurrentEvent().getId();
		if(model.getCurrentEvent().isThereNewVersion()){
			model.download(id);
			model.removeEvent(model.getCurrentEvent());
			
			try {
				model.loadFile(id);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			back(v);
		}else{
			Toast.makeText(this, "This event is a newest version", Toast.LENGTH_SHORT).show();
		}
	}

	private void uploadStaff() {
		model.getCurrentEvent().uploadStaffs();
		Toast.makeText(this, "upload staffs sucessfully", Toast.LENGTH_SHORT).show();
	}

	private void back(View v) {
		Intent myIntent = new Intent(v.getContext(), EventGUI.class);
		startActivityForResult(myIntent, Toast.LENGTH_SHORT);
	}
	
	

}
