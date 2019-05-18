package com.example.mediaplayer.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mediaplayer.R;
import com.example.mediaplayer.model.Music;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Music> mListMusic;

    public RecyclerViewAdapter(ArrayList<Music> listMusic) {
        this.mListMusic = listMusic;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem_horizontal, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.song_name.setText(mListMusic.get(i).getNamesong());
        viewHolder.song_artist.setText(mListMusic.get(i).getArtist());
    }

    @Override
    public int getItemCount() {
        return mListMusic.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView song_name;
        TextView song_artist;

        public ViewHolder(View itemView){
            super(itemView);
            song_name = itemView.findViewById(R.id.song_name);
            song_artist = itemView.findViewById(R.id.song_artist);
        }
    }

    public void updateListMusic(ArrayList<Music> listMusic){
        mListMusic.clear();
        mListMusic.addAll(listMusic);
        this.notifyDataSetChanged();
    }
}
