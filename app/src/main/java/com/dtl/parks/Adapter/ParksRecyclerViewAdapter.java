package com.dtl.parks.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtl.parks.R;
import com.dtl.parks.model.Park;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParksRecyclerViewAdapter extends RecyclerView.Adapter<ParksRecyclerViewAdapter.viewHolder> {
    private final List<Park> parks;
    private final ParkClickInterface OnparkClickInterface;

    public ParksRecyclerViewAdapter(List<Park> parks , ParkClickInterface OnparkClickInterface) {
        this.parks = parks;
        this.OnparkClickInterface = OnparkClickInterface;
    }

    @NonNull
    @Override
    public ParksRecyclerViewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.park_row,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParksRecyclerViewAdapter.viewHolder holder, int position) {
        Park park = parks.get(position);
        holder.parkName.setText(park.getFullName());
        holder.parkState.setText(park.getStates());
        holder.parkType.setText(park.getDesignation());
        if(park.getImages().size()>0){
             Picasso.get()
                    .load(park.getImages().get(0).getUrl())
                    .placeholder(android.R.drawable.stat_sys_download)
                    .error(android.R.drawable.stat_notify_error)
                     .resize(100,100)
                     .centerCrop()
                    .into(holder.parkImage);
        }
    }

    @Override
    public int getItemCount() {
        return parks.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView parkImage;
        TextView parkName;
        TextView parkType;
        TextView parkState;
        ParkClickInterface parkClickInterface;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.parkClickInterface = OnparkClickInterface;
            parkImage= itemView.findViewById(R.id.ParkImage);
            parkName= itemView.findViewById(R.id.ParkName);
            parkType= itemView.findViewById(R.id.ParkType);
            parkState= itemView.findViewById(R.id.ParkState);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Park park = parks.get(getAdapterPosition());
            OnparkClickInterface.ParkClick(park);

        }
    }
}
