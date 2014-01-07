package eventModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class Account {
	private String username;
	private String displayeName;
	private String password;
	private String email;
	private String phoneNumber;
	private String companyTaxNumb;

	public Account(String username, String displayeName, String password,
			String email, String phoneNumber, String companyTaxNo) {
		this.username = username;
		this.displayeName = displayeName;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.companyTaxNumb = companyTaxNo;
	}

	public boolean register() {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://mekong.rmit.edu.vn/~s3275058/MAD/newAccount.php");
		BasicResponseHandler re = new BasicResponseHandler();
		// HttpEntity entity;

		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
			nameValuePair.add(new BasicNameValuePair("username", this.username
					.trim()));
			nameValuePair.add(new BasicNameValuePair("displayName",
					this.displayeName.trim()));
			nameValuePair.add(new BasicNameValuePair("password", this.password
					.trim()));
			nameValuePair.add(new BasicNameValuePair("email", this.email.trim()));
			nameValuePair.add(new BasicNameValuePair("phoneNumber",
					this.phoneNumber.trim()));
			nameValuePair.add(new BasicNameValuePair("companyTaxNumb",
					this.companyTaxNumb + ""));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			String s = httpClient.execute(httpPost, re).trim();
			Log.d("Respone", s);
			// entity = respone.getEntity();
			// System.out.print(entity);
			if(s.equalsIgnoreCase("0")){
			return true;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
