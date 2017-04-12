package es.jj.coolbeer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.jj.coolbeer.adapters.RecyclerViewAdapter;
import es.jj.coolbeer.model.Beer;
import es.jj.coolbeer.model.Retailer;

public class ResultsActivity extends AppCompatActivity {

    TextView name,year,brewery,origin,type;
    ImageView icon;
    CoordinatorLayout coordinatorLayout;

    public RecyclerView.Adapter mAdapter;
    List<Object> retailerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SpannableString s = new SpannableString("Résultats");
        s.setSpan(new ForegroundColorSpan(getColor(R.color.primary_text)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        Intent i = getIntent();
        String stringJson = i.getStringExtra("beer");

        name = (TextView) findViewById(R.id.name);
        year = (TextView) findViewById(R.id.year);
        brewery = (TextView) findViewById(R.id.brewery);
        origin = (TextView) findViewById(R.id.origin);
        type = (TextView) findViewById(R.id.type);
        icon = (ImageView) findViewById(R.id.photo);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coord);

        retailerList = new ArrayList<>();
        Beer beerDetected;
        try {
            JSONObject json = new JSONObject(stringJson);
            JSONArray retailers = json.getJSONArray("retailers");

            //beerDetected = new Beer(json.getString("name"), json.getString("year"), json.getString("brewery"), json.getString("origin"),json.getString("type"));

            name.setText(json.getString("name"));
            brewery.setText(json.getString("brewery"));
            origin.setText(json.getString("origin"));
            type.setText(json.getString("type"));

            switch (json.getString("name")){
                case "Guinness":
                    icon.setImageDrawable(getDrawable(R.drawable.guinness));
                    break;
                case "Carling":
                    icon.setImageDrawable(getDrawable(R.drawable.carling));
                    break;
            }

            for(int j=0; j< retailers.length(); j++){
                JSONObject retailer = retailers.getJSONObject(j);
                Retailer retailerObject = new Retailer(retailer.getString("name"),retailer.getString("country"),retailer.getString("brandname"),retailer.getString("lat"),retailer.getString("lng"),retailer.getString("quantity"));
                retailerList.add(retailerObject);
            }


        }catch (Exception e){
            e.printStackTrace();
        }


        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        mAdapter= new RecyclerViewAdapter(getApplicationContext(),retailerList);
        rv.setAdapter(mAdapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
