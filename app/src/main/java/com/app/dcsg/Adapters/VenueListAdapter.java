package com.app.dcsg.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.dcsg.Model.VenueDetails;
import com.app.dcsg.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * This Class handles the Custom List Adapter coupled with the Recycler View
 * which is used for populating the Store details in VenueActivity
 * Created by Srikanth on 1/02/18
 */

public class VenueListAdapter extends RecyclerView.Adapter<VenueListAdapter.VenueViewHolder> {

    private List<VenueDetails> mVenueDetails;
    private Context mContext;
    private VenueListAdapter.VenueViewHolder.IOnFavoriteSelection mOnFavoriteSelection;

    public VenueListAdapter() {
    }

    public VenueListAdapter(Context context, List<VenueDetails> mVenueDetails,
                            VenueViewHolder.IOnFavoriteSelection mOnFavoriteSelection) {
        this.mContext = context;
        this.mVenueDetails = mVenueDetails;
        this.mOnFavoriteSelection = mOnFavoriteSelection;
    }

    /**
     * Class to map the Custom ViewHolder
     */
    public static class VenueViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView rating;
        TextView address;
        TextView city;
        Button moreDetails;
        ImageButton favorite;
        ImageView storeImage;

        public VenueViewHolder(View itemView) {
            super(itemView);

            //Map the variables with corresponding views

            name = itemView.findViewById(R.id.nameTextView);
            rating = itemView.findViewById(R.id.ratingTextView);
            address = itemView.findViewById(R.id.addressTextView);
            city = itemView.findViewById(R.id.cityTextView);
            moreDetails = itemView.findViewById(R.id.moreDetailsButton);
            favorite = itemView.findViewById(R.id.favoriteButton);
            storeImage = itemView.findViewById(R.id.storeImageView);

        }

        /**
         * Interface to handle the Favorite store selection
         */
        public interface IOnFavoriteSelection {
            void setFavoriteItem(int pos);
        }

    }

    /**
     * This method is called during the ViewHolder creation
     * @param parent ViewGroup instance
     * @param viewType ViewType value
     * @return ViewHolder instance
     */
    @Override
    public VenueViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        //Inflate the View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_venue_details,
                parent, false);
        return new VenueViewHolder(view);
    }

    /**
     * This method binds the ViewHolder to view
     * @param holder Holder instance
     * @param position Item Position
     */
    @Override
    public void onBindViewHolder(final VenueViewHolder holder, final int position) {
        holder.name.setText(mVenueDetails.get(position).getName());
        holder.rating.setText(String.format(Locale.ENGLISH, "%.1f",
                mVenueDetails.get(position).getRating()));
        holder.address.setText(mVenueDetails.get(position).getAddress());
        holder.city.setText(mVenueDetails.get(position).getCity());
        if (position == 0) {
            holder.favorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            holder.favorite.setImageResource(android.R.drawable.btn_star_big_off);
        }

        holder.moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.storeImage.getVisibility() == View.GONE) {
                    holder.storeImage.setVisibility(View.VISIBLE);
                    holder.moreDetails.setText(R.string.venue_button_text2);
                    Picasso.with(mContext)
                            .load(mVenueDetails.get(holder.getAdapterPosition()).getUrl())
                            .placeholder(R.drawable.dickslogo)
                            .into(holder.storeImage);
                } else {
                    holder.moreDetails.setText(R.string.venue_button_text1);
                    holder.storeImage.setVisibility(View.GONE);
                }
                notifyDataSetChanged();

            }
        });

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnFavoriteSelection.setFavoriteItem(holder.getAdapterPosition());
            }
        });
    }

    /**
     * This method returns the List size
     * @return List size
     */
    @Override
    public int getItemCount() {
        //return the List size
        return mVenueDetails.size();
    }

}