package com.android.fastcampus.kwave.plot;

import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.adapter.FindRecyclerAdapterSearch;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity  {
    private FindRecyclerAdapterSearch findRecyclerAdapterSearch;
    SearchView searchView;
    private Toolbar noticeSearchToolbar;
    private SearchView noticeSearchView;
    private RecyclerView noticeSearchRecycler;

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
        findRecyclerAdapterSearch = new FindRecyclerAdapterSearch(datas);
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
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
