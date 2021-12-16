package kr.test.exchange_rate_country_compare.JSoupGetData.Suhyup;

import java.util.ArrayList;

public class SuhyupVariable {

    public ArrayList<ArrayList<String>> lstSuhyupData = new ArrayList<ArrayList<String>>();    // 수협은행의 API를 모두 담는 이중리스트

    private static SuhyupVariable instance;
    public static SuhyupVariable ins() {
        if (instance == null) {
            instance = new SuhyupVariable();
        }
        return instance;
    }
}
