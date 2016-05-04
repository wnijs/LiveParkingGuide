package com.wannesnijs.liveparkingguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ParkingFragment extends Fragment {

    public MainActivity main = null;

    private Parking parking;

    public ParkingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        main = (MainActivity) getActivity();

        int position = getArguments().getInt("position");
        parking = (main).parkings.get(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parking, container, false);
        TextView text = (TextView) view.findViewById(R.id.test);
        text.setText(parking.getName());

        return view;
    }

}
