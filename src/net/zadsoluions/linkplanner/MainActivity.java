package net.zadsoluions.linkplanner;

import java.util.ArrayList;

import org.json.JSONObject;

import net.zadsolutions.containers.SamplePoints;
import net.zadsolutions.containers.MapAPI;
import net.zadsolutions.customs.MainGVCustomAdapter;
import net.zadsolutions.services.GPSTracker;
import net.zadsolutions.services.WSHandler;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	private GridView main_GV;
	private String[] titles = {
			"New Links",
			"Saved Calculations",
			"Map",
			"Register", 
			"About Us"
	};
	private int[] images = {
		R.drawable.ic_launcher,
		R.drawable.ic_launcher,
		R.drawable.ic_launcher,
		R.drawable.ic_launcher,
		R.drawable.ic_launcher
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		MainGVCustomAdapter mainGVCustomAdapter = new MainGVCustomAdapter(MainActivity.this, titles, images);
		this.main_GV = (GridView) findViewById(R.id.main_GV);
		this.main_GV.setAdapter(mainGVCustomAdapter);
		this.main_GV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Toast.makeText(MainActivity.this, "You Clicked at " +titles[+ position], Toast.LENGTH_SHORT).show();
				
				switch (position) {
				case 0:
					startActivity(new Intent(MainActivity.this, LinkData.class));					
					break;

				default:
					break;
				}

			}
		});
	}
	
	public void setEPints(ArrayList<SamplePoints> points){
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
