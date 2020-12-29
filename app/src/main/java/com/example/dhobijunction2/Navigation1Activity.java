package com.example.dhobijunction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.dhobijunction2.priceEstimator.PriceEstimatorActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Navigation1Activity extends AppCompatActivity {
    private Toolbar toolbar;

    DrawerLayout drawerLayout;
    FloatingActionButton floatingActionButton;
    BottomNavigationView navigationView;
    SharedPreferences preferences;

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation1);
        // getSupportActionBar().hide();
        drawerLayout = findViewById(R.id.drabale);
        floatingActionButton = findViewById(R.id.fab);
        navigationView = findViewById(R.id.bottom_nav_viwe);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact="+918780272970";
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


        NavigationView view = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.freame_layout, new HomeFragment()).commit();
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.itm1:
                        startActivity(new Intent(Navigation1Activity.this, PriceEstimatorActivity.class));
                        break;
                    case R.id.itm2:
                        startActivity(new Intent(Navigation1Activity.this, MyOffersActivity.class));
                        break;
                    case R.id.itm3:
                        navigationView.setSelectedItemId(R.id.nav_order);
                        break;
                    case R.id.itm4:
                        navigationView.setSelectedItemId(R.id.nav_cart);
                        break;
                    case R.id.itm5:
                        startActivity(new Intent(Navigation1Activity.this, ContacUsActivity.class));
                        break;
                    case R.id.itm6:
                        startActivity(new Intent(Navigation1Activity.this, AboutActivity.class));
                        break;
                    case R.id.itm7:
                        startActivity(new Intent(Navigation1Activity.this,ShareActivity.class));
                        break;
                    case R.id.itm8:
                        startActivity(new Intent(Navigation1Activity.this,TeamActivity.class));
                        break;
                    case R.id.itm9:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Navigation1Activity.this,loginActivity.class));
                        finish();
                        break;


                }
                return true;
            }
        });

       // getSupportFragmentManager().beginTransaction().replace(R.id.freame_layout, new HomeFragment()).commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment ;
                switch (item.getItemId()) {
                    case R.id.nav_home:

                        getSupportFragmentManager().beginTransaction().replace(R.id.freame_layout,
                                new HomeFragment() ).commit();
                        toolbar.setTitle("Dhobi Junction");
                        return true;
                    case R.id.nav_order:

                        getSupportFragmentManager().beginTransaction().replace(R.id.freame_layout,
                                new OrderFragement() ).commit();
                        toolbar.setTitle("My Order");
                        return true;
                    case R.id.nav_cart:

                        getSupportFragmentManager().beginTransaction().replace(R.id.freame_layout,
                                new CartFragment() ).commit();
                        toolbar.setTitle("My Cart");
                        return true;


                }
                return false;
            }
        });

    }

}
