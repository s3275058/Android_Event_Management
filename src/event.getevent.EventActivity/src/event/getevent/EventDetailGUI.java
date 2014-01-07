package event.getevent;

import eventModel.Model;
import eventModel.Task;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;

public class EventDetailGUI extends Activity {

	private Model model;
	private ExpandListAdapter adapter;
	private ExpandableListView listview;
	private AlertDialog.Builder listButton;
	private AlertDialog.Builder listButton2;
	private AlertDialog.Builder listButton3;
	private TextView event;
	private int child;

	private EventDetailGUI getThis() {
		return this;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listButton3 = new AlertDialog.Builder(this);
		listButton3.setTitle("Add Task");
		listButton3.setMessage("Input task name");
		final EditText taskName = new EditText(this);
		listButton3.setView(taskName);

		listButton = new AlertDialog.Builder(this);
		listButton.setTitle("List Action");
		listButton.setMessage("Do what?");
		final Button add = new Button(this);
		add.setText("Add staff");
		listButton.setView(add);
		event = (TextView) findViewById(R.id.exEvent);
		//Log.e("Name event",model.getCurrentEvent().getName());
		listButton2 = new AlertDialog.Builder(this);
		listButton2.setTitle("List Action");
		listButton2.setMessage("What do u want?");

		listview = (ExpandableListView) findViewById(R.id.ExpList);
		model = Model.getModel();
		adapter = new ExpandListAdapter(this);
		listview.setAdapter(adapter);
		Log.i("loginname", model.getCurrentLoginName());
		Log.i("creator", model.getCurrentEvent().getCreator());
		if(!model.getCurrentLoginName().equalsIgnoreCase(model.getCurrentEvent().getCreator())){
			listview.setEnabled(false);
			event.setEnabled(false);
		}
		
		
		event.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				listButton3.show();
			}
		});
		
		listview.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				model.setCurrentTask(position);
				// Intent myIntent = new Intent(v.getContext(),
				// ListStaffGUI.class);
				// startActivityForResult(myIntent, 0);
				listButton.show();
				return false;
			}
		});

		listButton.setPositiveButton("Done", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				model.getCurrentTask().setDone(true);
				backToPage();
			}
		});

		listButton.setNegativeButton("Cancel", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		add.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), ListStaffGUI.class);
				startActivityForResult(myIntent, 0);
			}

		});

//		listview.setOnG
		
//		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View v,
//					int position, long arg3) {
//				model.setCurrentTask(position);
//				Log.i("group position", position+"");
//				model.getCurrentEvent().removeTask(model.getCurrentTask());
//				Toast.makeText(getThis(), "Delete task sucess",
//						Toast.LENGTH_SHORT).show();
//				
//				
//				backToPage();
//				// TODO Auto-generated method stub
//				// popupWindow.showAsDropDown(v);
//				// listButton.show();
//				
//				return false;
//			}
//		});
		
		listview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				model.setCurrentTask(groupPosition);
				child = childPosition;
				listButton2.show();
				// TODO Auto-generated method stub
				return false;
			}
		});

		listButton2.setNegativeButton("Cancel", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		listButton2.setPositiveButton("Delete", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int position) {
				// TODO Auto-generated method stub
				model.getCurrentTask().removeTeamMemeber(model.getCurrentTask().getTeamMemberSet().get(child));
				backToPage();
			}
		});
		
		listButton3.setPositiveButton("Cancel", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		listButton3.setNegativeButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int position) {
				// TODO Auto-generated method stub
				model.getCurrentEvent().addTask(new Task(taskName.getText().toString()));
				backToPage();
			}
		});

	}
	
	private void backToPage(){
		Intent prefIntent = new Intent( getThis(),EventTabHost.class);
	      getThis().startActivity(prefIntent);
	}

}
