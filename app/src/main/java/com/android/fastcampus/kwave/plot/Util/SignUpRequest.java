package com.android.fastcampus.kwave.plot.Util;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kwave on 2017-08-16.
 */

public class SignUpRequest extends StringRequest {

    final static private String URL = "http://plot.ejjeong.com/api/member/signup/";
    private Map<String, String> parameters;

    public SignUpRequest(String userEmail, String userNickName, String userName, String userPassword, String passwordCheck, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", userEmail);
        parameters.put("nickname", userNickName);
//        parameters.put("img_profile", userImage);
        parameters.put("username", userName);
        parameters.put("password", userPassword);
        parameters.put("password2", passwordCheck);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
