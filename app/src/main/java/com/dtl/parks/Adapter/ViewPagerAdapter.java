package com.dtl.parks.Adapter;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dtl.parks.R;
import com.dtl.parks.model.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ImageSlider> {
    private List<Images> image_list;

    public ViewPagerAdapter(List<Images> image_list) {
        this.image_list = image_list;
    }

    @NonNull
    @Override
    public ViewPagerAdapter.ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_row,parent,false);
        return new ImageSlider(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerAdapter.ImageSlider holder, int position) {
        Picasso.get()
                .load(image_list.get(position).getUrl())
                .fit()
                .placeholder(android.R.drawable.stat_notify_error)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return image_list.size();
    }
    public class ImageSlider extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ImageSlider(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ViewPagerImage);
        }
    }
}
