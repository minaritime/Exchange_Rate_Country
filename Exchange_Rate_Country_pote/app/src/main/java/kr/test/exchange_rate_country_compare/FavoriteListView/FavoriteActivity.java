package kr.test.exchange_rate_country_compare.FavoriteListView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import kr.test.exchange_rate_country_compare.Abbreviation;
import kr.test.exchange_rate_country_compare.Define;
import kr.test.exchange_rate_country_compare.Dialog.DeleteDialog;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamCheckDir;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamCheckFile;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamRead;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamWrite;
import kr.test.exchange_rate_country_compare.JSoupGetData.Citi.CitiVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.IBK.IBKVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.KBkookmin.KBkookminVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.KEBhana.KEBhanaVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.NongHyup.NongHyupVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Shinhan.ShinhanVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Suhyup.SuhyupVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Woori.WooriVariable;
import kr.test.exchange_rate_country_compare.MainListView.listview_adapter;
import kr.test.exchange_rate_country_compare.MainListView.listview_item;
import kr.test.exchange_rate_country_compare.R;

public class FavoriteActivity extends AppCompatActivity {

    favoritelistview_adapter adapter;
    ListView mlist;
    Abbreviation cAbbreviation;
    FileIOStreamCheckDir cFileIOStreamCheckDir;
    FileIOStreamCheckFile cFileIOStreamCheckFile;
    FileIOStreamWrite cFileIOStreamWrite;
    FileIOStreamRead cFileIOStreamRead;
    String[] pos;           // 외화살때 환율 리스트 포지션값 모음
    String[] sendpos;       // 외화송금 환율 리스트 포지션값 모음
    String[] sellpos;       // 외화팔때 환율 리스트 포지션값 모음
    String delpos;          // 지울 포지션 값 삭제 후 파일에 다시 쓰기 위한 변수
    int delTemp;            // 지울 포지션 값을 저장할 변수
    String delcountry_name;     // 지울 나라 이름 값 삭제 후 파일에 다시 쓰기 위한 변수
    ArrayList<ArrayList<String>> blstData = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> slstData = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> dlstData = new ArrayList<ArrayList<String>>();

    DeleteDialog deleteDialog;

    /**
     * -즐겨찾기
     * 메인 화면의 즐겨찾기 버튼을 클릭시 이동하게되는 즐겨찾기 화면입니다.
     * <p>
     * -즐겨찾기리스트
     * 즐겨찾기가 되어있는 환율, 나라이름, 국기, 은행, 수수료 등을 한 눈에 볼 수 있습니다.
     * <p>
     * -삭제
     * 즐겨찾기 리스트 아이템 클릭 시 즐겨찾기에서 삭제할 것인지 묻는 커스텀다이얼로그가 팝업됩니다.
     * '네' 버튼을 누르면 메인 화면의 환율 리스트에서 현재 삭제할 예정인 나라 이름, 은행 이름을 비교하여 위치를 찾아 변수에 저장합니다.
     * 그리고 거래방식을 확인한 뒤 위치를 저장한 파일에서 제일 처음에 있는 해당 포지션값을 찾아 지우고 같은 방법으로 나라, 은행을 지웁니다.
     * '아니요' 버튼을 누르면 커스텀다이얼로그를 종료합니다.
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        cFileIOStreamCheckDir = new FileIOStreamCheckDir(this);
        cFileIOStreamCheckFile = new FileIOStreamCheckFile(this);
        cFileIOStreamRead = new FileIOStreamRead(this);
        cFileIOStreamWrite = new FileIOStreamWrite(this);

        cAbbreviation = new Abbreviation(this);
        mlist = (ListView) findViewById(R.id.favorite_country_list);
        adapter = new favoritelistview_adapter();
        mlist.setAdapter(adapter);
        deleteDialog = new DeleteDialog(this, this, adapter);

        try {
            System.out.println("status : " + Define.ins().status);

            // 외화살때라면
            if (Define.ins().status.equals("buymoney")) {
                Split(cFileIOStreamRead.readData("country_name"));  // 외화살때 즐겨찾기 파일을 2번 split
                pos = cFileIOStreamRead.readData("checkposition").split("#");   // 외화살때 즐겨찾기 포지션 파일을 split
                for (int i = 0; i < blstData.size(); i++) {
                    // (나라이름, 은행이름, 환율, 수수료)
                    // 은행에 따라 다른 API를 저장한 싱글톤을 불러 리스트를 추가한다.
                    if (blstData.get(i).get(1).equals("한국씨티은행")) {
                        adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), CitiVariable.ins().lstCitiData.get(Integer.valueOf(pos[i])).get(1), CitiVariable.ins().lstCitiData.get(Integer.valueOf(pos[i])).get(2));
                    } else if (blstData.get(i).get(1).equals("기업은행")) {
                        adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), IBKVariable.ins().lstIBKData.get(Integer.valueOf(pos[i])).get(1), IBKVariable.ins().lstIBKData.get(Integer.valueOf(pos[i])).get(2));
                    } else if (blstData.get(i).get(1).equals("KB국민은행")) {
                        adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(pos[i])).get(1), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(pos[i])).get(2));
                    } else if (blstData.get(i).get(1).equals("KEB하나은행")) {
                        adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(pos[i])).get(1), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(pos[i])).get(2));
                    } else if (blstData.get(i).get(1).equals("NH농협은행")) {
                        adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(pos[i])).get(1), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(pos[i])).get(2));
                    } else if (blstData.get(i).get(1).equals("신한은행")) {
                        adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(pos[i])).get(1), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(pos[i])).get(2));
                    } else if (blstData.get(i).get(1).equals("수협은행")) {
                        adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(pos[i])).get(1), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(pos[i])).get(2));
                    } else if (blstData.get(i).get(1).equals("우리은행")) {
                        adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), WooriVariable.ins().lstWooriData.get(Integer.valueOf(pos[i])).get(1), WooriVariable.ins().lstWooriData.get(Integer.valueOf(pos[i])).get(2));
                    }
                }
            }
            // 외화팔때라면
            if (Define.ins().status.equals("sellmoney")) {
                Split(cFileIOStreamRead.readData("sellcountry_name"));  // 외화팔때 즐겨찾기 파일을 2번 split
                sellpos = cFileIOStreamRead.readData("sellcheckposition").split("#");   // 외화팔때 즐겨찾기 포지션 파일을 split
                for (int i = 0; i < slstData.size(); i++) {
                    // (나라이름, 은행이름, 환율, 수수료)
                    // 은행에 따라 다른 API를 저장한 싱글톤을 불러 리스트를 추가한다.
                    if (slstData.get(i).get(1).equals("한국씨티은행")) {
                        adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), CitiVariable.ins().lstCitiData.get(Integer.valueOf(sellpos[i])).get(3), CitiVariable.ins().lstCitiData.get(Integer.valueOf(sellpos[i])).get(4));
                    } else if (slstData.get(i).get(1).equals("기업은행")) {
                        adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), IBKVariable.ins().lstIBKData.get(Integer.valueOf(sellpos[i])).get(3), IBKVariable.ins().lstIBKData.get(Integer.valueOf(sellpos[i])).get(4));
                    } else if (slstData.get(i).get(1).equals("KB국민은행")) {
                        adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(sellpos[i])).get(3), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(sellpos[i])).get(4));
                    } else if (slstData.get(i).get(1).equals("KEB하나은행")) {
                        adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(sellpos[i])).get(3), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(sellpos[i])).get(4));
                    } else if (slstData.get(i).get(1).equals("NH농협은행")) {
                        adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(sellpos[i])).get(3), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(sellpos[i])).get(4));
                    } else if (slstData.get(i).get(1).equals("신한은행")) {
                        adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(sellpos[i])).get(3), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(sellpos[i])).get(4));
                    } else if (slstData.get(i).get(1).equals("수협은행")) {
                        adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(sellpos[i])).get(3), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(sellpos[i])).get(4));
                    } else if (slstData.get(i).get(1).equals("우리은행")) {
                        adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), WooriVariable.ins().lstWooriData.get(Integer.valueOf(sellpos[i])).get(3), WooriVariable.ins().lstWooriData.get(Integer.valueOf(sellpos[i])).get(4));
                    }
                }
            }
            // 외화송금이라면
            if (Define.ins().status.equals("sendmoney")) {
                Split(cFileIOStreamRead.readData("sendcountry_name"));  // 외화송금 즐겨찾기 파일을 2번 split
                sendpos = cFileIOStreamRead.readData("sendcheckposition").split("#");   // 외화송금 즐겨찾기 포지션 파일을 split
                for (int i = 0; i < dlstData.size(); i++) {
                    // (나라이름, 은행이름, 환율, 수수료)
                    // 은행에 따라 다른 API를 저장한 싱글톤을 불러 리스트를 추가한다.
                    if (dlstData.get(i).get(1).equals("한국씨티은행")) {
                        adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), CitiVariable.ins().lstCitiData.get(Integer.valueOf(sendpos[i])).get(5), CitiVariable.ins().lstCitiData.get(Integer.valueOf(sendpos[i])).get(6));
                    } else if (dlstData.get(i).get(1).equals("기업은행")) {
                        adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), IBKVariable.ins().lstIBKData.get(Integer.valueOf(sendpos[i])).get(5), IBKVariable.ins().lstIBKData.get(Integer.valueOf(sendpos[i])).get(6));
                    } else if (dlstData.get(i).get(1).equals("KB국민은행")) {
                        adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(sendpos[i])).get(5), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(sendpos[i])).get(6));
                    } else if (dlstData.get(i).get(1).equals("KEB하나은행")) {
                        adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(sendpos[i])).get(5), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(sendpos[i])).get(6));
                    } else if (dlstData.get(i).get(1).equals("NH농협은행")) {
                        adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(sendpos[i])).get(5), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(sendpos[i])).get(6));
                    } else if (dlstData.get(i).get(1).equals("신한은행")) {
                        adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(sendpos[i])).get(5), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(sendpos[i])).get(6));
                    } else if (dlstData.get(i).get(1).equals("수협은행")) {
                        adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(sendpos[i])).get(5), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(sendpos[i])).get(6));
                    } else if (dlstData.get(i).get(1).equals("우리은행")) {
                        adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), WooriVariable.ins().lstWooriData.get(Integer.valueOf(sendpos[i])).get(5), WooriVariable.ins().lstWooriData.get(Integer.valueOf(sendpos[i])).get(6));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();    // 에러출력
        }

        System.out.println("buymoneypos : " + cFileIOStreamRead.readData("checkposition"));
        System.out.println("buymoneycountry_name : " + cFileIOStreamRead.readData("country_name"));

        // 즐겨찾기 리스트 아이템 클릭 이벤트
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Define.ins().delpos = position; // 클릭한 포지션 싱글톤에 저장
                deleteDialog.callFunction();    // 삭제를 할 것인지 묻는 다이얼로그를 호출
            }
        });
    }

    // split 2번하여 리스트이중배열을 만드는 함수
    public void Split(String getData) {
        if (!getData.equals("")) {
            // 최초값 : 미국#국민은행@일본#우리은행@중국#농협@
            String sTempArr[] = getData.split("@");
            for (int i = 0; i < sTempArr.length; i++) {
                String sTemp[] = sTempArr[i].split("#");
                ArrayList<String> lstTemp = new ArrayList<String>();
                for (int j = 0; j < sTemp.length; j++) {
                    lstTemp.add(sTemp[j]);
                }
                if (Define.ins().status.equals("buymoney")) {
                    blstData.add(lstTemp);
                }
                if (Define.ins().status.equals("sellmoney")) {
                    slstData.add(lstTemp);
                }
                if (Define.ins().status.equals("sendmoney")) {
                    dlstData.add(lstTemp);
                }
            }
        }
        System.out.println("blstData : " + blstData);
        System.out.println("slstData : " + slstData);
        System.out.println("dlstData : " + dlstData);
    }

    // 즐겨찾기 삭제하는 함수
    public void setDelete(int position) {
        // 외화살때라면
        if (Define.ins().status.equals("buymoney")) {
            favoritelistview_item list = adapter.bfitem.get(position);  // 선택된 나라의 정보를 넣는다
            System.out.println("지우기 시작");
            delpos = cFileIOStreamRead.readData("checkposition");
            delcountry_name = cFileIOStreamRead.readData("country_name");

            if (list.getFavorite_bank_name().equals("한국씨티은행")) {      // 선택된 나라의 은행이 시티은행이라면
                for (int i = 0; i < CitiVariable.ins().lstCitiData.size(); i++) {   // api값과 모두 비교
                    if (CitiVariable.ins().lstCitiData.get(i).get(0).equals(list.favorite_country_name)) {  // 같은 이름의 나라가 있다면
                        delTemp = i;    // 그 위치를 저장한다
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("기업은행")) {
                for (int i = 0; i < IBKVariable.ins().lstIBKData.size(); i++) {
                    if (IBKVariable.ins().lstIBKData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("KB국민은행")) {
                for (int i = 0; i < KBkookminVariable.ins().lstKookminData.size(); i++) {
                    if (KBkookminVariable.ins().lstKookminData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("KEB하나은행")) {
                for (int i = 0; i < KEBhanaVariable.ins().lstKEBhanaData.size(); i++) {
                    if (KEBhanaVariable.ins().lstKEBhanaData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("NH농협은행")) {
                for (int i = 0; i < NongHyupVariable.ins().lstNongHyupData.size(); i++) {
                    if (NongHyupVariable.ins().lstNongHyupData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("신한은행")) {
                for (int i = 0; i < ShinhanVariable.ins().lstShinhanData.size(); i++) {
                    if (ShinhanVariable.ins().lstShinhanData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("수협은행")) {
                for (int i = 0; i < SuhyupVariable.ins().lstSuhyupData.size(); i++) {
                    if (SuhyupVariable.ins().lstSuhyupData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("우리은행")) {
                for (int i = 0; i < WooriVariable.ins().lstWooriData.size(); i++) {
                    if (WooriVariable.ins().lstWooriData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }

            System.out.println("변경전 포지션 : " + cFileIOStreamRead.readData("checkposition"));
            System.out.println("변경전 나라이름 : " + cFileIOStreamRead.readData("country_name"));
            System.out.println("변경해야하는나라이름 : " + list.getFavorite_country_name() + "#" + list.getFavorite_bank_name() + "@");

            delpos = delpos.replaceFirst(delTemp + "#", "");    // 지울 포지션을 찾아 replace한다.
            // 지울 나라이름을 찾아 replace한다.
            delcountry_name = delcountry_name.replaceFirst(list.getFavorite_country_name() + "#" + list.getFavorite_bank_name() + "@", "");

            // 바꾼 값을 파일에 저장한다.
            cFileIOStreamWrite.writeData("checkposition", delpos);
            cFileIOStreamWrite.writeData("country_name", delcountry_name);

            // 바꾼 값을 싱글톤에 저장한다.
            Define.ins().checkposition = delpos;
            Define.ins().country_list = delcountry_name;

            System.out.println("변경된 포지션 : " + cFileIOStreamRead.readData("checkposition"));
            System.out.println("변경된 나라이름 : " + cFileIOStreamRead.readData("country_name"));

            // 중복 추가 방지
            adapter.bfitem.clear();
            blstData.clear();

            Split(cFileIOStreamRead.readData("country_name")); // 외화살때 즐겨찾기 파일을 2번 split
            pos = cFileIOStreamRead.readData("checkposition").split("#");   // 외화살때 즐겨찾기 포지션 파일을 split

            for (int i = 0; i < blstData.size(); i++) {
                // (나라이름, 은행이름, 환율, 수수료)
                // 은행에 따라 다른 API를 저장한 싱글톤을 불러 리스트를 추가한다.
                if (blstData.get(i).get(1).equals("한국씨티은행")) {
                    adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), CitiVariable.ins().lstCitiData.get(Integer.valueOf(pos[i])).get(1), CitiVariable.ins().lstCitiData.get(Integer.valueOf(pos[i])).get(2));
                } else if (blstData.get(i).get(1).equals("기업은행")) {
                    adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), IBKVariable.ins().lstIBKData.get(Integer.valueOf(pos[i])).get(1), IBKVariable.ins().lstIBKData.get(Integer.valueOf(pos[i])).get(2));
                } else if (blstData.get(i).get(1).equals("KB국민은행")) {
                    adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(pos[i])).get(1), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(pos[i])).get(2));
                } else if (blstData.get(i).get(1).equals("KEB하나은행")) {
                    adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(pos[i])).get(1), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(pos[i])).get(2));
                } else if (blstData.get(i).get(1).equals("NH농협은행")) {
                    adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(pos[i])).get(1), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(pos[i])).get(2));
                } else if (blstData.get(i).get(1).equals("신한은행")) {
                    adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(pos[i])).get(1), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(pos[i])).get(2));
                } else if (blstData.get(i).get(1).equals("수협은행")) {
                    adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(pos[i])).get(1), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(pos[i])).get(2));
                } else if (blstData.get(i).get(1).equals("우리은행")) {
                    adapter.additem(cAbbreviation.getFlag(blstData.get(i).get(0)), blstData.get(i).get(0), blstData.get(i).get(1), WooriVariable.ins().lstWooriData.get(Integer.valueOf(pos[i])).get(1), WooriVariable.ins().lstWooriData.get(Integer.valueOf(pos[i])).get(2));
                }
            }
        }
        // 아래부터는 위와 같은 방식(외화팔때, 외화송금)
        else if (Define.ins().status.equals("sellmoney")) {
            favoritelistview_item list = adapter.sfitem.get(position);
            System.out.println("지우기 시작");
            delpos = cFileIOStreamRead.readData("sellcheckposition");
            delcountry_name = cFileIOStreamRead.readData("sellcountry_name");

            if (list.getFavorite_bank_name().equals("한국씨티은행")) {
                for (int i = 0; i < CitiVariable.ins().lstCitiData.size(); i++) {
                    if (CitiVariable.ins().lstCitiData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("기업은행")) {
                for (int i = 0; i < IBKVariable.ins().lstIBKData.size(); i++) {
                    if (IBKVariable.ins().lstIBKData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("KB국민은행")) {
                for (int i = 0; i < KBkookminVariable.ins().lstKookminData.size(); i++) {
                    if (KBkookminVariable.ins().lstKookminData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("KEB하나은행")) {
                for (int i = 0; i < KEBhanaVariable.ins().lstKEBhanaData.size(); i++) {
                    if (KEBhanaVariable.ins().lstKEBhanaData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("NH농협은행")) {
                for (int i = 0; i < NongHyupVariable.ins().lstNongHyupData.size(); i++) {
                    if (NongHyupVariable.ins().lstNongHyupData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("신한은행")) {
                for (int i = 0; i < ShinhanVariable.ins().lstShinhanData.size(); i++) {
                    if (ShinhanVariable.ins().lstShinhanData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("수협은행")) {
                for (int i = 0; i < SuhyupVariable.ins().lstSuhyupData.size(); i++) {
                    if (SuhyupVariable.ins().lstSuhyupData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("우리은행")) {
                for (int i = 0; i < WooriVariable.ins().lstWooriData.size(); i++) {
                    if (WooriVariable.ins().lstWooriData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            delpos = delpos.replaceFirst(delTemp + "#", "");
            delcountry_name = delcountry_name.replaceFirst(list.getFavorite_country_name() + "#" + list.getFavorite_bank_name() + "@", "");

            cFileIOStreamWrite.writeData("sellcheckposition", delpos);
            cFileIOStreamWrite.writeData("sellcountry_name", delcountry_name);

            Define.ins().sellcheckposition = delpos;
            Define.ins().sellcountry_list = delcountry_name;

            System.out.println("변경된 포지션 : " + cFileIOStreamRead.readData("sellcheckposition"));
            System.out.println("변경된 나라이름 : " + cFileIOStreamRead.readData("sellcountry_name"));

            adapter.sfitem.clear();
            slstData.clear();
            Split(cFileIOStreamRead.readData("sellcountry_name"));
            sellpos = cFileIOStreamRead.readData("sellcheckposition").split("#");

            for (int i = 0; i < slstData.size(); i++) {
                // (나라이름, 은행이름, 환율, 수수료)
                if (slstData.get(i).get(1).equals("한국씨티은행")) {
                    adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), CitiVariable.ins().lstCitiData.get(Integer.valueOf(sellpos[i])).get(3), CitiVariable.ins().lstCitiData.get(Integer.valueOf(sellpos[i])).get(4));
                } else if (slstData.get(i).get(1).equals("기업은행")) {
                    adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), IBKVariable.ins().lstIBKData.get(Integer.valueOf(sellpos[i])).get(3), IBKVariable.ins().lstIBKData.get(Integer.valueOf(sellpos[i])).get(4));
                } else if (slstData.get(i).get(1).equals("KB국민은행")) {
                    adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(sellpos[i])).get(3), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(sellpos[i])).get(4));
                } else if (slstData.get(i).get(1).equals("KEB하나은행")) {
                    adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(sellpos[i])).get(3), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(sellpos[i])).get(4));
                } else if (slstData.get(i).get(1).equals("NH농협은행")) {
                    adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(sellpos[i])).get(3), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(sellpos[i])).get(4));
                } else if (slstData.get(i).get(1).equals("신한은행")) {
                    adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(sellpos[i])).get(3), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(sellpos[i])).get(4));
                } else if (slstData.get(i).get(1).equals("수협은행")) {
                    adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), slstData.get(i).get(1), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(sellpos[i])).get(3), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(sellpos[i])).get(4));
                } else if (slstData.get(i).get(1).equals("우리은행")) {
                    adapter.additem(cAbbreviation.getFlag(slstData.get(i).get(0)), slstData.get(i).get(0), cAbbreviation.getBank(slstData.get(i).get(1)), WooriVariable.ins().lstWooriData.get(Integer.valueOf(sellpos[i])).get(3), WooriVariable.ins().lstWooriData.get(Integer.valueOf(sellpos[i])).get(4));
                }
            }

        } else if (Define.ins().status.equals("sendmoney")) {
            favoritelistview_item list = adapter.dfitem.get(position);
            System.out.println("지우기 시작");
            delpos = cFileIOStreamRead.readData("sendcheckposition");
            delcountry_name = cFileIOStreamRead.readData("sendcountry_name");

            if (list.getFavorite_bank_name().equals("한국씨티은행")) {
                for (int i = 0; i < CitiVariable.ins().lstCitiData.size(); i++) {
                    if (CitiVariable.ins().lstCitiData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("기업은행")) {
                for (int i = 0; i < IBKVariable.ins().lstIBKData.size(); i++) {
                    if (IBKVariable.ins().lstIBKData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("KB국민은행")) {
                for (int i = 0; i < KBkookminVariable.ins().lstKookminData.size(); i++) {
                    if (KBkookminVariable.ins().lstKookminData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("KEB하나은행")) {
                for (int i = 0; i < KEBhanaVariable.ins().lstKEBhanaData.size(); i++) {
                    if (KEBhanaVariable.ins().lstKEBhanaData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("NH농협은행")) {
                for (int i = 0; i < NongHyupVariable.ins().lstNongHyupData.size(); i++) {
                    if (NongHyupVariable.ins().lstNongHyupData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("신한은행")) {
                for (int i = 0; i < ShinhanVariable.ins().lstShinhanData.size(); i++) {
                    if (ShinhanVariable.ins().lstShinhanData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("수협은행")) {
                for (int i = 0; i < SuhyupVariable.ins().lstSuhyupData.size(); i++) {
                    if (SuhyupVariable.ins().lstSuhyupData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            if (list.getFavorite_bank_name().equals("우리은행")) {
                for (int i = 0; i < WooriVariable.ins().lstWooriData.size(); i++) {
                    if (WooriVariable.ins().lstWooriData.get(i).get(0).equals(list.favorite_country_name)) {
                        delTemp = i;
                    }
                }
            }
            delpos = delpos.replaceFirst(delTemp + "#", "");
            delcountry_name = delcountry_name.replaceFirst(list.getFavorite_country_name() + "#" + list.getFavorite_bank_name() + "@", "");

            cFileIOStreamWrite.writeData("sendcheckposition", delpos);
            cFileIOStreamWrite.writeData("sendcountry_name", delcountry_name);

            Define.ins().sendcheckposition = delpos;
            Define.ins().sendcountry_list = delcountry_name;

            System.out.println("변경된 포지션 : " + cFileIOStreamRead.readData("sendcheckposition"));
            System.out.println("변경된 나라이름 : " + cFileIOStreamRead.readData("sendcountry_name"));

            adapter.dfitem.clear();
            dlstData.clear();
            Split(cFileIOStreamRead.readData("sendcountry_name"));
            sendpos = cFileIOStreamRead.readData("sendcheckposition").split("#");

            for (int i = 0; i < dlstData.size(); i++) {
                // (나라이름, 은행이름, 환율, 수수료)
                if (dlstData.get(i).get(1).equals("한국씨티은행")) {
                    adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), CitiVariable.ins().lstCitiData.get(Integer.valueOf(sendpos[i])).get(5), CitiVariable.ins().lstCitiData.get(Integer.valueOf(sendpos[i])).get(6));
                } else if (dlstData.get(i).get(1).equals("기업은행")) {
                    adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), IBKVariable.ins().lstIBKData.get(Integer.valueOf(sendpos[i])).get(5), IBKVariable.ins().lstIBKData.get(Integer.valueOf(sendpos[i])).get(6));
                } else if (dlstData.get(i).get(1).equals("KB국민은행")) {
                    adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(sendpos[i])).get(5), KBkookminVariable.ins().lstKookminData.get(Integer.valueOf(sendpos[i])).get(6));
                } else if (dlstData.get(i).get(1).equals("KEB하나은행")) {
                    adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(sendpos[i])).get(5), KEBhanaVariable.ins().lstKEBhanaData.get(Integer.valueOf(sendpos[i])).get(6));
                } else if (dlstData.get(i).get(1).equals("NH농협은행")) {
                    adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(sendpos[i])).get(5), NongHyupVariable.ins().lstNongHyupData.get(Integer.valueOf(sendpos[i])).get(6));
                } else if (dlstData.get(i).get(1).equals("신한은행")) {
                    adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(sendpos[i])).get(5), ShinhanVariable.ins().lstShinhanData.get(Integer.valueOf(sendpos[i])).get(6));
                } else if (dlstData.get(i).get(1).equals("수협은행")) {
                    adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(sendpos[i])).get(5), SuhyupVariable.ins().lstSuhyupData.get(Integer.valueOf(sendpos[i])).get(6));
                } else if (dlstData.get(i).get(1).equals("우리은행")) {
                    adapter.additem(cAbbreviation.getFlag(dlstData.get(i).get(0)), dlstData.get(i).get(0), dlstData.get(i).get(1), WooriVariable.ins().lstWooriData.get(Integer.valueOf(sendpos[i])).get(5), WooriVariable.ins().lstWooriData.get(Integer.valueOf(sendpos[i])).get(6));
                }
            }
        }
    }
}