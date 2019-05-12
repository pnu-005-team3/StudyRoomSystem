package com.example.studyroomsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


class BuildingCardData {
    public String text;
    public int img;

    public BuildingCardData(String text, int img) {
        this.text = text;
        this.img = img;
    }
}

public class BuildingRecyclerAdapter extends RecyclerView.Adapter<BuildingRecyclerAdapter.ViewHolder> {
    private ArrayList<BuildingCardData> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageCard;
        public TextView textCard;

        public ViewHolder(View v) {
            super(v);
            imageCard = (ImageView) v.findViewById(R.id.imageCard);
            textCard = (TextView) v.findViewById(R.id.textCard);
        }
    }

    public BuildingRecyclerAdapter(ArrayList<BuildingCardData> dataset) {
        mDataset = dataset;
    }

    @Override
    public BuildingRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageCard.setImageResource(mDataset.get(position).img);
        holder.textCard.setText(mDataset.get(position).text);

        holder.imageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), position + "Select", Toast.LENGTH_SHORT).show();

                // 인텐트 : 선택시 해당 학습공간 액티비티로 이동
                Context context = v.getContext();
                Intent in = new Intent(context, StudyRoomActivity.class);
                in.putExtra("BuildingPosition",position);

                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}