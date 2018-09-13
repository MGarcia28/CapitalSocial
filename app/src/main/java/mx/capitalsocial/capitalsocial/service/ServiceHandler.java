package mx.capitalsocial.capitalsocial.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

@SuppressLint("NewApi")
public class ServiceHandler {

	private static InputStream json = null;
	public final static int GET = 1;
    public final static int POST = 2;        

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static InputStream makeServiceCall(String url, int metodo, String jsonString) {
		
	   try {	
			
			HttpResponse httpResponse = null;			
			DefaultHttpClient httpClient = new DefaultHttpClient();	
			
			ClientConnectionManager con = httpClient.getConnectionManager();  			
			HttpParams paramsServer = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(paramsServer, 4000);
			httpClient = new DefaultHttpClient(con, paramsServer);

			getPolicyOff();	
								
			if (metodo == POST) {
							
				    HttpPost httpPost = new HttpPost(url);
					httpPost.setHeader("Content-type", "application/json");
					httpPost.setHeader("SO","Android");
					httpPost.setHeader("Version","2.5.2");

					httpPost.setEntity(new StringEntity(jsonString, "UTF-8"));

					httpResponse = httpClient.execute(httpPost);
					Log.d("HANDLER", "METODO POST" + httpResponse.toString());
						
			} else if (metodo == GET) {
				    HttpGet httpGet = new HttpGet(url);
				    httpGet.addHeader("Content-Type","application/json");

					httpResponse = httpClient.execute(httpGet);
					Log.d("HANDLER", "METODO GET" + httpResponse.toString());
			}			
			
			StatusLine statusLine = httpResponse.getStatusLine();
			 
			if (statusLine.getStatusCode() == 200) {
				   
			     HttpEntity entity = httpResponse.getEntity();
			     json = entity.getContent();
			       
			} else {
			   	 Log.d("JSON", "Failed to download file");
				return json = null;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return json = null;
		} catch (ClientProtocolException e) {
			Log.d("EXCEPCION1" , "SIN RESPUESTA");
			e.printStackTrace();	
			return json = null;
		} catch (IOException e) {
			Log.d("EXCEPCION2" , "SIN RESPUESTA " + json);
			e.printStackTrace();
			return json = null;
		}
		return json;
	}
	
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public static void getPolicyOff() {		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);		  
	}
	
}
