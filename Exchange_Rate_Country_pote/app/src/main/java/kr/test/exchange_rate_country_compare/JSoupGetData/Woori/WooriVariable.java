package kr.test.exchange_rate_country_compare.JSoupGetData.Woori;

import java.util.ArrayList;

public class WooriVariable {

    public ArrayList<ArrayList<String>> lstWooriData = new ArrayList<ArrayList<String>>();    // 우리은행의 API를 모두 담는 이중리스트

    private static WooriVariable instance;
    public static WooriVariable ins() {
        if (instance == null) {
            instance = new WooriVariable();
        }
        return instance;
    }
}