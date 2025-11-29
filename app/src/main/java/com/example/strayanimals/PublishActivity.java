package com.example.strayanimals;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.strayanimals.data.Post;
import com.example.strayanimals.data.model.AddressInfo;
import com.example.strayanimals.databinding.ActivityAnimaldetailBinding;
import com.example.strayanimals.databinding.ActivityPublishBinding;
import com.example.strayanimals.ui.post.PostSQLHelper;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class PublishActivity extends AppCompatActivity {
    private PostSQLHelper sqlHelper;
    private ActivityPublishBinding binding;
    private LocationManager locationManager;
    private Location location;
    private AddressInfo addressInfo;
    private LocationListener locationListener;
    private Toast toast;

    private void initSQL() {
        sqlHelper = new PostSQLHelper(this);
        try {
            sqlHelper.getReadableDatabase().query("post", null, null, null, null, null, null);
        } catch(Exception e) { //表单异常
            finish();
        }
    }

    private void initLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                PublishActivity.this.location = location;
                //Log.i("Loc", "Get Location: " + location.toString());
                //showToast("定位成功");
                new GetAddressTask().execute(location);
                showToast(addressInfo.getResult().getFormattedAddress());
                locationManager.removeUpdates(this);
            }
        };
    }

    private boolean isLocatePermitted() { return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED; }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocatePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION }, 100);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        showToast("定位中……");
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 1, locationListener);
        } else {
            //showToast("定位成功");
            //Log.i("Loc", "Get Location: " + location.toString());
            new GetAddressTask().execute(location);
            //showToast(addressInfo.getResult().getFormattedAddress());
        }
    }

    private class GetAddressTask extends AsyncTask<Location, Float, AddressInfo> {

        @Override
        protected AddressInfo doInBackground(Location... locations) {
            return getAddress(locations[0]);
        }

        @Override
        protected void onPostExecute(AddressInfo addressInfo) {
            super.onPostExecute(addressInfo);

            PublishActivity.this.addressInfo = addressInfo;
            PublishActivity.this.binding.locationText.setText(addressInfo.getResult().getFormattedAddress());
            showToast("定位成功");
        }

        private AddressInfo getAddress(Location location) {
            StringBuilder stringBuilder = new StringBuilder();
            String addrJson;
            try {
                URL url = new URL("https://api.map.baidu.com/geocoder?output=json&location="+location.getLatitude()+","+location.getLongitude()+"&ak=esNPFDwwsXWtsQfw4NMNmur1");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");//使用GET方法获取
                connection.setConnectTimeout(5000);
                int code = connection.getResponseCode();

                if (code == 200) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String b;
                    while ((b = bufferedReader.readLine()) != null) {
                        stringBuilder.append(b).append("\n");
                    }
                    inputStream.close();
                    addrJson = stringBuilder.toString();
                } else {
                    showToast("获取地址时出错");
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return new Gson().fromJson(addrJson, AddressInfo.class);
        }
    }

    private void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    private void addPost(String title, String content, Location location, AddressInfo addressInfo) {
        sqlHelper.addPost(new Post(title, content, getIntent().getStringExtra("username"), location != null ? location : new Location(""), addressInfo != null ? addressInfo.getResult().getFormattedAddress() : ""));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish);

        initSQL();
        initLocationManager();

        binding = ActivityPublishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText contentEditor = binding.content;
        EditText titleEditor = binding.title;
        ImageView backButton = binding.back;
        backButton.setOnClickListener(view -> finish());

        Button publishBtn = binding.publish;
        publishBtn.setOnClickListener(view -> {
            if (contentEditor.getText().toString().length() == 0) {
                showToast("内容不能为空！");
                return;
            }
            addPost(titleEditor.getText().toString(), contentEditor.getText().toString(), location, addressInfo);
            //Intent intent = new Intent(this, MainActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            //startActivityIfNeeded(intent, 0);
            finish();
        });

        LinearLayout locate = binding.locate;
        locate.setOnClickListener(view -> {
            if (!isLocatePermitted()) {
                getLocatePermission();
            }
            getLocation();
        });

        LinearLayout hashtag = binding.hashtag;
        hashtag.setOnClickListener(view -> contentEditor.setText(contentEditor.getText() + "#"));

        LinearLayout at = binding.at;
        at.setOnClickListener(view -> contentEditor.setText(contentEditor.getText() + "@"));
    }
}