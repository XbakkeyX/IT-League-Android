package com.mycompany.itleague;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.mycompany.itleague.adapters.MenuDataAdapter;
import com.mycompany.itleague.fragments.NewsFragment_;
import com.mycompany.itleague.fragments.TableFragment_;
import com.mycompany.itleague.fragments.ViolationsFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;


@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.navdrawer)
    ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerArrowDrawable drawerArrow;

    private MenuDataAdapter adapter;

    private ViolationsFragment_ fragmentViolations = new ViolationsFragment_();

    private NewsFragment_ fragmentNews = new NewsFragment_();

    private TableFragment_ fragmentTable = new TableFragment_();

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @AfterViews
    void MenuDefault() {
        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        this.updateMenu();
    }


    @Background
    void updateMenu() {
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        Context sContext = getApplicationContext();

        ArrayList<String> menu = new ArrayList<String>();
        menu.add("Расписание");
        menu.add("Новости");
        menu.add("Нарушения");
        menu.add("Турнир");
        adapter = new MenuDataAdapter(this, menu);
        this.setMenuInfo();
    }


    @UiThread
    void setMenuInfo() {

        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                switch (position) {
                    case 0:

                        drawerArrow.setProgress(1f);
                        break;
                    case 1:

                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, fragmentNews)
                                .addToBackStack(null)
                                .commitAllowingStateLoss();
                        mDrawerLayout.closeDrawer(mDrawerList);

                        break;
                    case 2:
                        mDrawerToggle.syncState();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, fragmentViolations)
                                .addToBackStack(null)
                                .commitAllowingStateLoss();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        mDrawerToggle.syncState();
                        break;
                    case 3:
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, fragmentTable)
                                .addToBackStack(null)
                                .commitAllowingStateLoss();
                        mDrawerLayout.closeDrawer(mDrawerList);
                        mDrawerToggle.syncState();
                        break;
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}