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

public class Company {

	private String taxNumb;
	private String companyName;

	public Company(String taxNumb, String companyName) {
		this.companyName = companyName;
		this.taxNumb = taxNumb;
	}

	public String getTaxNumb() {
		return this.taxNumb;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public boolean uploadCompany() {
		Log.i("company Upload", "start");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://mekong.rmit.edu.vn/~s3275058/MAD/newCompany.php");
		BasicResponseHandler re = new BasicResponseHandler();

		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair
					.add(new BasicNameValuePair("companyTaxNumb", taxNumb.trim()));
			nameValuePair.add(new BasicNameValuePair("companyName", companyName
					.trim()));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			String s = httpClient.execute(httpPost, re).trim();
			Log.i("company Upload", "respone" + s);
			if (s.equalsIgnoreCase("0")) {
				return true;
			}
		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.companyName;
	}

}
