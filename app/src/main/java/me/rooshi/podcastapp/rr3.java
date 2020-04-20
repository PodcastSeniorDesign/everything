package me.rooshi.podcastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class rr3 extends AppCompatActivity {

    List<PostModel> results = new ArrayList<PostModel>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rr3);

        setUpRecyclerView();

        database = FirebaseDatabase.getInstance().getReference("rooshiposts");

        populatePostsFromDatabase();
    }

    private void setUpRecyclerView() {
        results.add(new PostModel("1", "localtest", "now", "this was added in the constructor"));
        results.add(new PostModel("2", "localtest2", "also now", "this also was added in the constructor"));

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
    }

    public void newPost(View view) {

    }

    private void populatePostsFromDatabase() {

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("data snapshot:\n", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        database.addListenerForSingleValueEvent(postListener);
    }
}
