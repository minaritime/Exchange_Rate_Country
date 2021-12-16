package kr.test.exchange_rate_country_compare.JSoupGetData.NongHyup;

import java.util.ArrayList;

public class NongHyupVariable {

    public ArrayList<ArrayList<String>> lstNongHyupData = new ArrayList<ArrayList<String>>();    // 농협은행의 API를 모두 담는 이중리스트

    private static NongHyupVariable instance;
    public static NongHyupVariable ins() {
        if (instance == null) {
            instance = new NongHyupVariable();
        }
        return instance;
    }
}
