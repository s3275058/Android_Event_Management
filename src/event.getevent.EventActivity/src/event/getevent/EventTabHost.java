package event.getevent;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class EventTabHost extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_tab_host);

		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		intent = new Intent().setClass(this, EventDetailGUI.class);
		spec = tabHost.newTabSpec("Event Detail").setIndicator("Event Detail").setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, ContactListGUI.class);
		spec = tabHost.newTabSpec("Contact").setIndicator("Contact").setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, SettingGUI.class);
		spec = tabHost.newTabSpec("Setting").setIndicator("Setting").setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(0);
	}
}
