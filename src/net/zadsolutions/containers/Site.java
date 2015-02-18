package net.zadsolutions.containers;

import android.os.Parcel;
import android.os.Parcelable;

public class Site implements	 Parcelable {
	
	private String m_name;
	private Float m_lat;
	private Float m_lng;
	private Float m_height;
	
	public Site(String name, Float lat, Float lng, Float height){
		this.m_lat = lat;
		this.m_lng = lng;
		this.m_name = name;
		this.m_height = height;
	}
	public Site(Parcel parcel){
		this.m_name = parcel.readString();
		this.m_lat = parcel.readFloat();
		this.m_lng = parcel.readFloat();
		this.m_height = parcel.readFloat();
	}
	public Site(){
		
	}
	
	public String getName(){
		return this.m_name;
	}
	public void setName(String name){
		this.m_name = name;
	}
	
	public Float getLat(){
		return this.m_lat;
	}
	public void setLat(Float lat){
		this.m_lat = lat;
	}
	
	public Float getLng(){
		return this.m_lng;
	}
	public void setLng(Float lng){
		this.m_lng = lng;
	}

	public Float getHeight(){
		return this.m_height;
	}
	public void setHieght(Float height){
		this.m_height = height;
	}
	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(this.m_name);
        parcel.writeFloat(this.m_lat);
        parcel.writeFloat(this.m_lng);
        parcel.writeFloat(this.m_height);
	}
	
    // THIS IS ALSO NECESSARY
    public static final Creator<Site> CREATOR = new Creator<Site>() {
        @Override
        public Site createFromParcel(Parcel parcel) {
            return new Site(parcel);
        }

        @Override
        public Site[] newArray(int i) {
            return new Site[0];
        }
    };
}
