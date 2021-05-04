package no.hvl.dat110.aciotdevice.client;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {

	public RestClient() {
	}

	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static String logpath = "/accessdevice/log/";

	private String host = Configuration.host;
	private int port = Configuration.port;

	private String url = "http://" + host + ":" + port;
	private String urlLog = url + logpath;

	public void doPostAccessEntry(String message) {
		
		String jsonString = new Gson().toJson(new AccessMessage(message));

		RequestBody reqBody = RequestBody.create(JSON, jsonString);

		OkHttpClient client = new OkHttpClient();

		Request req = new Request.Builder().url(urlLog).post(reqBody).build();

		try (Response res = client.newCall(req).execute()) {
			System.out.println(res.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String codepath = "/accessdevice/code";

	public AccessCode doGetAccessCode() {
		
		AccessCode code = null;
		
		GsonBuilder builder = new GsonBuilder(); 
		
		Gson gson = builder.create();
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder().url(url + codepath).get().build();
		
		try (Response response = client.newCall(request).execute()){
			 String resbody = response.body().string(); 
			 
			code = gson.fromJson(resbody, AccessCode.class);
		}
		catch (Exception e){
			e.printStackTrace();
	}
		return code;
	}
}
