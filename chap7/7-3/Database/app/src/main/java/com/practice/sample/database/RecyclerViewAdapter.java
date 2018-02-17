package com.practice.sample.database;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private String[] notes = {"노트1", "노트2", "노트3", "노트4", "노트5", "노트6", "노트7", "노트8", "노트9", "노트10"};

    private String[] dates = {"17.01.01", "17.02.01", "17.03.01", "17.04.01", "17.05.01", "17.06.01", "17.07.01", "17.08.01", "17.09.01", "17.10.01"};

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(notes[position]);
        holder.date.setText(dates[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "클릭 되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.length;
    }

}
