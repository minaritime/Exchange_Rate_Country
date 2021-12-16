package kr.test.exchange_rate_country_compare.JSoupGetData.Shinhan;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class ShinhanMain {
    AppCompatActivity aShinhan;
    String sURLShinhan = "https://www.mibank.me/exchange/bank/index.php?search_code=088";

    public ShinhanMain(AppCompatActivity appCompatActivity) {
        aShinhan = appCompatActivity;
        new ShinhanJoup().execute(sURLShinhan);
    }

    private class ShinhanJoup extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                ShinhanVariable.ins().lstShinhanData.clear();
                Document document = Jsoup.connect(strings[0].toString()).get(); // Web에서 내용을 가져온다
//                System.out.println("getData : " + document.toString());

                Elements element = document.select("body > div.container_sub_banks_saving > div.right_contents > div.box_contents1 > table > tbody");
                System.out.println("ShinhanJoup : " + element.text()); // 텍스트 불러오기

                String sDataSplit[] = element.text().split(" ");

                // 쓰기 편하게 값을 조정한다
                for(int i = 8 ; i <= sDataSplit.length; i+=8) {
                    ArrayList<String> lstDataTemp = new ArrayList<String>();
                    for(int j = i-8 ; j <= i-1 ; j++) {
                        lstDataTemp.add(sDataSplit[j]);
                    }

                    ShinhanVariable.ins().lstShinhanData.add(lstDataTemp);
                }

                for(int i = 0 ; i < ShinhanVariable.ins().lstShinhanData.size() ; i++) {
                    System.out.println("국가 목록 : " + ShinhanVariable.ins().lstShinhanData.get(i).get(0));

                }

                System.out.println("ShinhanVariable.ins().lstShinhanData : " + ShinhanVariable.ins().lstShinhanData);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
