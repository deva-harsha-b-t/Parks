package com.dtl.parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dtl.parks.Adapter.ViewPagerAdapter;
import com.dtl.parks.model.Park;
import com.dtl.parks.model.ParkViewModel;

public class ParksDetails extends Fragment {
    private ParkViewModel parkViewModel;
    private ViewPagerAdapter pagerAdapter;
    private ViewPager2 viewPager2;
    private TextView ParkName;
    private TextView ParkType;
    private TextView ParkDescription;


    public ParksDetails() {
        // Required empty public constructor
    }
    public static ParksDetails newInstance() {
        ParksDetails fragment = new ParksDetails();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.ParkDetailsImages_viewPager);
        ParkName = view.findViewById(R.id.ParkDetailsName);
        ParkType = view.findViewById(R.id.ParkDetailsType);
        ParkDescription = view.findViewById(R.id.ParkDetailsDescription);
        parkViewModel.getSelectedPark().observe(this, new Observer<Park>() {
            @Override
            public void onChanged(Park park) {
                pagerAdapter = new ViewPagerAdapter(park.getImages());
                viewPager2.setAdapter(pagerAdapter);
                ParkName.setText(park.getFullName());
                ParkType.setText(park.getDesignation());
                ParkDescription.setText(park.getDescription());
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkViewModel = new ViewModelProvider(requireActivity()).get(ParkViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parks_details, container, false);
        return view;

    }
}