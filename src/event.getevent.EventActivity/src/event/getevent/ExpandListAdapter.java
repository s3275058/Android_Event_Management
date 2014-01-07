package event.getevent;

import java.util.ArrayList;
import eventModel.Model;
import eventModel.Staff;
import eventModel.Task;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ExpandListAdapter extends BaseExpandableListAdapter {
	
	private Model model = Model.getModel();
	private Context context;
	private ArrayList<Task> taskSet = model.getCurrentEvent().getTaskSet();

	public ExpandListAdapter(Context context) {
		this.context = context;

	}
	

	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		ArrayList<Staff> staffSet = taskSet.get(groupPosition)
				.getTeamMemberSet();
		return staffSet.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		Staff child = (Staff) getChild(groupPosition,
				childPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.expandlist_child_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tvChild);
		tv.setText(child.getDisplayName());
		tv.setTag(child.getPosition());
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		ArrayList<Staff> staffSet = taskSet.get(groupPosition).getTeamMemberSet();
		return staffSet.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return taskSet.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return taskSet.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Task task = (Task) getGroup(groupPosition);
		
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.expandlist_group_item, null);
		}
		
		TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
		tv.setText(task.getName());
		
		ExpandableListView eLV = (ExpandableListView) parent;
	    eLV.expandGroup(groupPosition);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
