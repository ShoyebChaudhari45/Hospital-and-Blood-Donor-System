package com.example.hospitalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.hospitalproject.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Button btn_satellite, btn_terrain, btn_default, btn_hybrid;

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int Request_code = 101;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btn_satellite = findViewById(R.id.btn_satellite);
        btn_terrain = findViewById(R.id.btn_terrain);
        btn_default = findViewById(R.id.btn_default);
        btn_hybrid = findViewById(R.id.btn_hybrid);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Map type button listeners
        btn_satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
            }
        });
        btn_terrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
            }
        });
        btn_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });
        btn_hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Adding markers for various hospitals
        LatLng jalna = new LatLng(19.82338030852863, 75.8821715381331);
        mMap.addMarker(new MarkerOptions().position(jalna).title("Jalna Hospital"));
        LatLng MJM = new LatLng(19.877268, 75.355326);
        mMap.addMarker(new MarkerOptions().position(MJM).title("MGM Hospital Aurangabad"));
        LatLng sai = new LatLng(19.845056448715102, 75.92262522279286);
        mMap.addMarker(new MarkerOptions().position(sai).title("Sai Hospital Jalna"));
        LatLng sanjivani = new LatLng(19.825284586278926, 75.8832385974424);
        mMap.addMarker(new MarkerOptions().position(sanjivani).title("Sanjeevani Multispeciality Hospital"));
        LatLng sahydri = new LatLng(19.84019077964779, 75.89008439265905);
        mMap.addMarker(new MarkerOptions().position(sahydri).title("Sahyadri Hospital"));
        LatLng deepak = new LatLng(19.842295810706624, 75.88607164442494);
        mMap.addMarker(new MarkerOptions().position(deepak).title("Deepak Hospital"));
        LatLng district = new LatLng(19.833148465986877, 75.87469331168828);
        mMap.addMarker(new MarkerOptions().position(district).title("District Hospital, Jalna"));
        LatLng varkad = new LatLng(19.860136055907628, 75.88854194263683);
        mMap.addMarker(new MarkerOptions().position(varkad).title("Warkad Hospital"));
        LatLng mission = new LatLng(19.84992293930596, 75.90543310732555);
        mMap.addMarker(new MarkerOptions().position(mission).title("Jalna Mission Hospital"));
        LatLng dq = new LatLng(19.868505191115858, 75.4040262380197);
        mMap.addMarker(new MarkerOptions().position(dq).title("District Hospital, Aurangabad"));

        // Set default camera position and zoom level
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jalna, 15), 5000, null);

        // Adding listener for marker click events
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Move and zoom the camera to the marker's position with animation
                LatLng markerPosition = marker.getPosition();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 18), 2000, null);
                return true; // Return true to indicate that the event has been handled
            }
        });

        // Get the current location of the user
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_code);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Toast.makeText(getApplicationContext(), "Location result is= " + locationResult, Toast.LENGTH_LONG).show();
                if (locationRequest == null) {
                    Toast.makeText(getApplicationContext(), "Current location is null", Toast.LENGTH_LONG).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        Toast.makeText(getApplicationContext(), "Current location is: " + location.getLongitude(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(@NonNull Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();

                    LatLng latLng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("My location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Request_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }
}
