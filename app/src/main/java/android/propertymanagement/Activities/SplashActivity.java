package android.propertymanagement.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.propertymanagement.R;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import rx.Subscription;


public class SplashActivity extends AppCompatActivity {


    private Context context;
    private static int SPLASH_TIME_OUT = 5000;
    private Subscription mSubscription;
    String Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;

        /* intializing and assigning ID's */
        initViews();

        /* Navigation's and using the views */
        setViews();
    }


    private void initViews() {



    }

    private void setViews() {

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();


            }
        }, SPLASH_TIME_OUT);


    }

}
