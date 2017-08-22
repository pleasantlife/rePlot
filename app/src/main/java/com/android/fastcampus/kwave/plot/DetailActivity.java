package com.android.fastcampus.kwave.plot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.fastcampus.kwave.plot.DataSource.ServerData;
import com.android.fastcampus.kwave.plot.Fragments.ExDetailFragment;
import com.android.fastcampus.kwave.plot.Fragments.ExInfoFragment;
import com.android.fastcampus.kwave.plot.Fragments.ExReviewFragment;
import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView postImg;
    TextView textTitle, textStartDate, textEndDate, textAddr;
    Button btnBooking, btnLocation, btnReview, btnWant;
    ExDetailFragment ExDetail;
    ExInfoFragment ExInfo;
    ExReviewFragment ExReview;
    List<Fragment> pages;
    PagerAdapter adapter;
    TabLayout tab;
    ViewPager pager;
    int position;
    Bundle bundle;
    ServerData serverData;
    String listKey = "";
    private CallbackManager callbackManager;
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setWidget();
        setTabLayout();
        setViewPager();
        setFragments();
        setTab2Pager();
        setPageAdapter();
        setData();
    }
    /*
    위젯 세팅
     */
    private void setWidget(){

        textTitle = (TextView) findViewById(R.id.textTitle);
        textStartDate = (TextView) findViewById(R.id.textStartDate);
        textEndDate = (TextView) findViewById(R.id.textEndDate);
        textAddr = (TextView) findViewById(R.id.textAddr);

        postImg = (ImageView) findViewById(R.id.postImg);

        btnBooking = (Button) findViewById(R.id.btnBooking);
        btnBooking.setOnClickListener(this);

        btnLocation = (Button) findViewById(R.id.btnLocation);
        btnLocation.setOnClickListener(this);
        btnReview = (Button) findViewById(R.id.btnReview);
        btnReview.setOnClickListener(this);
        btnWant = (Button) findViewById(R.id.btnWant);

    }


    /*
   뷰페이저 생성
    */
    private void setViewPager(){
        pager = (ViewPager) findViewById(R.id.pager);
    }

    /*
    뷰페이저와 탭레이아웃 연결
    */
    private void setTab2Pager(){
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

    }

    /*
     뷰페이져 아답터 연결
     */
    private void setPageAdapter(){
        adapter = new PagerAdapter(getSupportFragmentManager(),pages);
        pager.setAdapter(adapter);

    }

    /*
    포스터 아래 만들어놓은 버튼별 기능 구현(현재 예약하기, 댓글쓰기만 구현함)
    */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnBooking:
                goReserve();
                break;
            case R.id.btnLocation:
                goMap();
                break;
            case R.id.btnReview:
                goComment();
                break;
        }
    }

    /*
    뷰페이저 아답터
     */
    class PagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> pagelist;
        public PagerAdapter(FragmentManager fm, List<Fragment> pager) {
            super(fm);
            this.pagelist = pager;
        }
        @Override
        public Fragment getItem(int position) {
            return pagelist.get(position);
        }
        @Override
        public int getCount() {
            return pagelist.size();
        }
    }

    /*
    프래그먼트 세팅
    */
    private void setFragments(){
        pages = new ArrayList<>();
        ExDetail = new ExDetailFragment();
        ExInfo = new ExInfoFragment();
        ExReview = new ExReviewFragment();

        pages.add(ExDetail);
        pages.add(ExInfo);
        pages.add(ExReview);
        ExDetail = (ExDetailFragment) getSupportFragmentManager().findFragmentById(R.id.pager);
        ExInfo = (ExInfoFragment) getSupportFragmentManager().findFragmentById(R.id.pager);
        ExReview = (ExReviewFragment) getSupportFragmentManager().findFragmentById(R.id.pager);

    }

    /*
   탭레이아웃 세팅
   */
    private void setTabLayout(){
        tab = (TabLayout) findViewById(R.id.tab);
    }

    /*
    예매하기 버튼을 누르면 예매하는 액티비티로 이동하는 함수
     */
    private void goReserve(){
        Intent intent = new Intent(DetailActivity.this, ReserveActivity.class);
        intent.putExtra("Title", textTitle.getText().toString());
        String freeorNot = serverData.getFee();
        if(freeorNot.equals("유료")){
            Toast.makeText(getBaseContext(), "예매할 수 없는 전시입니다.", Toast.LENGTH_SHORT).show();
        } else if (freeorNot.equals("무료") || freeorNot.equals("0")){
            Toast.makeText(getBaseContext(), "무료 관람입니다.", Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra("Freeornot", serverData.getFee());
            startActivity(intent);
        }
    }

    /*
     댓글쓰기 버튼을 누르면 관람평을 남기는 액티비티로 이동하는 함수
     */
    private void goComment(){
        Intent intent = new Intent(DetailActivity.this, CommentActivity.class);
        intent.putExtra("Title", textTitle.getText().toString());
        startActivity(intent);
    }

    /*
    지도 액티비티로 이동하는 함수
     */
    private void goMap(){
        Intent intent = new Intent(DetailActivity.this, MapsActivity.class);
        intent.putExtra("fromDetailAddress", serverData.getPlace());
        intent.putExtra("fromDetailLocation", serverData.getLocation());
        startActivity(intent);
    }

    /**
     * ListActivity 에서 데이터 넘겨 받기
     */
    private void setData(){

        Intent intent = getIntent();
        position = intent.getIntExtra("POSITION", -1);
        listKey = intent.getStringExtra("ListId");
        bundle = intent.getExtras();
        switch (listKey){
            case "category":
                if(position > -1 ) {
                    serverData = (ServerData) bundle.getSerializable("fromList");
                    ExDetail.serverData = serverData;
                    ExInfo.serverData = serverData;
                    ExReview.serverData = serverData;

                    setValue();
                }
                break;

            case "MainActivity":
                if(position > -1 ) {
                    serverData = (ServerData) bundle.getSerializable("fromMain");
                    ExDetail.serverData = serverData;
                    ExInfo.serverData = serverData;
                    ExReview.serverData = serverData;
                    setValue();

                }
                break;
        }
    }

    /*
    ListActivity 정보 DetailActivity widget 에 연결
     */
    private void setValue(){
        textTitle.setText(serverData.getPoster_title());
        textStartDate.setText(serverData.getDate_start());
        textEndDate.setText(serverData.getDate_end());
        textAddr.setText(serverData.getPlace());
        Glide.with(this).load(serverData.getPoster_img()).into(postImg);
    }

    //액션바 버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.menu, menu);

        return true;
    }

    //액션바의 공유버튼을 눌렀을 때 실행되는 함수
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        /*
            item의 id값으로 어떤 메뉴버튼을 눌렀는지 판별함.
         */


        switch(item.getItemId()) {
            /*
                페이스북 이외의 앱(카카오톡, 문자메세지 등)을 선택할 수 있도록 함.
                단, 에뮬레이터에서 실행하면. 에뮬레이터에 설치된 앱이 없기 때문에 자동으로 sms 앱으로 넘어감.
             */
            case R.id.menu:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "이 전시회 어때요?! 같이 갈래요?!" + "\n" + "\n" +
                                                   "전시회 제목 : " + serverData.getPoster_title() + "\n" +
                                                    "전시 장소 : " + serverData.getLocation());
                Intent chooser = Intent.createChooser(intent, "공유할 앱 선택");
                startActivity(chooser);
                break;
            /*
                페이스북 버튼을 눌러 페이스북에서 공유할 수 있도록 함.
             */
            case R.id.fbshare:
                fbShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    /*
    * 페이스북 공유하기 기능을 구현한 함수
    * */

    private void fbShare(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(DetailActivity.this, "공유가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(DetailActivity.this, "공유가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(DetailActivity.this, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
            }
        });
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder().setContentUrl(Uri.parse(serverData.getLocation())).build();
        shareDialog.show(shareLinkContent);
    }
}
