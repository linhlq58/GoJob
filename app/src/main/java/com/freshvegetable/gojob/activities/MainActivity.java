package com.freshvegetable.gojob.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.freshvegetable.gojob.R;
import com.freshvegetable.gojob.adapters.CategoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.cardRecycleView)
//    RecyclerView cardRecycleView;
    @BindView(R.id.categoryListView)
    ListView categoryListView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private CategoryAdapter mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mCategoryAdapter = new CategoryAdapter(this);
        categoryListView.setAdapter(mCategoryAdapter);

/*        categoryListView.setOnScrollListener(new HidingScrollListenner() {
            @Override
            public void onHide() {
//                toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
//
//                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
//                int fabBottomMargin = lp.bottomMargin;
//                fab.animate().translationY(fab.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
                toolbar.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }

            @Override
            public void onShow() {
//                toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
//                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                toolbar.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            }
        });
*/

/*        categoryListView.setOnTouchListener(new View.OnTouchListener() {
            float height;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float height = event.getY();
                if(action == MotionEvent.ACTION_DOWN){
                    this.height = height;
                }else if(action == MotionEvent.ACTION_UP){
                    if(this.height < height){
                        toolbar.setVisibility(View.GONE);
                    }else if(this.height > height){
                        toolbar.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
*/
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onClick() {
//        TODO to add new post activity
        Snackbar.make(drawerLayout, "New post", Snackbar.LENGTH_SHORT).show();
    }
}
