package com.dtl.parks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dtl.parks.Utils.LoadingDialog;
import com.dtl.parks.Utils.UTIL;
import com.dtl.parks.data.repository;
import com.dtl.parks.model.Park;
import com.dtl.parks.model.ParkViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG ="PARKS" ;
    private GoogleMap mMap;
    BottomNavigationView bottomNavigation;
    private ParkViewModel parkViewModel;
    private  List<Park> parkList;
    private LoadingDialog loadingDialog;
    private View searchLayout;
    private EditText stateCode_entered;
    private ImageButton search_button;
    private String stateCODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        parkViewModel = new ViewModelProvider(this).get(ParkViewModel.class);
        parkViewModel.setCde("AZ");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        search_button = findViewById(R.id.SearchButton);
        stateCode_entered = findViewById(R.id.EnterCODE);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateCODE = stateCode_entered.getText().toString().trim();
                if(!TextUtils.isEmpty(stateCODE)){
                    parkList.clear();
                    parkViewModel.setCde(stateCODE);
                    onMapReady(mMap);
                }
                else {
                    Toast.makeText(MapsActivity.this,"Enter Correct State Code",Toast.LENGTH_SHORT).show();
                }

            }
        });
        loadingDialog = new LoadingDialog(mapFragment.getActivity());
        loadingDialog.startLoading();
        searchLayout = findViewById(R.id.includeID);
        mapFragment.getMapAsync(this);
        bottomNavigation = findViewById(R.id.bottomNavBar);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();
            if(id == R.id.BottomNavMap){
                mMap.clear();
                parkList.clear();
                getSupportFragmentManager().beginTransaction().replace(R.id.map,mapFragment).commit();
                loadingDialog.startLoading();
                if(searchLayout.getVisibility()==View.GONE){
                    searchLayout.setVisibility(View.VISIBLE);
                }
                mapFragment.getMapAsync(this);
                return true;

            }else if(id == R.id.BottomNavParks){
                searchLayout.setVisibility(View.GONE);
                fragment = ParksListFragment.newInstance();

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.map,fragment).commit();
            return true;
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        // Add a marker in Sydney and move the camera
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        parkList = new ArrayList<>();
        parkList.clear();
        mMap.clear();
        repository.getParks(parks -> {
            parkList = parks;
            for(Park park : parks){
                LatLng lt = new LatLng(Double.parseDouble(park.getLatitude()),Double.parseDouble(park.getLongitude()));
                MarkerOptions markerOptions = new MarkerOptions().position(lt).title(park.getFullName());
                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(park);
            }
            parkViewModel.setSelectedParks(parkList);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(parks.get(0).getLatitude()),Double.parseDouble(parks.get(0).getLongitude())),5));
        },parkViewModel.getStateCodeUrl().getValue());
        loadingDialog.stopLoading();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        searchLayout.setVisibility(View.GONE);
        Park park = (Park) marker.getTag();
        parkViewModel.setSelectedPark(park);
        getSupportFragmentManager().beginTransaction().replace(R.id.map,ParksDetails.newInstance()).commit();
    }

}