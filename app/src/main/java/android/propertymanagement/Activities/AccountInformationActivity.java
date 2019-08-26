package android.propertymanagement.Activities;

import android.os.Bundle;
import android.propertymanagement.Fragments.AccountOwnerFragment;
import android.propertymanagement.Fragments.UsersFragment;
import android.propertymanagement.R;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class AccountInformationActivity extends BaseActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);


        initViews();

    }

    private void initViews() {
        viewPager = findViewById(R.id.viewpager);

        // load the fragments when the tab is selected or page is swiped
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(2);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
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
        adapter.addFragment(new AccountOwnerFragment(), getString(R.string.account_owner));
        adapter.addFragment(new UsersFragment(), getString(R.string.users));
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
}
