package event.getevent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eventModel.Model;
import eventModel.Staff;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListStaffServerGUI extends Activity {

	private Model model;
	private Button back;
	private ListView listStaff;
	private String position;
	private AlertDialog.Builder positionInput;
	private ArrayAdapter<Staff> adapter;
	private EditText inputSearch;

	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_staff_from_server);

		model = Model.getModel();
		inputSearch = (EditText) findViewById(R.id.staffServer_inputSearch);
		listStaff = (ListView) findViewById(R.id.staffServer_list);
		back = (Button) findViewById(R.id.staffServer_back);
		positionInput = new AlertDialog.Builder(this);
		positionInput.setTitle("Position Input");
		positionInput.setMessage("input position for this staff");
		final EditText input = new EditText(this);
		positionInput.setView(input);

		positionInput.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dio, int arg1) {
						// TODO Auto-generated method stub
						position = input.getText().toString().trim();
						
						 Staff s = (Staff) listStaff.getSelectedItem();
						 s.setPosition(position);
						
						 model.getCurrentEvent().addStaff(s);
						 dio.dismiss();
					}

				});

		positionInput.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dio, int arg1) {
						// TODO Auto-generated method stub
						dio.dismiss();
					}

				});

		adapter = new ArrayAdapter<Staff>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				getStaffFromServer());

		listStaff.setAdapter(adapter);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(), ListStaffGUI.class);
				startActivityForResult(myIntent, 0);
			}
		});

		listStaff.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				positionInput.show();
				Toast.makeText(getThis(), "add staff sucessffly", Toast.LENGTH_SHORT).show();
			}
		});
		
		inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				ListStaffServerGUI.this.adapter.getFilter().filter(cs);
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
	
	private ListStaffServerGUI getThis(){
		return this;
	}
	
	private ArrayList<Staff> getStaffFromServer() {
		ArrayList<Staff> array = new ArrayList<Staff>();
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"https://mekong.rmit.edu.vn/~s3275058/MAD/getStaff.php");
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("companyTaxNumb", model.getCurrentEvent().getCompanyTaxNumb()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// ex

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httppost, responseHandler);
			Log.i("respone", responseBody);
			Log.i("state", "2.1");


			JSONArray jarray = new JSONArray(responseBody);
			Log.i("taxNumb", model.getCurrentEvent().getCompanyTaxNumb()+"");
			for (int i = 0; i < jarray.length(); i++) {
				JSONObject job = jarray.getJSONObject(i);
				array.add(new Staff(job.getString("displayName"), job
						.getString("email"), job.getString("phoneNumber"),
						model.getCurrentEvent().getCompanyTaxNumb(), true));

			}
			return array;

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
		return new ArrayList<Staff>();
	}

}
