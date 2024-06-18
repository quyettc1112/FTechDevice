package com.example.ftechdevice.UI.Activity.MapActivity;

import androidx.fragment.app.FragmentActivity;

import android.net.Uri;
import android.os.Bundle;


import com.example.ftechdevice.AppConfig.CustomView.CustomDialog.ConfirmDialog;
import com.example.ftechdevice.AppConfig.CustomView.CustomToolBar.CustomToolbar;
import com.example.ftechdevice.Common.Constants.Constants;
import com.example.ftechdevice.Common.DirectionHelper.FetchURL;
import com.example.ftechdevice.Common.DirectionHelper.TaskLoadedCallback;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ftechdevice.databinding.ActivityMapsBinding;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback , TaskLoadedCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private AutocompleteSupportFragment autocompleteSupportFragment;
    private MapsViewModels mapsViewModels;
    private LatLng currentLatLng;
    private LatLng destination = new LatLng(10.8802, 106.8055);
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapsViewModels = new ViewModelProvider(this).get(MapsViewModels.class);

        Places.initialize(getApplicationContext(), "AIzaSyCpmrOW6NygOmu6Sz2_k2LIH_n963Q8nKc");
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showDirection();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.customToolbar.setOnStartIconClickListener(new CustomToolbar.OnStartIconClickListener() {
            @Override
            public void onStartIconClick() {
                finish();
            }
        });
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void showDirection() {
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchURL(MapsActivity.this).execute(getUrl(currentLatLng,destination, "driving"), "driving");

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(currentLatLng);
                builder.include(destination);
                LatLngBounds bounds = builder.build();

                // Set the padding around the bounds (in pixels)
                int padding = 200; // Adjust as needed

                // Move the camera to fit the bounds
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (isLocationPermissionGranted()) {
            showCurrentLocation();


            findPlace();




        } else {
            requestLocationPermission();
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=AIzaSyCpmrOW6NygOmu6Sz2_k2LIH_n963Q8nKc";
        return url;
    }





    private void findPlace() {
        autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.autoComplete_fragment);
        autocompleteSupportFragment.setPlaceFields(List.of(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                //Toast.makeText(MapsActivity.this, status.getStatusMessage().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), DEFAULT_ZOOM));
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
            }
        });
    }


    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCurrentLocation();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                Intent intentToSetting = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                ConfirmDialog confirmDialog = new ConfirmDialog(
                        this,
                        new ConfirmDialog.ConfirmCallback() {
                            @Override
                            public void negativeAction() {
                                startActivity(intent);
                            }

                            @Override
                            public void positiveAction() {
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intentToSetting.setData(uri);
                                startActivity(intentToSetting);
                            }
                        },
                        "Cannot get location",
                        "Grant permission to access location",
                        "Yes",
                        "No"
                );
                confirmDialog.show();
            }
        }
    }

    private void showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            );
            return;
        }
        mMap.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(currentLatLng).title("Current Location"));
                mMap.addMarker(new MarkerOptions().position(destination).title("Shop Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM));
            }
        });
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;
}