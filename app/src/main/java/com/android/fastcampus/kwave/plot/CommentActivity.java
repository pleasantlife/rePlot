package com.android.fastcampus.kwave.plot;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    Button btnWriteComment;
    TextView reserveTitle;
    EditText textComment;
    String comment = "";
    String date;
    RatingBar writeRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        reserveTitle = (TextView) findViewById(R.id.reserveTitle);
        setTitle();
        writeRatingBar = (RatingBar) findViewById(R.id.writeRatingBar);
        btnWriteComment = (Button) findViewById(R.id.btnWriteComment);
        textComment = (EditText) findViewById(R.id.textComment);
        btnWriteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = textComment.getText().toString();
                float rate = writeRatingBar.getRating();
                Intent intent = new Intent();
                intent.putExtra("Comment", comment);
                intent.putExtra("Rate", rate);
                setDate();
                Toast.makeText(getBaseContext(), "관람평 = " + comment + "평점 = " + rate + "설정한 날짜 = " + date, Toast.LENGTH_SHORT).show();

            }
        });

    }


    public String setDate(){
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = sdf.format(nowDate);
        return date;
    }

    public void setTitle(){
        Intent intent = getIntent();
        reserveTitle.setText(intent.getStringExtra("Title"));
    }


}
