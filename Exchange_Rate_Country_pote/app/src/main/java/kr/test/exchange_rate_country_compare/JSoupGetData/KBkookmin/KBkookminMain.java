package kr.test.exchange_rate_country_compare.JSoupGetData.KBkookmin;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class KBkookminMain {

    AppCompatActivity aKBkookmin;
    String sURLKBkookmin = "https://www.mibank.me/exchange/bank/index.php?search_code=004";

    public KBkookminMain(AppCompatActivity appCompatActivity) {
        aKBkookmin = appCompatActivity;
        new KBkookminJoup().execute(sURLKBkookmin);
    }

    private class KBkookminJoup extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                KBkookminVariable.ins().lstKookminData.clear();
                Document document = Jsoup.connect(strings[0].toString()).get(); // Web에서 내용을 가져온다
//                System.out.println("getData : " + document.toString());

                Elements element = document.select("body > div.container_sub_banks_saving > div.right_contents > div.box_contents1 > table > tbody");
                System.out.println("KBkookminJoup : " + element.text()); // 텍스트 불러오기

                String sDataSplit[] = element.text().split(" ");

                // 쓰기 편하게 값을 조정한다
                for(int i = 8 ; i <= sDataSplit.length; i+=8) {
                    ArrayList<String> lstDataTemp = new ArrayList<String>();
                    for(int j = i-8 ; j <= i-1 ; j++) {
                        lstDataTemp.add(sDataSplit[j]);
                    }

                    KBkookminVariable.ins().lstKookminData.add(lstDataTemp);
                }

                for(int i = 0 ; i < KBkookminVariable.ins().lstKookminData.size() ; i++) {
                    System.out.println("국가 목록 : " + KBkookminVariable.ins().lstKookminData.get(i).get(0));
                }

                System.out.println("KBkookminVariable.ins().lstKookminData : " + KBkookminVariable.ins().lstKookminData);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}