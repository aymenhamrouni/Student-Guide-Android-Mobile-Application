package com.hamdroid.StudentGuide;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,RecyclerViewAdapter.ItemListener {
    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;

    String CIN,PASS;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        arrayList.add(new DataModel("Item 1", R.drawable.profile, "#00832f2f"));
        arrayList.add(new DataModel("Item 2", R.drawable.calender, "#00832f2f"));
        arrayList.add(new DataModel("Item 3", R.drawable.books, "#00832f2f"));
        arrayList.add(new DataModel("Item 4", R.drawable.support, "#00832f2f"));
        arrayList.add(new DataModel("Item 5", R.drawable.actua, "#00832f2f"));
        arrayList.add(new DataModel("Item 6", R.drawable.team, "#00832f2f"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);


        /**
         Simple GridLayoutManager that spans two columns
         **/
        /*GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);*/









        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(false);
        }
    }
    public void sendMessage( )
    {
        Intent intent = new Intent(this, Calender.class);
        startActivity(intent);
    }

    public void sendMessage2( )   {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("cin",CIN);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        CIN=getIntent().getExtras().getString("cin");
        TextView textView= (TextView) findViewById(R.id.input_c);
        textView.setText(CIN);
        email=getIntent().getExtras().getString("email");
        TextView textView2= (TextView) findViewById(R.id.textView1);
        textView2.setText(email);
        PASS=getIntent().getExtras().getString("pass");
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.logout) {

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemClick(DataModel item) {

        //Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
       if (item.text.equals("Item 1")) { sendMessage2(); } else if (item.text.equals("Item 2")
               ) {  sendMessage(); } else if (item.text.equals("Item 5"))  { sendMessage1(); }
       else if (item.text.equals("Item 3"))  { sendMessage3(); }
       else if (item.text.equals("Item 6"))  { sendMessage4(); }

    }

    private void sendMessage4() {
        Intent intent = new Intent(this, ClubActivity.class);
        startActivity(intent);
        
    }

    private void sendMessage1() {

        Intent intent = new Intent(this, ActualActivity.class);
        startActivity(intent);
    }

    private void sendMessage3() {

        Intent intent = new Intent(this, MainActivityEvents.class);
        intent.putExtra("pass",PASS);
        intent.putExtra("email",email);
        startActivity(intent);
    }
}
