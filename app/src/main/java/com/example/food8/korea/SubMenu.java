package com.example.food8.korea;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.tozzim.korea.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class SubMenu extends Activity {

    Spinner spin1;
    Spinner spin2;
    Spinner spin3;
    Spinner loSpin1;
    Spinner loSpin2;
    public int page;
    Button btnRequest;

    Handler handler = new Handler();

    String base_URL = "http://api.visitkorea.or.kr/openapi/service/rest/";

    String Service_kor = "KorService/areaBasedList?";
    String Service_key = "6tLZdNCgFtUUkC1aMEPPSDH5EqZB09HbJ9vEwO1DeRGItkpZQzyAxdTw2npenOfhIQdsklstTNt9qrj2RODhkQ%3D%3D";
    String contentTypeId = "&contentTypeId=";
    String contentTypeId_meta = "";
    String areaCode = "&areaCode=";
    String areaCode_meta = "";
    String sigunguCode = "&sigunguCode=";
    String sigunguCode_meta = "";
    String High_Category = "&cat1=";
    String High_Category_meta = "";
    String Middle_Category = "&cat2=";
    String Middle_Category_meta = "";
    String Row_Category = "&cat3=";
    String Row_Category_meta = "";
    String base_service = "&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12";
    String pageNo = "&pageNo=";
    String pageNo_meta = "";
    int totalCount = 0;
    String contentid = "";
    String contid_faxing = "";
    String conttype_faxing = "";


    ArrayList<ListItem> list = new ArrayList<ListItem>();
    ListView listView;

    String title = "";
    String addr = "";
    String zip = "";
    String tel = "";
    String url_image="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.sub_menu);

        Intent intent = getIntent();
        int values = intent.getIntExtra("values", 0);
        page = values;

        spin1 = (Spinner) findViewById(R.id.spinner1);
        spinnerMaker(values);
        spin2 = (Spinner) findViewById(R.id.spinner2);
        spin1.setOnItemSelectedListener(spinSelectedListner1);
        spin3 = (Spinner) findViewById(R.id.spinner3);
        spin2.setOnItemSelectedListener(spinSelectedListner2);

        loSpin1 = (Spinner) findViewById(R.id.localSelector1);
        localSpinner1(R.array.city_array);
        loSpin2 = (Spinner) findViewById(R.id.localSelector2);
        loSpin1.setOnItemSelectedListener(localSpinSelector);

        //
        if (page == 1) {
            contentTypeId_meta = "12";
        } else if (page == 2) {
            contentTypeId_meta = "14";
        } else if (page == 3) {
            contentTypeId_meta = "15";
        } else if (page == 4) {
            contentTypeId_meta = "25";
        } else if (page == 5) {
            contentTypeId_meta = "28";
        } else if (page == 6) {
            contentTypeId_meta = "32";
        } else if (page == 7) {
            contentTypeId_meta = "38";
        } else if (page == 8) {
            contentTypeId_meta = "39";
        }

        //

        final Button btn1 = (Button) findViewById(R.id.btn_under1);
        btn1.setSelected(true);
        btnRequest = (Button) this.findViewById(R.id.btnRequest);

        final String detail_url = "";

        //텍스트스크롤기능
        btnRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                final String inputText1 = loSpin1.getSelectedItem().toString();
                final String inputText2 = loSpin2.getSelectedItem().toString();

                sido_func(inputText1, inputText2);

                //다이알로그
                final MyDialogFragment frag = MyDialogFragment.newInstance();
                frag.show(getFragmentManager(), "TAG");

                //작업스레드
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            list.clear();

                                String temp = base_URL + Service_kor + "ServiceKey=" + Service_key + contentTypeId + contentTypeId_meta +
                                        areaCode + areaCode_meta + sigunguCode + sigunguCode_meta + High_Category + High_Category_meta + Middle_Category +
                                        Middle_Category_meta + Row_Category + Row_Category_meta + base_service + pageNo + pageNo_meta;    //URL변환은 여기에
                               totalCount = totalcount(temp);



                                //for (int i = 0; i < /*(totalCount / 12)*/ 3; i++) {
                                //getXmlData(temp + i); //파싱을하는 곳 이곳을 버튼까지 만드는 곳으로 번경
                                //}
                                //이부분이 파싱하는 부분
                            //트래픽불가시 정적으로 할당하여 확인하기 위한 코딩
                            list.add(new ListItem("AK플라자백화점 (구로점)", "서울특별시 구로구 구로중앙로 152 " + "(구로동)", "08292", "02-3449-4114", "132038", "38", "http://tong.visitkorea.or.kr/cms/resource/08/1309808_image2_1.jpg"));
                            list.add(new ListItem("롯데백화점 (본점)", "서울특별시 중구 을지로 30 "+"(소공동)", "04533", "02-771-2500", "132668", "38", "http://tong.visitkorea.or.kr/cms/resource/47/668147_image2_1.jpg"));
                            list.add(new ListItem("롯데백화점 (에비뉴엘)", "서울특별시 중구 남대문로 81 " + "(소공동)", "04533", "02-771-2500", "273806", "38", "http://tong.visitkorea.or.kr/cms/resource/56/668856_image2_1.jpg"));
                            list.add(new ListItem("신세계백화점 (본점)", "서울특별시 중구 소공로 63 "+"(충무로1가)", "04530", "1588-1234", "132642", "38", "http://tong.visitkorea.or.kr/cms/resource/61/668861_image2_1.jpg"));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listView = (ListView) findViewById(R.id.viewlist);

                                CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.list_row, list);

                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                        Intent intent1 = new Intent(SubMenu.this, Result.class);


                                        String nextstring = list.get(position).gettxtTitle() +"!" +list.get(position).getaddr()+"!" +list.get(position).getzipecode()+"!" +list.get(position).gettel()+"!" +list.get(position).getimage();
                                        contentid = list.get(position).getcontid();
                                        contentTypeId_meta = list.get(position).getconttype();

                                        String url_info1 = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey="+Service_key+"&contentTypeId="+contentTypeId_meta+"&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
                                        String url_info2 = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey=" + Service_key + "&contentTypeId=" + contentTypeId_meta + "&contentId=" + contentid + "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
                                        String detail_url = nextstring+"#"+url_info1 + "#" + url_info2;
                                        intent1.putExtra("values", detail_url);
                                        startActivity(intent1);
                                    }
                                });

                                frag.dismiss();
                            }
                        });
                    }
                });

                t.start();
            }
        });

    }


    private void setSpinner(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(fAdapter);
    }

    private void setSpinner1(int itemNum) {
        ArrayAdapter<CharSequence> kAdapter;
        kAdapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        kAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(kAdapter);
    }

    private void setSpinner2(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin3.setAdapter(fAdapter);
    }

    private void localSpinner1(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loSpin1.setAdapter(fAdapter);
    }

    private void localSpinner2(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this, itemNum, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loSpin2.setAdapter(fAdapter);
    }

    public AdapterView.OnItemSelectedListener localSpinSelector = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:
                    localSpinner2(R.array.default_contury);
                    break;
                case 1:
                    localSpinner2(R.array.seoul_array);
                    areaCode_meta = "1";
                    break;
                case 2:
                    localSpinner2(R.array.incheon_array);
                    areaCode_meta = "2";
                    break;
                case 3:
                    localSpinner2(R.array.daejeon_array);
                    areaCode_meta = "3";
                    break;
                case 4:
                    localSpinner2(R.array.daegu_array);
                    areaCode_meta = "4";
                    break;
                case 5:
                    localSpinner2(R.array.gwangju_array);
                    areaCode_meta = "5";
                    break;
                case 6:
                    localSpinner2(R.array.busan_array);
                    areaCode_meta = "6";
                    break;
                case 7:
                    localSpinner2(R.array.ulsan_array);
                    areaCode_meta = "7";
                    break;
                case 8:
                    localSpinner2(R.array.sejong_array);
                    areaCode_meta = "8";
                    break;
                case 9:
                    localSpinner2(R.array.gyunggi_array);
                    areaCode_meta = "31";
                    break;
                case 10:
                    localSpinner2(R.array.gangwon_array);
                    areaCode_meta = "32";
                    break;
                case 11:
                    localSpinner2(R.array.norchung_array);
                    areaCode_meta = "33";
                    break;
                case 12:
                    localSpinner2(R.array.souchung_array);
                    areaCode_meta = "34";
                    break;
                case 13:
                    localSpinner2(R.array.norgyung_array);
                    areaCode_meta = "35";
                    break;
                case 14:
                    localSpinner2(R.array.sougyung_array);
                    areaCode_meta = "36";
                    break;
                case 15:
                    localSpinner2(R.array.norjun_array);
                    areaCode_meta = "37";
                    break;
                case 16:
                    localSpinner2(R.array.soujun_array);
                    areaCode_meta = "38";
                    break;
                case 17:
                    localSpinner2(R.array.jeju_array);
                    areaCode_meta = "39";
                    break;
                default:
                    localSpinner2(R.array.default_contury);
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public int data;

    public void spinnerMaker(int values) {
        switch (values) {
            case 1:
                setSpinner(R.array.High_array_1);
                break;
            case 2:
                setSpinner(R.array.High_array_2);
                break;
            case 3:
                setSpinner(R.array.High_array_3);
                break;
            case 4:
                setSpinner(R.array.High_array_4);
                break;
            case 5:
                setSpinner(R.array.High_array_5);
                break;
            case 6:
                setSpinner(R.array.High_array_6);
                break;
            case 7:
                setSpinner(R.array.High_array_7);
                break;
            case 8:
                setSpinner(R.array.High_array_8);
                break;
            default:
                setSpinner(R.array.High_array);
                break;
        }
    }

    public AdapterView.OnItemSelectedListener spinSelectedListner1 = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (page) {
                case 1:
                    switch (position) {
                        case 0:
                            setSpinner1(R.array.Mid_default);
                            data = 0;
                            break;
                        case 1:
                            setSpinner1(R.array.Mid_array_1);
                            data = 1;
                            break;
                        case 2:
                            setSpinner1(R.array.Mid_array_2);
                            data = 2;
                            break;
                    }
                    break;
                case 2:
                    switch (position) {
                        case 0:
                            setSpinner1(R.array.Mid_default);
                            data = 0;
                            break;
                        case 1:
                            setSpinner1(R.array.Mid_array_2);
                            data = 2;
                            break;
                    }
                    break;
                case 3:
                    switch (position) {
                        case 0:
                            setSpinner1(R.array.Mid_default);
                            data = 0;
                            break;
                        case 1:
                            setSpinner1(R.array.Mid_array_2);
                            data = 2;
                            break;
                    }
                    break;
                case 4:
                    switch (position) {
                        case 0:
                            setSpinner1(R.array.Mid_default);
                            data = 0;
                            break;
                        case 1:
                            setSpinner1(R.array.Mid_array_7);
                            data = 7;
                            break;
                    }
                    break;
                case 5:
                    switch (position) {
                        case 0:
                            setSpinner1(R.array.Mid_default);
                            data = 0;
                            break;
                        case 1:
                            setSpinner1(R.array.Mid_array_3);
                            data = 3;
                            break;
                    }
                    break;
                case 6:
                    switch (position) {
                        case 0:
                            setSpinner1(R.array.Mid_default);
                            data = 0;
                            break;
                        case 1:
                            setSpinner1(R.array.Mid_array_6);
                            data = 6;
                            break;
                    }
                    break;
                case 7:
                    switch (position) {
                        case 0:
                            setSpinner1(R.array.Mid_default);
                            data = 0;
                            break;
                        case 1:
                            setSpinner1(R.array.Mid_array_4);
                            data = 4;
                            break;
                    }
                    break;
                case 8:
                    switch (position) {
                        case 0:
                            setSpinner1(R.array.Mid_default);
                            data = 0;
                            break;
                        case 1:
                            setSpinner1(R.array.Mid_array_5);
                            data = 5;
                            break;
                    }
                    break;
                default:
                    data = position;
                    switch (position) {
                        case (0):
                            setSpinner1(R.array.Mid_default);
                            break;
                        case (1):
                            setSpinner1(R.array.Mid_array_1);
                            break;
                        case (2):
                            setSpinner1(R.array.Mid_array_2);
                            break;
                        case (3):
                            setSpinner1(R.array.Mid_array_3);
                            break;
                        case (4):
                            setSpinner1(R.array.Mid_array_4);
                            break;
                        case (5):
                            setSpinner1(R.array.Mid_array_5);
                            break;
                        case (6):
                            setSpinner1(R.array.Mid_array_6);
                            break;
                        case (7):
                            setSpinner1(R.array.Mid_array_7);
                            break;
                    }
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };


    public AdapterView.OnItemSelectedListener spinSelectedListner2 = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (data) {
                case (0):
                    setSpinner2(R.array.Low_default);
                    break;
                case (1):
                    switch (position) {
                        case (0):
                            setSpinner2(R.array.Low_default);
                            break;
                        case (1):
                            setSpinner2(R.array.Low_array_1_1);
                            break;
                        case (2):
                            setSpinner2(R.array.Low_array_1_2);
                            break;
                    }
                    break;
                case (2):
                    switch (position) {
                        case (0):
                            setSpinner2(R.array.Low_default);
                            break;
                        case (1):
                            setSpinner2(R.array.Low_array_2_1);
                            break;
                        case (2):
                            setSpinner2(R.array.Low_array_2_2);
                            break;
                        case (3):
                            setSpinner2(R.array.Low_array_2_3);
                            break;
                        case (4):
                            setSpinner2(R.array.Low_array_2_4);
                            break;
                        case (5):
                            setSpinner2(R.array.Low_array_2_5);
                            break;
                        case (6):
                            setSpinner2(R.array.Low_array_2_6);
                            break;
                        case (7):
                            setSpinner2(R.array.Low_array_2_7);
                            break;
                        case (8):
                            setSpinner2(R.array.Low_array_2_8);
                            break;
                    }
                    break;
                case (3):
                    switch (position) {
                        case (0):
                            setSpinner2(R.array.Low_default);
                            break;
                        case (1):
                            setSpinner2(R.array.Low_array_3_1);
                            break;
                        case (2):
                            setSpinner2(R.array.Low_array_3_2);
                            break;
                        case (3):
                            setSpinner2(R.array.Low_array_3_3);
                            break;
                        case (4):
                            setSpinner2(R.array.Low_array_3_4);
                            break;
                        case (5):
                            setSpinner2(R.array.Low_array_3_5);
                            break;

                    }
                    break;
                case (4):
                    switch (position) {
                        case (0):
                            setSpinner2(R.array.Low_default);
                            break;
                        case (1):
                            setSpinner2(R.array.Low_array_4_1);
                            break;
                    }
                    break;
                case (5):
                    switch (position) {
                        case (0):
                            setSpinner2(R.array.Low_default);
                            break;
                        case (1):
                            setSpinner2(R.array.Low_array_5_1);
                            break;
                    }
                    break;
                case (6):
                    switch (position) {
                        case (0):
                            setSpinner2(R.array.Low_default);
                            break;
                        case (1):
                            setSpinner2(R.array.Low_array_6_1);
                            break;
                    }
                    break;
                case (7):
                    switch (position) {
                        case (0):
                            setSpinner2(R.array.Low_default);
                            break;
                        case (1):
                            setSpinner2(R.array.Low_array_7_1);
                            break;
                        case (2):
                            setSpinner2(R.array.Low_array_7_2);
                            break;
                        case (3):
                            setSpinner2(R.array.Low_array_7_3);
                            break;
                        case (4):
                            setSpinner2(R.array.Low_array_7_4);
                            break;
                        case (5):
                            setSpinner2(R.array.Low_array_7_5);
                            break;
                        case (6):
                            setSpinner2(R.array.Low_array_7_6);
                            break;
                    }
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };

    public void mainClick(View v) {
        Button newButton = (Button) v;
        Button btn1 = (Button) findViewById(R.id.btn_under1);
        Button btn3 = (Button) findViewById(R.id.btn_under3);
        if (newButton == btn1) {
            newButton.setSelected(true);
            btn3.setSelected(false);
        } else if (newButton == btn3) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
    }

    public int totalcount(String url_base) {
        int totalcount = 0;
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
                        if (tag.equals("totalCount")) {
                            xpp.next();
                            totalcount = Integer.parseInt(xpp.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:

                        break;
                }

                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalcount;
    }

    public static class MyDialogFragment extends DialogFragment {
        public static MyDialogFragment newInstance() {
            return new MyDialogFragment();
        }

        public Dialog onCreateDialog(Bundle savedInstancestate) {

            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle("네트워크");
            dialog.setMessage("요청중...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            return dialog;
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
                        if (tag.equals("title")) {
                            xpp.next();
                            title = xpp.getText();
                        } else if (tag.equals("addr1")) {
                            xpp.next();
                            addr = xpp.getText();
                        } else if (tag.equals("addr2")) {
                            xpp.next();
                            addr = addr + " " + xpp.getText();
                        } else if (tag.equals("zipcode")) {
                            xpp.next();
                            zip = xpp.getText();
                        } else if (tag.equals("tel")) {
                            xpp.next();
                            tel = xpp.getText();
                        } else if (tag.equals("contentid")) {
                            xpp.next();
                            contid_faxing = xpp.getText();
                        } else if (tag.equals("contenttypeid")) {
                            xpp.next();
                            conttype_faxing = xpp.getText();
                        } else if (tag.equals("firstimage")){
                            xpp.next();
                            url_image = xpp.getText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기

                        if (tag.equals("item")){
                            list.add(new ListItem(title, addr, zip, tel, contid_faxing, conttype_faxing, url_image));
                        }



                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void sido_func(String inputText1, String inputText2) {


        if (inputText1.contains("서울")) {
            areaCode_meta = "1";

            if (inputText2.contains("강남")) {
                sigunguCode_meta = "1";
            } else if (inputText2.contains("강동")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("강북")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("강서")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("관악")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("광진")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("구로")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("금천")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("노원")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("도봉")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("동대문")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("동작")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("마포")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("서대문")) {
                sigunguCode_meta = "14";

            } else if (inputText2.contains("서초")) {
                sigunguCode_meta = "15";

            } else if (inputText2.contains("성동")) {
                sigunguCode_meta = "16";

            } else if (inputText2.contains("성북")) {
                sigunguCode_meta = "17";

            } else if (inputText2.contains("송파")) {
                sigunguCode_meta = "18";

            } else if (inputText2.contains("양천")) {
                sigunguCode_meta = "19";

            } else if (inputText2.contains("영등포")) {
                sigunguCode_meta = "20";

            } else if (inputText2.contains("용산")) {
                sigunguCode_meta = "21";

            } else if (inputText2.contains("은평")) {
                sigunguCode_meta = "22";

            } else if (inputText2.contains("종로")) {
                sigunguCode_meta = "23";

            } else if (inputText2.contains("중구")) {
                sigunguCode_meta = "24";

            } else if (inputText2.contains("중랑")) {
                sigunguCode_meta = "25";

            }
        } else if (inputText1.contains("인천")) {
            areaCode_meta = "2";
            if (inputText2.contains("강화")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("계양")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("남구")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("남동")) {
                sigunguCode_meta = "4";

            } else if (inputText2.equalsIgnoreCase("동구")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("부평")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("서")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("연수")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("옹진")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("중구")) {
                sigunguCode_meta = "10";

            }
        } else if (inputText1.contains("대전")) {
            areaCode_meta = "3";
            if (inputText2.contains("대덕")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("동")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("서")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("유성")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("중")) {
                sigunguCode_meta = "5";

            }
        } else if (inputText1.contains("대구")) {
            areaCode_meta = "4";
            if (inputText2.contains("남")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("날서")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("달성")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("동")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("북")) {
                sigunguCode_meta = "5";

            } else if (inputText2.equalsIgnoreCase("서구")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("수성")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("중")) {
                sigunguCode_meta = "8";

            }
        } else if (inputText1.contains("광주")) {
            areaCode_meta = "5";
            if (inputText2.contains("광양구")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("남구")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("동구")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("북구")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("서구")) {
                sigunguCode_meta = "5";

            }
        } else if (inputText1.contains("부산")) {
            areaCode_meta = "6";
            if (inputText2.contains("강서")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("금정")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("기장")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("남")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("동구")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("동래")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("부산")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("북")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("사상")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("사하")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("서")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("수영")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("연제")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("영도")) {
                sigunguCode_meta = "14";

            } else if (inputText2.contains("중")) {
                sigunguCode_meta = "15";

            } else if (inputText2.contains("해운대")) {
                sigunguCode_meta = "16";

            }
        } else if (inputText1.contains("울산")) {
            areaCode_meta = "7";
            if (inputText2.contains("중")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("남")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("동")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("북")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("울주")) {
                sigunguCode_meta = "5";

            }
        } else if (inputText1.contains("세종")) {
            areaCode_meta = "8";
            sigunguCode_meta = "1";
        }else if (inputText1.contains("경기")) {
            areaCode_meta = "31";
            if (inputText2.contains("가평")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("고양")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("과천")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("광명")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("광주")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("구리")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("군포")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("김포")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("남양")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("동두천")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("부천")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("성남")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("수원")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("시흥")) {
                sigunguCode_meta = "14";

            } else if (inputText2.contains("안산")) {
                sigunguCode_meta = "15";

            } else if (inputText2.contains("안성")) {
                sigunguCode_meta = "16";

            } else if (inputText2.contains("안양")) {
                sigunguCode_meta = "17";

            } else if (inputText2.contains("양주")) {
                sigunguCode_meta = "18";

            } else if (inputText2.contains("양평")) {
                sigunguCode_meta = "19";

            } else if (inputText2.contains("여주")) {
                sigunguCode_meta = "20";

            } else if (inputText2.contains("연천")) {
                sigunguCode_meta = "21";

            } else if (inputText2.contains("오산")) {
                sigunguCode_meta = "22";

            } else if (inputText2.contains("용인")) {
                sigunguCode_meta = "23";

            } else if (inputText2.contains("의왕")) {
                sigunguCode_meta = "24";

            } else if (inputText2.contains("의정부")) {
                sigunguCode_meta = "25";

            } else if (inputText2.contains("이천")) {
                sigunguCode_meta = "26";

            } else if (inputText2.contains("파주")) {
                sigunguCode_meta = "27";

            } else if (inputText2.contains("평택")) {
                sigunguCode_meta = "28";

            } else if (inputText2.contains("포천")) {
                sigunguCode_meta = "29";

            } else if (inputText2.contains("하남")) {
                sigunguCode_meta = "30";

            } else if (inputText2.contains("화성")) {
                sigunguCode_meta = "31";

            }
        } else if (inputText1.contains("강원")) {
            areaCode_meta = "32";
            if (inputText2.contains("강릉")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("고성")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("동해")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("삼척")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("속초")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("양구")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("양양")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("영월")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("원주")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("인제")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("정선")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("철원")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("춘천")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("태백")) {
                sigunguCode_meta = "14";

            } else if (inputText2.contains("평창")) {
                sigunguCode_meta = "15";

            } else if (inputText2.contains("홍천")) {
                sigunguCode_meta = "16";

            } else if (inputText2.contains("화천")) {
                sigunguCode_meta = "17";

            } else if (inputText2.contains("횡성")) {
                sigunguCode_meta = "18";

            }
        } else if (inputText1.contains("충북")||inputText1.contains("충청북")) {
            areaCode_meta = "33";
            if (inputText2.contains("괴산")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("단양")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("보은")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("영동")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("옥천")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("음성")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("제천")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("진천")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("청원")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("청주")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("충주")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("증평")) {
                sigunguCode_meta = "12";

            }
        } else if (inputText1.contains("충남")||inputText1.contains("충청남")) {
            areaCode_meta = "34";
            if (inputText2.contains("공주")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("금산")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("논산")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("당진")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("보령")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("부여")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("서산")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("서천")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("아산")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("예산")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("천안")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("청양")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("태안")) {
                sigunguCode_meta = "14";

            } else if (inputText2.contains("홍성")) {
                sigunguCode_meta = "15";

            } else if (inputText2.contains("계룡")) {
                sigunguCode_meta = "16";

            }
        } else if (inputText1.contains("경북")||inputText1.contains("경상북")) {
            areaCode_meta = "35";
            if (inputText2.contains("경산")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("경주")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("고령")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("구미")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("군위")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("김천")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("문경")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("봉화")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("상주")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("성주")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("안동")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("영덕")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("영양")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("영주")) {
                sigunguCode_meta = "14";

            } else if (inputText2.contains("영천")) {
                sigunguCode_meta = "15";

            } else if (inputText2.contains("예천")) {
                sigunguCode_meta = "16";

            } else if (inputText2.contains("울릉")) {
                sigunguCode_meta = "17";

            } else if (inputText2.contains("울진")) {
                sigunguCode_meta = "18";

            } else if (inputText2.contains("의성")) {
                sigunguCode_meta = "19";

            } else if (inputText2.contains("청도")) {
                sigunguCode_meta = "20";

            } else if (inputText2.contains("청송")) {
                sigunguCode_meta = "21";

            } else if (inputText2.contains("칠곡")) {
                sigunguCode_meta = "22";

            } else if (inputText2.contains("포항")) {
                sigunguCode_meta = "23";

            }
        } else if (inputText1.contains("경상남")||inputText1.contains("경남")) {
            areaCode_meta = "36";
            if (inputText2.contains("거제")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("거창")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("고성")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("김해")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("남해")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("마산")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("밀양")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("사천")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("산청")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("양산")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("의령")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("진주")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("진해")) {
                sigunguCode_meta = "14";

            } else if (inputText2.contains("창녕")) {
                sigunguCode_meta = "15";

            } else if (inputText2.contains("창원")) {
                sigunguCode_meta = "16";

            } else if (inputText2.contains("통영")) {
                sigunguCode_meta = "17";

            } else if (inputText2.contains("하동")) {
                sigunguCode_meta = "18";

            } else if (inputText2.contains("함안")) {
                sigunguCode_meta = "19";

            } else if (inputText2.contains("함양")) {
                sigunguCode_meta = "20";

            } else if (inputText2.contains("합천")) {
                sigunguCode_meta = "21";

            }
        } else if (inputText1.contains("전라북")||inputText1.contains("전북")) {
            areaCode_meta = "37";
            if (inputText2.contains("고창")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("군산")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("김제")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("남원")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("무주")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("부안")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("순창")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("완주")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("익산")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("임실")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("장수")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("전주")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("정읍")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("진안")) {
                sigunguCode_meta = "14";

            }
        } else if (inputText1.contains("전라남")||inputText1.contains("전남")) {
            areaCode_meta = "38";
            if (inputText2.contains("강진")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("고흥")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("곡성")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("광양")) {
                sigunguCode_meta = "4";

            } else if (inputText2.contains("구례")) {
                sigunguCode_meta = "5";

            } else if (inputText2.contains("나주")) {
                sigunguCode_meta = "6";

            } else if (inputText2.contains("담양")) {
                sigunguCode_meta = "7";

            } else if (inputText2.contains("목포")) {
                sigunguCode_meta = "8";

            } else if (inputText2.contains("무안")) {
                sigunguCode_meta = "9";

            } else if (inputText2.contains("보성")) {
                sigunguCode_meta = "10";

            } else if (inputText2.contains("순천")) {
                sigunguCode_meta = "11";

            } else if (inputText2.contains("신안")) {
                sigunguCode_meta = "12";

            } else if (inputText2.contains("여수")) {
                sigunguCode_meta = "13";

            } else if (inputText2.contains("영광")) {
                sigunguCode_meta = "16";

            } else if (inputText2.contains("영암")) {
                sigunguCode_meta = "17";

            } else if (inputText2.contains("완도")) {
                sigunguCode_meta = "18";

            } else if (inputText2.contains("장성")) {
                sigunguCode_meta = "19";

            } else if (inputText2.contains("진흥")) {
                sigunguCode_meta = "20";

            } else if (inputText2.contains("진도")) {
                sigunguCode_meta = "21";

            } else if (inputText2.contains("함평")) {
                sigunguCode_meta = "22";

            } else if (inputText2.contains("해남")) {
                sigunguCode_meta = "23";

            } else if (inputText2.contains("화순")) {
                sigunguCode_meta = "24";

            }
        } else if (inputText1.contains("제주")) {
            areaCode_meta = "39";
            if (inputText2.contains("남제주")) {
                sigunguCode_meta = "1";

            } else if (inputText2.contains("북제주")) {
                sigunguCode_meta = "2";

            } else if (inputText2.contains("서귀포")) {
                sigunguCode_meta = "3";

            } else if (inputText2.contains("제주시")) {
                sigunguCode_meta = "4";

            }
        }

        //Toast.makeText(SubMenu.this,"#"+areaCode_meta+"#"+sigunguCode_meta+"#",Toast.LENGTH_LONG).show();
    }
    }
