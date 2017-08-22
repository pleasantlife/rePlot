package com.android.fastcampus.kwave.plot;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fastcampus.kwave.plot.DataSource.ExpandableListDataSource;
import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.adapter.CustomExpandableListAdapter;
import com.android.fastcampus.kwave.plot.adapter.RankRecyclerAdapterMain;
import com.android.fastcampus.kwave.plot.adapter.RankViewPagerAdapterMain;
import com.android.fastcampus.kwave.plot.navigation.FragmentNavigationManager;
import com.android.fastcampus.kwave.plot.navigation.NaviDrawerSetting;
import com.android.fastcampus.kwave.plot.navigation.NavigationManager;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.android.fastcampus.kwave.plot.ThrowData2Activity.task;
import static com.android.fastcampus.kwave.plot.Util.LoginCode.LOGIN_OK;
import static com.android.fastcampus.kwave.plot.Util.LoginCode.REQUEST_CODE;

public class MainActivity extends AppCompatActivity implements NaviDrawerSetting, View.OnClickListener, ILoadData {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    private String[] items;
    private ExpandableListView mExpandableListView, navList;
    private ExpandableListAdapter mExpandableListAdapter;
    private List<String> mExpandableListTitle;

    private NavigationManager mNavigationManager;
    private Map<String, List<String>> mExpandableListData;
    private RecyclerView rankRecycler_main;
    private RankRecyclerAdapterMain rankRecyclerAdapterMain;
    private LinearLayout rank, categoryLinear, naviUser, cotentScroll;
    private Spinner categoryGenre, categoryLocation;
    private TextView categoryDate, textView, textHome, textMypage, textUserNickName;
    private ViewPager rankViewPagerMain;
    private RankViewPagerAdapterMain viewPagerAdapterMain;
    private Button btnNaviUserLogin;
    ArrayAdapter<CharSequence> genre, location;
    int year;
    int month;
    int day;
    private static final int DATE_DIALOG_ID = 1;

    public static String url = "http://plot.ejjeong.com/api/post/postlist/";
    public static List<ServerData> datas = new ArrayList<>();

    String userEmail, userPassword, message, nickName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initItems();
        if (savedInstanceState == null) {
            selectFirstItemAsDefault();
        }

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("pk");
        userPassword = intent.getStringExtra("token");

        nickName = intent.getStringExtra("nick_name");

        message = nickName + "님";

        task(this);
        initNaviDrawer();
        initToolbar();

        initView();
        setViewPager();
        setRecyclerView();
        addDrawerItems();
        setupDrawer();
        initSpinner();
        initCalrendarDialog();


    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); // 전체 레이아웃
        cotentScroll = (LinearLayout) findViewById(R.id.cotent_scroll); // 메인페이지 content 전체 scroll
        rank = (LinearLayout) findViewById(R.id.rank);                  // 랭크 1~5위까지 보이기 linear
        rankRecycler_main = (RecyclerView) findViewById(R.id.recycler_main); // 랭크 6~10위까지 보이기 list
        rankViewPagerMain = (ViewPager) findViewById(R.id.rankViewPager_main);
        navList = (ExpandableListView) findViewById(R.id.navList);
        textView = (TextView) findViewById(R.id.textView);
        categoryLinear = (LinearLayout) findViewById(R.id.category_linear);     // 상단 카테고리 레이어
        categoryLocation = (Spinner) findViewById(R.id.category_location);     // 상단 카테고리 지역
        categoryGenre = (Spinner) findViewById(R.id.category_genre);           // 상단 카테고리 장르
        categoryDate = (TextView) findViewById(R.id.category_date);             // 상단 카테고리 날짜
        textHome = (TextView) findViewById(R.id.textHome);
        textMypage = (TextView) findViewById(R.id.textMypage);
        categoryDate.setText(items[2]);

        textHome.setOnClickListener(this);
        textMypage.setOnClickListener(this);
    }


    /**
     * 랭크 1~5위까지 보이기 Horizontal ScrollView
     */
    private void setViewPager() {
        rankViewPagerMain = (ViewPager) findViewById(R.id.rankViewPager_main);
        viewPagerAdapterMain = new RankViewPagerAdapterMain(getSupportFragmentManager());
        rankViewPagerMain.setAdapter(viewPagerAdapterMain);
        /**
         * 뷰페이저 인디케이터 구현하는 부분
         */
        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(rankViewPagerMain);
    }


    /**
     * 랭크 6~10위까지 보이기 RecyclerView Setting
     */
    private void setRecyclerView() {

        // RecyclerView Setting
        rankRecyclerAdapterMain = new RankRecyclerAdapterMain(datas, this);
        rankRecycler_main.setAdapter(rankRecyclerAdapterMain);
        rankRecycler_main.setLayoutManager(new LinearLayoutManager(this));
//        rankRecyclerAdapterMain.setData(datas);
    }


    /**
     * 여기서부터 navi Drawer custom
     */
    private void initNaviDrawer() {
        mActivityTitle = getTitle().toString();

        mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
        mNavigationManager = FragmentNavigationManager.obtain(this);

        LayoutInflater inflater = getLayoutInflater();
        View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        mExpandableListView.addHeaderView(listHeaderView);  // Navi Drawer에서 User 정보를 나타냄
        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());

        btnNaviUserLogin = (Button) listHeaderView.findViewById(R.id.btnNaviUserLogin);
        btnNaviUserLogin.setOnClickListener(this);
        naviUser = (LinearLayout) listHeaderView.findViewById(R.id.naviUser);
        naviUser.setOnClickListener(this);
        naviUser.setVisibility(View.VISIBLE);
        btnNaviUserLogin.setVisibility(View.GONE);
        textUserNickName = (TextView) findViewById(R.id.textUserNickName);
        textUserNickName.setText(message);
    }

    @Override
    // 상단에 내가 선택한 항목을 띄워주기
    public void selectFirstItemAsDefault() {
        if (mNavigationManager != null) {
            String firstActionMovie = getResources().getStringArray(R.array.genre)[0];
            getSupportActionBar().setTitle(firstActionMovie);
        }
    }

    @Override
    public void initItems() {
        items = getResources().getStringArray(R.array.plotCategory);
    }

    @Override
    public void addDrawerItems() {
        mExpandableListAdapter = new CustomExpandableListAdapter(this, mExpandableListTitle, mExpandableListData);
        mExpandableListView.setAdapter(mExpandableListAdapter);
        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle(R.string.goNavi);            // navi drawer가 열리면 액션바에 글자가 바뀜
            }
        });
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition))))
                        .get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if (items[0].equals(mExpandableListTitle.get(groupPosition))) {
//                    mNavigationManager.showFragmentAction(selectedItem);                     // TODO 해당 액티비티로 보내는 로직 만들기
                } else if (items[1].equals(mExpandableListTitle.get(groupPosition))) {
//                    mNavigationManager.showFragmentComedy(selectedItem);                    // TODO 해당 액티비티로 보내는 로직 만들기
                } else if (items[2].equals(mExpandableListTitle.get(groupPosition))) {
//                    mNavigationManager.showFragmentDrama(selectedItem);                    // TODO 해당 액티비티로 보내는 로직 만들기
                } else {
                    throw new IllegalArgumentException("Not supported fragment type");
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    // Navi Drawer 열렸을 때와 닫았을 때 어떻게 할지 정하기
    public void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.goNavi);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /**
     * @param menu 메뉴 항목
     * @return toolbar에 menu 항목이 항상 있으므로 true를 리턴
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.magnifying_glass:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            // naviDrawer에서 login 페이지로 넘어가기
            case R.id.btnNaviUserLogin:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.naviUser:
//                naviUser.setVisibility(View.GONE);
//                btnNaviUserLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.textHome:
                intent = new Intent(MainActivity.this, com.android.fastcampus.kwave.plot.ListActivity.class);
                startActivity(intent);
                break;
            case R.id.textMypage:
                if (REQUEST_CODE == LOGIN_OK) {
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "로그인 해주세요.", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 상단 카테고리 설정
     */
    private void initSpinner() {
// Create an ArrayAdapter using the string array and a default spinner layout
        genre = ArrayAdapter.createFromResource(this,
                R.array.genre, android.R.layout.simple_spinner_item);
        location = ArrayAdapter.createFromResource(this,
                R.array.location, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        genre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        categoryLocation.setAdapter(location);
        categoryGenre.setAdapter(genre);
        initSpinnerGenre();
        initSpinnerLocation();

    }

    private void initSpinnerGenre() {
        //스피너 이벤트 발생
        categoryGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(getApplicationContext(), genre.getItem(position) + Integer.toString(position), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), genre.getItem(position) + Integer.toString(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, com.android.fastcampus.kwave.plot.ListActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerLocation() {
        //스피너 이벤트 발생
        categoryLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(getApplicationContext(), location.getItem(position) + Integer.toString(position), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), location.getItem(position) + Integer.toString(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, com.android.fastcampus.kwave.plot.ListActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initCalrendarDialog() {
        initCalendar();
        categoryDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(categoryDate.getWindowToken(), 0);
                showDialog(DATE_DIALOG_ID);
                return true;
            }
        });
    }

    private void initCalendar() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        Log.i("year", "---------------------------year" + year);
        month = calendar.get(Calendar.MONTH);
        Log.i("month", "---------------------------month" + month);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.i("day", "---------------------------day" + day);
    }

    @Override
    // 다이얼로그가 실행이 되면서 반영됨
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, year, month, day);
        }
        return null;
    }

    // updates the date we display in the TextView
    private void updateDisplay() {
        /*
         * Hide virtual keyboard
         */
        categoryDate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(year).append("-")
                .append(month + 1).append("-")
                .append(day).append(""));
    }

    /**
     * // 다이얼로그에서 날짜를 지정하고 확인버튼을 누르면 아래가 실행됨
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int myear, int monthOfYear, int dayOfMonth) {
            year = myear;
            Log.i("myear", "---------------------------myear" + myear);
            month = monthOfYear;
            Log.i("monthOfYear", "---------------------------monthOfYear" + monthOfYear);
            day = dayOfMonth;
            Log.i("dayOfMonth", "---------------------------dayOfMonth" + dayOfMonth);
            updateDisplay();
            //Todo 여기에 쿼리를 보내는 함수를 넣어서 놓기
        }
    };


    @Override
    public void setServerData(List<ServerData> data) {
        datas = data;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
