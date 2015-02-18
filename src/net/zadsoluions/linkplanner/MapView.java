package net.zadsoluions.linkplanner;
import net.zadsolutions.containers.Site;
import net.zadsolutions.services.GPSTracker;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.*;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MapView extends FragmentActivity {
	
	TextView lblSiteName;
	EditText txtLat, txtLng;
	Button btnSaveCoordinate;
	
	Site site;
	Double lat, lng;
	String side;
	
	GPSTracker gpsTracker;
	LatLng position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_view);
		
		this.initActivity();
    	
    	this.btnSaveCoordinate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				site.setLat(Float.valueOf(txtLat.getText().toString()));
				site.setLng(Float.valueOf(txtLng.getText().toString()));
				Toast.makeText(MapView.this, "Coordinates saved", Toast.LENGTH_SHORT).show();
				
				Intent data = new Intent();
				data.putExtra("site", site);
				data.putExtra("side", side);
				setResult(RESULT_OK, data) ;	
				finish(); // ends current activity				
			}
		});
	}

	private void initActivity(){
		this.getWidgets();
		
		this.gpsTracker = new GPSTracker(MapView.this);
		this.lat = gpsTracker.latitude;
		this.lng = gpsTracker.longitude;
		this.site = new Site("Site", Float.valueOf(this.lat.toString()), Float.valueOf(this.lng.toString()), Float.valueOf(String.valueOf(0.0)));
		
		
		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null) {
			site = bundle.getParcelable("site");
			side = bundle.getString("side");
			
			if (!site.getLat().toString().equals("0.0") && !site.getLng().toString().equals("0.0")){
				this.lat = Double.valueOf(site.getLat());
				this.lng = Double.valueOf(site.getLng());
			}
		}
		
		this.txtLat.setText( this.lat.toString() );
		this.txtLng.setText( this.lng.toString() );
		this.lblSiteName.setText(this.site.getName().toString());
		
		this.position = new LatLng(this.lat, this.lng);
		initMap();
	}
	
	private void getWidgets(){
		this.txtLat = (EditText) findViewById(R.id.mapview_txt_lat);
		this.txtLng = (EditText) findViewById(R.id.mapview_txt_lng);
		this.btnSaveCoordinate = (Button) findViewById(R.id.mapview_btn_save_gps);
		this.lblSiteName = (TextView) findViewById(R.id.mapview_lbl_site_name);
	}
	
	private void initMap(){
		// Get a handle to the Map Fragment
        GoogleMap map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));
//
//        map.addMarker(new MarkerOptions()
//                .title("Tehran")
//                .snippet("Shervin application.")
//                .position(position));
        
    	map.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng point) {

				Toast.makeText(MapView.this, String.valueOf(point.latitude), Toast.LENGTH_SHORT).show();
				txtLat.setText(String.valueOf(point.latitude));
				txtLng.setText(String.valueOf(point.longitude));
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_view, menu);
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
