package edu.asu.mc29.mywardrobe;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.asu.mc29.mywardrobe.data.Channel;
import edu.asu.mc29.mywardrobe.data.Item;
import edu.asu.mc29.mywardrobe.service.WeatherServiceCallback;
import edu.asu.mc29.mywardrobe.service.YahooWeatherService;


public class LocalWeather extends AppCompatActivity implements LocationListener, WeatherServiceCallback{

    protected LocationManager locationManager;
    //private TextView cityLat;
    protected String finalAddress;
    private ImageView weatherIconImageView;
    private  TextView tempretureTextView;
    private  TextView conditionTextView;
    private TextView locationTextView;
    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_weather);

        weatherIconImageView = (ImageView)findViewById(R.id.weatherImageView);
        tempretureTextView = (TextView)findViewById(R.id.tempretureTextView);
        conditionTextView = (TextView)findViewById(R.id.conditionTextView);
        locationTextView = (TextView)findViewById(R.id.locationTextView);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, this);
        }
        catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();

        int resourcesID = getResources().getIdentifier("drawable/i"+channel.getItem().getCondition().getCode(),null,getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourcesID);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        tempretureTextView.setText(item.getCondition().getTempreture() + "\u00b0" + channel.getUnits().getTempreture());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(finalAddress);


    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.d("Latitude",Double.toString(location.getLatitude()));
        //locationTextView.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        try {
            List<Address> address = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if(address.size() > 0 ){
                finalAddress = address.get(0).getLocality()+", "+address.get(0).getAdminArea();
                service = new YahooWeatherService(this);
                service.refreshWeather(finalAddress);
            }
            //cityLat = (TextView)findViewById(R.id.cityTextView);
            //cityLat.setText(finalAddress); //This will display the final address.

        } catch (IOException e) {}
        catch (NullPointerException e) {}
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_local_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
