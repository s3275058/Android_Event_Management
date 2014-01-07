package event.getevent;

import eventModel.Model;
import eventModel.Staff;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddStaffGUI extends Activity {

	private Model model;
	private EditText displayNameText;
	private EditText emailText;
	private EditText phoneNumberText;
	private EditText positionText;
	private Button addStaffButt;
	private Button cancelButt;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_staff);

		model = Model.getModel();

		displayNameText = (EditText) findViewById(R.id.addStaff_displayName);
		emailText = (EditText) findViewById(R.id.addStaff_email);
		phoneNumberText = (EditText) findViewById(R.id.addStaff_phone);
		positionText = (EditText) findViewById(R.id.addStaff_position);
		addStaffButt = (Button) findViewById(R.id.addStaff_btnAdd);
		cancelButt = (Button) findViewById(R.id.addStaff_btnCancel);

		addStaffButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				add();
				backToActivity(v);
			}
		});

		cancelButt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				backToActivity(v);
			}
		});

	}	
	


	private void add() {
		if (displayNameText.getText().toString().trim() == null
				|| emailText.getText().toString().trim() == null
				|| phoneNumberText.getText().toString().trim() == null
				|| positionText.getText().toString().trim() == null) {
			Toast.makeText(this, "Add staff fail, do not leave blank input", Toast.LENGTH_SHORT).show();
			return;
		}

		Staff staff = new Staff(displayNameText.getText().toString().trim(),
				emailText.getText().toString().trim(), phoneNumberText
						.getText().toString().trim(), positionText.getText()
						.toString().trim(), model.getCurrentCompanyTaxNumb());
		model.getCurrentEvent().addStaff(staff);
		Toast.makeText(this, "Add staff sucessfully", Toast.LENGTH_SHORT).show();
	}
	
	private void backToActivity(View v){
		Intent myIntent = new Intent(v.getContext(),
				ListStaffGUI.class);
		startActivityForResult(myIntent, Toast.LENGTH_SHORT);
	}
}
