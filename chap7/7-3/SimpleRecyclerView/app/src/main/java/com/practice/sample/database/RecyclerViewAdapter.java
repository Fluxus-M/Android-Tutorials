package com.practice.sample.database;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private String[] notes = {"노트1", "노트2", "노트3", "노트4", "노트5", "노트6", "노트7", "노트8", "노트9", "노트10"};

    public class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(TextView textView) {
            super(textView);
            this.textView = textView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        ViewHolder vh = new ViewHolder(textView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(notes[position]);
    }

    @Override
    public int getItemCount() {
        return notes.length;
    }

}
