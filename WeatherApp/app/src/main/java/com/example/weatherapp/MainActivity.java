package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText city;
    TextView temp;
    TextView weather;
    ImageView wcon;
    TextView sr;
    TextView ss;

    public void load(View v) {

        // use https for higher android versions
        String c = city.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + c + "&appid=71a5eac806cbd7df1553aaeda2ce7893";
        //String url2 = "https://api.openweathermap.org/data/2.5/weather?q=Pune&appid=71a5eac806cbd7df1553aaeda2ce7893";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // weather object index 1 ( id, main, description, icon)
                            JSONObject jr = response.getJSONArray("weather").getJSONObject(0);
                            weather.setText(jr.get("main").toString());
                            Glide.with(getApplicationContext()) // replace with 'this' if it's in activity
                                    .load("https://openweathermap.org/img/wn/"+ jr.get("icon").toString() +"@2x.png")
                                    .into(wcon);
                         /*   URL icon = new URL("https://openweathermap.org/img/wn/"+ jr.get("icon").toString() +"@2x.png");
                            Bitmap bmp = BitmapFactory.decodeStream(icon.openConnection().getInputStream());
                            wcon.setImageBitmap(bmp); */

                            // main object ( temp, temp_min, temp_max, pressure, humidity)
                            double k = Math.floor(response.getJSONObject("main").getDouble("temp"));
                            int c = (int)(k - 273);
                            temp.setText( "" + c);

                            // sys object ( country, sunrise, sunset)
                            JSONObject sys = response.getJSONObject("sys");
                            sys.getLong("sunrise");
                            Date time = new Date((long)sys.getLong("sunrise")*1000);
                            sr.setText(time.toString());
                            time.setTime((long)sys.getLong("sunset")*1000);
                            ss.setText(time.toString());


                        } catch (JSONException  ex) {
                            Toast.makeText(getApplicationContext(), ex.getMessage() + 100 , Toast.LENGTH_SHORT).show();
                            city.setText("");
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        // cannot use this
                        Toast.makeText(getApplicationContext(), error.toString() + 101 , Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.city);
        temp = findViewById(R.id.city_temp);
        weather = findViewById(R.id.weather);
        wcon = findViewById(R.id.weather_icon);
        sr = findViewById(R.id.sunrisetime);
        ss = findViewById(R.id.sunsettime);
    }

}