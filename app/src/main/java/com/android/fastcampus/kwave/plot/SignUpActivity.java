package com.android.fastcampus.kwave.plot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.fastcampus.kwave.plot.Util.SignUpRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.android.fastcampus.kwave.plot.R.id.signUpCheckPassword;
import static com.android.fastcampus.kwave.plot.R.id.signUpEmail;
import static com.android.fastcampus.kwave.plot.R.id.signUpNickname;
import static com.android.fastcampus.kwave.plot.R.id.signUpPassword;
import static com.android.fastcampus.kwave.plot.R.id.signUpUsername;

public class SignUpActivity extends AppCompatActivity{


    private Button signUpSaveButton;
    private Button signUpCancelButton;
    String email, pw, pwCheck, nickname, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
    }

    private void initView() {
        final EditText emailText = (EditText) findViewById(signUpEmail);
        final EditText nickNameText  = (EditText) findViewById(signUpNickname);
        final EditText userNameText = (EditText) findViewById(signUpUsername);
        final EditText passwordText = (EditText) findViewById(signUpPassword);
        final EditText passwordCheckText = (EditText) findViewById(signUpCheckPassword);
        signUpSaveButton = (Button) findViewById(R.id.signUpSaveButton);
        signUpCancelButton = (Button) findViewById(R.id.signUpCancelButton);

        signUpSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = emailText.getText().toString();
                String userNickName = nickNameText.getText().toString();
                String userName = userNameText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String passwordCheck = passwordCheckText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    /**
                     * 회원가입 성공시 서버에 있는 것 찾기.
                     * 회원가입 성공시 받아올 수 있는 값은 email, nickName, userName, img_profile
                     */
                    public void onResponse(String response) {
                        Intent intent;
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("email");
                            if(success != null){
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage("회원 등록에 성공했습니다")
                                        .setPositiveButton("확인",null)
                                        .create()
                                        .show();
                                intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage("회원 등록에 실패했습니다")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                                Toast.makeText(SignUpActivity.this, "회원 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SignUpRequest signUpRequest = new SignUpRequest(userEmail, userNickName, userName, userPassword, passwordCheck, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                queue.add(signUpRequest);
            }
        });
        signUpCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
//
//    @Override
//    public void onClick(View v) {
//        Intent intent;
//        switch (v.getId()){
//            case R.id.signUpSaveButton :
//                save(v);
//                break;
//            case R.id.signUpCancelButton :
//                intent = new Intent(SignUpActivity.this, LoginActivity.class);
//                startActivity(intent);
//                break;
//
//        }
//    }
//
//    /* onClick에서 정의한 이름과 똑같은 이름으로 생성 */
//    public void save(View view)
//    {
//    /* 버튼을 눌렀을 때 동작하는 소스 */
//        email = signUpEmail.getText().toString();
//        nickname = signUpNickname.getText().toString();
//        name = signUpUsername.getText().toString();
//        pw = signUpPassword.getText().toString();
//        pwCheck = signUpCheckPassword.getText().toString();
//
//        if(pw.equals(pwCheck))
//        {
//        /* 패스워드 확인이 정상적으로 됨 */
//            Toast.makeText(this, "패스워드가 맞습니다.", Toast.LENGTH_SHORT).show();
//            RegistSignUp registSignUp = new RegistSignUp();
//            registSignUp.execute();
//        }
//        else
//        {
//        /* 패스워드 확인이 불일치 함 */
//            Toast.makeText(this, "패스워드가 안 맞습니다.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public class RegistSignUp extends AsyncTask<Void, Integer, Void> {
//
//        @Override
//        protected Void doInBackground(Void... unused) {
//
///* 인풋 파라메터값 생성 */
//            String param = "u_id=" + email + "&u_pw=" + pw + "";
//            try {
///* 서버연결 */
//                URL url = new URL("plot.ejjeong.com/api/member/signup/");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestMethod("POST");
//                conn.setDoInput(true);
//                conn.connect();
//
///* 안드로이드 -> 서버 파라메터값 전달 */
//                OutputStream outs = conn.getOutputStream();
//                outs.write(param.getBytes("UTF-8"));
//                outs.flush();
//                outs.close();
//
///* 서버 -> 안드로이드 파라메터값 전달 */
//                InputStream is = null;
//                BufferedReader br = null;
//                String data = "";
//
//                is = conn.getInputStream();
//                br = new BufferedReader(new InputStreamReader(is), 8 * 1024);
//                String line = null;
//                StringBuffer buff = new StringBuffer();
//                while ( ( line = br.readLine() ) != null )
//                {
//                    buff.append(line + "\n");
//                }
//                data = buff.toString().trim();
//                Log.e("RECV DATA",data);
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//    }
}
