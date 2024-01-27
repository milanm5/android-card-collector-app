package com.example.cardproject.ui.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardproject.R;
import com.example.cardproject.util.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng dragonGames = new LatLng(Constants.DRAGON_GAMES_NS_LAT, Constants.DRAGON_GAMES_NS_LNG);
            LatLng kraken = new LatLng(Constants.KRAKEN_SU_LAT, Constants.KRAKEN_SU_LNG);
            LatLng klubRavnica = new LatLng(Constants.KLUB_RAVNICA_BG_LAT, Constants.KLUB_RAVNICA_BG_LNG);
            LatLng conflux = new LatLng(Constants.CONFLUX_BG_LAT, Constants.CONFLUX_BG_LNG);
            googleMap.addMarker(new MarkerOptions().position(dragonGames).title("Dragon Games DOO"));
            googleMap.addMarker(new MarkerOptions().position(kraken).title("Kraken"));
            googleMap.addMarker(new MarkerOptions().position(klubRavnica).title("Klub Ravnica DOO"));
            googleMap.addMarker(new MarkerOptions().position(conflux).title("Conflux"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(dragonGames));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}