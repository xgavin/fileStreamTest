package com.example.gavin.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button writeButton;
    private Button readButton;
    private EditText editText1;
    private EditText editText2;
    private Button sdcard;
    SharedPreferences sharedPreferences = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        writeButton = (Button)findViewById(R.id.writeButton);
        readButton = (Button)findViewById(R.id.readButton);
        editText1 = (EditText)findViewById(R.id.edit1);
        editText2 = (EditText)findViewById(R.id.edit2);
        sdcard = (Button)findViewById(R.id.sdcard);
        final Intent intent = new Intent();
        intent.setClass(MainActivity.this, sdcardTest.class);
        sdcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        /*
        //获得SharedPreferences的编辑者
        final SharedPreferences.Editor editor;
        //创建SP文件
        sharedPreferences = getSharedPreferences("testSP",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = sharedPreferences.getString("time", null);
                int randNum = sharedPreferences.getInt("randNum", 0);
                String result = String.format(time + randNum);
                if(result == null)
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                }

            }
        });

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + "hh:mm:ss");
                //存入数据
                editor.putString("time", sdf.format(new Date()));
                editor.putInt("randNum", (int) (Math.random() * 100));
                //提交数据
                editor.commit();
            }
        });*/

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    FileOutputStream fileOutputStream = openFileOutput("testFOS",MODE_APPEND);
                    PrintStream printStream = new PrintStream(fileOutputStream);
                    printStream.println(editText1.getText().toString());
                    printStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                editText1.setText("");
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileInputStream fileInputStream = openFileInput("testFOS");
                    byte[] buff = new byte[1024];
                    //StringBuilder stringBuilder = new StringBuilder("");
                    int hasHead = 0;
                    while ((hasHead = fileInputStream.read(buff)) > 0){
                        editText2.setText(new String(buff, 0, hasHead));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
