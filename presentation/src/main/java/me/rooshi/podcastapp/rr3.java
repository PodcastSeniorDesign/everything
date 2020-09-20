/*
package me.rooshi.podcastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class rr3 extends AppCompatActivity {

    List<Post> results = new ArrayList<Post>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rr3);

        setUpRecyclerView();

        populatePostsFromDatabase();
    }

    private void setUpRecyclerView() {

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


        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void newPost(View view) {
        EditText inputText = findViewById(R.id.textInput);
        String text = inputText.getText().toString();
        String user;
        if (mAuth.getCurrentUser() == null) {
            user = "anon";
        } else {
            user = mAuth.getCurrentUser().getDisplayName();
        }
        Post post = new Post(new Date().toString(), 0, text, user);
        String key = mDatabase.child("posts").push().getKey();
        post.setKey(key);
        mDatabase.child("posts").child(key).setValue(post);
    }

    private void populatePostsFromDatabase() {

        DatabaseReference ref = mDatabase.child("posts");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                results.clear();
                //recyclerView.removeAllViews();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Post p = ds.getValue(Post.class);
                    results.add(p);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        ref.addValueEventListener(postListener);
    }
}
*/
