package net.zadsolutions.customs;

import net.zadsoluions.linkplanner.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainGVCustomAdapter extends BaseAdapter {
	
	private Context m_context;
	private final String[] m_titles;
	private final int[] m_images;
	
	public MainGVCustomAdapter(Context context, String[] titles, int[] images){
		this.m_context = context;
		this.m_titles = titles;
		this.m_images = images;
	}

	@Override
	public int getCount() {

		return this.m_titles.length;
	}

	@Override
	public Object getItem(int arg0) {

		return null;
	}

	@Override
	public long getItemId(int arg0) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View grid;
	      LayoutInflater inflater = (LayoutInflater) this.m_context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	          if (convertView == null) {
	            grid = new View(this.m_context);
	        grid = inflater.inflate(R.layout.main_gv_item, null);
	            TextView textView = (TextView) grid.findViewById(R.id.main_gv_item_lbl);
	            ImageView imageView = (ImageView)grid.findViewById(R.id.main_gv_item_iv);
	            textView.setText(this.m_titles[position]);
	            imageView.setImageResource(this.m_images[position]);
	          } else {
	            grid = (View) convertView;
	          }
	      return grid;
	}

}
