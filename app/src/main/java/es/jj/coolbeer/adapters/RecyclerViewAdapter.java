package es.jj.coolbeer.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import es.jj.coolbeer.MapsActivity;
import es.jj.coolbeer.R;
import es.jj.coolbeer.model.Beer;
import es.jj.coolbeer.model.Retailer;
import es.jj.coolbeer.viewholders.BeerViewHolder;
import es.jj.coolbeer.viewholders.RetailerViewHolder;

/**
 * Created by Jesus on 11/04/2017.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {

    Context context;
    List<Object> items = new ArrayList<>();
    List<Object> itemsCopy = new ArrayList<>();

    private final int BEER = 0, RETAILER =1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
        itemsCopy.addAll(items);
    }


    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof Beer){
            return BEER;
        }
        if(items.get(position) instanceof Retailer){
            return RETAILER;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case BEER:
                View v1 = inflater.inflate(R.layout.cardview_beer, parent, false);
                viewHolder = new BeerViewHolder(v1);
                break;
            case RETAILER:
                View v2 = inflater.inflate(R.layout.cardview_retailer, parent, false);
                viewHolder = new RetailerViewHolder(v2);
                break;
            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case BEER:
                BeerViewHolder vh1 = (BeerViewHolder) holder;
                configureBeerViewHolder(vh1, position);
                break;
            case RETAILER:
                RetailerViewHolder vh2 = (RetailerViewHolder) holder;
                configureRetailerViewHolder(vh2, position);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void configureBeerViewHolder(final BeerViewHolder vh, int position){
        //
    }


    public void configureRetailerViewHolder(final RetailerViewHolder vh, int position) {
        final Retailer retailer = (Retailer) items.get(position);

        Log.d("retailers",retailer.getName());
        vh.name.setText(retailer.getName());
        vh.country.setText(retailer.getCountry());
        vh.brandName.setText(retailer.getBrandName());
        vh.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), MapsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("lat", retailer.getLat());
                i.putExtra("lng", retailer.getLng());
                v.getContext().startActivity(i);
            }
        });

        switch(retailer.getBrandName()){
            case "Carrefour":
                vh.icon.setImageDrawable(context.getDrawable(R.drawable.carrefour));
                break;
            case "Auchan":
                vh.icon.setImageDrawable(context.getDrawable(R.drawable.auchan));
                break;
            case "Cora":
                vh.icon.setImageDrawable(context.getDrawable(R.drawable.cora));
                break;
            case "Franprix":
                vh.icon.setImageDrawable(context.getDrawable(R.drawable.franprix));
                break;
            case "Leclerc":
                vh.icon.setImageDrawable(context.getDrawable(R.drawable.leclerc));
                break;
            case "Monoprix":
                vh.icon.setImageDrawable(context.getDrawable(R.drawable.monoprix));
                break;
            case "Simply":
                vh.icon.setImageDrawable(context.getDrawable(R.drawable.simply));
                break;
        }

        if(Integer.valueOf(retailer.getQuantity())<5){
            vh.availability.setBackground(context.getDrawable(R.drawable.bg_red));
        }else if(Integer.valueOf(retailer.getQuantity())<10){
            vh.availability.setBackground(context.getDrawable(R.drawable.bg_yellow));
        }else{
            vh.availability.setBackground(context.getDrawable(R.drawable.bg_green));
        }
    }



}