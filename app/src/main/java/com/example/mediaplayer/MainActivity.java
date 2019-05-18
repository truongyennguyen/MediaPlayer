package com.example.mediaplayer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;

    public  static String DATABASE_NAME = "dbmediaplayer.sqlite";
    String DB_PATH_SUFFIX = "/databases/";

    public  static SQLiteDatabase database = null;
    public static Context contextOfApplication;
    public static boolean DA_CHON_TAB_YEU_THICH = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setBackgroundResource(R.drawable.menubackground);
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        checkAndRequestPermissions();
        xuLySaoChepSQLTuAssetVaoHeThongMobile();
        MoKetNoiCSDL();

        //set nav_menu
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        checkAndRequestPermissions();
        xuLySaoChepSQLTuAssetVaoHeThongMobile();
        MoKetNoiCSDL();
    }


    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }


    // handle Navigation item click
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_songslist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListSongFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    //Kiểm tra và yêu cầu quyền truy cập bộ nhớ điện thoại
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

    //Hàm xao chép file sqlite từ thư mục Asset vào hệ thống điện thoại
    private void xuLySaoChepSQLTuAssetVaoHeThongMobile() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        //Kiểm tra có tồn tại database
        if (!dbFile.exists())
        {
            try
            {
                CopyDatabaseFromAsset();
                /*Toast.makeText(this,"Sao chép CSDL vào hệ thống thành công",Toast.LENGTH_LONG).show();*/
            }
            catch (Exception e)
            {
                Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try
        {
            //Lấy dữ liệu trong asset
            InputStream myInput = getAssets().open(DATABASE_NAME);
            //Lấy đường dẫn output;
            String outFileName = layDuongDanLuuTru();

            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            //Kiểm tra file có tồn tại đường dẫn
            if (!f.exists())
            {
                f.mkdir(); //Không tồn tại thì tạo mới
            }

            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            //Chép input vào ouput mỗi lần 1024 byte
            while ((length=myInput.read(buffer))>0) {
                myOutput.write(buffer, 0, length);
            }
            //Đóng kết nối
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception ex)
        {
            Log.e("Lỗi sao chép:",ex.toString());
        }
    }

    private String layDuongDanLuuTru() {
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }


    private void MoKetNoiCSDL()
    {
        //Bước 1: mở CSDL
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
    }
}
