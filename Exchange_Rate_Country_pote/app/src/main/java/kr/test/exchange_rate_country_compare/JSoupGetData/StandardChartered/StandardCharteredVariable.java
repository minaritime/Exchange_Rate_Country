package kr.test.exchange_rate_country_compare.JSoupGetData.StandardChartered;

import java.util.ArrayList;

public class StandardCharteredVariable {

    public ArrayList<ArrayList<String>> lstStandardCharteredData = new ArrayList<ArrayList<String>>();    // 스탠다드차티드은행의 API를 모두 담는 이중리스트

    private static StandardCharteredVariable instance;
    public static StandardCharteredVariable ins() {
        if (instance == null) {
            instance = new StandardCharteredVariable();
        }
        return instance;
    }
}
