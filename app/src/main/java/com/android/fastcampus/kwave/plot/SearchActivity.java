package com.android.fastcampus.kwave.plot;

import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.Util.SearchRequest;
import com.android.fastcampus.kwave.plot.adapter.FindRecyclerAdapterSearch;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity  {
    private FindRecyclerAdapterSearch findRecyclerAdapterSearch;
    SearchView searchView;
    private Toolbar noticeSearchToolbar;
    private SearchView noticeSearchView;
    private RecyclerView noticeSearchRecycler;
    String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initRecycler();
        noticeSearchRecycler.setVisibility(View.GONE);
    }

    private void initRecycler() {
        List<ServerData> datas = new ArrayList<>();
        findRecyclerAdapterSearch = new FindRecyclerAdapterSearch();
        findRecyclerAdapterSearch.setData(datas);
        noticeSearchRecycler.setAdapter(findRecyclerAdapterSearch);
        noticeSearchRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

//    @Override
//    public void onClick(final View v) {
//        switch (v.getId()) {
//            case R.id.btnSearch:
//                recyclerSearch.setVisibility(View.VISIBLE);
//                break;
//        }
//    }

    private void initView() {
//        btnSearch.setOnClickListener(this);
//        editSearch.getText().toString();
//        noticeSearchToolbar = (Toolbar) findViewById(R.id.notice_search_toolbar);
//        noticeSearchView = (SearchView) findViewById(R.id.notice_search_view);
        noticeSearchRecycler = (RecyclerView) findViewById(R.id.notice_search_recycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // SearchView Hint 변경하기
        MenuItem searchItem = menu.findItem(R.id.menu_search);
//        searchView.onActionViewExpanded(); //바로 검색 할 수 있도록
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("검색어를 입력하세요.");

        // SearchView 입력 글자색과 힌트 색상 변경하기
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.WHITE);

        // SearchView 확장/축소 이벤트 처리
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {  // 툴바에서 searchView가 확장됐을때
//                Toast.makeText(SearchActivity.this, "SearchView 확장됐다!!", Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {    // 툴바에서 searchView가 축소됐을때
//                Toast.makeText(SearchActivity.this, "SearchView 축소됐다!!", Toast.LENGTH_LONG).show();
                return true;
            }
        };

        MenuItemCompat.setOnActionExpandListener(searchItem, expandListener);

        // SearchView 검색어 입력/검색 이벤트 처리
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {    // 문자열 입력을 완료했을 때 문자열 반환
//                Toast.makeText(SearchActivity.this, "[검색버튼클릭] 검색어 = " + query, Toast.LENGTH_LONG).show();
                noticeSearchRecycler.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {  // 문자열이 변할 때마다 바로바로 문자열 반환
//                Toast.makeText(SearchActivity.this, "입력하고있는 단어 = " + newText, Toast.LENGTH_LONG).show();
                System.out.println(newText);
                        Response.Listener<String> resStringListener = new Response.Listener<String>() {
                            @Override
                            /**
                             * 로그인 성공시 서버에 있는 것을 받아오기.
                             * 우리 서버에서는 pk 값과 token 값만 넘겨주므로 일단 두가지만 했음
                             */
                            public void onResponse(String newText) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(newText);
                                    int success1 = jsonResponse.getInt("pk");
                                    success = Integer.toString(success1);
                                    if(success != null){
                                        initRecycler();
                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                                        builder.setMessage("데이터를 가져오지 못 했습니다.")
                                                .setNegativeButton("다시 시도", null)
                                                .create()
                                                .show();
                                        Toast.makeText(SearchActivity.this, "데이터를 가져오지 못 했습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };

                        SearchRequest searchRequest = new SearchRequest(resStringListener);
                        RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);
                        queue.add(searchRequest);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
