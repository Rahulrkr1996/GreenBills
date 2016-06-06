package com.greenbills.www.greenbills;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView homeSwipeHeaderImage;
    private FloatingActionButton plus_main,plus_scan_qr,plus_scan_bill,plus_new_manual_expense,plus_add_cheque,plus_online_order;
    private TextView plus_text_scan_qr,plus_text_scan_bill,plus_text_manual_expense,plus_text_add_cheque,plus_text_online_order;
    private RelativeLayout home_main_layout;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Obtaining Views
        plus_main = (FloatingActionButton)findViewById(R.id.plus_main);
        plus_scan_qr  = (FloatingActionButton)findViewById(R.id.plus_scan_qr);
        plus_scan_bill = (FloatingActionButton)findViewById(R.id.plus_scan_bill);
        plus_new_manual_expense = (FloatingActionButton)findViewById(R.id.plus_new_manual_expense);
        plus_add_cheque = (FloatingActionButton)findViewById(R.id.plus_add_cheque);
        plus_online_order = (FloatingActionButton)findViewById(R.id.plus_online_order);

        plus_text_scan_qr = (TextView)findViewById(R.id.plus_text_scan_qr);
        plus_text_scan_bill = (TextView)findViewById(R.id.plus_text_scan_bill);
        plus_text_manual_expense = (TextView)findViewById(R.id.plus_text_manual_expense);
        plus_text_add_cheque = (TextView)findViewById(R.id.plus_text_add_cheque);
        plus_text_online_order = (TextView)findViewById(R.id.plus_text_online_order);

        home_main_layout = (RelativeLayout)findViewById(R.id.home_main_layout);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        plus_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFAB();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_home_page);
        homeSwipeHeaderImage = (ImageView)headerView.findViewById(R.id.homeSwipeHeaderImage);
        String path = Environment.getExternalStorageDirectory()+"/Green Bills/Profile/";
        String file = "profile_picture.jpg";
        Glide.with(this).load(new File(path+file)).into(homeSwipeHeaderImage);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void animateFAB(){

        if(isFabOpen){

            plus_main.startAnimation(rotate_backward);
            plus_scan_qr.startAnimation(fab_close);
            plus_scan_bill.startAnimation(fab_close);
            plus_online_order.startAnimation(fab_close);
            plus_add_cheque.startAnimation(fab_close);
            plus_new_manual_expense.startAnimation(fab_close);

            plus_scan_bill.setClickable(false);
            plus_scan_qr.setClickable(false);
            plus_online_order.setClickable(false);
            plus_add_cheque.setClickable(false);
            plus_new_manual_expense.setClickable(false);

            isFabOpen = false;
            home_main_layout.setVisibility(View.GONE);

        } else {

            plus_main.startAnimation(rotate_forward);
            plus_scan_qr.startAnimation(fab_open);
            plus_scan_bill.startAnimation(fab_open);
            plus_online_order.startAnimation(fab_open);
            plus_add_cheque.startAnimation(fab_open);
            plus_new_manual_expense.startAnimation(fab_open);

            plus_scan_bill.setClickable(true);
            plus_scan_qr.setClickable(true);
            plus_online_order.setClickable(true);
            plus_add_cheque.setClickable(true);
            plus_new_manual_expense.setClickable(true);

            isFabOpen = true;
            home_main_layout.setVisibility(View.VISIBLE);
        }
    }


}
