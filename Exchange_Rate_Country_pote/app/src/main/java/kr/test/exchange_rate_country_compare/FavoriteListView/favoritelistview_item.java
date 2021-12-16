package kr.test.exchange_rate_country_compare.FavoriteListView;

import android.graphics.drawable.Drawable;

public class favoritelistview_item {

    Drawable favorite_flag;
    String favorite_country_name;
    String favorite_bank_name;
    String favorite_exchange;
    String favorite_susuryo;

    /**
     *  아래 메소드들은 쉽게 아이템을 추가하고 불러오기 위한 메소드이다.
     */
    public void setFavorite_flag(Drawable favorite_flag){
        this.favorite_flag = favorite_flag;
    }

    public Drawable getFavorite_flag() {
        return this.favorite_flag;
    }

    public void setFavorite_country_name(String favorite_country_name) {
        this. favorite_country_name = favorite_country_name;
    }

    public String getFavorite_country_name(){
        return this.favorite_country_name;
    }

    public void setFavorite_bank_name(String favorite_bank_name){
        this.favorite_bank_name = favorite_bank_name;
    }

    public String getFavorite_bank_name(){
        return this.favorite_bank_name;
    }

    public void setFavorite_exchange(String favorite_exchange){
        this.favorite_exchange = favorite_exchange;
    }

    public String getFavorite_exchange(){
        return this.favorite_exchange;
    }

    public void setFavorite_susuryo(String favorite_susuryo){
        this.favorite_susuryo = favorite_susuryo;
    }

    public String getFavorite_susuryo(){
        return this.favorite_susuryo;
    }
}
