package kp673.flix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import org.parceler.Parcels;

import kp673.flix.models.Movie;

public class DetailActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvOverview;
    TextView tvRelease;
    RatingBar ratingBar;
    CheckBox adultCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.flix_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview= findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        tvRelease = findViewById(R.id.tvRelease);
        adultCheck = findViewById(R.id.adultCheck);


        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRating());
        tvRelease.setText(movie.getReleaseDate());
        adultCheck.setChecked(movie.isAdultCheck());

    }
}