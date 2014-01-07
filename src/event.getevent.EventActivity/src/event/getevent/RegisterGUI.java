package event.getevent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import eventModel.Account;
import eventModel.Company;

public class RegisterGUI extends Activity {

	EditText loginName;
	EditText displayName;
	EditText password;
	EditText rePassword;
	EditText email;
	EditText phoneNumber;
	EditText companyTaxNo;
	EditText companyName;
	Button registerButton;
	Spinner spinner;
	TextView linkLogin;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		Log.i("declare", "start");

		loginName = (EditText) findViewById(R.id.reg_loginName);
		displayName = (EditText) findViewById(R.id.reg_displayName);
		password = (EditText) findViewById(R.id.reg_password);
		rePassword = (EditText) findViewById(R.id.reg_rePassword);
		email = (EditText) findViewById(R.id.reg_email);
		phoneNumber = (EditText) findViewById(R.id.reg_phone);
		companyTaxNo = (EditText) findViewById(R.id.reg_addCompanyTaxNo);
		companyName = (EditText) findViewById(R.id.reg_addCompanyName);
		registerButton = (Button) findViewById(R.id.btnRegister);
		spinner = (Spinner) findViewById(R.id.reg_compList);
		linkLogin = (TextView) findViewById(R.id.link_to_login);
		Log.i("declare", "end");

		Log.i("declare 2", "start");
		// final ArrayList<Company> comp = this.getCompanyList();
		// ArrayAdapter<Company> fAdapter;
		// fAdapter = new ArrayAdapter<Company>(this,
		// R.id.reg_compList, comp);
		// companyList.setAdapter(fAdapter);

		// second way
		addItemsOnSpinner();

		Log.i("declare 2 ", "end");

		linkLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(),
						EventActivity.class);
				startActivityForResult(myIntent, Toast.LENGTH_SHORT);
			}
		});

		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("register", "start");

				Account a = null;
				if (!password.getText().toString()
						.equals(rePassword.getText().toString())) {
					password.setText("");
					rePassword.setText("");
					return;
				}

				if (companyName.getText().toString().trim().equals("")
						&& companyTaxNo.getText().toString().trim().equals("")) {
					Company com = (Company) spinner.getSelectedItem();
					a = new Account(loginName.getText().toString(), displayName
							.getText().toString(), password.getText()
							.toString(), email.getText().toString(),
							phoneNumber.getText().toString(), com.getTaxNumb());

				} else {

					Company c = new Company(companyTaxNo.getText().toString(),
							companyName.getText().toString());

					a = new Account(loginName.getText().toString(), displayName
							.getText().toString(), password.getText()
							.toString(), email.getText().toString(),
							phoneNumber.getText().toString(), companyTaxNo
									.getText().toString());

					if (!c.uploadCompany()) {

						Toast.makeText(getThis(),
								"Company name or Tax number already be used.",
								Toast.LENGTH_SHORT).show();
						companyName.setText("");
						companyTaxNo.setText("");

						return;
					}
				}

				if (a.register()) {

					Toast.makeText(getThis(), "Register sucessfully!",
							Toast.LENGTH_SHORT).show();
					Intent myIntent = new Intent(v.getContext(),
							EventActivity.class);
					startActivityForResult(myIntent, Toast.LENGTH_SHORT);
				} else {
					Toast.makeText(getThis(),
							"Register Fail, login name already exsit!",
							Toast.LENGTH_LONG).show();
					password.setText("");
					rePassword.setText("");
					loginName.setText("");
				}

			}
		});

	}

	public void addItemsOnSpinner() {

		spinner = (Spinner) findViewById(R.id.reg_compList);
		ArrayList<Company> list = this.getCompanyList();

		ArrayAdapter<Company> dataAdapter = new ArrayAdapter<Company>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

	}

	private ArrayList<Company> getCompanyList() {
		try {
			ArrayList<Company> compList = new ArrayList<Company>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"https://mekong.rmit.edu.vn/~s3275058/MAD/getCompany.php");


			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httppost, responseHandler);
			Log.i("respone", responseBody);

			// Parse
			// JSONObject json = new JSONObject(responseBody);
			// JSONArray jArray = json.getJSONArray("posts");
			JSONArray jarray = new JSONArray(responseBody);

			for (int i = 0; i < jarray.length(); i++) {
				JSONObject job = jarray.getJSONObject(i);
				Log.i("taxNumber", job.getString("companyTaxNumb"));
				Log.i("companyName", job.getString("companyName"));
				compList.add(new Company(job.getString("companyTaxNumb"), job
						.getString("companyName")));
			}

			return compList;
		} catch (JSONException e1) {
			Log.e("error 1", e1.getMessage());
		} catch (ParseException e1) {
			Log.e("error 2", e1.getMessage());
		} catch (UnsupportedEncodingException e) {
			Log.e("error 3", e.getMessage());
		} catch (ClientProtocolException e) {
			Log.e("error 4", e.getMessage());
		} catch (IOException e) {
			Log.e("error 5", e.getMessage());
		}
		return null;
	}

	private RegisterGUI getThis() {
		return this;
	}

}
