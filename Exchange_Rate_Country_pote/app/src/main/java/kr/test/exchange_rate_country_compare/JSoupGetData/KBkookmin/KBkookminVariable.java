package kr.test.exchange_rate_country_compare.JSoupGetData.KBkookmin;

import java.util.ArrayList;

public class KBkookminVariable {

    public ArrayList<ArrayList<String>> lstKookminData = new ArrayList<ArrayList<String>>();    // KB국민은행의 API를 모두 담는 이중리스트

    private static KBkookminVariable instance;
    public static KBkookminVariable ins() {
        if (instance == null) {
            instance = new KBkookminVariable();
        }
        return instance;
    }
}