package com.android.fastcampus.kwave.plot.Util;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by kwave on 2017-08-16.
 */

public class SearchRequest extends StringRequest {

    final static private String searchURL = "http://plot.ejjeong.com/api/post/post_search/?q=";
    private Map<String, String> parameters;

    /**
     * 검색을 위한 request 보내기
      */

    public SearchRequest(Response.Listener<String> listener) {
        super(Method.GET, searchURL, listener, null);
        parameters = new HashMap<>();
        parameters.get("무민");
    }

    public Map<String, String> getParams() {
        return parameters;
    }
}
