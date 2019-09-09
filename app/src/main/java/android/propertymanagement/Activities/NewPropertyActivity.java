package android.propertymanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.propertymanagement.Fragments.AccountOwnerFragment;
import android.propertymanagement.Fragments.InformationFragment;
import android.propertymanagement.Fragments.PropertyWalkFragment;
import android.propertymanagement.Fragments.RentRollFragment;
import android.propertymanagement.Fragments.SignageFragment;
import android.propertymanagement.Fragments.UnitdemographicsFragment;
import android.propertymanagement.Fragments.UsersFragment;
import android.propertymanagement.Fragments.UsersNewPropertyFragment;
import android.propertymanagement.Fragments.ValueAddFragment;
import android.propertymanagement.R;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewPropertyActivity extends BaseActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TextView cmpny_stp;
    private ImageView back_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_property);


        initViews();

    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        cmpny_stp = findViewById(R.id.cmpny_stp);
        back_image = findViewById(R.id.back_image);

        // load the fragments when the tab is selected or page is swiped
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(7);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        cmpny_stp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPropertyActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPropertyActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * * Layout manager that allows the user to flip left and right
     * through pages of data.  You supply an implementation of a
     * {PagerAdapter} to generate the pages that the view shows.
     *
     * @param viewPager
     */

    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new InformationFragment(), getString(R.string.information));
        adapter.addFragment(new UsersNewPropertyFragment(), getString(R.string.users));
        adapter.addFragment(new ValueAddFragment(), getString(R.string.value_add));
        adapter.addFragment(new UnitdemographicsFragment(), getString(R.string.unit_demographics));
        adapter.addFragment(new SignageFragment(), getString(R.string.signage));
        adapter.addFragment(new PropertyWalkFragment(), getString(R.string.property_walk));
        adapter.addFragment(new RentRollFragment(), getString(R.string.rent_roll));
        viewPager.setAdapter(adapter);

    }

    /**
     * * Implementation of {PagerAdapter} that
     * represents each page as a {@link Fragment} that is persistently
     * kept in the fragment manager as long as the user can return to the page.
     */

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewPropertyActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
