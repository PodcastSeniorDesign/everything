package me.rooshi.podcastapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ResultHolder> {
    private List<Post> posts;
    private LayoutInflater inflater;
    private static DatabaseReference mDatabase;


    public static class ResultHolder extends RecyclerView.ViewHolder {

        public Post post;
        public TextView postText;
        public TextView userText;
        public TextView likeCountView;
        public TextView dateText;
        public ImageButton likeButton;

        public ResultHolder(View view) {
            super(view);
            this.postText = view.findViewById(R.id.postText);
            this.userText = view.findViewById(R.id.userText);
            this.likeCountView = view.findViewById(R.id.likeCountView);
            this.dateText = view.findViewById(R.id.dateText);
            this.likeButton = view.findViewById(R.id.likeButton);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    post.likes++;
                    likeCountView.setText(String.valueOf(post.likes));
                    Map<String, Object> postValues = post.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/posts/" + post.key, postValues);
                    mDatabase.updateChildren(childUpdates);
                }
            });
        }
        public void setPostModelReference(Post post) {
            this.post = post;
        }
    }

    public FeedAdapter(Context context, List<Post> posts) {
        this.posts = posts;
        this.inflater = LayoutInflater.from(context);
        setHasStableIds(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public FeedAdapter.ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_row, parent, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        Post post = posts.get(position);
        holder.userText.setText(String.format("%s has made a post", post.user));
        holder.postText.setText(post.text);
        holder.likeCountView.setText(String.valueOf(post.likes));
        holder.dateText.setText(post.date);
        holder.setPostModelReference(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
