package com.wannesnijs.liveparkingguide;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public MainActivity main = null;

    private Parking parking;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView name = (TextView) view.findViewById(R.id.detail_name);
        name.setText(parking.getName());
        TextView spaces = (TextView) view.findViewById(R.id.detail_spaces);
        spaces.setText(parking.getAvailableCapacity() + getString(R.string.available_tag));
        TextView address = (TextView) view.findViewById(R.id.detail_address);
        address.setText(parking.getAddress());
        TextView contact = (TextView) view.findViewById(R.id.detail_contact);
        contact.setText(parking.getContact());

        return view;
    }

}
