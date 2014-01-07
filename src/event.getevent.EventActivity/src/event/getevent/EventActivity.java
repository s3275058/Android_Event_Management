package event.getevent;

import event.getevent.R;
import eventModel.AppStatus;
import eventModel.Model;
import SQLite.OfflineDatabase;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends Activity {

	
	private EditText loginNameText;
	private EditText passwordText;
	private TextView register;
	private Button loginButton;
	private Model model = Model.getModel();
	private OfflineDatabase db;
	private boolean login ;
	private AppStatus app = AppStatus.getInstance(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		loginNameText = (EditText) findViewById(R.id.loginName);
		passwordText = (EditText) findViewById(R.id.password);
		loginButton = (Button) findViewById(R.id.btnLogin);
		register = (TextView) findViewById(R.id.link_to_register);
		db = new OfflineDatabase(this);
		
		
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!isNetworkAvailable()) {
					Log.i("LOGIN", "OFFLINE");
					 login = db.login(loginNameText.getText().toString(), passwordText
							.getText().toString());
					
				} else {
					Log.i("LOGIN", "ONLINE");
					 login = model.login(loginNameText.getText()
							.toString(), passwordText.getText().toString());
				}
				
				
					if (login) {
						db.addOfflineAccount(
								loginNameText.getText().toString().trim(),
								passwordText.getText().toString().trim());
						model.setCurrentLoginName(loginNameText.getText().toString());
						model.getCompanyTaxNumberByLoginName();
						db.getEventFile(loginNameText.getText().toString());
						Intent myIntent = new Intent(v.getContext(),
								EventGUI.class);
						
						startActivityForResult(myIntent, 0);
					} else {
						Toast.makeText(getThis(), "Login failed, try again",
								Toast.LENGTH_SHORT).show();
					}
				
			}
		});
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("register","start");
				Intent myIntent = new Intent(v.getContext(),
						RegisterGUI.class);
				startActivityForResult(myIntent, 0);
			}
		});

	}

	private boolean isNetworkAvailable() {
		return app.isOnline();
	}

	private EventActivity getThis() {
		return this;
	}
	

}