package me.rooshi.podcastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ===== Kaila's button click =====
        final Button kailabtn = (Button) findViewById(R.id.kaila);
        kailabtn.setOnClickListener(new View.OnClickListener() { // we're passing in an instance of a new onclicklistener
            @Override
            public void onClick(View view) {
//                Log.i("kaila_btn", "kaila's log message!!!! :)");
//                Toast.makeText(getApplicationContext(), "clicked the button!", Toast.LENGTH_SHORT).show();//show a toast message
                // context is give it a context that is the UI that is being shown
                kailabtn.setText("clicked!");
            }
        });


    }

    public void searchTerm(View view) {
        EditText inputText = findViewById(R.id.textInput);
        String toSearch = inputText.getText().toString();
        //String toSearch = "money";

        final TextView testTextView = findViewById(R.id.testTextView);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String baseItunesUrl ="https://itunes.apple.com/search?media=podcast";
        Uri uri = Uri.parse(baseItunesUrl);
        uri = uri.buildUpon().appendQueryParameter("term", toSearch).build();
        String url = uri.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        testTextView.setText("Response is: "+ response);
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
}
