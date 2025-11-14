package com.example.lab14;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private List<Marker> markers = new ArrayList<>();
    private MyLocationNewOverlay myLocationOverlay;
    private final EnumMap<MapType, ImageButton> mapTypeButtons = new EnumMap<>(MapType.class);
    private MapType currentMapType;

    private ImageButton btnNormal, btnSatellite, btnHybrid, btnTerrain;
    private ImageButton btnMyLocation, btnAddMarker, btnClearMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize osmdroid configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main_lab14);

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize map
        mMap = findViewById(R.id.map);
        setupMap();

        // Initialize buttons
        btnNormal = findViewById(R.id.btnNormal);
        btnSatellite = findViewById(R.id.btnSatellite);
        btnHybrid = findViewById(R.id.btnHybrid);
        btnTerrain = findViewById(R.id.btnTerrain);
        btnMyLocation = findViewById(R.id.btnMyLocation);
        btnAddMarker = findViewById(R.id.btnAddMarker);
        btnClearMarkers = findViewById(R.id.btnClearMarkers);

        // Set up button listeners
        setupButtonListeners();
    }

    private void setupMap() {
        mMap.setTileSource(TileSourceFactory.MAPNIK);
        mMap.setMultiTouchControls(true);
        mMap.setBuiltInZoomControls(true);

        // Default location: FPT University, Vietnam
        GeoPoint fptUniversity = new GeoPoint(10.8411, 106.8098);
        mMap.getController().setZoom(15.0);
        mMap.getController().setCenter(fptUniversity);

        // Add default marker
        addMarker(fptUniversity, "FPT University");

        // Set up map click listener
        mMap.getOverlays().add(new org.osmdroid.views.overlay.Overlay() {
            @Override
            public boolean onSingleTapConfirmed(android.view.MotionEvent e, MapView mapView) {
                GeoPoint geoPoint = (GeoPoint) mapView.getProjection().fromPixels((int) e.getX(), (int) e.getY());
                addMarker(geoPoint, "Location: " + String.format("%.4f, %.4f", geoPoint.getLatitude(), geoPoint.getLongitude()));
                return true;
            }
        });

        // Initialize location overlay
        myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mMap);
        myLocationOverlay.enableMyLocation();
        mMap.getOverlays().add(myLocationOverlay);

        // Check and request location permission
        checkLocationPermission();
    }

    private void setupButtonListeners() {
        setupMapTypeButtons();

        btnMyLocation.setOnClickListener(v -> goToMyLocation());

        btnAddMarker.setOnClickListener(v -> {
            if (mMap != null) {
                GeoPoint center = (GeoPoint) mMap.getMapCenter();
                addMarker(center, "Marker " + (markers.size() + 1));
            }
        });

        btnClearMarkers.setOnClickListener(v -> clearAllMarkers());
    }

    private void setupMapTypeButtons() {
        mapTypeButtons.clear();
        mapTypeButtons.put(MapType.NORMAL, btnNormal);
        mapTypeButtons.put(MapType.SATELLITE, btnSatellite);
        mapTypeButtons.put(MapType.HYBRID, btnHybrid);
        mapTypeButtons.put(MapType.TERRAIN, btnTerrain);

        for (Map.Entry<MapType, ImageButton> entry : mapTypeButtons.entrySet()) {
            ImageButton button = entry.getValue();
            if (button != null) {
                button.setOnClickListener(v -> applyMapType(entry.getKey(), true));
            }
        }

        applyMapType(currentMapType != null ? currentMapType : MapType.NORMAL, false);
    }

    private void applyMapType(@NonNull MapType mapType, boolean showToast) {
        if (mMap == null) {
            return;
        }

        if (currentMapType != mapType) {
            mMap.setTileSource(mapType.getTileSource());
            currentMapType = mapType;
        }

        updateMapTypeSelection(mapType);

        if (showToast) {
            String label = getString(mapType.getLabelRes());
            Toast.makeText(this, getString(R.string.map_type_switched, label), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMapTypeSelection(MapType activeType) {
        for (Map.Entry<MapType, ImageButton> entry : mapTypeButtons.entrySet()) {
            ImageButton button = entry.getValue();
            if (button == null) {
                continue;
            }

            boolean isSelected = entry.getKey() == activeType;
            button.setSelected(isSelected);
            button.setAlpha(isSelected ? 1f : 0.7f);

            String label = getString(entry.getKey().getLabelRes());
            button.setContentDescription(isSelected
                    ? getString(R.string.map_type_selected, label)
                    : label);
        }
    }

    private enum MapType {
        NORMAL(TileSourceFactory.MAPNIK, R.string.map_type_normal),
        SATELLITE(TileSourceFactory.USGS_SAT, R.string.map_type_satellite),
        HYBRID(TileSourceFactory.USGS_TOPO, R.string.map_type_hybrid),
        TERRAIN(TileSourceFactory.OPEN_SEAMAP, R.string.map_type_terrain);

        private final ITileSource tileSource;
        private final int labelRes;

        MapType(ITileSource tileSource, @StringRes int labelRes) {
            this.tileSource = tileSource;
            this.labelRes = labelRes;
        }

        ITileSource getTileSource() {
            return tileSource;
        }

        @StringRes
        int getLabelRes() {
            return labelRes;
        }
    }

    private void addMarker(GeoPoint position, String title) {
        if (mMap != null) {
            Marker marker = new Marker(mMap);
            marker.setPosition(position);
            marker.setTitle(title);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setOnMarkerClickListener((m, mapView) -> {
                m.showInfoWindow();
                Toast.makeText(MainActivity.this, "Marker: " + m.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            });
            mMap.getOverlays().add(marker);
            markers.add(marker);
            mMap.invalidate();
            Toast.makeText(this, "Marker added: " + title, Toast.LENGTH_SHORT).show();
        }
    }

    private void clearAllMarkers() {
        for (Marker marker : markers) {
            mMap.getOverlays().remove(marker);
        }
        markers.clear();
        mMap.invalidate();
        Toast.makeText(this, "All markers cleared", Toast.LENGTH_SHORT).show();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void enableMyLocation() {
        if (mMap != null && myLocationOverlay != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                myLocationOverlay.enableMyLocation();
                myLocationOverlay.enableFollowLocation();
            }
        }
    }

    private void goToMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            checkLocationPermission();
            return;
        }

        Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(location -> {
            if (location != null) {
                GeoPoint myLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                mMap.getController().animateTo(myLocation);
                mMap.getController().setZoom(15.0);
                Toast.makeText(this, "My Location: " + location.getLatitude() + ", " + location.getLongitude(),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
            }
        });

        locationTask.addOnFailureListener(e ->
            Toast.makeText(this, "Error getting location: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMap != null) {
            mMap.onPause();
        }
    }
}
