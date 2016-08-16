package com.ag.testapplication;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LovedTracksAdapter extends RecyclerView.Adapter<LovedTracksAdapter.LovedTracksViewHolder> {


    private List<LovedTrack> lovedTracks;
    private Context context;

    public LovedTracksAdapter(Context context,List<LovedTrack> lovedTracks) {
        this.lovedTracks = lovedTracks;
        this.context = context;
    }

    @Override
    public LovedTracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new LovedTracksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LovedTracksViewHolder holder, int position) {
        holder.artist.setText(lovedTracks.get(position).getArtist());
        holder.track.setText(lovedTracks.get(position).getTrack());

        Picasso.with(context).load(lovedTracks.get(position).getImageURL()).fit().into(holder.album);
    }

    @Override
    public int getItemCount() {
        return lovedTracks.size();
    }

    public class LovedTracksViewHolder extends RecyclerView.ViewHolder {

        public TextView artist,track;
        public ImageView album;

        public LovedTracksViewHolder(View itemView) {
            super(itemView);

            artist = (TextView) itemView.findViewById(R.id.artist);
            track = (TextView) itemView.findViewById(R.id.track);
            album = (ImageView)itemView.findViewById(R.id.album_image);

        }

    }

}



