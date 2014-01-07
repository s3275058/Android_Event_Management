package event.getevent;

import eventModel.Event;
import eventModel.Model;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewEventGUI extends Activity {

	private Model model;
	private Button add;
	private Button cancel;
	private EditText name;
	private EditText password;
	private EditText description;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);

		model = Model.getModel();
		add = (Button) findViewById(R.id.addEvent_btnAdd);
		cancel = (Button) findViewById(R.id.addEvent_btnCancel);
		name = (EditText) findViewById(R.id.addEvent_name);
		password = (EditText) findViewById(R.id.addEvent_password);
		description = (EditText) findViewById(R.id.addEvent_description);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), EventGUI.class);
				startActivityForResult(myIntent, Toast.LENGTH_SHORT);
			}
		});
		
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addEvent(v);
			}
		});
	}

	private void addEvent(View v) {
		if (name.getText() == null || password.getText() == null
				|| description.getText() == null) {
			Toast.makeText(this, "Do not leave blank input", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		Event e = new Event(name.getText().toString().trim(), password
				.getText().toString().trim(), model.getCurrentLoginName(),
				description.getText().toString().trim());
		e.setCompanyTaxNumb(model.getCurrentCompanyTaxNumb());
		model.addEvent(e);

		Toast.makeText(this, "Add event successfully", Toast.LENGTH_SHORT)
				.show();

		Intent myIntent = new Intent(v.getContext(), EventGUI.class);
		startActivityForResult(myIntent, Toast.LENGTH_SHORT);
	}
}
