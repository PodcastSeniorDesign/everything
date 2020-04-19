package me.rooshi.podcastapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ResultHolder> {
    private List<PostModel> posts;
    private LayoutInflater inflater;

    public static class ResultHolder extends RecyclerView.ViewHolder {

        public TextView postText;
        public TextView userText;
        public TextView likeCount;
        public TextView dateText;
        public ImageButton likeButton;

        public ResultHolder(View view) {
            super(view);
            this.postText = view.findViewById(R.id.postText);
            this.userText = view.findViewById(R.id.userText);
            this.likeCount = view.findViewById(R.id.likeCount);
            this.dateText = view.findViewById(R.id.dateText);
            this.likeButton = view.findViewById(R.id.likeButton);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public FeedAdapter(Context context, List<PostModel> posts) {
        this.posts = posts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public FeedAdapter.ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_row, parent, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        PostModel post = posts.get(position);
        holder.postText.setText(String.format("%s has made a post", post.username));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

}
