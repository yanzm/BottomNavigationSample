package net.yanzm.bottomnavigationsample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG_RECENT = "recent";
    private static final String FRAGMENT_TAG_FAVORITES = "favorites";
    private static final String FRAGMENT_TAG_NEARBY = "nearby";

    private RecentFragment recentFragment;
    private FavoritesFragment favoritesFragment;
    private NearbyFragment nearbyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recent:
                        switchFragment(recentFragment, FRAGMENT_TAG_RECENT);
                        break;
                    case R.id.action_favorites:
                        switchFragment(favoritesFragment, FRAGMENT_TAG_FAVORITES);
                        break;
                    case R.id.action_nearby:
                        switchFragment(nearbyFragment, FRAGMENT_TAG_NEARBY);
                        break;
                }
                return true;
            }
        });

        final FragmentManager manager = getSupportFragmentManager();
        recentFragment = (RecentFragment) manager.findFragmentByTag(FRAGMENT_TAG_RECENT);
        favoritesFragment = (FavoritesFragment) manager.findFragmentByTag(FRAGMENT_TAG_FAVORITES);
        nearbyFragment = (NearbyFragment) manager.findFragmentByTag(FRAGMENT_TAG_NEARBY);

        if (recentFragment == null) {
            recentFragment = new RecentFragment();
        }
        if (favoritesFragment == null) {
            favoritesFragment = new FavoritesFragment();
        }
        if (nearbyFragment == null) {
            nearbyFragment = new NearbyFragment();
        }

        if (savedInstanceState == null) {
            switchFragment(recentFragment, FRAGMENT_TAG_RECENT);
        }
    }

    private void switchFragment(@NonNull Fragment fragment, String tag) {
        if (fragment.isAdded()) {
            return;
        }

        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction ft = manager.beginTransaction();

        final Fragment currentFragment = manager.findFragmentById(R.id.container);
        if (currentFragment != null) {
            ft.detach(currentFragment);
        }
        if (fragment.isDetached()) {
            ft.attach(fragment);
        } else {
            ft.add(R.id.container, fragment, tag);
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
