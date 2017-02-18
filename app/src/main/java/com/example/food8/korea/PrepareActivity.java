package com.example.food8.korea;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.tozzim.korea.R;

public class PrepareActivity extends AppCompatActivity {

    private RadioButton optionWomen;
    private RadioButton optionMen;
    private RadioButton optionWing;
    private RadioGroup radioGroup;
    Spinner loSpin1;
    Spinner loSpin2;
    String channel_loSpin1 ;
    String channel_loSpin2 ;

    boolean flag_channel_passible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        final Button btn2 = (Button) findViewById(R.id.btn_under3);
        btn2.setSelected(true);

        channel_loSpin1 = "";
        channel_loSpin2 = "";

        loSpin1 = (Spinner) findViewById(R.id.localSelector1);
        localSpinner1(R.array.city_array);
        loSpin2 = (Spinner) findViewById(R.id.localSelector2);
        loSpin1.setOnItemSelectedListener(localSpinSelector);


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

                    flag_channel_passible=true;

                    break;
                case 2:
                    localSpinner2(R.array.incheon_array);
                    flag_channel_passible=true;
                    break;
                case 3:
                    localSpinner2(R.array.daejeon_array);
                    flag_channel_passible=true;
                    break;
                case 4:
                    localSpinner2(R.array.daegu_array);
                    flag_channel_passible=true;
                    break;
                case 5:
                    localSpinner2(R.array.gwangju_array);
                    flag_channel_passible=true;
                    break;
                case 6:
                    localSpinner2(R.array.busan_array);
                    flag_channel_passible=true;
                    break;
                case 7:
                    localSpinner2(R.array.ulsan_array);
                    flag_channel_passible=true;
                    break;
                case 8:
                    localSpinner2(R.array.sejong_array);
                    flag_channel_passible=true;
                    break;
                case 9:
                    localSpinner2(R.array.gyunggi_array);
                    flag_channel_passible=true;
                    break;
                case 10:
                    localSpinner2(R.array.gangwon_array);
                    flag_channel_passible=true;
                    break;
                case 11:
                    localSpinner2(R.array.norchung_array);
                    flag_channel_passible=true;
                    break;
                case 12:
                    localSpinner2(R.array.souchung_array);
                    flag_channel_passible=true;
                    break;
                case 13:
                    localSpinner2(R.array.norgyung_array);
                    flag_channel_passible=true;
                    break;
                case 14:
                    localSpinner2(R.array.sougyung_array);
                    flag_channel_passible=true;
                    break;
                case 15:
                    localSpinner2(R.array.norjun_array);
                    flag_channel_passible=true;
                    break;
                case 16:
                    localSpinner2(R.array.soujun_array);
                    flag_channel_passible=true;
                    break;
                case 17:
                    localSpinner2(R.array.jeju_array);
                    flag_channel_passible=true;
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


    public void clickComfirmUsername(View view) {

        channel_loSpin1 = loSpin1.getSelectedItem().toString();
        channel_loSpin2 = loSpin2.getSelectedItem().toString();

        if(flag_channel_passible==true) {
            Intent intent = new Intent(PrepareActivity.this, ChatActivity.class);
            startActivity(intent);

            intent.putExtra("values", channel_loSpin1 + " " + channel_loSpin2);
            startActivity(intent);
        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(PrepareActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage("지역을 모두 선택해주셔야 합니다.");
                alert.show();
        }

    }

    public void mainClick(View v){
        Button newButton = (Button) v;
        Button btn1 = (Button) findViewById(R.id.btn_under1);
        Button btn3 = (Button) findViewById(R.id.btn_under3);
        if(newButton == btn1) {
            Intent intent = new Intent(this,MainMenu.class);
            startActivity(intent);
            finish();
        }
        else if(newButton == btn3){
            newButton.setSelected(true);
            btn1.setSelected(false);
        }
    }
}