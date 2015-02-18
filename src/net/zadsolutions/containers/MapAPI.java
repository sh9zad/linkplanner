package net.zadsolutions.containers;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class MapAPI {
	private String _URL = "https://maps.googleapis.com/maps/api/elevation/";
	
	public String _SRC;
	public String _DEST;
	public String _SAMPLES;
	public String _TYPE = "json";
	public String _KEY = "";
	public String _SENSOR = "false";
	
	public MapAPI(String src, String dest, String samples){
		this._SRC = src;
		this._DEST = dest;
		this._SAMPLES = samples;
	}
	
	public List<NameValuePair> getData(){
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("path", this._SRC.toString() + "|" + this._DEST.toString()));
		data.add(new BasicNameValuePair("samples", this._SAMPLES.toString()));
		data.add(new BasicNameValuePair("sensor", "false"));
		
		return data;
	}
	
	public String getURL(){
		return this._URL.toString() + this._TYPE.toString() + "?" + URLEncodedUtils.format(this.getData(), "utf-8");
	}
}
