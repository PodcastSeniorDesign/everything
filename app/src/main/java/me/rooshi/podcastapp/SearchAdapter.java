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
    private List<String> results;
    private LayoutInflater inflater;

    public static class ResultHolder extends RecyclerView.ViewHolder {

        public TextView result;
        public ResultHolder(View result) {
            super(result);
            this.result = result.findViewById(R.id.podcastText);
        }
    }

    public SearchAdapter(Context context, List<String> results) {
        this.results = results;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public SearchAdapter.ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.podcast_row, parent, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        String podcast = results.get(position);
        holder.result.setText(podcast);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

}
