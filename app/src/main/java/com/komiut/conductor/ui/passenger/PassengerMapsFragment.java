package com.komiut.conductor.ui.passenger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.komiut.conductor.R;
import com.komiut.conductor.model.PassengerMapCoordinates;

public class PassengerMapsFragment extends Fragment {

    String latitude, longitude;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            PassengerMapCoordinates coordinates=PassengerMapsFragmentArgs.fromBundle(getArguments()).getPassangerCoordinates();
            LatLng stage = new LatLng(Double.valueOf(coordinates.getLatitutde()), Double.valueOf(coordinates.getLongitude()));
            googleMap.addMarker(new MarkerOptions().position(stage).title("Passenger"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(stage));
            googleMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle bundle = getActivity().getIntent().getExtras();
        latitude = bundle.getString("stage_latitude");
        longitude = bundle.getString("stage_longitude");

        return inflater.inflate(R.layout.fragment_passenger_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}