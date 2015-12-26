package com.example.gavin.myapplication;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.RandomAccess;

public class sdcardTest extends AppCompatActivity {

    private Button writeButton;
    private Button readButton;
    private EditText editText1;
    private EditText editText2;
    private TextView textView;
    private String FILE_NAME = "/sdcardTest.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard_test);
        writeButton = (Button)findViewById(R.id.writeButton);
        readButton = (Button)findViewById(R.id.readButton);
        editText1 = (EditText)findViewById(R.id.edit1);
        editText2 = (EditText)findViewById(R.id.edit2);
        textView = (TextView)findViewById(R.id.text);
        //读写sdCard之前先设置读写权限
        // <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"></uses-permission>
        //<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        //获取sd卡的目录
                        File dir = Environment.getExternalStorageDirectory();
                        //创建文件
                        File test = new File(dir.getCanonicalPath() + FILE_NAME);
                        //已文件创建raf文件对象
                        textView.setText(test.toString());
                        RandomAccessFile randomAccessFile = new RandomAccessFile(test, "rw");
                        randomAccessFile.seek(test.length());
                        randomAccessFile.write(editText1.getText().toString().getBytes());
                        randomAccessFile.close();
                        Toast.makeText(sdcardTest.this, "Write Successfully", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(sdcardTest.this, "SDcard error", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        File file = Environment.getExternalStorageDirectory() ;
                        String path = file.getCanonicalPath() + FILE_NAME;

                        FileInputStream fileInputStream = new FileInputStream(path);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

                        StringBuilder stringBuilder = new StringBuilder("");
                        String line = null;
                        while((line = bufferedReader.readLine()) != null){
                            stringBuilder.append(line);
                        }
                        textView.setText(stringBuilder.toString());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

    }
}
