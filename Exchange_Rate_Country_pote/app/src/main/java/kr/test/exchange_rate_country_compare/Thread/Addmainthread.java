package kr.test.exchange_rate_country_compare.Thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

import kr.test.exchange_rate_country_compare.Define;
import kr.test.exchange_rate_country_compare.JSoupGetData.Citi.CitiVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.IBK.IBKVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.KBkookmin.KBkookminVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.KEBhana.KEBhanaVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.NongHyup.NongHyupVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Shinhan.ShinhanVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.StandardChartered.StandardCharteredVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Suhyup.SuhyupVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Woori.WooriVariable;
import kr.test.exchange_rate_country_compare.MainListView.listview_adapter;
import kr.test.exchange_rate_country_compare.SetMain;

public class Addmainthread extends Thread {
    AppCompatActivity aaddmainthread;
    Handler handler;
    SetMain csetMain;

    public Addmainthread(AppCompatActivity appCompatActivity) {
        aaddmainthread = appCompatActivity;
        handler = new Handler();
        csetMain = new SetMain(aaddmainthread);
    }

    // 앱 실행시 계속해서 실행되는 코드
    @Override
    public void run() {
        super.run();
        while (true) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("쓰레드 진입 : " + Define.ins().Fin);
                        csetMain.Addcountry();      // 리스트 추가

                    } catch (OutOfMemoryError e) {
                        System.out.println("Memory leak");
                    } catch (Exception e) {

                    }
                }
            });
            try {
                // 만약 API가 하나라도 불러오지 못한 상태라면 3초 뒤에 위의 코드를 실행
                if(CitiVariable.ins().lstCitiData.size() == 0 ||
                IBKVariable.ins().lstIBKData.size() == 0 ||
                KBkookminVariable.ins().lstKookminData.size() == 0 ||
                KEBhanaVariable.ins().lstKEBhanaData.size() == 0 ||
                NongHyupVariable.ins().lstNongHyupData.size() == 0 ||
                ShinhanVariable.ins().lstShinhanData.size() == 0 ||
                SuhyupVariable.ins().lstSuhyupData.size() == 0 ||
                WooriVariable.ins().lstWooriData.size() == 0 ){
                    Thread.sleep(3000);
                }
                // 아니라면 1초마다 실행
                else {
                    Thread.sleep(1000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
