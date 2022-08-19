package com.hamdroid.StudentGuide;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class ProfileActivity extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 3;
    String json;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String cin,email,name,lastname,numero,niveau,classe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        unknown s=new unknown();
        s.execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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


    private class unknown extends AsyncTask<Void, Void, String> {
        String JSON_URL;
        String JSON_STRING;
        private final ProgressDialog dialog = new ProgressDialog(ProfileActivity.this,R.style.AppTheme_Dark_Dialog);

        protected String doInBackground(Void... params) {


            try {
                java.net.URL url = new URL(JSON_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                Log.e("Error1", e.getMessage());
            } catch (IOException e) {
                Log.e("Error2", e.getMessage());
            }

            return "Something is wrong";
        }

        @Override
        protected void onPreExecute() {
            JSON_URL = "https://studentguidev1.000webhostapp.com/fetch.php";
       /* alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");*/
            this.dialog.setMessage("Gathering Information...");
            this.dialog.show();


        }

        @Override
        protected void onPostExecute(String result) {



            String cinpassed;
            cinpassed=getIntent().getExtras().getString("cin");
            json = result;
            boolean reply=false;
            try {
                jsonObject=new JSONObject(json);
                int i=0;
                jsonArray=jsonObject.getJSONArray("answer");
                while ((i < jsonArray.length())&&(!reply)) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    name = jo.getString("name");
                    cin = jo.getString("cin");
                    lastname = jo.getString("lastname");
                    email = jo.getString("email");
                    numero = jo.getString("numero");
                    niveau = jo.getString("niveau");
                    classe = jo.getString("classe");
                    if (cin.equals(cinpassed)) reply = true;
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextView textView= (TextView) findViewById(R.id.tvNumber6);
            textView.setText(cin);
            TextView textView2= (TextView) findViewById(R.id.tvNumber3);
            textView2.setText(email);
            TextView textView3= (TextView) findViewById(R.id.tvNumber4);
            textView3.setText(name+" "+lastname);
            TextView textView4= (TextView) findViewById(R.id.tvNumber5);
            textView4.setText(niveau+classe);
            TextView textView5= (TextView) findViewById(R.id.tvNumber1);
            textView5.setText(numero);
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
            collapsingToolbar.setTitle(name+"."+lastname.substring(0,1));
            this.dialog.dismiss();


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    }
