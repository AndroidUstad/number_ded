package com.example.number_ded.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.number_ded.Model.Tests;
import com.example.number_ded.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Tests> arrayList;
    ProgressBar progressBar;

    public RecyclerAdapter(ArrayList<Tests> arrayList) {
        this.arrayList = arrayList;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView number;
        ImageView image;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
            image = (ImageView) itemView.findViewById(R.id.image);
            progressBar=(ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        return new ItemViewHolder((layoutView));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        Tests test = arrayList.get(i);

        itemViewHolder.name.setText(test.getName());
        itemViewHolder.number.setText(test.getNumber());
        Glide.with(viewHolder.itemView.getContext()).load(test.getImage()).placeholder(R.drawable.download).into(itemViewHolder.image);
        if (progressBar!=null){
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}