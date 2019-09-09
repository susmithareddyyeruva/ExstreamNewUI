package android.propertymanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.propertymanagement.Adapter.ExpandablePropertyAdapter;
import android.propertymanagement.ModelClass.ResponseModelClasses.GetPropertySetupResponce;
import android.propertymanagement.R;
import android.propertymanagement.Services.APIConstantURL;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.Constants;
import android.propertymanagement.Utils.SharedPrefsData;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static final String TAG = HomeActivity.class.getSimpleName();

    private ImageView edit_info, properties_icon;
    private AlertDialog alertDialog, alert;
    TextView logout_dialog,username_dialog,myprofile_dialog,title_text;
    String userName,user;
    private String authorizationToken;
    private Subscription mSubscription;
    private TextView about_tv, terms_of_use_tv, privacy_policy_tv;

   private ExpandableListView lst_propertys;
   private ExpandablePropertyAdapter lst_propertyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userName =  SharedPrefsData.getString(this, Constants.USERNAME, Constants.PREF_NAME);
        user =  SharedPrefsData.getString(this, Constants.user, Constants.PREF_NAME);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(2).setActionView(R.layout.menu_image);
        navigationView.getMenu().getItem(2).setTitle(user);

        Menu menu = navigationView.getMenu();

        MenuItem tools = menu.findItem(R.id.nav_exstream);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
        tools.setTitle(s);
        navigationView.setNavigationItemSelectedListener(this);


        initViews();

        setViews();
        getProperty();
    }

    private void initViews() {
        edit_info = findViewById(R.id.edit_info);
        properties_icon = findViewById(R.id.properties_icon);
        about_tv = findViewById(R.id.about_tv);
        terms_of_use_tv = findViewById(R.id.terms_of_use_tv);
        privacy_policy_tv = findViewById(R.id.privacy_policy_tv);
        lst_propertys = findViewById(R.id.lst_property);
    }

    private void setViews() {

        edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AccountInformationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        properties_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NewPropertyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
        about_tv.setOnClickListener(this);
        terms_of_use_tv.setOnClickListener(this);
        privacy_policy_tv.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_company_setup) {
            // Handle the camera action
        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_exstream) {

            selectExsgtream();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void selectExsgtream() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_exstream, null);
        dialogBuilder.setView(dialogView);
        logout_dialog = dialogView.findViewById(R.id.logout_dialog);
        username_dialog = dialogView.findViewById(R.id.username_dialog);
        myprofile_dialog = dialogView.findViewById(R.id.myprofile_dialog);
        title_text = dialogView.findViewById(R.id.title_text);
        logout_dialog.setTypeface(ResourcesCompat.getFont(this, R.font.helveticaneue));
        username_dialog.setTypeface(ResourcesCompat.getFont(this, R.font.helveticaneue));
        myprofile_dialog.setTypeface(ResourcesCompat.getFont(this, R.font.helveticaneue));
        title_text.setTypeface(ResourcesCompat.getFont(this, R.font.oswald_extralight));
        username_dialog.setText(userName);
        title_text.setText(user);

        alertDialog = dialogBuilder.create();
        /**
         * @param OnClickListner
         */
        logout_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefsData.putInt(getApplicationContext(), Constants.ISLOGIN, 0, Constants.PREF_NAME);
                SharedPrefsData.getInstance(getApplicationContext()).ClearData(getApplicationContext());
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                alertDialog.dismiss();


            }
        });

        alertDialog.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.about_tv:

                /*
                 *  Custom dialog for aboutus
                 */
                aboutUsDialog();

                break;

            case R.id.terms_of_use_tv:

                /*
                 * Custom dialog for terms of use
                 */
                termsOfUseDialog();

                break;

            case R.id.privacy_policy_tv:

                /*
                 * Custom dialog for privacy policy
                 */
                privacyPolicyDialog();

                break;

        }

    }
    private void aboutUsDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_about_us, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void termsOfUseDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_terms_of_use, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void privacyPolicyDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_privacy_policy, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
    public void getProperty() {
        authorizationToken = SharedPrefsData.getString(HomeActivity.this, Constants.access_token, Constants.PREF_NAME);
        ExStreamApiService service = ServiceFactory.createRetrofitService(HomeActivity.this, ExStreamApiService.class);
        mSubscription = service.GetAllProperty(APIConstantURL.GetAllPropertys, "bearer" + " " + authorizationToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<GetPropertySetupResponce>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ((HttpException) e).code();
                            ((HttpException) e).message();
                            ((HttpException) e).response().errorBody();
                            try {
                                ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(ArrayList<GetPropertySetupResponce> mResponse) {

                        Log.e(TAG, "-- analysis ---> getproperty responce --->"+mResponse.toString());
                        lst_propertyAdapter =new ExpandablePropertyAdapter(mResponse,HomeActivity.this);

                        lst_propertys.setAdapter(lst_propertyAdapter);
                        lst_propertys.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                            @Override
                            public void onGroupExpand(int groupPosition) {
                              /*  Toast.makeText(getApplicationContext(),
                                        mResponse.get(groupPosition) + " List Expanded.",
                                        Toast.LENGTH_SHORT).show();*/
                            }
                        });

                        lst_propertys.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                            @Override
                            public void onGroupCollapse(int groupPosition) {
                               /* Toast.makeText(getApplicationContext(),
                                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                                        Toast.LENGTH_SHORT).show();*/

                            }
                        });

                        lst_propertys.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v,
                                                        int groupPosition, int childPosition, long id) {
                              /*  Toast.makeText(
                                        getApplicationContext(),
                                        expandableListTitle.get(groupPosition)
                                                + " -> "
                                                + expandableListDetail.get(
                                                expandableListTitle.get(groupPosition)).get(
                                                childPosition), Toast.LENGTH_SHORT
                                ).show();*/
                                return false;
                            }
                        });


                    }
                });
    }

}
