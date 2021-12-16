package kr.test.exchange_rate_country_compare.JSoupGetData.Citi;

import java.util.ArrayList;

public class CitiVariable {

    public ArrayList<ArrayList<String>> lstCitiData = new ArrayList<ArrayList<String>>();       // 시티은행의 API를 모두 담는 이중리스트

    private static CitiVariable instance;
    public static CitiVariable ins() {
        if (instance == null) {
            instance = new CitiVariable();
        }
        return instance;
    }
}