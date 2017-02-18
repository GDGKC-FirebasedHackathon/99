package com.example.food8.korea;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tozzim.korea.R;


public class MainMenu extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        final Button btn1 = (Button) findViewById(R.id.btn_under1);
        btn1.setSelected(true);
    }

    public void mainClick(View v){
        Button newButton = (Button) v;
        Button btn1 = (Button) findViewById(R.id.btn_under1);
        Button btn3 = (Button) findViewById(R.id.btn_under3);
        if(newButton == btn1) {
            newButton.setSelected(true);
            btn3.setSelected(false);
        }
        else if(newButton == btn3){
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public  void onClick01(View view) {
        Intent intent = new Intent(this, SubMenu.class);
        intent.putExtra("values",1);
        startActivity(intent);

    }
    public  void onClick02(View view) {
        Intent intent = new Intent(this, SubMenu.class);
        intent.putExtra("values",2);
        startActivity(intent);

    }
    public  void onClick03(View view) {
        Intent intent = new Intent(this, SubMenu.class);
        intent.putExtra("values",3);
        startActivity(intent);

    }
    public  void onClick04(View view) {
        Intent intent = new Intent(this, SubMenu.class);
        intent.putExtra("values",4);
        startActivity(intent);

    }
    public  void onClick05(View view) {
        Intent intent = new Intent(this, SubMenu.class);
        intent.putExtra("values",5);
        startActivity(intent);
    }
    public  void onClick06(View view) {
        Intent intent = new Intent(this, SubMenu.class);
        intent.putExtra("values",6);
        startActivity(intent);
    }
}

