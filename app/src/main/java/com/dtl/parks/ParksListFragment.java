package com.dtl.parks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dtl.parks.Adapter.ParkClickInterface;
import com.dtl.parks.Adapter.ParksRecyclerViewAdapter;
import com.dtl.parks.data.AsyncResponse;
import com.dtl.parks.data.repository;
import com.dtl.parks.model.Park;
import com.dtl.parks.model.ParkViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParksListFragment extends Fragment implements ParkClickInterface {
    private static final String TAG ="OnClickPARK" ;
    private RecyclerView recyclerView;
    private ParksRecyclerViewAdapter adapter;
    private List<Park> parks_list;
    private ParkViewModel parkViewModel;

    public ParksListFragment() {
        // Required empty public constructor
    }


    public static ParksListFragment newInstance() {
        return new ParksListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parks_list = new ArrayList<>();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        parkViewModel = new ViewModelProvider(requireActivity()).get(ParkViewModel.class);

        if(parkViewModel.getSelectedParks()!=null){
            parks_list = parkViewModel.getSelectedParks().getValue();
            adapter = new ParksRecyclerViewAdapter(parks_list,this);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parks_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void ParkClick(Park park) {
        getFragmentManager().beginTransaction().replace(R.id.parkLayout,ParksDetails.newInstance()).commit();
        parkViewModel.setSelectedPark(park);

    }
}