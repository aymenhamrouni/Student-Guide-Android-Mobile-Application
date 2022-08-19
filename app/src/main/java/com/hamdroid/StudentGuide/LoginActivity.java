
package com.hamdroid.StudentGuide;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Bind;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static String URL = "https://studentguidev1.000webhostapp.com/login.php";
    private static final int REQUEST_SIGNUP = 0;
    private static int a=0;

    String json;
    String cin, name, lastname,email ;
    MediaPlayer mediaPlayer;

    @Bind(R.id.input_CIN)
    EditText _CINText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;
    JSONObject jsonObject;
    JSONArray jsonArray;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
       if(a==0) {mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.yoursong);

        mediaPlayer.start(); a++; }
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             if (haveNetworkConnection())  { login();  } else { nointernet(); }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(true);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        // TODO: Implement your own authentication logic here.


        final String CIN = _CINText.getText().toString();
        final String password = _passwordText.getText().toString();


        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String response = null;

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.equals("Login")) {
                            progressDialog.dismiss();
                            unknown s= new unknown();
                            s.execute();



                        } else {
                            progressDialog.dismiss();
                            onLoginFailed();
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                        Log.d("ErrorResponse", finalResponse);

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cin", CIN);
                params.put("password_hash", password);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


        //LOGIII

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed

                        // onLoginFailed();

                    }
                }, 5000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("cin",cin);
        intent.putExtra("name",name);
        intent.putExtra("lastname",lastname);
        intent.putExtra("email",email);
        intent.putExtra("pass",_passwordText.getText().toString());

        startActivity(intent);

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String CIN = _CINText.getText().toString();
        String password = _passwordText.getText().toString();


        if (CIN.isEmpty()) {
            _CINText.setError("Enter a valid CIN address");
            valid = false;
        } else {
            _CINText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Password Must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    private class unknown extends AsyncTask<Void, Void, String> {
        String JSON_URL;
        String JSON_STRING;
        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this,R.style.AppTheme_Dark_Dialog);
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
            this.dialog.setMessage("Fetching Data...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
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
                 if (cin.equals(_CINText.getText().toString())) { reply=true; }
                    i++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.dialog.dismiss();
            onLoginSuccess();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void nointernet()
    {


        Toast.makeText(getApplicationContext(), "You need to turn on WIFI first",
                Toast.LENGTH_LONG).show();

    }
}



