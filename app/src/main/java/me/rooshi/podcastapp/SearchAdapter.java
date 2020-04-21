package me.rooshi.podcastapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ResultHolder> {
    private List<Podcast> results;
    private LayoutInflater inflater;

    private RecyclerViewClickListener clickListener;

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

    public static class ResultHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener listener;
        public TextView result;

        public ResultHolder(View result, RecyclerViewClickListener listener) {
            super(result);
            this.listener = listener;
            this.result = result.findViewById(R.id.podcastText);
            result.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public SearchAdapter(Context context, List<Podcast> results, RecyclerViewClickListener listener) {
        this.results = results;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = listener;
    }

    public static class Podcast {
        public String track;
        public String artist;
        public String artwork;

        public Podcast(String track, String artist, String artwork) {
            this.track = track;
            this.artist = artist;
            this.artwork = artwork;
        }
    }

    @Override
    public SearchAdapter.ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.podcast_row, parent, false);
        return new ResultHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        String podcast = results.get(position).track;
        holder.result.setText(podcast);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}
