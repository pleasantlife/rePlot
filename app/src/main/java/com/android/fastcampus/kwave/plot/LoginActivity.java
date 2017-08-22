package com.android.fastcampus.kwave.plot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fastcampus.kwave.plot.Util.LoginRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static com.android.fastcampus.kwave.plot.Util.LoginCode.LOGIN_OK;
import static com.android.fastcampus.kwave.plot.Util.LoginCode.REQUEST_CODE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    LoginButton fbLogin;
    //AccessTokenTracker accessTokenTracker;
    //String userToken;
    public String fbuserid = "";
    public String fbuserName;
    ProfileTracker profileTracker;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

//    // UI references.
//    private AutoCompleteTextView textEmail;
//    private EditText editPassword;
//    private View mProgressView;
//    private View mLoginFormView;
//    private ProgressBar loginProgress;
//    private ScrollView loginForm;
//    private LinearLayout emailLoginForm;
//    private Button emailSignInButton;
    private Button signUpButton;
    String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }


    private void initView() {
//        loginProgress = (ProgressBar) findViewById(R.id.login_progress);
//        loginForm = (ScrollView) findViewById(R.id.login_form);
//        emailLoginForm = (LinearLayout) findViewById(R.id.email_login_form);
        final EditText emailText = (AutoCompleteTextView) findViewById(R.id.email);
        final EditText passwordText = (EditText) findViewById(R.id.password);
        final Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        signUpButton = (Button) findViewById(R.id.email_sign_up_button);
//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
        loginButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final String userEmail = emailText.getText().toString();
                   final String userPassword = passwordText.getText().toString();

                   Response.Listener<String> resStringListener = new Response.Listener<String>() {
                       @Override
                       /**
                        * 로그인 성공시 서버에 있는 것을 받아오기.
                        * 우리 서버에서는 pk 값과 token 값만 넘겨주므로 일단 두가지만 했음
                        */
                       public void onResponse(String response) {
                           try {
                               JSONObject jsonResponse = new JSONObject(response);
                               int success1 = jsonResponse.getInt("pk");
                               success = Integer.toString(success1);
                               if(success != null){
                                   String email = jsonResponse.getString("pk");
                                   String password = jsonResponse.getString("token");
                                   String nickName = jsonResponse.getString("nick_name");
                                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                   intent.putExtra("pk",email);
                                   intent.putExtra("token",password);
                                   intent.putExtra("nick_name",nickName);
                                   LoginActivity.this.startActivity(intent);
                               }else{
                                   AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                   builder.setMessage("로그인에 실패했습니다")
                                           .setNegativeButton("다시 시도", null)
                                           .create()
                                           .show();
                                   Toast.makeText(LoginActivity.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                               }
                           }catch (Exception e){
                               e.printStackTrace();
                           }
                       }
                   };

                   LoginRequest loginRequest = new LoginRequest(userEmail, userPassword, resStringListener);
                   RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                   queue.add(loginRequest);
               }
           });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });


        // Set up the login form.
        /* 페이스북 로그인을 위한 버튼 및 함수 설정 시작 */
        fbLogin = (LoginButton) findViewById(R.id.fbLogin);
        fbLoginProcess();
        /* 페이스북 로그인을 위한 버튼 및 함수 설정 끝 */

    }


//        populateAutoComplete();
//
//        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == R.id.login || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//    }
//
//
//
//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }
//
//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(textEmail, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }

//    /**
//     * Callback received when a permissions request has been completed.
//     * 퍼미션 요청이 끝나면 콜백이 받아집니다.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }

//
//    /**
//     * Attempts to sign in or register the account specified by the login form.
//     * If there are form errors (invalid email, missing fields, etc.), the
//     * errors are presented and no actual login attempt is made.
//     * 로그인 양식에 지정된 계정에 로그인 또는 등록을 시도합니다.
//     * 양식 오류 (잘못된 이메일, 누락 된 필드 등)가 있으면 오류가 표시되고 실제 로그인 시도가 이루어지지 않습니다.
//     */
//    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }
//
//        // Reset errors.
//        textEmail.setError(null);
//        editPassword.setError(null);
//
//        // Store values at the time of the login attempt.
//        String email = textEmail.getText().toString();
//        String password = editPassword.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            editPassword.setError(getString(R.string.error_invalid_password));
//            focusView = editPassword;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            textEmail.setError(getString(R.string.error_field_required));
//            focusView = textEmail;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            textEmail.setError(getString(R.string.error_invalid_email));
//            focusView = textEmail;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first form field with an error.
//            // 오류가 있었습니다. 로그인을 시도하지 마십시요 그리고 에러가 있는 필드에 첫번째 폼에 촛점을 맞추세요.
//            focusView.requestFocus();
//        } else {
//            // Show a progress spinner, and kick off a background task to perform the user login attempt.
//            // 프로그레스 스피너를 표시하면, 사용자 로그인 시도를 수행하기위한 백그라운드 작업을 시작할 수 있습니다.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
//        }
//    }
//
//    private boolean isEmailValid(String email) {
//        //TODO: Replace this with your own logic
//        if (email == null) {
//            return false;
//        } else {
//            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
//        }
//    }
//
//    private boolean isPasswordValid(String password) {
//        //TODO: Replace this with your own logic
//        return password.length() > 4;
//    }

//    /**
//     * Shows the progress UI and hides the login form.
//     * 프로그레스 UI를 보여주며 로그인 폼을 숨겨줍니다.
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show and hide the relevant UI components.
//            // ViewPropertyAnimator API를 사용할 수 없으므로 관련 UI 구성 요소를 표시하거나 숨깁니다.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(this,
//                // Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE + " = ?",
//                new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
//        addEmailsToAutoComplete(emails);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }

//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        textEmail.setAdapter(adapter);
//    }
//
//    private interface ProfileQuery {
//        String[] PROJECTION = {
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
//        };
//
//        int ADDRESS = 0;
//        int IS_PRIMARY = 1;
//    }



    /* 페이스북 로그인 프로세스 (별도의 함수로 분리하였습니다.)
     */
    private void fbLoginProcess() {

        /*
            1) CallbackManager 객체를 선언해서 Factory.create() 함수를 호출하여 생성합니다.
            (자세히는 알 수 없지만, facebook 서버와의 연동을 대신 맡아서 해주는 듯)
        */
        callbackManager = CallbackManager.Factory.create();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                if(oldProfile == null) {
                    fbuserName = currentProfile.getName();
                } else {
                    fbuserName = oldProfile.getName();
                }
                Log.e("fbuserName", fbuserName);
            }
        };


        /*
            2) Login 부분을 담당하는 LoginManager 호출. LoginManager.getInstance()로 현재 내용을 불러온 뒤
            콜백매니저에 LoginManager를 등록합니다.
            이러면, 로그인이 완료되었을 때 CallbackManager에 상황별로 해야할 일을 부여할 수 있습니다.
         */

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                String username = loginResult.getAccessToken().getUserId();
                fbuserid = loginResult.getAccessToken().getUserId();
                REQUEST_CODE = LOGIN_OK;
                saveFBID(fbuserid);
                Log.e("userid", fbuserid);
                Toast.makeText(getBaseContext(), "로그인 성공 : " + username, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("nick_name", fbuserName);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                // TODO 로그인 실패시
            }

            @Override
            public void onError(FacebookException error) {
                // TODO 로그인 에러시
            }
        });


    }

    public void saveFBID(String fbuserid){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FBUSERID", fbuserid);
        editor.commit();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        /*
            onActivityResult에 반드시 callbackManager의 결과값을 등록해야 합니다.
            등록하지 않으면 callbackmanager가 실행되지 않아 아무런 결과도 얻어올 수 없습니다.
         */
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


//
//    /**
//     * Represents an asynchronous login/registration task used to authenticate the user.
//     * 사용자를 인증하는 데 사용되는 비동기 로그인 / 등록 작업을 나타냅니다.
//     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                REQUEST_CODE = 999;
//                finish();
//            } else {
//                editPassword.setError(getString(R.string.error_incorrect_password));
//                editPassword.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
//



     /*

           // 페이스북 로그인 시 토큰 받아오기

           // 1) accessTokenTracker로 현재 토큰이 있는지, 없으면 새로 만든 토큰은 무엇인지 추적합니다.
            로그인 기능과 연동되므로 'AccessTokenTracker'만 생성해주면 됩니다.


        accessTokenTracker = new AccessTokenTracker() {


               // 토큰 감지
               // 토큰은 그냥 string 값으로 받아옵니다.

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null) {
                    userToken = currentAccessToken.getToken();
                } else {
                    userToken = oldAccessToken.getToken();
                }
            }
        };
        */
}

