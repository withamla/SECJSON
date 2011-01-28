package com.schindler.us.secjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidHTTPClient extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Button button = (Button) findViewById(R.id.btnGetContact);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				final EditText edittext = (EditText) findViewById(R.id.entry);
				TextView textView = (TextView) findViewById(R.id.outputTextView);
				String url = URL;
							RestClient client = new RestClient(
						url);
				try {
					client.Execute(RequestMethod.GET);
				} catch (Exception e) {
					textView.setText(e.getMessage());
					Toast.makeText(AndroidHTTPClient.this, "error",
							Toast.LENGTH_SHORT).show();
				}

				String response = client.getResponse();
				textView.setText(response);
				/**
				String x = "";
				try {
					JSONArray entries = new JSONArray(response);
					 x = "JSON parsed.\nThere are [" + entries.length() + "]\n\n";

		            int i;
		            for (i=0;i<entries.length();i++)
		            {
		                JSONObject post = entries.getJSONObject(i);
		                x += "------------\n";
		                x += "UUID:" + post.getString("uuids") + "\n";
		          		            }
		            textView.setText(x);

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					 textView.setText("Error w/file: " + e1.getMessage());

				}
				**/
			

				JSONObject jObject = new JSONObject();
				try {
					jObject = new JSONObject(response);
					JSONArray uuidArray = jObject.getJSONArray("uuids");
					String sUuid = uuidArray.getString(0);
					textView.setText(sUuid);
					JSONObject couchObject = new JSONObject();
					couchObject.put("_id", sUuid);
					couchObject.put("firstName","Larry");
					couchObject.put("lastName","Test");
					String url = URL;
					DefaultHttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);
					
					StringEntity se;
					try {
						se = new StringEntity(couchObject.toString());
						httppost.setEntity(se);
						httppost.setHeader("Content-type", "application/json");
						HttpResponse postResponse;
						try {
							postResponse = httpclient.execute(httppost);
							
							HttpEntity entity = postResponse.getEntity();
							 
				            if (entity != null)
				            {
				 			InputStream is = entity.getContent();
				 			
				 			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				 	        StringBuilder sb = new StringBuilder();
				 	 
				 	        String line = null;
				 	        try
				 	        {
				 	            while ((line = reader.readLine()) != null)
				 	            {
				 	                sb.append(line + "\n");
				 	            }
				 	        }
				 	        catch (IOException e)
				 	        {
				 	            e.printStackTrace();
				 	        }
				 	        finally
				 	        {
				 	            try
				 	            {
				 	                is.close();
				 	            }
				 	            catch (IOException e)
				 	            {
				 	                e.printStackTrace();
				 	            }
				 	        }
            
				 
				                // Closing the input stream will trigger connection release
				                is.close();
				            	textView.setText(sb.toString());
				            }
							
							
						
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							textView.setText(e.getMessage());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							textView.setText(e.getMessage());
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						textView.setText(e.getMessage());
					}
					
//					httppost.setHeader("Accept", "application/json");
					
				
					/**
					RestClient putclient = new RestClient(
					"http://thinkre.couchone.com/automath");
					try {
						client.Execute(RequestMethod.POST);
					} catch (Exception e) {
						textView.setText(e.getMessage());
						Toast.makeText(AndroidHTTPClient.this, "error",
								Toast.LENGTH_SHORT).show();
					}

					String putresponse = putclient.getResponse();
					textView.setText(putresponse);
					**/
				}  catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
}