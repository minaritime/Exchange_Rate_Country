package kr.test.exchange_rate_country_compare;

import java.util.ArrayList;

public class Define {

    public String country_list = "";        // 외화살때 즐겨찾기되어있는 나라와은행을 모은변수    (리스트에서 즐겨찾기된 항목에 별표를 추가하기위해 존재하는 변수)
    public String sellcountry_list = "";    // 외화팔때 즐겨찾기되어있는 나라와은행을 모은변수
    public String sendcountry_list = "";    // 외화송금때 즐겨찾기되어있는 나라와은행을 모은변수

    public String checkposition = "";       // 외화살때 즐겨찾기되어있는 나라의 포지션을 모은변수
    public String sellcheckposition = "";   // 외화팔때 즐겨찾기되어있는 나라의 포지션을 모은변수
    public String sendcheckposition = "";   // 외화송금때 즐겨찾기되어있는 나라의 포지션을 모은변수

    // 리스트뷰가 변경될때 버튼 비활성화
    public boolean Fin = false;

    public int pos;     // 즐겨찾기할 나라의 포지션을 전달하기 위해 저장하는 변수
    public int delpos;  // 즐겨찾기에서 지울 나라의 포지션을 전달하기 위해 저장하는 변수

    public String Temp = "";        // 즐겨찾기할 나라의 이름과 은행을 함께 저장하는 변수
    public String posTemp = "";     // 즐겨찾기할 나라의 포지션을 저장하는 변수

    public ArrayList<ArrayList<String>> lstcountry = new ArrayList<ArrayList<String>>();    // 특정 은행의 API값을 모두 담는 변수

    public String status = "buymoney";  // 기본값을 외화 살때로 설정

    private static Define instance;
    public static Define ins(){
        if(instance == null){
            instance = new Define();
        }

        return instance;
    }
}
