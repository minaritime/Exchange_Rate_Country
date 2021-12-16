package kr.test.exchange_rate_country_compare.JSoupGetData.KEBhana;

import java.util.ArrayList;

public class KEBhanaVariable {

    public ArrayList<ArrayList<String>> lstKEBhanaData = new ArrayList<ArrayList<String>>();    // KEB하나은행의 API를 모두 담는 이중리스트

    private static KEBhanaVariable instance;
    public static KEBhanaVariable ins() {
        if (instance == null) {
            instance = new KEBhanaVariable();
        }
        return instance;
    }

}