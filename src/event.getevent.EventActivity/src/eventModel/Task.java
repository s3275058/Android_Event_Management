package eventModel;

import java.util.ArrayList;

public class Task {

	private String name;
	private int id = 0;
	private ArrayList<Staff> teamMemberSet = new ArrayList<Staff>();
	private boolean done = false;

	public Task(String name) {
		this.name = name;
		++id;
	}
	
	public Task(String name, boolean done) {
		this.done = done;
		this.name = name;
		++id;
	}

	public boolean getDone() {
		return this.done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}


	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	public ArrayList<Staff> getTeamMemberSet(){
		return this.teamMemberSet;
	}

	public void addTeamMember(Staff staff) {
		teamMemberSet.add(staff);
	}

	public void removeTeamMemeber(Staff staff) {
		teamMemberSet.remove(staff);
	}

}
