package kr.test.exchange_rate_country_compare.JSoupGetData.StandardChartered;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class StandardCharteredMain{

    AppCompatActivity aStandardChartered;
    String sURLStandardChartered = "https://www.mibank.me/exchange/bank/index.php?search_code=023";

    public StandardCharteredMain(AppCompatActivity appCompatActivity) {
        aStandardChartered = appCompatActivity;
        new StandardCharteredJoup().execute(sURLStandardChartered);
    }

    // API 값이 이상하여 실제로는 쓰지 않는다.
    private class StandardCharteredJoup extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try{
                StandardCharteredVariable.ins().lstStandardCharteredData.clear();
                Document document = Jsoup.connect(strings[0].toString()).get(); // Web에서 내용을 가져온다
//                System.out.println("getData : " + document.toString());

                Elements element = document.select("body > div.container_sub_banks_saving > div.right_contents > div.box_contents1 > table > tbody");
                System.out.println("StandardCharteredJoup : " + element.text()); // 텍스트 물러오기

                String aa = element.text().replace(" MYR ", " ");

                System.out.println("바뀜 : " + aa);

                String sDataSplit[] = aa.split(" ");

                // 쓰기 편하게 값을 조정한다
                StandardCharteredVariable.ins().lstStandardCharteredData.clear();
                for(int i = 8 ; i < sDataSplit.length; i+=8) {
                    ArrayList<String> lstDataTemp = new ArrayList<String>();
                    for(int j = i-8 ; j <= i-1 ; j++) {
                        lstDataTemp.add(sDataSplit[j]);
                    }

                    StandardCharteredVariable.ins().lstStandardCharteredData.add(lstDataTemp);
                }

                for(int i = 0 ; i < StandardCharteredVariable.ins().lstStandardCharteredData.size() ; i++) {
                    System.out.println("국가 목록 : " + StandardCharteredVariable.ins().lstStandardCharteredData.get(i).get(0));

                }

                System.out.println("KBkookminVariable.ins().lstStandardCharteredData : " + StandardCharteredVariable.ins().lstStandardCharteredData);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
