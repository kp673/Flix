package kp673.flix;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import javax.security.auth.login.LoginException;

import kp673.flix.models.Movie;
import okhttp3.Headers;


public class DetailActivity extends YouTubeBaseActivity{
    private static final String YOUTUBE_API_KEY = "AIzaSyCVZ_T75B---wnH1-PT9-DA2ccLygA62tw";
    private static final String VIDEOS_URL= "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    TextView tvOverview;
    TextView tvRelease;
    RatingBar ratingBar;
    CheckBox adultCheck;
    YouTubePlayerView youTubePlayerView;
    Boolean popular=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview= findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        tvRelease = findViewById(R.id.tvRelease);
        adultCheck = findViewById(R.id.adultCheck);
        youTubePlayerView = findViewById(R.id.player);


        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRating());
        tvRelease.setText(movie.getReleaseDate());
        adultCheck.setChecked(movie.isAdultCheck());
        if (movie.getRating()>5){
            popular= true;
        }

        AsyncHttpClient client= new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d("DetailActivity", "onSuccess");
                try {
                    JSONArray results= json.jsonObject.getJSONArray("results");
                    if (results.length() == 0){
                        return;
                    }
                    String youTubeKey= results.getJSONObject(0).getString("key");
                    Log.d("DetailActivity", youTubeKey);
                    initializeYoutube(youTubeKey);

                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON",e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("DetailActivity", "onFailure");

            }
        });

    }

    public void initializeYoutube(final String youTubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "onInitializationSuccess");
                if (popular){
                    youTubePlayer.loadVideo(youTubeKey);

                }
                else {
                    youTubePlayer.cueVideo(youTubeKey);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("DetailActivity", "onInitializationFailure" + youTubeInitializationResult);
            }
        });
    }
}