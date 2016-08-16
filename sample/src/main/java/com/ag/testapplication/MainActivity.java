package com.ag.testapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ag.lfm.Lfm;
import com.ag.lfm.LfmError;
import com.ag.testapplication.fragments.LovedTracksFragment;
import com.ag.testapplication.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {


    private FragmentTransaction mTransaction;
    private LovedTracksFragment lovedTracksFragment = new LovedTracksFragment();
    private MainFragment mainFragment = new MainFragment();
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        //Check user login  state
        Lfm.wakeUpSession(new Lfm.LfmCallback<Lfm.LoginState>() {
            @Override
            public void onResult(Lfm.LoginState result) {
                switch (result) {
                    case LoggedOut:
                        //if user logged out go to LoginActivity
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case LoggedIn:
                        replace(mainFragment);
                        break;
                }
            }

            @Override
            public void onError(LfmError error) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loved_tracks:
                replace(lovedTracksFragment);
                return true;
            case R.id.scrobble_tracks:
                return true;
            default:
                return false;
        }
    }

    private void replace(Fragment fragment) {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.container, fragment);
        mTransaction.commit();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
    }

}
