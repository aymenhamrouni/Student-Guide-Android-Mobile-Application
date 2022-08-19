package com.hamdroid.StudentGuide;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hamdroid.StudentGuide.models.ClubModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DetailActivity extends ActionBarActivity {

    private ImageView ivClubIcon;
    private TextView tvClub;
    private TextView tvTagline;
    private TextView tvYear;
    private TextView tvDuration;
    private TextView tvDirector;
    private RatingBar rbClubRating;
  //  private TextView tvCast;
    private TextView tvStory;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Showing and Enabling clicks on the Home/Up button
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // setting up text views and stuff
        setUpUIViews();

        // recovering data from MainActivity, sent via intent
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String json = bundle.getString("clubModel"); // getting the model from MainActivity send via extras
            ClubModel clubModel = new Gson().fromJson(json, ClubModel.class);

            // Then later, when you want to display image
            ImageLoader.getInstance().displayImage(clubModel.getImage(), ivClubIcon, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            tvClub.setText(clubModel.getClub());
            tvTagline.setText(clubModel.getTagline());
            tvYear.setText("Year: " + clubModel.getYear());
            tvDuration.setText("Duration:" + clubModel.getDuration());
            tvDirector.setText("Director:" + clubModel.getDirector());

            // rating bar
            rbClubRating.setRating(clubModel.getRating() / 2);

            //StringBuffer stringBuffer = new StringBuffer();
            //for(ClubModel.Cast cast : clubModel.getCastList()){
               // stringBuffer.append(cast.getName() + ", ");
            //}

          //  tvCast.setText("Cast:" + stringBuffer);
            tvStory.setText(clubModel.getStory());

        }

    }

    private void setUpUIViews() {
        ivClubIcon = (ImageView)findViewById(R.id.ivIcon);
        tvClub = (TextView)findViewById(R.id.tvClub);
        tvTagline = (TextView)findViewById(R.id.tvTagline);
        tvYear = (TextView)findViewById(R.id.tvYear);
        tvDuration = (TextView)findViewById(R.id.tvDuration);
        tvDirector = (TextView)findViewById(R.id.tvDirector);
        rbClubRating = (RatingBar)findViewById(R.id.rbClub);
      //  tvCast = (TextView)findViewById(R.id.tvCast);
        tvStory = (TextView)findViewById(R.id.tvStory);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
