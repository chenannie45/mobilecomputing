package edu.asu.mc29.mywardrobe.service;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import edu.asu.mc29.mywardrobe.data.Channel;

/**
 * Created by cchen211 on 10/28/2015.
 */
public class YahooWeatherService {
    private  WeatherServiceCallback callback;
    private String location;
    private Exception error;

    public YahooWeatherService(WeatherServiceCallback callback){
        this.callback = callback;
    }

    public String getLocation(){
        return location;
    }

    public void refreshWeather(String loc){
        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... strings) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")",strings[0]);
                //String YQL ="select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"tempe, Arizona\")";
                String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try{
                    Log.d("location",strings[0]);
                    Log.d("url",endPoint);
                    URL url = new URL(endPoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        result.append(line);
                    }
                    return  result.toString();
                }catch (Exception e){
                   error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null && error != null){
                    callback.serviceFailure(error);
                    return;
                }

                try{
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResult = data.optJSONObject("query");
                    int count = queryResult.optInt("count");
                    if(count == 0){
                        callback.serviceFailure(new LocationWeatherException("No weather info for " + location));
                        return;
                    }
                    JSONObject results = queryResult.optJSONObject("results");
                    Channel channel = new Channel();
                    channel.populate(results.optJSONObject("channel"));
                    callback.serviceSuccess(channel);
                }catch (JSONException e){
                    callback.serviceFailure(e);
                }
            }
        }.execute(loc);
    }

    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String detailMessage){
            super(detailMessage);
        }
    }
}
