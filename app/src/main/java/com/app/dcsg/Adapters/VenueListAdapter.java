package com.app.dcsg.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.dcsg.Model.ResponseModel.VenueResponseModel;
import com.app.dcsg.Model.VenueDetails;
import com.app.dcsg.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by srika on 1/31/2018.
 */

public class VenueListAdapter extends RecyclerView.Adapter<VenueListAdapter.VenueViewHolder> {

    private List<VenueDetails> mVenueDetails;
    private Context mContext;

    public VenueListAdapter() {
    }

    public VenueListAdapter(Context context, List<VenueDetails> mVenueDetails) {
        this.mContext = context;
        this.mVenueDetails = mVenueDetails;
    }

    public static class VenueViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView rating;
        TextView address;
        TextView city;
        Button favorite;

        public VenueViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTextView);
            rating = itemView.findViewById(R.id.ratingTextView);
            address = itemView.findViewById(R.id.addressTextView);
            city = itemView.findViewById(R.id.cityTextView);
            favorite = itemView.findViewById(R.id.favoriteButton);

        }
    }

    @Override
    public VenueViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_venue_details, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,"Clicked"+getItemId(),Toast.LENGTH_LONG).show();
            }
        });
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VenueViewHolder holder, final int position) {
        holder.name.setText(mVenueDetails.get(position).getName());
        holder.rating.setText(String.format(Locale.ENGLISH,"%.1f", mVenueDetails.get(position).getRating()));
        holder.address.setText(mVenueDetails.get(position).getAddress());
        holder.city.setText(mVenueDetails.get(position).getCity());
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        //Log.e("in Adapter", ""+mVenueDetails.size());
        return mVenueDetails.size();
    }
}
