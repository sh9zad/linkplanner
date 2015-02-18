package net.zadsoluions.linkplanner;

import net.zadsolutions.containers.Site;
import net.zadsolutions.utility.LinkCalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class LinkCalculation extends ActionBarActivity {

	Bundle bundle;
	Site siteA, siteB;
	Integer frequancy;
	LinkCalculator linkCalculator;
	
	TextView calc_lbl_distance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link_calculation);
		
		this.initWidgets();
		this.initActivity();
		this.calculateLink();
	}
	
	private void initWidgets(){
		this.calc_lbl_distance = (TextView) findViewById(R.id.calc_lbl_distance);
	}
	
	private void initActivity(){
		this.siteA = new Site();
		this.siteB = new Site();
		
		this.bundle = this.getIntent().getExtras();
		if (this.bundle != null){
			this.siteA = this.bundle.getParcelable("siteA");
			this.siteB = this.bundle.getParcelable("siteB");
			this.frequancy = Integer.valueOf(this.bundle.getString("freq"));
			this.linkCalculator = new LinkCalculator(Float.valueOf(this.frequancy), this.siteA, this.siteB);
			//Toast.makeText(LinkCalculation.this, this.siteA.getName() + ":" + this.frequancy.toString(), Toast.LENGTH_SHORT).show();
		}
		else {
			this.linkCalculator = new LinkCalculator();
		}
	}

	private void calculateLink(){
		linkCalculator.setFrequancy(Float.valueOf(this.frequancy));
		linkCalculator.setSiteA(this.siteA);
		linkCalculator.setSiteB(this.siteB);
		String str = this.linkCalculator.distance().toString();
		this.calc_lbl_distance.setText(str);
		//Toast.makeText(LinkCalculation.this, str, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.link_calculation, menu);
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
