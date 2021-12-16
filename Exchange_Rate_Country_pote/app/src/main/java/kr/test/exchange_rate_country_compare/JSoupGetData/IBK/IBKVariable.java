package kr.test.exchange_rate_country_compare.JSoupGetData.IBK;

import java.util.ArrayList;

public class IBKVariable {

    public ArrayList<ArrayList<String>> lstIBKData = new ArrayList<ArrayList<String>>();       // 기업은행의 API를 모두 담는 이중리스트

    private static IBKVariable instance;
    public static IBKVariable ins() {
        if (instance == null) {
            instance = new IBKVariable();
        }
        return instance;
    }
}