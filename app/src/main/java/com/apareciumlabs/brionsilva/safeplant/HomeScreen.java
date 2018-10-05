package com.apareciumlabs.brionsilva.safeplant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        //default fragment (Home fragment)
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.relativelayout_for_fragment, new HomeFragment());
        tx.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Home fragment navigation
        if (id == R.id.nav_home) {

            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    homeFragment,
                    homeFragment.getTag()).commit();

        //Scheduler fragment navigation
        }else if (id == R.id.nav_scheduler) {

            SchedulerFragment schedulerFragment = new SchedulerFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    schedulerFragment,
                    schedulerFragment.getTag()).commit();

        //Ask a question fragment navigation
        } else if (id == R.id.nav_askQuestion) {

            AskQuestionFragment askQuestionFragment = new AskQuestionFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    askQuestionFragment,
                    askQuestionFragment.getTag()).commit();

        //Feedback fragment navigation
        } else if (id == R.id.nav_feedback) {

            FeedbackFragment feedbackFragment = new FeedbackFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    feedbackFragment,
                    feedbackFragment.getTag()).commit();

        //sos fragment navigation
        }else if (id == R.id.nav_sos) {

            SOSFragment sosFragment = new SOSFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    sosFragment,
                    sosFragment.getTag()).commit();

        //prescription fragment navigation
        }else if (id == R.id.nav_prescriptions) {

            PrescriptionsFragment prescriptionsFragment = new PrescriptionsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    prescriptionsFragment,
                    prescriptionsFragment.getTag()).commit();

        //reports fragment navigation
        }else if (id == R.id.nav_reports) {

            ReportsFragment reportsFragment = new ReportsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    reportsFragment,
                    reportsFragment.getTag()).commit();

         /** Sub menu area **/
        //change account fragment navigation
        }else if (id == R.id.nav_changeAccount) {

            Toast.makeText(getApplicationContext(),"Coming Soon!",Toast.LENGTH_SHORT).show();

        //donate fragment navigation
        }else if (id == R.id.nav_donate) {


            DonateFragment donateFragment = new DonateFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    donateFragment,
                    donateFragment.getTag()).commit();

        //About us fragment navigation
        }else if (id == R.id.nav_about) {

            AboutFragment aboutFragment = new AboutFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    aboutFragment,
                    aboutFragment.getTag()).commit();

        //contact us fragment navigation
        }else if (id == R.id.nav_contact) {

            ContactFragment contactFragment = new ContactFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.relativelayout_for_fragment,
                    contactFragment,
                    contactFragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
