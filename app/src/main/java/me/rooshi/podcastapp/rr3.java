package me.rooshi.podcastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class rr3 extends AppCompatActivity {

    List<String> results = new ArrayList<String>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rr3);

        results.add("Hello World");
        results.add("This is a post test");

        recyclerView = (RecyclerView) findViewById(R.id.feedView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FeedAdapter(this, results);
        recyclerView.setAdapter(mAdapter);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            mAuth.signInAnonymously();
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void newPost(View view) {
        EditText inputText = findViewById(R.id.textInput);
        String text = inputText.getText().toString();

        Post post = new Post(new Date().toString(), 0, text, mAuth.getCurrentUser().getDisplayName());
        String key = mDatabase.child("posts").push().getKey();
        mDatabase.child("posts").child(key).setValue(post);
    }
}
