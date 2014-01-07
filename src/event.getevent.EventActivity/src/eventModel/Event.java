package eventModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Environment;
import android.util.Log;

public class Event {
	private int id;
	private String name;
	private String password = null;
	private String creator;
	private String description;
	private ArrayList<Task> taskSet = new ArrayList<Task>();
	private ArrayList<Staff> staffSet = new ArrayList<Staff>();
	private String companyTaxNumb;
	private DecimalFormat df = new DecimalFormat("#.##");


	private double version = 0.0;
//	private double progress;


	public Event(String name, String password, String creator,
			String description) {
		this.name = name;
		this.password = password;
		this.creator = creator;
		this.description = description;

	}

	public Event(int id,String name, String password, String creator,
			String description, String companyTaxNumb, double version) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.companyTaxNumb = companyTaxNumb;
		this.creator = creator;
		this.description = description;
		this.version = version;
	}

	public double calculateProgress(){
		double total=0;
		double progress=0;
		for(int i=0; i<this.taskSet.size(); i++){
			if(this.taskSet.get(i).getDone()==true){
				total ++;
			}
		}
		double s = taskSet.size();
		progress = (total*100)/s;
		return progress;
	}
	
	// create eventFile
	private void createFile() throws IOException {
		Log.i("State", " start create File ");
		File root = Environment.getExternalStorageDirectory();
		File dir = new File(root.getAbsolutePath() + "/event");
		dir.mkdirs();
		File eventFile = new File(dir, this.id + "");
		if(eventFile.exists()){
			eventFile.delete();
		}
		saveEvent(eventFile);

		Log.i("State", " end create File");
	}

	private void saveEvent(File eventFile) throws IOException {
		FileOutputStream f = new FileOutputStream(eventFile);
		OutputStreamWriter osw = new OutputStreamWriter(f);
		osw.write("e|" + this.id + "|" + this.name + "|" + this.password + "|"
				+ this.creator + "|" + this.description + "|"
				+ this.companyTaxNumb + "|"+this.version+"\n");
		for (int i = 0; i < staffSet.size(); i++) {
			Staff staff = staffSet.get(i);
			osw.write("s|" + staff.getDisplayName() + "|" + staff.getEmail()
					+ "|" + staff.getPhoneNumber() + "|" + staff.getPosition()
					+ "|" + staff.getCompanyTaxNumb() + "|" + staff.getUpload()
					+ "\n");
		}
		// write phase

			// write task
			for (int j = 0; j <taskSet.size(); j++) {
				Task task = taskSet.get(j);
				osw.write("t|" + task.getName() + "|" + task.getDone() + "\n");
				// write member
				for (int z = 0; z < task.getTeamMemberSet().size(); z++) {
					Staff member = task.getTeamMemberSet().get(z);
					osw.write("m|" + member.getDisplayName() + "|"
							+ member.getEmail() + "|" + member.getPhoneNumber()
							+ "|" + member.getPosition() + "|"
							+ member.getCompanyTaxNumb() + "|"
							+ member.getUpload() + "\n");
				}
			}
		
		osw.close();
	}

	// upload event
	public void uploadEvent() {
		if (version == 0.0) {
			uploadEvent1();
		} else {
			uploadEventFile();
		}
	}

	private void uploadEvent1() {
		Log.i("State", " start upload 1");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://mekong.rmit.edu.vn/~s3275058/MAD/uploadEvent.php");
		BasicResponseHandler re = new BasicResponseHandler();
		// HttpEntity entity;

		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
			nameValuePair.add(new BasicNameValuePair("eventName", this.name));
			nameValuePair
					.add(new BasicNameValuePair("password", this.password));
			nameValuePair.add(new BasicNameValuePair("creator", this.creator));
			nameValuePair.add(new BasicNameValuePair("version", this.version
					+ ""));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			// get EventId
			String a = httpClient.execute(httpPost, re).trim();

			this.id = Integer.parseInt(a);
			
			
			Log.i("State", " end upload 1");
			
			uploadEventFile();

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
	}
	
	public boolean isThereNewVersion(){
		getVerionFromServer();
		if(this.version<this.getVerionFromServer()){
			Log.i("version", " there is a new one ");
			return true;
		}
		Log.i("version", "this one is current ggod");
		return false;
	}
	
	private Double getVerionFromServer(){
		
		Log.i("State", " start get version ");
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://mekong.rmit.edu.vn/~s3275058/MAD/getEventVersion.php");
		BasicResponseHandler re = new BasicResponseHandler();
		// HttpEntity entity;
		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
			nameValuePair.add(new BasicNameValuePair("eventId", this.id+""));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			// get EventId
			String g = httpClient.execute(httpPost, re).trim();
			Log.i("EVENT version", g+"");
			Double a = Double.parseDouble(httpClient.execute(httpPost, re).trim());
			
			return a;

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return null;
	}

	private void uploadEventFile() {
		
		
		
		
		Log.i("State", " start upload 2");
		this.version = this.version + 0.1;
		
		try {
			createFile();
			BasicResponseHandler re = new BasicResponseHandler();
			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(
					"https://mekong.rmit.edu.vn/~s3275058/MAD/uploadFile.php");
			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/event/" + this.id);
			Log.i("file exsit", file.exists()+"");
			FileBody bin = new FileBody(file);
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("file", bin);
			httpPost.setEntity(reqEntity);

			String a = client.execute(httpPost, re);
			Log.i("File upload", a);

			// upload file name
			Log.i("version",""+this.version);
			HttpPost post = new HttpPost(
					"https://mekong.rmit.edu.vn/~s3275058/MAD/uploadFileName.php");
			HttpClient clients = new DefaultHttpClient();
			List<NameValuePair> nameValuePai = new ArrayList<NameValuePair>(3);
			nameValuePai.add(new BasicNameValuePair("eventId", this.id + ""));
			nameValuePai.add(new BasicNameValuePair("version", this.version
					+ ""));

			post.setEntity(new UrlEncodedFormEntity(nameValuePai));
			String d =  clients.execute(post, re);
			Log.i("respone upload version", d);
			Log.i("State", " end upload 2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// upload all staff
	public void uploadStaffs() {
		Log.i("upload staff", "upload");

		Iterator<Staff> iter = staffSet.iterator();
		while (iter.hasNext()) {
			Staff s = iter.next();
			if (!s.getUpload()) {
				uploadStaff(s);
				s.setUpload(true);
			}
		}
	}

	// upload 1 staff method
	private void uploadStaff(Staff staff) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"https://mekong.rmit.edu.vn/~s3275058/MAD/uploadStaff.php");
		BasicResponseHandler re = new BasicResponseHandler();

		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
			nameValuePair.add(new BasicNameValuePair("displayName", staff
					.getDisplayName()));
			nameValuePair
					.add(new BasicNameValuePair("email", staff.getEmail()));
			nameValuePair.add(new BasicNameValuePair("phoneNumber", staff
					.getPhoneNumber()));
			nameValuePair.add(new BasicNameValuePair("companyTaxNumb",
					companyTaxNumb + ""));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

			String s = httpClient.execute(httpPost, re);
			Log.d("Respone server", s);

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
	}
	
	public ArrayList<Staff> getStaffSet(){
		return this.staffSet;
	}
	
	public ArrayList<Task> getTaskSet(){
		return this.taskSet;
	}

	public Double getVersion() {
		return this.version;
	}

	public String getPassword() {
		return this.password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getCreator() {
		return creator;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void addTask(Task task) {
		taskSet.add(task);
	}

	public void removeTask(Task task) {
		taskSet.remove(task);
	}

	public void addStaff(Staff staff) {
		staffSet.add(staff);
	}

	public void removeStaff(Staff staff) {
		staffSet.remove(staff);
	}

	public void setCompanyTaxNumb(String companyTaxNumb) {
		this.companyTaxNumb = companyTaxNumb;
	}

	public String getCompanyTaxNumb() {
		return companyTaxNumb;
	}



	public double getProgress() {
		if(taskSet.size()==0){
			return 0;
		}
		return calculateProgress();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Event name: "+this.name+	"  id:"+this.id+	"\n Version: "+this.version+"       Process:"+df.format(getProgress())+"%";
	}
	
	public void setVersion(Double d){
		this.version = d;
	}

}
