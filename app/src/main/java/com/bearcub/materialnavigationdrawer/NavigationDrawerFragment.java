package com.bearcub.materialnavigationdrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 4/27/2015.
 */
public class NavigationDrawerFragment extends Fragment {
    public static final String SHARED_PREFERENCES_FILE_NAME = "vtmaa_shared_preferences";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private static final String TAG = "nav_drawer_fragment";

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private NavigationDrawerAdapter adapter;
    private boolean userLearnedDrawer;
    private boolean fromSavedInstanceState;
    private View containerView;

    public NavigationDrawerFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userLearnedDrawer = Boolean.valueOf(readFromSharedPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));

        if(savedInstanceState != null){
            fromSavedInstanceState = true;
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.navigation_drawer_recycler_view);
        adapter = new NavigationDrawerAdapter(getActivity(), getNavigationDrawerList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.d(TAG, "onCreateView complete");

        return view;
    }

    public void initDrawer(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar){
        containerView = getActivity().findViewById(fragmentId);
        this.drawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_closed){
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);

                if(!userLearnedDrawer){
                    userLearnedDrawer = true;
                    writeToSharedPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, Boolean.toString(userLearnedDrawer));
                }
                getActivity().invalidateOptionsMenu();
            }
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);

                getActivity().invalidateOptionsMenu();
            }

        };

        if(!userLearnedDrawer && !fromSavedInstanceState){
            drawerLayout.openDrawer(containerView);
        }
        drawerLayout.setDrawerListener(drawerToggle);

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }

    private static List<NavigationDrawerItem> getNavigationDrawerList(){
        List<NavigationDrawerItem> list = new ArrayList<>();
        int[] images = {R.color.primary, R.color.primary_dark, R.color.primary_light, R.color.primary_dark_material_dark,
                R.color.primary_material_light, R.color.material_blue_grey_800};
        String[] labels = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};

        for (int i = 0; i < 100; i++){
            NavigationDrawerItem item = new NavigationDrawerItem();
            item.imageId = images[i%images.length];
            item.label = labels[i%labels.length];
            list.add(item);
        }
        return list;
    }

    public void writeToSharedPreferences(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String readFromSharedPreferences(Context context, String key, String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

}
