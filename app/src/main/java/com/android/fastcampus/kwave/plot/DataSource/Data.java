package com.android.fastcampus.kwave.plot.DataSource;

import android.content.Context;

import com.android.fastcampus.kwave.plot.R;

/**
 * Created by kwave on 2017-08-01.
 */

public class Data {
    public String title;        // 전시회 제목
    public String date_start;   // 전시회 시작일
    public String date_end;     // 전시회 마감일
    public String subTitle;     // 전시회 부제
    public String exhibition;   // 전시회 장소
    public String grade;        // 전시회 관람등급
    public float star;           // 별점
    public int no;
    public String image;        // 전시회 포스터
    public int resId;           // 각 전시회별 pk


    public void setImage(String str, Context context){
        image = str;
        // 문자열로 리소스 아이디 가져오기
        resId = context.getResources().getIdentifier(image, "mipmap", context.getPackageName());
    }

    /**
     * 메인 페이지의 랭킹 Best 이미지 자료
     */
    public Integer[] rankBestImage = {
            R.drawable.irene,
            R.drawable.dahyun,
            R.drawable.yein1,
            R.drawable.jisu,
            R.drawable.chorong,
            R.drawable.irene1,
            R.drawable.bomi,
            R.drawable.yein,
            R.drawable.sana,
            R.drawable.taeyeon,
    };
}
