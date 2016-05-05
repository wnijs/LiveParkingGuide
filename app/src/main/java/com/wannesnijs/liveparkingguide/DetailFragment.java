package com.wannesnijs.liveparkingguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements OnMapReadyCallback {

    public MainActivity main = null;

    private Parking parking;
    MapView mapView;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        main = (MainActivity) getActivity();

        int position = getArguments().getInt("position");
        parking = main.parkings.get(position);
    }

    // Fills all the fields of the fragment and returns the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView name = (TextView) view.findViewById(R.id.detail_name);
        name.setText(parking.getName());
        String temp = String.format(main.getResources().getString(R.string.available_tag), parking.getAvailableCapacity());
        TextView spaces = (TextView) view.findViewById(R.id.detail_spaces);
        spaces.setText(temp);
        TextView address = (TextView) view.findViewById(R.id.detail_address);
        address.setText(parking.getAddress());
        TextView contact = (TextView) view.findViewById(R.id.detail_contact);
        contact.setText(parking.getContact());

        // Get the mapview and load the map asynchronously
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng location = new LatLng(parking.getLatitude(),parking.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(location));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
