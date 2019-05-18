package com.example.mediaplayer.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mediaplayer.MainActivity;
import com.example.mediaplayer.R;
import com.example.mediaplayer.model.Music;

import java.util.List;

public class ListSongAdapter extends ArrayAdapter<Music> {
    Activity context;
    int resource;
    List<Music> objects;

    public ListSongAdapter(Activity context, int resource, List<Music> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(resource,null);
        TextView txtNameSong = (TextView)row.findViewById(R.id.title);
        TextView txtNameArtist = row.<TextView>findViewById(R.id.artist);
//        btnLike = row.<ImageButton>findViewById(R.id.btnlike);
//        btnDislike = row.<ImageButton>findViewById(R.id.btndislike);

        final Music music = this.objects.get(position);
        txtNameSong.setText(music.getNamesong());
        txtNameArtist.setText(String.valueOf(music.getArtist()));
//
//        if (music.getFavorite()){
//            btnLike.setVisibility(View.INVISIBLE);
//            btnDislike.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            btnLike.setVisibility(View.VISIBLE);
//            btnDislike.setVisibility(View.INVISIBLE);
//        }
//
//        btnLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                xuLyThich(music);
//            }
//        });
//
//        btnDislike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                xuLyKhongThich(music);
//            }
//        });
        return row;
    }

//    private void xuLyKhongThich(Music music) {
//        music.setFavorite(false);
//        btnDislike.setVisibility(View.INVISIBLE);
//        btnLike.setVisibility(View.VISIBLE);
//        ContentValues row = new ContentValues();
//        row.put("favorite",false);
//        MainActivity.database.update("music",row,"idsong=?",new String[]{music.getIdsong()});
//        notifyDataSetChanged();
//        if (MainActivity.DA_CHON_TAB_YEU_THICH)
//            ListfavsongActivity.LayDuLieuBaiHatYeuThichTuCSDL();
//    }
//    //Xử lý chọn thich bài hát
//    private void xuLyThich(Music music) {
//        music.setFavorite(true);
//        btnDislike.setVisibility(View.VISIBLE);
//        btnLike.setVisibility(View.INVISIBLE);
//        ContentValues row = new ContentValues();
//        row.put("favorite",true);
//        MainActivity.database.update("music",row,"idsong=?",new String[]{music.getIdsong()});
//        notifyDataSetChanged();
//        if (MainActivity.DA_CHON_TAB_YEU_THICH)
//            ListfavsongActivity.LayDuLieuBaiHatYeuThichTuCSDL();
//    }
}
