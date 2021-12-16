package kr.test.exchange_rate_country_compare;

import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * - getFlag()
 * 나라 이름을 받아 국기를 반환
 *
 * - getBank()
 * 영어이름인 은행을 한국어로 바꿔줌   (ex. KEBhana -> return "KEB하나은행";)
 *
 * - getAbbreviation()
 * 나라 이름을 받아 외화 줄임말로 반환함    (ex. 미국 -> return "USD";)
 *
 */
public class Abbreviation {

    AppCompatActivity aAbbreviation;

    public Abbreviation(AppCompatActivity appCompatActivity){
        aAbbreviation = appCompatActivity;
    }

    public String getBank(String bankname){
        if(bankname.equals("Citi")){
            return "한국씨티은행";
        }
        else if(bankname.equals("IBK")){
            return "기업은행";
        }
        else if(bankname.equals("KBkookmin")){
            return "KB국민은행";
        }
        else if(bankname.equals("KEBhana")){
            return "KEB하나은행";
        }
        else if(bankname.equals("NongHyup")){
            return "NH농협은행";
        }
        else if(bankname.equals("Shinhan")){
            return "신한은행";
        }
        else if(bankname.equals("Suhyup")){
            return "수협은행";
        }
        else if(bankname.equals("Woori")){
            return "우리은행";
        }

        return "";
    }

    public String getAbbreviation(String country_name){
        if(country_name.equals("중국")) {
            return "CNY";
        }
        else if(country_name.equals("일본")) {
            return "JPY";
        }
        else if(country_name.equals("미국")){
            return "USD";
        }
        else if(country_name.equals("유럽연합")){
            return "EUR";
        }
        else if(country_name.equals("홍콩")) {
            return "HKD";
        }
        else if(country_name.equals("대만")){
            return "TWD";
        }
        else if(country_name.equals("싱가폴")){
            return "SGD";
        }
        else if(country_name.equals("태국")){
            return "THB";
        }
        else if(country_name.equals("필리핀")){
            return "PHP";
        }
        else if(country_name.equals("베트남")){
            return "VND";
        }
        else if(country_name.equals("영국")){
            return "GBP";
        }
        else if(country_name.equals("호주")){
            return "AUD";
        }

        else if(country_name.equals("캐나다")){
            return "CAD";
        }
        else if(country_name.equals("브라질")){
            return "BRL";
        }
        else if(country_name.equals("칠레")){
            return "CLP";
        }
        else if(country_name.equals("멕시코")){
            return "MXN";
        }

        else if(country_name.equals("뉴질랜드")){
            return "NZD";
        }

        else if(country_name.equals("인도네시아")){
            return "IDR";
        }
        else if(country_name.equals("말레이시아")){
            return "MYR";
        }
        else if(country_name.equals("터키")){
            return "TRY";
        }
        else if(country_name.equals("인도")){
            return "INR";
        }
        else if(country_name.equals("몽골")){
            return "MNT";
        }
        else if(country_name.equals("이스라엘")){
            return "ILS";
        }
        else if(country_name.equals("사우디")){
            return "SAR";
        }
        else if(country_name.equals("쿠웨이트")){
            return "KWD";
        }
        else if(country_name.equals("바레인")){
            return "BHD";
        }
        else if(country_name.equals("U.A.E")){
            return "AED";
        }
        else if(country_name.equals("카자흐스탄")){
            return "KZT";
        }
        else if(country_name.equals("카타르")){
            return "QAR";
        }
        else if(country_name.equals("파키스탄")){
            return "PKR";
        }
        else if(country_name.equals("방글라데시")){
            return "BDT";
        }
        else if(country_name.equals("브루나이")){
            return "BND";
        }
        else if(country_name.equals("오만")){
            return "OMR";
        }
        else if(country_name.equals("요르단")){
            return "JOD";
        }
        else if(country_name.equals("스위스")){
            return "CHF";
        }

        else if(country_name.equals("러시아")){
            return "RUB";
        }
        else if(country_name.equals("스웨덴")){
            return "SEK";
        }
        else if(country_name.equals("덴마크")){
            return "DKK";
        }
        else if(country_name.equals("노르웨이")){
            return "NOK";
        }
        else if(country_name.equals("헝가리")){
            return "HUF";
        }
        else if(country_name.equals("체코")){
            return "CZK";
        }
        else if(country_name.equals("폴란드")){
            return "PLN";
        }

        else if(country_name.equals("남아공")){
            return "ZAR";
        }
        else if(country_name.equals("이집트")){
            return "EGP";
        }

        return "";
    }

    public Drawable getFlag(String country_name){
        if(country_name.equals("중국")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.china);
        }
        else if(country_name.equals("일본")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.japan);
        }
        else if(country_name.equals("미국")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.usa);
        }
        else if(country_name.equals("유럽연합")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.eur);
        }
        else if(country_name.equals("홍콩")) {
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.hongkong);
        }
        else if(country_name.equals("대만")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.taiwan);
        }
        else if(country_name.equals("싱가폴")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.singapore);
        }
        else if(country_name.equals("태국")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.thailand);
        }
        else if(country_name.equals("필리핀")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.philippines);
        }
        else if(country_name.equals("베트남")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.vietnam);
        }
        else if(country_name.equals("영국")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.england);
        }
        else if(country_name.equals("호주")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.saudiarabia);
        }
        else if(country_name.equals("캐나다")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.canada);
        }
        else if(country_name.equals("브라질")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.brazil);
        }
        else if(country_name.equals("칠레")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.chile);
        }
        else if(country_name.equals("멕시코")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.mexico);
        }
        else if(country_name.equals("뉴질랜드")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.newziland);
        }
        else if(country_name.equals("인도네시아")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.indonesia);
        }
        else if(country_name.equals("말레이시아")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.malaysia);
        }
        else if(country_name.equals("터키")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.turkey);
        }
        else if(country_name.equals("인도")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.india);
        }
        else if(country_name.equals("몽골")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.mongolia);
        }
        else if(country_name.equals("이스라엘")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.israel);
        }
        else if(country_name.equals("사우디")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.saudiarabia);
        }
        else if(country_name.equals("쿠웨이트")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.kuwait);
        }
        else if(country_name.equals("바레인")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.bahrain);
        }
        else if(country_name.equals("U.A.E")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.arabemirates);
        }
        else if(country_name.equals("카자흐스탄")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.kazakhstan);
        }
        else if(country_name.equals("카타르")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.qatar);
        }
        else if(country_name.equals("파키스탄")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.pakistan);
        }
        else if(country_name.equals("방글라데시")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.bangladesh);
        }
        else if(country_name.equals("브루나이")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.brunai);
        }
        else if(country_name.equals("오만")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.oman);
        }
        else if(country_name.equals("요르단")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.jordan);
        }
        else if(country_name.equals("스위스")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.swiss);
        }
        else if(country_name.equals("러시아")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.rusia);
        }
        else if(country_name.equals("스웨덴")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.sweden);
        }
        else if(country_name.equals("덴마크")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.denmark);
        }
        else if(country_name.equals("노르웨이")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.norway);
        }
        else if(country_name.equals("헝가리")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.hungary);
        }
        else if(country_name.equals("체코")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.czech);
        }
        else if(country_name.equals("폴란드")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.poland);
        }
        else if(country_name.equals("남아공")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.southafrica);
        }
        else if(country_name.equals("이집트")){
            return ContextCompat.getDrawable(aAbbreviation,R.drawable.egypt);
        }

        return ContextCompat.getDrawable(aAbbreviation,R.drawable.ic_launcher_background);
    }

}
