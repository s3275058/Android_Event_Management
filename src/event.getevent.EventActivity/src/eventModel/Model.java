package eventModel;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

@SuppressLint({ "UseSparseArrays" })
public class Model {
	private static Model model = new Model();
	private String currentLoginName = null;
	private String currentCompanyTaxNumb = null;
	private int currentEvent;
	private int currentTask;

	private Model() {
	}

	public static Model getModel() {
		return model;
	}

	private ArrayList<Event> eventSet = new ArrayList<Event>(1000);

	public ArrayList<Event> getEventSet() {
		return eventSet;
	}

	public void addEvent(Event event) {
		eventSet.add(event);
	}

	public void removeEvent(Event event) {
		eventSet.remove(event);
	}
	
	public void removeAllEvent(){
//		Iterator<Event> iter = eventSet.iterator();
//		while (iter.hasNext()) {
//			Event ev = iter.next();
//			eventSet.remove(ev);
//		}
		eventSet.clear();
//		for(int i=0; i<eventSet.size(); i++){
//			eventSet.remove(i);
//		}

	}

	public boolean isEvent(int eventId, String password) {
		Log.i("getEvent", "start");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://mekong.rmit.edu.vn/~s3275058/MAD/getEvent.php");
		ResponseHandler<String> re = new BasicResponseHandler();

		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("eventId", eventId + ""));
			nameValuePair.add(new BasicNameValuePair("password", password
					.trim()));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			String s = httpClient.execute(httpPost, re).trim();
			Log.i("getEvent", "repone " +s);
			if (s.equalsIgnoreCase("0")) {
				return true;
			}

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return false;
	}

	public boolean login(String loginName, String password) {

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://mekong.rmit.edu.vn/~s3275058/MAD/login.php");
		ResponseHandler<String> re = new BasicResponseHandler();

		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
			nameValuePair.add(new BasicNameValuePair("username", loginName
					.trim()));
			nameValuePair.add(new BasicNameValuePair("password", password
					.trim()));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			String s = httpClient.execute(httpPost, re).trim();
			if (s.equalsIgnoreCase("0")) {
				return true;
			}

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return false;
	}

	public void getCompanyTaxNumberByLoginName() {
		Log.i("getCompanyTaxNumbByLoginName", "start");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://mekong.rmit.edu.vn/~s3275058/MAD/getCompanyTaxNumb.php");
		ResponseHandler<String> re = new BasicResponseHandler();

		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
			nameValuePair.add(new BasicNameValuePair("username",
					this.currentLoginName.trim()));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			String s = httpClient.execute(httpPost, re).trim();
			Log.i("taxNumb", s);
			Log.i("getCompanyTaxNumbByLoginName", "end");
			this.currentCompanyTaxNumb = s;

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
	}
	
	private Event e;
	private Task t;
	public void loadFile(int id) throws FileNotFoundException{
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/event/"+id);
		Log.e("load root", file.getAbsolutePath());
		Log.i("file exsit", file.exists()+"");
		Log.i("loadfile", "start");
		Scanner scan = new Scanner(file);
		while(scan.hasNext()){
			String s = scan.nextLine();
			Log.i("read next line",s);
			String[] item = s.split("\\|");
			if(item[0].equals("e")){
				e = new Event(Integer.parseInt(item[1]), item[2], item[3], item[4], item[5], item[6],Double.parseDouble(item[7]));
				this.addEvent(e);
			}else if(item[0].equals("s")){
				e.addStaff(new Staff(item[1], item[2], item[3], item[4], item[5], Boolean.parseBoolean(item[6])));
			}else if(item[0].equals("t")){
				t =  new Task(item[1], Boolean.parseBoolean(item[2]));
				 e.addTask(t);
			}else if(item[0].equals("m")){
				t.addTeamMember(new Staff(item[1], item[2], item[3], item[4], item[5], Boolean.parseBoolean(item[6])));
			}
		}
		Log.i("loadfile", "end");
	}
	
	public void download(int id){
		try {
			URL url = new URL("https://mekong.rmit.edu.vn/~s3275058/MAD/upload/"+id);
			url.openConnection();
			InputStream reader = url.openStream();
			
			//file
			File root = Environment.getExternalStorageDirectory();
			Log.i("root can write", root.canWrite()+"");
			
			File dir = new File(root.getAbsolutePath() + "/event");
			dir.mkdirs();
			Log.i("dir exist", dir.getAbsolutePath() + dir.exists());
			File test = new File(dir.getAbsolutePath(), id + "");
			Log.i("test before", test.getAbsolutePath() + "  " + test.exists() + "");
			if (test.exists()) {
				test.delete();
				Log.i("after delete", test.exists() + "");
			}
			
			FileOutputStream writer = new FileOutputStream(test);
			
			byte[] buffer = new byte[153600];
			int totalBytesRead = 0;
	        int bytesRead = 0;
			
	        while ((bytesRead = reader.read(buffer)) > 0)
	        {  
	           writer.write(buffer, 0, bytesRead);
	           buffer = new byte[153600];
	           totalBytesRead += bytesRead;
	        }
	        
	        if (test.exists()) {
				Log.i("after download", test.exists() + "");
			}
	        
	        writer.close();
	        reader.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setCurrentLoginName(String loginName) {
		this.currentLoginName = loginName;
	}

	public String getCurrentLoginName() {
		return currentLoginName;
	}

	public void setCurrentCompanyTaxNumb(String companyTaxNumb) {
		this.currentCompanyTaxNumb = companyTaxNumb;
	}

	public String getCurrentCompanyTaxNumb() {
		return currentCompanyTaxNumb;
	}

	public void setCurrentEvent(int currentEvent) {
		this.currentEvent = currentEvent;
	}

	public Event getCurrentEvent() {
		return getEventSet().get(currentEvent);
	}

	public void setCurrentTask(int currentTask) {
		this.currentTask = currentTask;
	}

	public Task getCurrentTask() {
		return getEventSet().get(currentEvent).getTaskSet().get(currentTask);
	}

}
