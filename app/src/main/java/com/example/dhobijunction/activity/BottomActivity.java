package com.example.dhobijunction.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.dhobijunction.fragment.CartFragment;
import com.example.dhobijunction.fragment.HomeFragment;
import com.example.dhobijunction.fragment.OrderFragment;
import com.example.dhobijunction2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class BottomActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    BottomNavigationView bottomNav;
    NavigationView navigationView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        preferences = getApplicationContext().getSharedPreferences("Users", 0);
        editor = preferences.edit();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.setDrawerIndicatorEnabled(true);
        toogle.syncState();

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.item1:
                        startActivity(new Intent(BottomActivity.this,ProductActivity.class));
                        break;
                    case R.id.item2:
                        startActivity(new Intent(BottomActivity.this,OffersActivity.class));
                        break;
                    case R.id.item3:
                        bottomNav.setSelectedItemId(R.id.nav_order);
                        break;
                    case R.id.item4:
                        bottomNav.setSelectedItemId(R.id.nav_cart);
                        break;
                    case R.id.item5:
                        startActivity(new Intent(BottomActivity.this,ContactUsActivity.class));
                        break;
                    case R.id.item6:
                        startActivity(new Intent(BottomActivity.this,AboutUsActivity.class));
                        break;
                 //   case R.id.item7:
                   //     startActivity(new Intent(BottomActivity.this,ShareAppActivity.class));
                    //    break;
                    case R.id.item8:
                        startActivity(new Intent(BottomActivity.this,TermsAndConditionActivity.class));
                        break;
                    case R.id.item9:
                        editor.clear();
                        editor.commit();
                        startActivity(new Intent(BottomActivity.this,RegistrationActivity.class));
                        finish();
                        break;

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return  false;

            }

        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            setTitle("Dhobi Junction");
                            break;
                        case R.id.nav_order:
                            selectedFragment = new OrderFragment();
                            setTitle("My Order");
                            break;
                        case R.id.nav_cart:
                            selectedFragment = new CartFragment();
                            setTitle("My Cart");
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        if(bottomNav.getSelectedItemId()!=R.id.nav_home){
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
        else
            bottomNav.setSelectedItemId(R.id.nav_home);
    }
}