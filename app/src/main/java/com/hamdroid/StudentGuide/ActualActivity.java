package com.hamdroid.StudentGuide;

/**
 * Created by Eymen Hamrouni on 04/12/2017.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import com.hamdroid.StudentGuide.adapter.AdapterTuto;
import com.hamdroid.StudentGuide.modele.Tuto;


public class ActualActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Tuto> tutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actual_activity);
        charger();
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                       charger();




                    }
                },3000);
            }
        });

    }

    private void charger() {

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        tutos = new ArrayList<>();
        mAdapter = new AdapterTuto(tutos, this);
        mRecyclerView.setAdapter(mAdapter);

        new TutoAndroidFranceTask().execute();

    }


    public void callFunction(View view) {

        Uri uri = Uri.parse("http://www.supcom.mincom.tn"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);


    }


    class TutoAndroidFranceTask extends AsyncTask<Void, String, ArrayList<Tuto>> {

        @Override
        protected ArrayList<Tuto> doInBackground(Void... params) {

            Document document;
            try {

                // need http protocol
                document = Jsoup.connect("http://www.supcom.mincom.tn").get();


                ArrayList<Tuto> cl = new ArrayList<>();

                Tuto item;

                //Element classement = document.getElementById("classement");

                Elements elements = document.select("div.div_actu");
                Elements title = elements.select("div.overflow_hidden height100_pourcent zoom").select("a.titr_actu for_underline");

                for (Element e : elements) {

                    item = new Tuto();

                    // On récupère l'image
                    item.setUrl("http://www.supcom.mincom.tn/"+e.select("img").attr("src"));
                    item.setTitre(e.select("img").attr("title"));
                    item.setDescription(e.select("p.disc_actu").select("a").text());

                    cl.add(item);
                }



                return cl;

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Tuto> ts) {
            super.onPostExecute(ts);

            try {
                if (tutos == null)
                    tutos = new ArrayList<>();

                tutos.addAll(ts);
                mAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
