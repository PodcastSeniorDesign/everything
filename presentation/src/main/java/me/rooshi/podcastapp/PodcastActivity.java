package me.rooshi.podcastapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PodcastActivity extends AppCompatActivity {

    String trackName = "";
    String artistName = "";
    String artworkURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        trackName = getIntent().getStringExtra("trackName");
        artistName = getIntent().getStringExtra("artistName");
        artworkURL = getIntent().getStringExtra("artworkURL");

        TextView track = findViewById(R.id.trackName);
        TextView artist = findViewById(R.id.artistName);
        ImageView artwork = findViewById(R.id.artwork);

        artworkURL = artworkURL.replace("100", "400");
        System.out.println(artworkURL);

        track.setText(trackName);
        artist.setText(artistName);
        Picasso.get().load(artworkURL).into(artwork);

    }
}
