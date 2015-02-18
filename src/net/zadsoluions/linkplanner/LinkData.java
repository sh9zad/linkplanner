package net.zadsoluions.linkplanner;

import net.zadsolutions.containers.Site;
import net.zadsolutions.services.GPSTracker;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LinkData extends ActionBarActivity {
	
	Button link_data_site_b_gps_lng, link_data_site_a_gps_lat;
	Button link_data_site_a_map_lat, link_data_site_b_map_lat;
	Button link_data_btn_calculate;
	
	EditText link_data_site_a_lat_txt, link_data_site_a_lng_txt;
	EditText link_data_site_b_lat_txt, link_data_site_b_lng_txt;
	EditText link_data_site_a_name_txt, link_data_site_b_name_txt;
	EditText link_data_site_a_txt_height, link_data_site_b_txt_height;
	EditText link_data_txt_freq;
	
	GPSTracker m_GpsTracker;
	
	Site siteA, siteB;
	String inputError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link_data);
		
		this.siteA = new Site();
		this.siteB = new Site();
		
		initWidgets();
			
		this.link_data_site_a_gps_lat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				m_GpsTracker = new GPSTracker(LinkData.this);
				link_data_site_a_lat_txt.setText(String.valueOf(m_GpsTracker.getLatitude()));
				link_data_site_a_lng_txt.setText(String.valueOf(m_GpsTracker.getLongitude()));
			}
		});
						
		this.link_data_site_b_gps_lng.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				m_GpsTracker = new GPSTracker(LinkData.this);
				link_data_site_b_lat_txt.setText(String.valueOf(m_GpsTracker.getLatitude()));
				link_data_site_b_lng_txt.setText(String.valueOf(m_GpsTracker.getLongitude()));
			}
		});
		
		this.link_data_site_a_map_lat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String siteName = (link_data_site_a_name_txt.getText().equals("") || link_data_site_a_name_txt.getText().equals(null)) 
						? "Site A" : link_data_site_a_name_txt.getText().toString();
				
				Float Lat = (link_data_site_a_lat_txt.getText().toString().equals("") 
								|| link_data_site_a_lat_txt.getText().toString().equals(null))
						? Float.valueOf(0) : Float.valueOf(link_data_site_a_lat_txt.getText().toString());
						
				Float Lng = (link_data_site_a_lng_txt.getText().toString().equals("") 
								|| link_data_site_a_lng_txt.getText().toString().equals(null))
						? Float.valueOf(0) : Float.valueOf(link_data_site_a_lng_txt.getText().toString());
						
				Float Hght = (link_data_site_a_txt_height.getText().toString().equals("") 
								|| link_data_site_a_txt_height.getText().toString().equals(null))
						? Float.valueOf(0) : Float.valueOf(link_data_site_a_txt_height.getText().toString());
				
				siteA = new Site(siteName, Lat, Lng, Hght);
				
				Intent mapIntent = new Intent(LinkData.this, MapView.class);
				mapIntent.putExtra("site", siteA);
				mapIntent.putExtra("side", "a");
				
				startActivityForResult(mapIntent, 1);
			}
		});
		
		this.link_data_site_b_map_lat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				String siteName = (link_data_site_b_name_txt.getText().equals("") || link_data_site_b_name_txt.getText().equals(null)) 
						? "Site A" : link_data_site_b_name_txt.getText().toString();
				
				Float Lat = (link_data_site_b_lat_txt.getText().toString().equals("") 
								|| link_data_site_b_lat_txt.getText().toString().equals(null))
						? Float.valueOf(0) : Float.valueOf(link_data_site_b_lat_txt.getText().toString());
						
				Float Lng = (link_data_site_b_lng_txt.getText().toString().equals("") 
								|| link_data_site_b_lng_txt.getText().toString().equals(null))
						? Float.valueOf(0) : Float.valueOf(link_data_site_b_lng_txt.getText().toString());
				
				Float Hght = (link_data_site_b_txt_height.getText().toString().equals("") 
								|| link_data_site_b_txt_height.getText().toString().equals(null))
						? Float.valueOf(0) : Float.valueOf(link_data_site_b_txt_height.getText().toString());
				siteB = new Site(siteName, Lat, Lng, Hght);
				
				Intent mapIntent = new Intent(LinkData.this, MapView.class);
				mapIntent.putExtra("site", siteB);
				mapIntent.putExtra("side", "b");
				
				startActivityForResult(mapIntent, 1);
			}
		});
	
		this.link_data_btn_calculate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				/* Site A*/
				Float Lat = (link_data_site_a_lat_txt.getText().toString().equals("") 
						|| link_data_site_a_lat_txt.getText().toString().equals(null))
					? Float.valueOf(-900) : Float.valueOf(link_data_site_a_lat_txt.getText().toString());
				
				Float Lng = (link_data_site_a_lng_txt.getText().toString().equals("") 
						|| link_data_site_a_lng_txt.getText().toString().equals(null))
					? Float.valueOf(-900) : Float.valueOf(link_data_site_a_lng_txt.getText().toString());
				Float Hght = (link_data_site_a_txt_height.getText().toString().equals("") 
							|| link_data_site_a_txt_height.getText().toString().equals(null))
					? Float.valueOf(0) : Float.valueOf(link_data_site_a_txt_height.getText().toString());
				siteA.setLat(Lat);
				siteA.setLng(Lng);
				siteA.setHieght(Hght);
				siteA.setName(link_data_site_a_name_txt.getText().toString());

				/* Site B */
				Lat = (link_data_site_b_lat_txt.getText().toString().equals("") 
						|| link_data_site_b_lat_txt.getText().toString().equals(null))
					? Float.valueOf(-900) : Float.valueOf(link_data_site_b_lat_txt.getText().toString());
				
				Lng = (link_data_site_b_lng_txt.getText().toString().equals("") 
						|| link_data_site_b_lng_txt.getText().toString().equals(null))
					? Float.valueOf(-900) : Float.valueOf(link_data_site_b_lng_txt.getText().toString());
				Hght = (link_data_site_b_txt_height.getText().toString().equals("") 
							|| link_data_site_b_txt_height.getText().toString().equals(null))
					? Float.valueOf(0) : Float.valueOf(link_data_site_b_txt_height.getText().toString());
				siteB.setLat(Lat);
				siteB.setLng(Lng);
				siteB.setHieght(Hght);
				siteB.setName(link_data_site_b_name_txt.getText().toString());
				
				if (validateData()){
					Intent linkCalc = new Intent(LinkData.this, LinkCalculation.class);
					linkCalc.putExtra("siteA", siteA);
					linkCalc.putExtra("siteB", siteB);
					linkCalc.putExtra("freq", link_data_txt_freq.getText().toString());
					Toast.makeText(LinkData.this, "Calculations", Toast.LENGTH_SHORT).show();
					startActivity(linkCalc);
				}
				else 
					Toast.makeText(LinkData.this, inputError, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void initWidgets(){
		this.link_data_site_a_lat_txt = (EditText) findViewById(R.id.link_data_site_a_lat_txt);
		this.link_data_site_a_lng_txt = (EditText) findViewById(R.id.link_data_site_a_lng_txt);
		
		this.link_data_site_b_lat_txt = (EditText) findViewById(R.id.link_data_site_b_lat_txt);
		this.link_data_site_b_lng_txt = (EditText) findViewById(R.id.link_data_site_b_lng_txt);
		
		this.link_data_site_a_name_txt = (EditText) findViewById(R.id.link_data_site_a_name_txt);
		this.link_data_site_b_name_txt = (EditText) findViewById(R.id.link_data_site_b_name_txt);
		
		this.link_data_site_a_txt_height = (EditText) findViewById(R.id.link_data_site_a_txt_height);
		this.link_data_site_b_txt_height = (EditText) findViewById(R.id.link_data_site_b_txt_height);
		
		this.link_data_txt_freq = (EditText) findViewById(R.id.link_data_txt_freq);
		
		this.link_data_site_a_gps_lat = (Button) findViewById(R.id.link_data_site_a_gps_lat);
		this.link_data_site_b_gps_lng = (Button) findViewById(R.id.link_data_site_b_gps_lat);
		this.link_data_site_a_map_lat = (Button) findViewById(R.id.link_data_site_a_map_lat);
		this.link_data_site_b_map_lat = (Button) findViewById(R.id.link_data_site_b_map_lat);
		this.link_data_btn_calculate = (Button) findViewById(R.id.link_data_btn_calculate);
	}

	private boolean validateData(){
		
		if (this.siteA == null || this.siteB == null){
			this.inputError = "Site A or site B are not properly defined.";
			return false;
		}
		if (this.link_data_txt_freq.getText().toString().equals("") 
				|| this.link_data_txt_freq.getText().equals(null)){
			this.inputError = "The frequancy is not set.";
			return false;
		}
		/*Site A*/
		if (siteA.getLat().equals(null) || siteA.getLat().equals(0.0)){
			this.inputError = "Site A latitude value not set.";
			return false;
		}
		else if (siteA.getLat() > 90 || siteA.getLat() < -90){
			this.inputError = "Site A latitude value is out of range.";
			return false;
		}
		if (siteA.getLng().equals(null) || siteA.getLng().equals(0.0)){
			this.inputError = "Site A longtitude value not set.";
			return false;
		}
		else if (siteA.getLng() > 180 || siteA.getLng() < -180){
			this.inputError = "Site A longtitude value is out of range.";
			return false;
		}
			
		if (siteA.getName().equals(null) || siteA.getName().equals("")){
			this.inputError = "Site A name is not set.";
			return false;
		}
		
		if (siteA.getHeight().equals(null) || siteA.getHeight() <= 0.0){
			this.inputError = "Site A antenna hight is out of range.";
			return false;
		}
		
		/*Site B*/			
		if (siteB.getLat().equals(null) || siteB.getLat().equals(0.0)){
			this.inputError = "Site B latitude value not set.";
			return false;
		}
		else if (siteB.getLat() > 90 || siteB.getLat() < -90){
			this.inputError = "Site B latitude value is out of range.";
			return false;
		}
		if (siteB.getLng().equals(null) || siteB.getLng().equals(0.0)){
			this.inputError = "Site B longtitude value not set.";
			return false;
		}
		else if (siteB.getLng() > 180 || siteB.getLng() < -180){
			this.inputError = "Site B longtitude value is out of range.";
			return false;
		}
			
		if (siteB.getName().equals(null) || siteB.getName().equals("")){
			this.inputError = "Site B name is not set.";
			return false;
		}
		
		if (siteB.getHeight().equals(null) || siteB.getHeight() <= 0.0){
			this.inputError = "Site B antenna hight is out of range.";
			return false;
		}
		
		return true;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	         if(resultCode == RESULT_OK){
	        	 Bundle bundle = data.getExtras();
	        	 
	        	 if (bundle != null) {
	        		 if (bundle.getString("side").equals("a")){
	        			 this.siteA = bundle.getParcelable("site");
	        			 this.link_data_site_a_lat_txt.setText(this.siteA.getLat().toString());
	        			 this.link_data_site_a_lng_txt.setText(this.siteA.getLng().toString());
	        			 this.link_data_site_a_name_txt.setText(this.siteA.getName());
	        		 }
	        		 else if (bundle.getString("side").equals("b")) {
	        			 this.siteB = bundle.getParcelable("site");
	        			 this.link_data_site_b_lat_txt.setText(this.siteB.getLat().toString());
	        			 this.link_data_site_b_lng_txt.setText(this.siteB.getLng().toString());
	        			 this.link_data_site_b_name_txt.setText(this.siteB.getName());
	        		 }
	        		 Toast.makeText(this, bundle.getString("side"), Toast.LENGTH_SHORT).show();
	        	 }
	         }
	    }
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.link_data, menu);
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
