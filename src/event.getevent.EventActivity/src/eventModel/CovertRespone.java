package eventModel;

public class CovertRespone {

	public String convertError(String s){
		if(s.equalsIgnoreCase("1")){
			return "Account already exist";
		}else if(s.equalsIgnoreCase("2")){
			return "Login fail, wrong username or password";
		}else if(s.equalsIgnoreCase("3")){
			return "Company tax number already exist";
		}else if(s.equalsIgnoreCase("4")){
			return "Company name already exist";
		}else if(s.equalsIgnoreCase("5")){
			return "Event name already exist";
		}else{
			return "Successful";
		}
	}
	
}
