package com.android.fastcampus.kwave.plot.Util;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kwave on 2017-08-16.
 */

public class LoginRequest extends StringRequest {

    final static private String URL = "http://plot.ejjeong.com/api/member/login/";
    private Map<String, String> parameters;

    /**
     * 로그인을 위한 request 보내기
     * 로그인이기 때문에 id와 pw만 보낸다.
      */

    public LoginRequest(String userEmail, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("email", userEmail);
        parameters.put("password", userPassword);
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
