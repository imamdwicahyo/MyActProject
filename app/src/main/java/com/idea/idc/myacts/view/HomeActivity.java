package com.idea.idc.myacts.view;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;

import com.idea.idc.myacts.R;
import com.idea.idc.myacts.database.AppDatabase;
import com.idea.idc.myacts.entity.Aktivitas;
import com.idea.idc.myacts.view.fragment.HomeFragment;
import com.idea.idc.myacts.view.fragment.ProfilFragment;
import com.idea.idc.myacts.view.fragment.TaskFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Aktivitas aktivitas;
    public static AppDatabase db;

    List<Aktivitas> aktivitasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // membuat transparan notifikasi
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setFlags(
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//            );
//        }

        final Intent intent = getIntent();
        String message = intent.getStringExtra("DATAINTENT");

        if (message == "task_fragment"){
            loadFragment(new TaskFragment());
        }else{
            // set default fragment
            loadFragment(new HomeFragment());
        }

        // BottomNavigaionView listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // untuk database room
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"aktivitas").allowMainThreadQueries().build();

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;
            case R.id.navigation_task:
                fragment = new TaskFragment();
                break;
            case R.id.navigation_profil:
                fragment = new ProfilFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, fragment).commit();
            return true;
        }
        return false;
    }

    public void btn_today(View view) {
        Intent intent = new Intent(HomeActivity.this, ListActivity.class);
        startActivity(intent);
    }
}
