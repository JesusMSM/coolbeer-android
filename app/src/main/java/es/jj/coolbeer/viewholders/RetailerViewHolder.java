package es.jj.coolbeer.viewholders;

import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import es.jj.coolbeer.R;

/**
 * Created by Jesus on 11/04/2017.
 */
public class RetailerViewHolder extends RecyclerView.ViewHolder {
    public TextView name, country, brandName, availability;
    public CardView cardView;
    public ImageView icon;


    public RetailerViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        country = (TextView) itemView.findViewById(R.id.country);
        brandName = (TextView) itemView.findViewById(R.id.brandName);
        cardView = (CardView) itemView.findViewById(R.id.cv);
        icon = (ImageView) itemView.findViewById(R.id.icon);
        availability = (TextView) itemView.findViewById(R.id.availability);



    }

}