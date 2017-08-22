package com.android.fastcampus.kwave.plot;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReserveActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    String[] number = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String date;
    Spinner spinnerNormal, spinnerStudent, spinnerPackage, spinnerWeekday, spinnerWeekend;
    ArrayAdapter adapter;
    TextView txtPriceResult, txtTitle, txtNormalPrice, txtViewDate;
    Button btnGoBuy, btnSetVisitDate;
    String priceString;
    DatePickerDialog datePickerDialog;


    static int normalPrice, studentPrice, packagePrice, weekdayPrice, weekendPrice, resultPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        adapter = new ArrayAdapter(getApplicationContext(), R.layout.layout_spinner_item, number);

        initView();
        setSpinnerAdapter(spinnerNormal);
        setSpinnerAdapter(spinnerStudent);
        setSpinnerAdapter(spinnerPackage);
        setSpinnerAdapter(spinnerWeekday);
        setSpinnerAdapter(spinnerWeekend);


    }
    /*
     뷰 생성 (버튼 및 스피너, 텍스트 등)
     */
   public void initView(){
       spinnerNormal = (Spinner) findViewById(R.id.spinnerNormal);
       spinnerStudent = (Spinner) findViewById(R.id.spinnerStudent);
       spinnerPackage = (Spinner) findViewById(R.id.spinnerPackage);
       spinnerWeekday = (Spinner) findViewById(R.id.spinnerWeekday);
       spinnerWeekend = (Spinner) findViewById(R.id.spinnerWeekend);

       txtPriceResult = (TextView) findViewById(R.id.txtPriceResult);
       txtTitle = (TextView) findViewById(R.id.txtTitle);
       txtNormalPrice = (TextView) findViewById(R.id.txtNormalPrice);
       txtViewDate = (TextView) findViewById(R.id.txtViewDate);


       btnGoBuy = (Button) findViewById(R.id.btnGoBuy);
       btnGoBuy.setOnClickListener(this);

       btnSetVisitDate = (Button) findViewById(R.id.btnSetDate);
       btnSetVisitDate.setOnClickListener(this);
       setTitle();
       txtNormalPrice.setText(getPrice() + " 원");
   }

   /*
   스피너 사용을 위한 어댑터 구성
    */

   public void setSpinnerAdapter(Spinner spinner){
       spinner.setAdapter(adapter);
       spinner.setOnItemSelectedListener(this);
   }

   public String getPrice(){
       Intent intent = getIntent();
       priceString = intent.getStringExtra("Freeornot");
       return priceString;
   }

   /*
    1) 결제하기 버튼을 눌렀을 때 Toast 알림 메세지 띄움.
    2) setDate 함수를 통해 현재 시스템의 날짜 가져옴.
    3) setNoti 함수를 통해 결재 내용을 간단하게 노티로 띄움.
    */

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnGoBuy:
                Toast.makeText(getBaseContext(), resultPrice + " 원 결제 되었습니다.", Toast.LENGTH_SHORT).show();


                setDate();
                Intent intent = new Intent(ReserveActivity.this, PayActivity.class);
                intent.putExtra("resultPrice", resultPrice);
                //setNoti(resultPrice);
                startActivity(intent);
                break;
            case R.id.btnSetDate:
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
                int getYear = Integer.parseInt(yearFormat.format(date));
                int getMonth = Integer.parseInt(monthFormat.format(date))-1;
                int getDate = Integer.parseInt(dateFormat.format(date));

                datePickerDialog = new DatePickerDialog(this, listener, getYear, getMonth, getDate);
                datePickerDialog.show();
        }
    }

    /*
        권종별 스피너에서 인원을 클릭했을 때의 값을 합산하여
        합계 텍스트가 변하게 함.
     */

    //spinner에 onItemSelected를 추가하면 선택했을 때의 포지션 값 등을 알 수 있음.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int basePrice = Integer.parseInt(getPrice());
        switch(parent.getId()){
            case R.id.spinnerNormal:
                normalPrice = position * basePrice;
                break;
            case R.id.spinnerStudent:
                studentPrice = position * 10000;
                break;
            case R.id.spinnerPackage:
                packagePrice = position * 28000;
                break;
            case R.id.spinnerWeekday:
                weekdayPrice = position * 15000;
                break;
            case R.id.spinnerWeekend:
                weekendPrice = position * 17000;
                break;
        }
        // 스피너로 인원 수 선택 후 값을 합산하여 보여줌.
        resultPrice = normalPrice + studentPrice + packagePrice + weekdayPrice + weekendPrice;

        txtPriceResult.setText(resultPrice+"");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
        어떤 전시를 선택했는지 알아볼 수 있도록 리스트 액티비티에서 intent로 제목을 가져옴.
    */
    public void setTitle(){
        Intent intent = getIntent();
        txtTitle.setText(intent.getStringExtra("Title"));
    }


    /*
        전시 시작일을 받아올 함수. 현재는 전시 시작일 데이터가 넘어오지 않기 때문에,
        임의로 8월 28일로 설정해두었음.
     */
    public void setDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 8);
        calendar.set(Calendar.DATE, 28);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        date = (calendar.get(Calendar.MONTH)) + "월" + (calendar.get(Calendar.DATE)) + "일";

    }

    /*
        노티피케이션을 생성하는 함수. setDate 함수를 통해 전시 시작일을 받아와서 노티에 나타나게 할 예정.
        현재는 setDate에서 지정한 날짜 + 결제한 금액이 나옴.
     */

    public void setNoti(int resultPrice){
        int requestCode = 365;
        if(requestCode == 365) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(getApplicationContext())
                    .setContentTitle("예매 완료")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(date + "에 보실 전시 예매." + resultPrice + " 원 결제하였습니다.")
                    .build();

            notificationManager.notify(requestCode, notification);
        }
    }

    public DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            int realMonth = month + 1;
            txtViewDate.setText(year + "년 " + realMonth + "월 " + dayOfMonth + "일");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Toast.makeText(getBaseContext(), calendar+"", Toast.LENGTH_SHORT).show();
        }
    };

}
