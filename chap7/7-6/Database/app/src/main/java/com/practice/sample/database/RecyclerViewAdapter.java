package com.practice.sample.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public interface RecyclerViewAdapterEventListener {
        void onClick(View view);
    }

    private MemoListDbHelper memoListDbHelper;
    private ArrayList<Memo> memoArrayList;

    private RecyclerViewAdapterEventListener listener;

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
        }
    }

    public RecyclerViewAdapter(Context context, RecyclerViewAdapterEventListener listener) {
        memoListDbHelper = new MemoListDbHelper(context);
        this.listener = listener;
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
        Memo memo = memoArrayList.get(position);

        holder.title.setText(memo.fileName);
        holder.date.setText(memo.date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (memoArrayList == null) {
            return 0;
        }
        return memoArrayList.size();
    }

    public Memo getMemo(int position) {
        if (memoArrayList == null || memoArrayList.size() < position) {
            return null;
        }

        return memoArrayList.get(position);
    }

    public void refreshMemoList() {
        memoArrayList = memoListDbHelper.loadMemoList();
        notifyDataSetChanged();
    }

}
