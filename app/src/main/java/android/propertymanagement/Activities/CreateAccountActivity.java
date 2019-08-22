package android.propertymanagement.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.propertymanagement.ModelClass.RequestModelClasses.CreateAccountAPIRequestModel;
import android.propertymanagement.ModelClass.RequestModelClasses.PasswordResetRequestModel;
import android.propertymanagement.R;
import android.propertymanagement.Services.ExStreamApiService;
import android.propertymanagement.Services.ServiceFactory;
import android.propertymanagement.Utils.CommonUtil;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    private EditText firstnameEdt, lastnameEdt, companyEdt, emailEdt, phonenoEdt, enterUrlEdt,
            passwordEdt, confirm_passEdt, emailEdt_dialog;
    private RadioGroup radioGroup;
    private RadioButton charactersRB, captial_lowerRB, alphanumericRB, agreeRB;
    private Button create_account_btn, cancelBtn, submitBtn;
    private TextView about_tv, terms_of_use_tv,
            privacy_policy_tv, existing_user_Textview, password_reset_TextView, use_exstream_TextView, contact_us_TextView;
    private AlertDialog alertDialog, alert;
    private Subscription mSubscription;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        context = this;

        /* intializing and assigning ID's */
        initViews();


        /* Navigation's and using the views */
        setViews();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    /* intializing and assigning ID's */
    private void initViews() {
        firstnameEdt = findViewById(R.id.firstnameEdt);
        lastnameEdt = findViewById(R.id.lastnameEdt);
        companyEdt = findViewById(R.id.companyEdt);
        emailEdt = findViewById(R.id.emailEdt);
        phonenoEdt = findViewById(R.id.phonenoEdt);
        enterUrlEdt = findViewById(R.id.enterUrlEdt);
        passwordEdt = findViewById(R.id.passwordEdt);
        confirm_passEdt = findViewById(R.id.confirm_passEdt);
        radioGroup = findViewById(R.id.radioGroup);
        charactersRB = findViewById(R.id.charactersRB);
        captial_lowerRB = findViewById(R.id.captial_lowerRB);
        alphanumericRB = findViewById(R.id.alphanumericRB);
        create_account_btn = findViewById(R.id.create_account_btn);
        agreeRB = findViewById(R.id.agreeRB);
        existing_user_Textview = findViewById(R.id.existing_user_Textview);
        password_reset_TextView = findViewById(R.id.password_reset_TextView);
        about_tv = findViewById(R.id.about_tv);
        terms_of_use_tv = findViewById(R.id.terms_of_use_tv);
        privacy_policy_tv = findViewById(R.id.privacy_policy_tv);
        contact_us_TextView = findViewById(R.id.contact_us_TextView);
    }

    /* Navigation's and using the views */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setViews() {
        /**
         * @param OnClickListner
         */
        about_tv.setOnClickListener(this);
        terms_of_use_tv.setOnClickListener(this);
        privacy_policy_tv.setOnClickListener(this);

        SpannableString ss = new SpannableString(getResources().getString(R.string.use_exstream));
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan1, 96, 118, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan((new ForegroundColorSpan(getResources().getColor(R.color.login_button))), 96, 118, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        use_exstream_TextView = findViewById(R.id.use_exstream_TextView);
        use_exstream_TextView.setText(ss);
        use_exstream_TextView.setMovementMethod(LinkMovementMethod.getInstance());
        use_exstream_TextView.setHighlightColor(context.getResources().getColor(R.color.login_button));

        SpannableString spannableString = new SpannableString(getResources().getString(R.string.not_sure));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                // startActivity(new Intent(MyActivity.this, NextActivity.class));
                // Toast.makeText(CreateAccountActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };


        spannableString.setSpan(clickableSpan, 67, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan((new ForegroundColorSpan(getResources().getColor(R.color.login_button))), 67, 78, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        contact_us_TextView = findViewById(R.id.contact_us_TextView);
        contact_us_TextView.setText(spannableString);
        contact_us_TextView.setMovementMethod(LinkMovementMethod.getInstance());
        contact_us_TextView.setHighlightColor(context.getResources().getColor(R.color.login_button));

        SpannableString ssRB = new SpannableString(getResources().getString(R.string.read_agree));
        ClickableSpan clickableSpanRB = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ssRB.setSpan(clickableSpanRB, 34, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssRB.setSpan((new ForegroundColorSpan(getResources().getColor(R.color.login_button))), 34, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        agreeRB.setText(ssRB);
        agreeRB.setMovementMethod(LinkMovementMethod.getInstance());
        agreeRB.setHighlightColor(context.getResources().getColor(R.color.login_button));

        existing_user_Textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        password_reset_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * custom forgotPasswordDialog for forgot password
                 */
                forgotPasswordDialog();

            }
        });
        passwordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirm_passEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        create_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * Validations for user name and password
                 *
                 * @return
                 */
                if (createaccountvalidations()) {
                    //API to get CreateAccount
                    getCreateAccount();
                }
            }
        });
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
    private boolean createaccountvalidations() {
        if (TextUtils.isEmpty(firstnameEdt.getText().toString())) {
            firstnameEdt.setError(getString(R.string.err_please_enter_first_name));
            firstnameEdt.setEnabled(true);
            firstnameEdt.requestFocus();

            return false;
        } else if (TextUtils.isEmpty(lastnameEdt.getText().toString())) {
            lastnameEdt.setError(getString(R.string.err_please_enter_last_name));
            lastnameEdt.requestFocus();
            lastnameEdt.setEnabled(true);
            return false;
        } else if ((TextUtils.isEmpty(companyEdt.getText().toString()))) {
            companyEdt.setError(getString(R.string.err_please_enter_company_name));
            companyEdt.requestFocus();
            companyEdt.setEnabled(true);
            return false;
        } else if (TextUtils.isEmpty(emailEdt.getText().toString())) {
            emailEdt.setError(getString(R.string.err_please_enter_email));
            emailEdt.requestFocus();
            emailEdt.setEnabled(true);
            return false;
        } else if (!isValidEmail(emailEdt.getText().toString())) {
            emailEdt.setError(getString(R.string.err_please_enter_valid_email));
            emailEdt.requestFocus();
            emailEdt.setEnabled(true);
            return false;
        } else if ((TextUtils.isEmpty(phonenoEdt.getText().toString()))) {
            phonenoEdt.setError(getString(R.string.err_please_enter_phone_number));
            phonenoEdt.setEnabled(true);
            phonenoEdt.requestFocus();
            return false;
        } else if ((TextUtils.isEmpty(enterUrlEdt.getText().toString()))) {
            enterUrlEdt.setError(getString(R.string.err_please_enter_customer_url));
            enterUrlEdt.setEnabled(true);
            enterUrlEdt.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(passwordEdt.getText().toString().trim())) {
            passwordEdt.setError(getString(R.string.err_please_enter_password));
            passwordEdt.setEnabled(true);
            passwordEdt.requestFocusFromTouch();
            passwordEdt.requestFocus();
            return false;
        } else if (!isValidPassword(passwordEdt.getText().toString()) || passwordEdt.getText().toString().length() < 8) {
            passwordEdt.setError(getString(R.string.password_must_contain_onecharacter));
            passwordEdt.setEnabled(true);
            passwordEdt.requestFocusFromTouch();
            passwordEdt.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(confirm_passEdt.getText().toString())) {
            confirm_passEdt.setError(getString(R.string.err_please_enter_confirm_pass));
            confirm_passEdt.setEnabled(true);
            confirm_passEdt.requestFocus();
            return false;
        } else if (!passwordEdt.getText().toString().equals(confirm_passEdt.getText().toString())) {
            confirm_passEdt.setError(getString(R.string.err_password_do_not_match));
            confirm_passEdt.requestFocus();
            confirm_passEdt.setEnabled(true);
            return false;
        }
        return true;
    }

    /**
     * is valid password or not
     *
     * @param password
     * @return
     */
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

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
                        CommonUtil.customToast(mResponse, CreateAccountActivity.this);
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

    //API to get CreateAccount
    private void getCreateAccount() {

        JsonObject object = createAccountObject();
        ExStreamApiService service = ServiceFactory.createRetrofitService(getApplicationContext(),
                ExStreamApiService.class);
        mSubscription = service.postRegisterAccount(object)
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
                            CommonUtil.customToast(e.getMessage(), CreateAccountActivity.this);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(String mResponse) {
                        // to hide the Progress Dialog
                        hideProgressDialog();
                        CommonUtil.customToast(mResponse, CreateAccountActivity.this);
                        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                });
    }

    /**
     * Json object of createAccountObject()
     *
     * @return
     */
    private JsonObject createAccountObject() {
        CreateAccountAPIRequestModel mRequest = new CreateAccountAPIRequestModel();
        mRequest.setFirstName(firstnameEdt.getText().toString());
        mRequest.setLastName(lastnameEdt.getText().toString());
        mRequest.setPhoneNumber(phonenoEdt.getText().toString());
        mRequest.setPassword(passwordEdt.getText().toString());
        mRequest.setAccountName(companyEdt.getText().toString());
        mRequest.setWebSiteURL(enterUrlEdt.getText().toString());
        mRequest.setEmail(emailEdt.getText().toString());
        return new Gson().toJsonTree(mRequest).getAsJsonObject();
    }

}
