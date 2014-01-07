package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import eventModel.Event;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CheckUpdate extends Service {

	private HashSet<Event> eventSet;

	public CheckUpdate(HashSet<Event> eventSet) {
		this.eventSet = eventSet;

	}

	@Override
	public void onCreate() {
		checkAllUpdate();
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				checkAllUpdate();
			}
		}, 5 * 60 * 1000);
	}

	private void checkAllUpdate() {
		Iterator<Event> iter = eventSet.iterator();
		while (iter.hasNext()) {
			Event e = iter.next();
			check1Update(e);
		}
	}

	private void check1Update(Event event) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://mekong.rmit.edu.vn/cosc2171/s3275058/MAD/checkUpdate.php");
		BasicResponseHandler re = new BasicResponseHandler();
		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("eventId", event.getId()
					+ ""));
			nameValuePair.add(new BasicNameValuePair("version", event
					.getVersion() + ""));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			String s = httpClient.execute(httpPost, re);
			Log.i("respone", s);
			Toast.makeText(this,
					"Event " + event.getName() + " has new update", 10).show();

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onDestroy(Bundle b){
		
	}

}
