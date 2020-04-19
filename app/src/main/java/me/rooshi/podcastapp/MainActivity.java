package me.rooshi.podcastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    TextView showValue;
    int counter = 0;

    private FirebaseFunctions mFunctions;

    boolean playing = false;
    MediaPlayer mediaPlayer = new MediaPlayer();
    List<String> results = new ArrayList<String>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    JSONObject searchResponse;
    boolean kailabtnclicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results.add("Hello World");

        recyclerView = (RecyclerView) findViewById(R.id.podcastList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new SearchAdapter(this, results);
        recyclerView.setAdapter(mAdapter);

        initPlayer();


        showValue = (TextView) findViewById(R.id.likeCount);


    }

    public void RR3Intent(View view) {
        Intent intent = new Intent(this, rr3.class);
        startActivity(intent);
    }

    public void Like(View view){
        counter++;
        showValue.setText(Integer.toString(counter));
        mFunctions = FirebaseFunctions.getInstance();
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

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        testTextView.setText("Response is: " + response);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("search");

                        myRef.setValue(response);

                        try {
                            searchResponse = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(response);
                        }

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

    public void loginWithGoogle(View view) {
        Intent intent = new Intent(this, GoogleLoginActivity.class);
        startActivity(intent);
    }
  
    private void initPlayer() {
        String podcastEpisode = "https://locator.simplecastcdn.com/e7ec86c9-5b4f-4c1c-af7b-0957921e175d/dcb5d4e2-c757-4b6b-ae0c-691b26f70e7a.mp3";
        Uri uri = Uri.parse(podcastEpisode);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepareAsync();
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

    public void recyclerDisplay(View view) {
        if (searchResponse == null) return;
        results.clear();
        mAdapter.notifyDataSetChanged();
        try {
            JSONArray results = searchResponse.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String trackName = result.getString("trackName");
                this.results.add(0, trackName);
                mAdapter.notifyItemInserted(0);
            }
        } catch (JSONException e) {
            System.out.println("JSON Error on parsing search results");
        }

    }

    public void toggletext(View view){

        final TextView testTextView = findViewById(R.id.testTextView);

        final Button kailabtn = (Button) findViewById(R.id.kaila);
        kailabtnclicked = !kailabtnclicked;
        if(kailabtnclicked == true){
            kailabtn.setText("toggle click!");
        }else{
            kailabtn.setText("kaila");
        }

        EditText inputText = findViewById(R.id.textInput);
        String toSearch = inputText.getText().toString();

        getSearch(toSearch)
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Exception e = task.getException();
                            if (e instanceof FirebaseFunctionsException) {
                                FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                                FirebaseFunctionsException.Code code = ffe.getCode();
                                Object details = ffe.getDetails();
                            }

                            // [START_EXCLUDE]
                            Log.w("notsurewhatthisis", "getSearch:onFailure", e);
//                            showSnackbar("An error occurred.");
                            return;
                            // [END_EXCLUDE]
                        }// if the task is successful

                        // [START_EXCLUDE]
                        String result = task.getResult();
                        testTextView.setText("This is the result from the firebase function: " + result);
                        // [END_EXCLUDE]
                    }
                });
    }

    private Task<String> getSearch(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);
        data.put("push", true);
        return mFunctions
                .getHttpsCallable("getSearch")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = task.getResult().getData().toString();
                        return result;
                    }
                }
                );
    }
}
