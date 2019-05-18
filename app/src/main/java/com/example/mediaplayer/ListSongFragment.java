package com.example.mediaplayer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mediaplayer.adapter.ListSongAdapter;
import com.example.mediaplayer.model.Music;

import java.util.ArrayList;

public class ListSongFragment extends Fragment {

    ListView lvBaiHatGoc;
    ArrayList<Music> dsBaiHatGoc;
    ListSongAdapter adapterBaiHatGoc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listsong_layout, container, false);
        AddControls(view);

        return view;
    }

    private void AddControls(View view) {
        lvBaiHatGoc = view.<ListView>findViewById(R.id.listSong);
        dsBaiHatGoc = new ArrayList<>();
        adapterBaiHatGoc = new ListSongAdapter(getActivity(),R.layout.layout_listitem_vertical,dsBaiHatGoc);
        lvBaiHatGoc.setAdapter(adapterBaiHatGoc);

        DanhSachMusicLayTuDatabase();

        adapterBaiHatGoc.notifyDataSetChanged(); //cập nhật lại apdapter
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
        }
        cursor.close(); //Đóng kết nối
    }

}
