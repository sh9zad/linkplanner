package net.zadsolutions.utility;

import java.util.ArrayList;

import net.zadsolutions.containers.Site;

public class LinkCalculator {
	
	private final Double earth_radius = 6372.795477598; // in km
	private final Integer c_speed = 299792250; // in meters per hour
	
	private Float m_freuancy;
	private Site  m_siteA, m_siteB;
	private Long m_distnce;
	
	public LinkCalculator(){}
	public LinkCalculator(Float freq, Site A, Site B){
		this.m_freuancy = freq;
		this.m_siteA = A;
		this.m_siteB = B;
		this.m_distnce = this.distance();
	}

	public Float getFrequancy(){
		return this.m_freuancy;
	}
	public void setFrequancy(Float freq){
		this.m_freuancy = freq;
	}
	
	public Site getSiteA(){
		return this.m_siteA;
	}
	public void setSiteA(Site A){
		this.m_siteA = A;
	}
	
	public Site getSiteB(){
		return this.m_siteB;
	}
	public void setSiteB(Site B){
		this.m_siteB = B;
	}

	public Long distance(){
		Float delta_lat = this.m_siteA.getLat() - this.m_siteB.getLat();
		Float delta_lon = this.m_siteA.getLng() - this.m_siteB.getLng();
		
		Float alpha    = delta_lat/2;
		Float beta     = delta_lon/2;
		Double a        = Math.sin(Math.toDegrees(alpha)) * Math.sin(Math.toDegrees(alpha)) 
				+ Math.cos(Math.toDegrees(this.m_siteA.getLat())) * Math.cos(Math.toDegrees(this.m_siteB.getLat())) 
				* Math.sin(Math.toDegrees(beta)) * Math.sin(Math.toDegrees(beta)) ;
		Double c        = Math.asin((Math.min(1, Math.sqrt(a))));
		Double distance = 2*this.earth_radius * c;
		this.m_distnce = Math.round(distance);
	
		//echo $distance;
		return this.m_distnce;
	}

	public void getPL(){
		Double pl = 32.45 + (20*Math.log10(this.m_freuancy)) + (20*Math.log10(this.m_distnce));
		Double r = 8.657* Math.sqrt(this.m_distnce/this.m_freuancy);
		//return array("PL"=>$pl, "R"=>$r);
	}
	
	public ArrayList<Double> getFZ(){
		ArrayList<Double> res = new ArrayList<Double>();
		
		Double ln = (double) (this.c_speed /(this.m_freuancy*1000));
		Double dest_in_meters = (double) (this.m_distnce * 1000);
		//echo "distance = $destM<br/>";
		Integer dS = 0;
		Double dF = dest_in_meters;
		int i = 0;
		Integer step = (int) Math.round(((dest_in_meters)/150));
		
		while (i < dest_in_meters){
			double d1 = (i + dS)/1000;
			double d2 = (dF - i)/1000;
			
			double fz = Math.round(Math.sqrt(ln*d1*d2/m_distnce));
			res.add(fz);
			//echo "$i = $fz <br/>";
			i += step;
		}
		//print_r($res);
		return res;
	}
}
