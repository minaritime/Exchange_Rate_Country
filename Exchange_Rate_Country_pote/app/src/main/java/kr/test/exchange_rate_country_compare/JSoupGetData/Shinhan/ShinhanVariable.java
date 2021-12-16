package kr.test.exchange_rate_country_compare.JSoupGetData.Shinhan;

import java.util.ArrayList;

public class ShinhanVariable {

    public ArrayList<ArrayList<String>> lstShinhanData = new ArrayList<ArrayList<String>>();    // 신한은행의 API를 모두 담는 이중리스트

    private static ShinhanVariable instance;
    public static ShinhanVariable ins() {
        if (instance == null) {
            instance = new ShinhanVariable();
        }
        return instance;
    }
}
