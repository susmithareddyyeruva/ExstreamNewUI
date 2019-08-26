package android.propertymanagement.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.propertymanagement.ModelClass.RequestModelClasses.LoginPageAPIRequestModel;
import android.propertymanagement.ModelClass.RequestModelClasses.PasswordResetRequestModel;
import android.propertymanagement.R;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.CommonUtil;
import android.propertymanagement.Utils.Constants;
import android.propertymanagement.Utils.SharedPrefsData;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    private EditText passwordEdt, emailEdt, emailEdt_dialog;
    private Button login_button, cancelBtn, submitBtn;
    private AlertDialog alertDialog, alert;
    private Subscription mSubscription;
    private CheckBox stayinloggedCB;
    private TextView about_tv, terms_of_use_tv, privacy_policy_tv,
            password_reset_tv, create_account_tv;
    private int expires_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        /* intializing and assigning ID's */
        initViews();


        /* Navigation's and using the views */
        setViews();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /* intializing and assigning ID's */
    private void initViews() {
        emailEdt = findViewById(R.id.email);
        passwordEdt = findViewById(R.id.password);
        login_button = findViewById(R.id.login_button);
        stayinloggedCB = findViewById(R.id.stayinloggedCB);
        about_tv = findViewById(R.id.about_tv);
        terms_of_use_tv = findViewById(R.id.terms_of_use_tv);
        privacy_policy_tv = findViewById(R.id.privacy_policy_tv);
        password_reset_tv = findViewById(R.id.password_reset_tv);
        create_account_tv = findViewById(R.id.create_account_tv);
    }

    /* Navigation's and using the views */
    private void setViews() {
        /**
         * @param OnClickListner
         */
        login_button.setOnClickListener(this);
        password_reset_tv.setOnClickListener(this);
        create_account_tv.setOnClickListener(this);
        about_tv.setOnClickListener(this);
        terms_of_use_tv.setOnClickListener(this);
        privacy_policy_tv.setOnClickListener(this);
        stayinloggedCB.setTypeface(ResourcesCompat.getFont(context, R.font.helveticaneue));
        emailEdt.setTypeface(ResourcesCompat.getFont(context, R.font.oswald_extralight));
        passwordEdt.setTypeface(ResourcesCompat.getFont(context, R.font.oswald_extralight));

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_button:

                /**
                 * Validations for user name and password
                 *
                 * @return
                 */
                if (validations()) {
                    // to start the Progress Dialog
                    showProgressDialog();

                    //    getLogin();
                    LoiginAPIPOST();
                }
                break;

            case R.id.password_reset_tv:
                /**
                 * custom forgotPasswordDialog for forgot password
                 */
                forgotPasswordDialog();

                break;

            case R.id.create_account_tv:

                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                break;


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


    /**
     * Validations for user name and password
     *
     * @return
     */
    private boolean validations() {
        if (TextUtils.isEmpty(emailEdt.getText().toString())) {
            emailEdt.setError(getString(R.string.err_please_enter_email));
            emailEdt.requestFocus();
            emailEdt.setEnabled(true);
            return false;
        } else if (!isValidEmail(emailEdt.getText().toString())) {
            emailEdt.setError(getString(R.string.err_please_enter_valid_email));
            emailEdt.requestFocus();
            emailEdt.setEnabled(true);
            return false;
        } else if (TextUtils.isEmpty(passwordEdt.getText().toString().trim())) {
            passwordEdt.setError(getString(R.string.err_please_enter_password));
            passwordEdt.setEnabled(true);
            passwordEdt.requestFocusFromTouch();
            passwordEdt.requestFocus();
            return false;
        }
        return true;
    }


    /**
     * Valid email or not
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(final String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();


    }

    /**
     * custom forgotPasswordDialog for forgot password
     */
    public void forgotPasswordDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_forgot_password, null);
        dialogBuilder.setView(dialogView);
        emailEdt_dialog = dialogView.findViewById(R.id.emailEdt_dialog);
        cancelBtn = dialogView.findViewById(R.id.cancelBtn);
        submitBtn = dialogView.findViewById(R.id.submitBtn);
        emailEdt_dialog.setTypeface(ResourcesCompat.getFont(context, R.font.oswald_extralight));
        alertDialog = dialogBuilder.create();
        /**
         * @param OnClickListner
         */
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(emailEdt_dialog.getText().toString())) {
                    emailEdt_dialog.setError(getString(R.string.err_please_enter_valid_email));
                    emailEdt_dialog.requestFocus();
                    emailEdt_dialog.setEnabled(true);
                } else {

                    //API to get forgot Password
                    forgotPassWord();
                }


            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }


    private void LoiginAPIPOST() {

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        //this is the url where you want to send the request
        //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
        String url = "http://exstreamapiqa.azurewebsites.net/token";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        // _response.setText(response);
                        Log.d("onResponse", "onResponse: " + response.toString());
                        // to hide the Progress Dialog
                        hideProgressDialog();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Object Username = jsonObject.get("userName");
                            Object AccountId = jsonObject.getString("AccountId");
                            Object accountName = jsonObject.getString("accountName");
                            Object useremail = jsonObject.getString("email");
                            Object token_type = jsonObject.getString("token_type");
                            Object access_token = jsonObject.getString("access_token");
                            // Object expires_in = jsonObject.getInt("expires_in");
                            Object tokenIssued = jsonObject.getString("tokenIssued");
                            Object tokenExpires = jsonObject.getString("tokenExpires");
                            Object user = jsonObject.getString("user");

                            String userNameStr = Username.toString();
                            String AccountIdStr = AccountId.toString();
                            Integer AccountIdInt = Integer.parseInt(AccountId.toString());
                            String accountNameStr = accountName.toString();
                            String useremailStr = useremail.toString();
                            String token_typeStr = token_type.toString();
                            String access_tokenStr = access_token.toString();
                            //int expires_inStr = ((Integer) expires_in).intValue();
                            String tokenIssuedStr = tokenIssued.toString();
                            String tokenExpiresStr = tokenExpires.toString();
                            String userStr = user.toString();

                            SharedPrefsData.putInt(context, Constants.ISLOGIN, 1, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.USERNAME, userNameStr, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.ACCOUNTID, AccountIdStr, Constants.PREF_NAME);
                            SharedPrefsData.putInt(context, Constants.ACCOUNTID, AccountIdInt, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.accountName, accountNameStr, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.useremail, useremailStr, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.token_type, token_typeStr, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.access_token, access_tokenStr, Constants.PREF_NAME);
                            // SharedPrefsData.putInt(context, Constants.expires_in, expires_in, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.tokenIssued, tokenIssuedStr, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.tokenExpires, tokenExpiresStr, Constants.PREF_NAME);
                            SharedPrefsData.putString(context, Constants.user, userStr, Constants.PREF_NAME);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            CommonUtil.customToast(" " +
                                    getString(R.string.login_success), LoginActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
                CommonUtil.customToast(" " +
                        getString(R.string.login_error_message), LoginActivity.this);

            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", emailEdt.getText().toString());
                params.put("password", passwordEdt.getText().toString());
                params.put("grant_type", "password");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    //API to get forgot Password
    private void forgotPassWord() {
        JsonObject object = forgotPasswordObject();
        ExStreamApiService service = ServiceFactory.createRetrofitService(getApplicationContext(),
                ExStreamApiService.class);
        mSubscription = service.putpassword(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
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
                    public void onNext(String mResponse) {
                        // to hide the Progress Dialog
                        hideProgressDialog();
                        CommonUtil.customToast(mResponse, LoginActivity.this);
                        if (mResponse.equalsIgnoreCase
                                ("Password reset link sent to your registered email")) {
                            emailEdt_dialog.setText("");
                            alertDialog.dismiss();
                        }

                    }

                });
    }

    /**
     * Json object of forgotPasswordObject()
     *
     * @return
     */
    private JsonObject forgotPasswordObject() {
        PasswordResetRequestModel mRequest = new PasswordResetRequestModel();
        mRequest.setEmail(emailEdt_dialog.getText().toString());
        return new Gson().toJsonTree(mRequest).getAsJsonObject();
    }


}
