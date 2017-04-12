package es.jj.coolbeer.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import es.jj.coolbeer.R;

/**
 * Created by Jesus on 11/04/2017.
 */
public class BeerViewHolder extends RecyclerView.ViewHolder {
    public TextView name, year, brewery, origin, type;


    public BeerViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        year = (TextView) itemView.findViewById(R.id.year);
        brewery = (TextView) itemView.findViewById(R.id.brewery);
        origin = (TextView) itemView.findViewById(R.id.origin);
        type = (TextView) itemView.findViewById(R.id.type);



    }

}