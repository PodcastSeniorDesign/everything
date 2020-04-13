package me.rooshi.podcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;

import android.content.Intent;

import android.media.AudioManager;
import android.media.MediaPlayer;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Arrays;
import java.util.List;

import java.io.IOException;



public class MainActivity extends AppCompatActivity {

    boolean playing = false;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean kailabtnclicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPlayer();

    }

    public void searchTerm(View view) {
        EditText inputText = findViewById(R.id.textInput);
        String toSearch = inputText.getText().toString();
        //String toSearch = "money";

        final TextView testTextView = findViewById(R.id.testTextView);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String baseItunesUrl = "https://itunes.apple.com/search?media=podcast";
        Uri uri = Uri.parse(baseItunesUrl);
        uri = uri.buildUpon().appendQueryParameter("term", toSearch).build();
        String url = uri.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        testTextView.setText("Response is: " + response);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("search");

                        myRef.setValue(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                testTextView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void login(View view) {
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }
  
    private void initPlayer() {
        String podcastEpisode = "https://locator.simplecastcdn.com/e7ec86c9-5b4f-4c1c-af7b-0957921e175d/dcb5d4e2-c757-4b6b-ae0c-691b26f70e7a.mp3";
        Uri uri = Uri.parse(podcastEpisode);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.e("", "initPlayer: ", e);
        }
    }
    public void playPause(View view) {

        if (!playing) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }
        playing = !playing;
    }

    public void toggletext(View view){
        final Button kailabtn = (Button) findViewById(R.id.kaila);
        kailabtnclicked = !kailabtnclicked;
        if(kailabtnclicked == true){
            kailabtn.setText("Kaila");
        }else{
            kailabtn.setText("toggle click!");
        }
    }
}
