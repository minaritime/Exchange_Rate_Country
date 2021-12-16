package kr.test.exchange_rate_country_compare.MainListView;

import android.graphics.drawable.Drawable;

public class listview_item {

    Drawable flag;
    String country_name;
    String abbreviation;
    String exchange_rate;
    String fees;
    boolean bfavorite;

    /**
     *  아래 메소드들은 쉽게 아이템을 추가하고 불러오기 위한 메소드이다.
     */
    public void setBfavorite(boolean bfavorite){
        this.bfavorite = bfavorite;
    }

    public boolean getBfavorite(){
        return this.bfavorite;
    }

    public void setflag(Drawable flag) {
        this.flag = flag;
    }

    public Drawable getflag() {
        return this.flag;
    }

    public void setcountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getcountry_name() {
        return this.country_name;
    }

    public void setabbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getabbreviation() {
        return this.abbreviation;
    }

    public void setexchange_rate(String exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    public String getexchange_rate() {
        return this.exchange_rate;
    }

    public void setfees(String fees) {
        this.fees = fees;
    }

    public String getfees() {
        return this.fees;
    }
}
