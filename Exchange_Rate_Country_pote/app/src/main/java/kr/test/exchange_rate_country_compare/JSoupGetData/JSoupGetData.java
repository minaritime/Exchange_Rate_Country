package kr.test.exchange_rate_country_compare.JSoupGetData;

import androidx.appcompat.app.AppCompatActivity;

import kr.test.exchange_rate_country_compare.JSoupGetData.Citi.CitiMain;
import kr.test.exchange_rate_country_compare.JSoupGetData.IBK.IBKMain;
import kr.test.exchange_rate_country_compare.JSoupGetData.KBkookmin.KBkookminMain;
import kr.test.exchange_rate_country_compare.JSoupGetData.KEBhana.KEBhanaMain;
import kr.test.exchange_rate_country_compare.JSoupGetData.NongHyup.NongHyupMain;
import kr.test.exchange_rate_country_compare.JSoupGetData.Shinhan.ShinhanMain;
import kr.test.exchange_rate_country_compare.JSoupGetData.StandardChartered.StandardCharteredMain;
import kr.test.exchange_rate_country_compare.JSoupGetData.Suhyup.SuhyupMain;
import kr.test.exchange_rate_country_compare.JSoupGetData.Woori.WooriMain;

public class JSoupGetData {
    AppCompatActivity aJSoupGetData;

    KBkookminMain cKBkookmin;
    KEBhanaMain cKEBhana;
    WooriMain cWoori;
    NongHyupMain cNongHyup;
    IBKMain cIBK;
//    StandardCharteredMain cSC;
    CitiMain cCiti;
    ShinhanMain cShinhan;
    SuhyupMain cSuhyup;

    public JSoupGetData(AppCompatActivity appCompatActivity){
        aJSoupGetData = appCompatActivity;
        setJSoupGetData();
    }

    public void setJSoupGetData(){
        cKBkookmin = new KBkookminMain(aJSoupGetData);
        cKEBhana = new KEBhanaMain(aJSoupGetData);
        cWoori = new WooriMain(aJSoupGetData);
        cNongHyup = new NongHyupMain(aJSoupGetData);
        cIBK = new IBKMain(aJSoupGetData);
//        cSC = new StandardCharteredMain(aJSoupGetData);
        cCiti = new CitiMain(aJSoupGetData);
        cShinhan = new ShinhanMain(aJSoupGetData);
        cSuhyup = new SuhyupMain(aJSoupGetData);
    }


}
