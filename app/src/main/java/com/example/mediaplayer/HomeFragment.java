package com.example.mediaplayer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.mediaplayer.adapter.RecyclerViewAdapter;
import com.example.mediaplayer.model.Music;


import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList<Music> dsBaiHatGoc = new ArrayList<>();
    ArrayList<Music> dsBaiHatYeuThich = new ArrayList<>();
    RecyclerViewAdapter adapterListSong, adapterListFavoriteSong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home, container, false);
        //set RecyclerView list song
        RecyclerView recyclerViewListSong = view.findViewById(R.id.listSong);
        adapterListSong = new RecyclerViewAdapter(dsBaiHatGoc);
        recyclerViewListSong.setAdapter(adapterListSong);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false);
        recyclerViewListSong.setLayoutManager(layoutManager);

        //set RecyclerView list favorite song
        RecyclerView recyclerViewListFavoriteSong = view.findViewById(R.id.listFavorireSong);
        adapterListFavoriteSong = new RecyclerViewAdapter(dsBaiHatYeuThich);
        recyclerViewListFavoriteSong.setAdapter(adapterListFavoriteSong);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false);
        recyclerViewListFavoriteSong.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        AddControls();
        return view;
    }


    private void AddControls() {
        dsBaiHatGoc = new ArrayList<>();
        //init RecyclerView

        if (KiemTraLanDauChayApp()) { //nếu lần đầu tiên chạy app thì mới quét điện thoại
            /*KhoiTaoList();
            DanhSachMusicQuetDuoc();*/
            getListSongs();
        }
        else //lấy từ database ra thôi
        {
            DanhSachMusicLayTuDatabase();
        }
        adapterListSong.updateListMusic(dsBaiHatGoc);
        adapterListFavoriteSong.updateListMusic(dsBaiHatYeuThich);
    }

    private void DanhSachMusicLayTuDatabase() {
        Cursor cursor = MainActivity.database.query("music",null,null,null,null,null,null);

        dsBaiHatGoc.clear();
        while (cursor.moveToNext())
        {
            Music music = new Music();
            music.setIdsong(cursor.getString(0));
            music.setNamesong(cursor.getString(1));
            music.setArtist(cursor.getString(2));
            music.setAlbum(cursor.getString(3));
            Boolean bool = cursor.getInt(4)>0;
            music.setFavorite(bool);
            music.setPath(cursor.getString(5));
            dsBaiHatGoc.add(music);
            if(music.getFavorite()){
                dsBaiHatYeuThich.add(music);
            }
        }
        cursor.close(); //Đóng kết nối
    }

    private boolean KiemTraLanDauChayApp()
    {
        Cursor cursor = MainActivity.database.query("music",null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    void getListSongs(){
        ContentResolver contentResolver = MainActivity.getContextOfApplication().getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(songUri, null, null, null, null);
        if(songUri != null && cursor.moveToFirst()){
            do{
                String s = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                if (s.endsWith(".mp3")) {
                    Music music = new Music();
                    music.setIdsong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                    music.setNamesong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    music.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    music.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    music.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                    music.setFavorite(false);

                    ContentValues row = new ContentValues();
                    row.put("idsong", music.getIdsong());
                    row.put("namesong", music.getNamesong());
                    row.put("artist", music.getArtist());
                    row.put("album", music.getAlbum());
                    row.put("favorite", music.getFavorite());
                    row.put("path", music.getPath());

                    long r = MainActivity.database.insert("music", null, row);
                    dsBaiHatGoc.add(music);
                }

            }while(cursor.moveToNext());
        }
        cursor.close();
    }

}
