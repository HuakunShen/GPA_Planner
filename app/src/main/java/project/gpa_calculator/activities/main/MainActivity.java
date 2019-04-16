package project.gpa_calculator.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import project.gpa_calculator.R;
import project.gpa_calculator.activities.GPA_setter.GPA_setter_Activity;
import project.gpa_calculator.activities.login.login_activity;
import project.gpa_calculator.activities.year.YearActivity;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Button details_btn;
    private MainActivityController controller;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    public static final String userFile = "userFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupController();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFAB();
        setupNavigation(toolbar);
        setupButton();
    }


    private void setupController() {
        controller = new MainActivityController();
        controller.setContext(this);
    }

    private void setupNavigation(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        user = mAuth.getCurrentUser();
        View headerView = navigationView.getHeaderView(0);
        TextView username_tv = headerView.findViewById(R.id.username);
        TextView email_tv = headerView.findViewById(R.id.email_tv);
        if (user != null) {
            username_tv.setText(user.getDisplayName());
            email_tv.setText(user.getEmail());
        } else {
            // Connection is slow, user could be null if just signed in, then we cannot load user's
            // name and email
            for (; user == null; ) {
                user = mAuth.getCurrentUser();
            }
            username_tv.setText(user.getDisplayName());
            email_tv.setText(user.getEmail());
        }

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_event_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupButton() {
        details_btn = findViewById(R.id.details_Btn);
        details_btn.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        user = mAuth.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.details_Btn:
                intent = new Intent(getApplication(), YearActivity.class);
                startActivity(intent);
                break;
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
//                startActivity(new Intent(getApplication(), GPA_setter_Activity.class));
                return true;
            case R.id.signout_btn:
                mAuth.signOut();
                // Check if is logged in with facebook, if so, then log out facebook
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
//                    Toast.makeText(this, "is logged in", Toast.LENGTH_LONG).show();
                    LoginManager.getInstance().logOut();
                }
                startActivity(new Intent(this, login_activity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.gpa_setting_btn) {
            startActivity(new Intent(getApplication(), GPA_setter_Activity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
