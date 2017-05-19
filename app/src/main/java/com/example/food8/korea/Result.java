package com.example.food8.korea;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.tozzim.korea.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Result extends Activity {


    String URL_info = "";
    String[] detail_url_info;

    String title = "";
    String phone = "대표번호 없음";
    String zipcode = "";
    String addr = "";
    String contid="";

    String mapx = "";
    String mapy = "";
    //
    TextView txttitle;
    TextView txtaddr;
    TextView txtzipcode;
    TextView txtphone;
    ImageView mainimage;
    TextView txtinfo;
    ImageButton btnzzim;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();            //이거 붙여 주면 openstream에러 안남!!!!
        setContentView(R.layout.result);
        Intent intent = getIntent();
        URL_info = intent.getStringExtra("values");

        detail_url_info = URL_info.split("#");


        txttitle = (TextView) findViewById(R.id.txttitle_result);
        txtaddr = (TextView) findViewById(R.id.txtaddr_result);
        txtzipcode = (TextView) findViewById(R.id.txtzipcode_result);
        txtphone = (TextView) findViewById(R.id.txtphone_result);
        mainimage = (ImageView) findViewById(R.id.mainimage_result);
        txtinfo = (TextView) findViewById(R.id.txtinfo_result);
        btnzzim = (ImageButton) findViewById(R.id.btnzzim_result);

        //
        geturltext(detail_url_info[0]);
        //

        //

        //

        contid = detail_url_info[1].substring(detail_url_info[1].indexOf("&contentId=")+10, detail_url_info[1].indexOf("&MobileOS=ETC"));
        int sub_contypeid = detail_url_info[2].indexOf("contentTypeId=");
        final String url_temp2 = detail_url_info[2].substring(sub_contypeid+14, sub_contypeid+16);
        getXmlData(detail_url_info[1]);
        String result = getXmlData_sub(detail_url_info[2], url_temp2.replaceAll(" ", ""));

        txtinfo.setText(result);


        btnzzim.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //
                /*AlertDialog.Builder alert = new AlertDialog.Builder(Result.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage(mapx + " / " +mapy);
                alert.show();*/
                //
                Intent intent = new Intent(Result.this, GoogleMapShow.class);

                intent.putExtra("values", mapx+"!"+mapy);
                startActivity(intent);

            }
        });

    }

    public void geturltext(String text){
        String[] divide_text = text.split("!");

        title = divide_text[0];
        addr = divide_text[1];
        zipcode = divide_text[2];
        phone = divide_text[3];

        txttitle.setText(divide_text[0]);
        txtaddr.setText("주소 : "+divide_text[1]);
        txtzipcode.setText("우편번호 : "+divide_text[2]);
        txtphone.setText("전화번호 : "+divide_text[3]);
        GetXMLTask task = new GetXMLTask();
        task.execute(new String[] { divide_text[4] });
    }

    public void mainClick(View v) {
        Button newButton = (Button) v;
        Button btn1 = (Button) findViewById(R.id.btn_under1);
        Button btn3 = (Button) findViewById(R.id.btn_under3);
        if (newButton == btn1) {
            newButton.setSelected(true);
            btn3.setSelected(false);
        }else if (newButton == btn3) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
    }

    String getXmlData_sub(String url_base, String divide){

        StringBuffer buffer=new StringBuffer();
        try {
            URL url= new URL(url_base); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결


            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    /*case XmlPullParser.START_DOCUMENT:
                        buffer.append("Strat :");
                        break;*/

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기



                        if(divide.equals("39")) {
                            if (tag.equals("chkcreditcardfood")) {
                                buffer.append("신용 카드 사용 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("firstmenu")){
                                buffer.append("대표 메뉴  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("reservationfood")){
                                buffer.append("예약 안내  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("seat")){
                                buffer.append("좌석수  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("treatmenu")){
                                buffer.append("취급 메뉴  : ");
                                xpp.next();
                                buffer.append(xpp.getText().replaceAll("<br/>", "")); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("smoking")){
                                buffer.append("금연/흡연  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("restdatefood")){
                                buffer.append("쉬는 날  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("parkingfood")){
                                buffer.append("주차 시설  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("packing")){
                                buffer.append("포장 가능  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                        }else if(divide.equals("12")){
                            if(tag.equals("chkbabycarriage")){
                                buffer.append("유모차 대여 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("chkcreditcard")){
                                buffer.append("신용카드 가능 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("chkpet")){
                                buffer.append("애완동물 동반 가능 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("infocenter")){
                                buffer.append("문의 및 안내 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                        }else if(divide.equals("14")){                  //문화시설
                            if(tag.equals("chkbabycarriageculture")){
                                buffer.append("유모차 대여 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("chkcreditcardculture")){
                                buffer.append("신용카드 가능 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("chkpetculture")){
                                buffer.append("애완동물 동반 가능 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("infocenterculture")){
                                buffer.append("문의 및 안내 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("parkingculture")){
                                buffer.append("주차시설 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("usetimeculture")){
                                buffer.append("이용시간 :");
                                xpp.next();
                                buffer.append(xpp.getText().replaceAll("br", "").replaceAll(" />", "")); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                        }else if(divide.equals("15")){                  //축제
                            if(tag.equals("sponsor1")){
                                buffer.append("주최자 정보 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("sponsor1tel")){
                                buffer.append("주최자 연락처  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("eventstartdate")){
                                buffer.append("행사시작일 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("eventenddate")){
                                buffer.append("행사종료일 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("eventplace")){
                                buffer.append("행사장소 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("playtime")){
                                buffer.append("공연시간 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("usetimefestival")){
                                buffer.append("이용시간 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("bookingplace")){
                                buffer.append("예매처 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("eventhomepage")){
                                buffer.append("이벤트 홈페이지 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                        }else if(divide.equals("25")){                  //여행코스
                            if(tag.equals("distance")){
                                buffer.append("총 거리 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("taketime")){
                                buffer.append("소요시간  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                        }else if(divide.equals("32")){                  //숙박
                            if(tag.equals("checkintime")){
                                buffer.append("체크인 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("checkouttime")){
                                buffer.append("체크아웃 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("infocenterlodging")){
                                buffer.append("문의 및 안내 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("reservationlodging")){
                                buffer.append("예약 안내 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("pickup")){
                                buffer.append("픽업서비스 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("roomtype")){
                                buffer.append("객실 유형 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("roomcount")){
                                buffer.append("객실 수 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("chkcooking")){
                                buffer.append("조리가능 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("parkinglodging")){
                                buffer.append("주차 가능 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                        }else if(divide.equals("38")){                  //쇼핑
                            if(tag.equals("chkbabycarriageshopping")){
                                buffer.append("유모차대여여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("chkcreditcardshopping")){
                                buffer.append("신용카드 가능 여부  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("chkpetshopping")){
                                buffer.append("애완동물 동반가능여부  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("infocentershopping")){
                                buffer.append("문의 및 안내 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("opentime")){
                                buffer.append("영업시간 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("parkingshopping")){
                                buffer.append("주차시설  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("saleitem")){
                                buffer.append("판매품목  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("restdateshopping")){
                                buffer.append("쉬는날  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("restroom")){
                                buffer.append("화장실  : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                        }else if(divide.equals("28")){
                            if(tag.equals("chkbabycarriageleports")){
                                buffer.append("유모차 대여 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("chkcreditcardleports")){
                                buffer.append("신용카드 가능 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                            else if(tag.equals("chkpetleports")){
                                buffer.append("애완동물 동반 가능 여부 : ");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }else if(tag.equals("infocenterleports")){
                                buffer.append("문의 및 안내 :");
                                xpp.next();
                                buffer.append(xpp.getText()); //description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                buffer.append("\n");          //줄바꿈 문자 추가
                            }
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();    //테그 이름 얻어오기

                        if(tag.equals("item")) {

                        }
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buffer.toString();

    }//getXmlData method...




    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {


            mainimage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            /*MATRIX 원본 크기 그대로 보여줌 (왼쪽상단 정렬)

            CENTER 원본 크기 그대로 보여줌 (가운데 정렬)

            CENTER_CROP View 영역에 공백이 있으면 채워서 보여줌(비율유지)

            CENTER_INSIDE View 영역을 벗어나면 맞춰서 보여줌(비율유지)

            FIT_START View 영역에 맞게 보여줌 (왼쪽상단 정렬, 비율유지)

            FIT_CENTER View 영역에 맞게 보여줌 (가운데 정렬, 비율유지)

            FIT_END View 영역에 맞게 보여줌 (왼쪽하단 정렬, 비율유지)

            FIT_XY View 영역을 가득 채워서 보여줌(비율유지 안함)*/
            mainimage.setImageBitmap(result);

        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }


    }


    void getXmlData(String url_base) {

        try {
            URL url = new URL(url_base); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream();  //url위치로 입력스트림 연결
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));  //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();
                        //테그 이름 얻어오기
                        if (tag.equals("mapx")) {
                            xpp.next();
                            mapx = xpp.getText();
                        } else if (tag.equals("mapy")) {
                            xpp.next();
                            mapy = xpp.getText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기



                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}


