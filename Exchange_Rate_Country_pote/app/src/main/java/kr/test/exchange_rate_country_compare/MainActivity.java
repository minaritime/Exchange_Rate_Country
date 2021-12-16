package kr.test.exchange_rate_country_compare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kr.test.exchange_rate_country_compare.Thread.Addmainthread;

public class MainActivity extends AppCompatActivity {

    SetMain setMain;
    Addmainthread caddmainthread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setMain = new SetMain(this);    // 메인액티비티의 코드를 옮김 (주요 코드)
        caddmainthread = new Addmainthread(this);   // 쓰레드 설정
        caddmainthread.start(); // 쓰레드 시작
    }
}
