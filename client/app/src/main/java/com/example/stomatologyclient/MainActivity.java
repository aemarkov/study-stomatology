package com.example.stomatologyclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.stomatologyclient.auth.StomatologyAccountManager;
import com.example.stomatologyclient.fragments.CategoriesFragment;
import com.example.stomatologyclient.fragments.ClinicInfoFragment;
import com.example.stomatologyclient.fragments.DoctorsFragment;
import com.example.stomatologyclient.fragments.OrdersFragment;
import com.example.stomatologyclient.fragments.PatientFragment;
import com.example.stomatologyclient.fragments.PatientsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView header_username;
    Menu navigation_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigation_menu = navigationView.getMenu();


        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        header_username = (TextView)header.findViewById(R.id.header_username_view);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new ClinicInfoFragment());
        ft.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        set_menu();;
        String name = StomatologyAccountManager.getUsername(this);

        if(name==null)
            header_username.setVisibility(View.GONE);
        else
        {
            header_username.setVisibility(View.VISIBLE);
            header_username.setText(name);
        }
    }

    private void set_menu()
    {
        int role = StomatologyAccountManager.getRole(this);


        MenuItem visits = navigation_menu.findItem(R.id.nav_visits);
        MenuItem patients = navigation_menu.findItem(R.id.nav_patients);
        MenuItem orders = navigation_menu.findItem(R.id.nav_orders);
        MenuItem logout = navigation_menu.findItem(R.id.nav_logout);
        MenuItem login = navigation_menu.findItem(R.id.nav_signin);

        //Добавление специифичных для каждой категории пользователей пунтков меню
        if(role==StomatologyAccountManager.ROLE_PATIENT)
        {
            visits.setVisible(true);
            orders.setVisible(false);
            patients.setVisible(false);
        }
        else if(role==StomatologyAccountManager.ROLE_DOCTOR)
        {
            visits.setVisible(false);
            orders.setVisible(false);
            patients.setVisible(true);
        }
        else if(role==StomatologyAccountManager.ROLE_TECHNICAN)
        {
            visits.setVisible(false);
            orders.setVisible(true);
            patients.setVisible(false);
        }
        else if(role==StomatologyAccountManager.ROLE_ADMIN)
        {
            patients.setVisible(true);
            orders.setVisible(true);
            visits.setVisible(false);
        }

        if(role!=-1) {
            logout.setVisible(true);
            login.setVisible(false);
        }
        else
        {
            logout.setVisible(false);
            login.setVisible(true);
        }
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
        // Inflate the navigation_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(id==R.id.nav_about)
        {
            ft.replace(R.id.container,new ClinicInfoFragment());
        }
        else if(id==R.id.nav_categories)
        {
            ft.replace(R.id.container,new CategoriesFragment());
        }
        else if(id==R.id.nav_doctors)
        {
            ft.replace(R.id.container,new DoctorsFragment());
        }
        else if(id==R.id.nav_signin)
        {
            StomatologyAccountManager.showLoginActivity(this);
        }
        else if(id==R.id.nav_visits)
        {
            ft.replace(R.id.container, new PatientFragment());
        }
        else if(id==R.id.nav_patients)
        {
            ft.replace(R.id.container,new PatientsFragment());
        }
        else if(id==R.id.nav_orders)
        {
            ft.replace(R.id.container, new OrdersFragment());
        }
        else if(id==R.id.nav_logout)
        {
            StomatologyAccountManager.logout(this);
            finish();
            startActivity(getIntent());
        }

        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Запускает активити
    private void run_activity(Class<?> activity_class)
    {
        Intent intent = new Intent(this, activity_class);
        startActivity(intent);
    }
}
