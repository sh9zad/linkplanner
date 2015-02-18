package net.zadsolutions.services;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.zadsolutions.containers.SamplePoints;
import net.zadsolutions.containers.MapAPI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

public class WSHandler extends AsyncTask<MapAPI, String, JSONObject> {
	
	private static final int REGISTRATION_TIMEOUT = 3 * 1000;
	private static final int WAIT_TIMEOUT = 30 * 1000;
	private final HttpClient httpclient = new DefaultHttpClient();

	public String rescontent;
	private JSONObject json = null;

	final HttpParams params = httpclient.getParams();
	HttpResponse response;

	private String content = null;
	private boolean error = false;

	public ProgressDialog dialog;
	private Context context;


	public WSHandler(Context context) {
		this.context = context;
	}

	protected void onCancelled() {

	}

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(this.context);
		dialog.setMessage("اتصال به سرور...");
		dialog.setIndeterminate(true);
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected void onPostExecute(JSONObject result) {

		super.onPostExecute(result);
		try {
			String sr = json.getString("status");

			if (json.getString("status").equals("OK")) {

				ArrayList<SamplePoints> points = new ArrayList<SamplePoints>();
				//String login = json.getString("login");
				JSONArray results = json.getJSONArray("results");
				for(int i = 0; i < results.length(); i++){
					points.add(new SamplePoints(
							Float.valueOf(results.getJSONObject(i).getJSONObject("location").getString("lat").toString()),
							Float.valueOf(results.getJSONObject(i).getJSONObject("location").getString("lng").toString()), 
							Float.valueOf(results.getJSONObject(0).getString("elevation").toString())
							)
					);
				}
				sr = String.valueOf(results.length());
				
				Toast.makeText(context, sr, 1).show();
			}
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		}

		this.dialog.dismiss();

	}

	@SuppressLint("NewApi")
	protected JSONObject doInBackground(MapAPI... postrequests) {
		String URL = null;

		try {
			URL = postrequests[0].getURL();
			HttpConnectionParams.setConnectionTimeout(params,
					REGISTRATION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
			ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

			HttpPost httpPost = new HttpPost(URL);

			// Any other parameters you would like to set
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

//			httpPost.setEntity(new UrlEncodedFormEntity(postrequests[0]._Data,
//					"UTF-8"));

			// Response from the Http Request
			HttpGet myGet = new HttpGet(postrequests[0].getURL());
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(myGet);

			StatusLine statusLine = response.getStatusLine();
			// Check the Http Request for success
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				content = out.toString();

			} else {
				// Closes the connection.
				Log.w("HTTP1:", statusLine.getReasonPhrase());
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			Log.w("HTTP2:", e);
			content = e.getMessage();
			error = true;
			cancel(true);
		} catch (IOException e) {
			Log.w("HTTP3:", e);
			content = e.getMessage();
			error = true;
			cancel(true);
		} catch (Exception e) {
			Log.w("HTTP4:", e);
			content = e.getMessage();
			error = true;
			cancel(true);
		}

		this.rescontent = content;
		try {
			// JSONArray j_arry = new JSONArray(content);
			json = new JSONObject(content);
			
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			// this.error = "4: " + e.getMessage();
			return null;
		}

		// return JSON String
		return json;
		// return content;
	}
}
