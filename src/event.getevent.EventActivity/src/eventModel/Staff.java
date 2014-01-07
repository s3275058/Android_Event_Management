package eventModel;

public class Staff {
	private int id = 0;
	private String displayName;
	private String email;
	private String phoneNumber;
	private String position = null;
	private String companyTaxNumb;
	private boolean upload = false;

	//for new create
	public Staff(String displayName, String email, String phoneNumber,
			String position, String companyTaxNumb) {
		this.companyTaxNumb = companyTaxNumb;
		this.displayName = displayName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.position = position;
		++id;
	}
	
	//for readfile
	public Staff(String displayName, String email, String phoneNumber,
			String position, String companyTaxNumb, boolean upload) {
		this.companyTaxNumb = companyTaxNumb;
		this.displayName = displayName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.position = position;
		this.upload = upload;
		++id;
	}
	
	public Staff(String displayName, String email, String phoneNumber,
			 String companyTaxNumb, boolean upload) {
		this.companyTaxNumb = companyTaxNumb;
		this.displayName = displayName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.upload = upload;
		++id;
	}

	public boolean getUpload() {
		return this.upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}
	
	public void setPosition(String position){
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getEmail() {
		return email;
	}

	public String getCompanyTaxNumb() {
		return this.companyTaxNumb;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if(this.position == null){
			return "Name: "+this.displayName+"\n"+"Email: "+this.email+"\n Phone number " +this.phoneNumber;
		}
		return "Name: "+this.displayName+"\n"+"Email: "+this.email+"\n Phone Number " +this.phoneNumber+"\n Position: "+this.position;
	}

}
