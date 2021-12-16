package kr.test.exchange_rate_country_compare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import kr.test.exchange_rate_country_compare.Dialog.CustomDialog;
import kr.test.exchange_rate_country_compare.Dialog.FavoriteCustomDialog;
import kr.test.exchange_rate_country_compare.FavoriteListView.FavoriteActivity;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamCheckDir;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamCheckFile;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamRead;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamWrite;
import kr.test.exchange_rate_country_compare.JSoupGetData.Citi.CitiVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.IBK.IBKVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.JSoupGetData;
import kr.test.exchange_rate_country_compare.JSoupGetData.KBkookmin.KBkookminVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.KEBhana.KEBhanaVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.NongHyup.NongHyupVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Shinhan.ShinhanVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Suhyup.SuhyupVariable;
import kr.test.exchange_rate_country_compare.JSoupGetData.Woori.WooriVariable;
import kr.test.exchange_rate_country_compare.MainListView.listview_adapter;
import kr.test.exchange_rate_country_compare.MainListView.listview_item;

public class SetMain {

    Abbreviation cAbbreviation;
    JSoupGetData cJSoupGetData;
    AppCompatActivity aSetMain;
    ListView mcountrylistview;
    listview_adapter adapter;
    ImageView favoriteimage;
    Button digbutton;
    // 은행 별 항목 버튼
    Button KEBhanabank;
    Button Wooribank;
    Button KBkookminbank;
    Button shinhanbank;
    Button NongHyupbank;
    Button IBKbank;
    Button citibank;
    Button suhyupbank;
    FileIOStreamCheckDir cFileIOStreamCheckDir;
    FileIOStreamCheckFile cFileIOStreamCheckFile;
    FileIOStreamWrite cFileIOStreamWrite;
    FileIOStreamRead cFileIOStreamRead;
    ArrayList<ArrayList<String>> blstData = new ArrayList<ArrayList<String>>();     // 외화살때 즐겨찾기에 저장한 항목을 저장한 리스트
    ArrayList<ArrayList<String>> slstData = new ArrayList<ArrayList<String>>(); // 외화팔때 즐겨찾기에 저장한 항목을 저장한 리스트
    ArrayList<ArrayList<String>> dlstData = new ArrayList<ArrayList<String>>(); // 외화송금 즐겨찾기에 저장한 항목을 저장한 리스트

    FavoriteCustomDialog favoriteCustomDialog;

    /**
     * -환율 정보 화면
     * 처음 앱을 실행시 보여지는 화면이며 환율을 한눈에 알 수 있으며, 거래방식과 은행에 따라 환율을 볼 수 있습니다.
     *
     * -거래방식
     * 기본값은 '외화살때'이며 버튼을 누르면 커스텀다이얼로그가 팝업되며 외화살때, 외화팔때, 외화송금 중 하나를 선택할 수 있습니다.
     * 선택 시 싱글톤에 거래방식이 저장됩니다.
     * 취소 버튼을 누르면 커스텀다이얼로그가 종료됩니다.
     *
     * -즐겨찾기
     * 나라 리스트가 모두 추가되기전까지 비활성화 되며 활성화된 즐겨찾기 버튼을 누르면 즐겨찾기 화면으로 이동됩니다.
     *
     * -은행구분
     * 호라이즌스크롤뷰를 이용하여 은행별로 버튼을 만들었습니다. 해당 버튼을 클릭 시 클릭된 버튼의 텍스트 색상이 하얀색으로 변경하고 다른 은행 버튼들은 검은색으로 변경되어 클릭한 버튼을 알 수 있습니다.
     * 또한 클릭 시 파일에 해당 은행을 저장하고 리스트가 전부 추가될때까지 버튼들은 비활성화가 됩니다.
     *
     * -나라추가쓰레드
     * 3초마다 은행을 저장한 파일을 불러 해당 은행의 API를 불러옵니다. 그리고 싱글톤에 저장된 거래방식을 확인하고 나라 API를 스플릿합니다.
     * 즐겨찾기에 저장되어있는 환율정보라면 별표를 아니라면 빈별표를 표시하기 위해 즐겨찾기되어있는 나라인지 확인하고 추가하며 버튼을 다시 활성화합니다.
     *
     * -항목구분
     * 리스트뷰에 각 값들이 무엇인지 구별하기 위해 항목을 리스트뷰 상단에 출력하였습니다.
     *
     * -즐겨찾기 등록
     * 리스트뷰 아이템을 선택하면 즐겨찾기를 할 것인지 사용자에게 묻는 커스텀다이얼로그가 팝업됩니다.
     * '네' 버튼을 누르면 싱글톤에 저장된 거래방식을 확인한뒤 해당 환율의 나라이름, 위치(포지션), 은행을 파일에 저장합니다.
     * 만약 해당 환율의 정보가 이미 즐겨찾기 되어있다면 제거한뒤 다시 추가합니다.
     * '아니요' 버튼을 누르면 커스텀다이얼로그가 종료됩니다.
     *
     * @param appCompatActivity
     *
     * API 불러오는 코드
     * -클래스 : SetMain
     * -시작라인 : 335
     * -마지막라인 : 341
     *
     * 리스트 띄우는 코드
     * -클래스 : SetMain
     * -시작라인 : 368
     * -마지막라인 : 407
     * +
     * -시작라인 : 485
     * -마지막라인 : 487
     *
     * 즐겨찾기파일 저장하는 코드
     * -클래스 : SetMain
     * -시작라인 : 491
     * -마지막라인 : 527
     *
     * 즐겨찾기 리스트 추가하는 코드
     * -클래스 : FavoriteActivity
     * -시작라인 : 87
     * -마지막라인 : 112
     *
     * 즐겨찾기 삭제하는 코드
     * -클래스 : FavoriteActivity
     * -시작라인 : 211
     * -마지막라인 : 224
     * 중략
     * -시작라인 : 279
     * -마지막라인 : 322
     *
     *
     *
     *
     */

    public SetMain(AppCompatActivity appCompatActivity) {
        aSetMain = appCompatActivity;
        cAbbreviation = new Abbreviation(aSetMain);
        cJSoupGetData = new JSoupGetData(aSetMain);
        cFileIOStreamCheckDir = new FileIOStreamCheckDir(aSetMain);
        cFileIOStreamCheckFile = new FileIOStreamCheckFile(aSetMain);
        cFileIOStreamRead = new FileIOStreamRead(aSetMain);
        cFileIOStreamWrite = new FileIOStreamWrite(aSetMain);
        adapter = new listview_adapter(aSetMain);
        favoriteCustomDialog = new FavoriteCustomDialog(aSetMain, aSetMain, this);

        System.out.println("Setcountry in()...");
        Setcountry();   // 메인화면의 이벤트를 설정, 파일을 생성
        Addcountry();   // API를 불러와 리스트를 추가
    }

    // 이벤트 설정, 파일 생성
    public void Setcountry() {

        cFileIOStreamCheckDir.checkDir();

        cFileIOStreamCheckFile.checkFile("country_name", "");           // 외화살때 즐겨찾기 저장한 나라 이름 모음 파일
        cFileIOStreamCheckFile.checkFile("sellcountry_name", "");       // 외화팔때 즐겨찾기 저장한 나라 이름 모음 파일
        cFileIOStreamCheckFile.checkFile("sendcountry_name", "");       // 외화송금 즐겨찾기 저장한 나라 이름 모음 파일

        cFileIOStreamCheckFile.checkFile("checkposition", "");          // 외화살때 즐겨찾기 저장한 나라 포지션 모음 파일
        cFileIOStreamCheckFile.checkFile("sellcheckposition", "");      // 외화팔때 즐겨찾기 저장한 나라 포지션 모음 파일
        cFileIOStreamCheckFile.checkFile("sendcheckposition", "");      // 외화송금 즐겨찾기 저장한 나라 포지션 모음 파일

        cFileIOStreamCheckFile.checkFile("bank_memory", "KEBhana");     // 메인에 뜨는 리스트뷰 은행 기억 파일

        // id 연결
        favoriteimage = (ImageView) aSetMain.findViewById(R.id.favorite_image);
        digbutton = (Button) aSetMain.findViewById(R.id.dialogbt);
        KEBhanabank = (Button) aSetMain.findViewById(R.id.KEBhana);
        Wooribank = (Button) aSetMain.findViewById(R.id.Woori);
        KBkookminbank = (Button) aSetMain.findViewById(R.id.KBkookmin);
        shinhanbank = (Button) aSetMain.findViewById(R.id.Shinhan);
        NongHyupbank = (Button) aSetMain.findViewById(R.id.NongHyup);
        IBKbank = (Button) aSetMain.findViewById(R.id.IBK);
        citibank = (Button) aSetMain.findViewById(R.id.Citi);
        suhyupbank = (Button) aSetMain.findViewById(R.id.Suhyup);
        mcountrylistview = (ListView) aSetMain.findViewById(R.id.lv1);
        mcountrylistview.setAdapter(adapter);

        // 커스텀 다이얼로그를 띄움
        digbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(aSetMain);
                customDialog.callFunction(digbutton);
            }
        });

        // 즐겨찾기에 추가할 때
        mcountrylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Define.ins().pos = position;
                System.out.println("포지션 저장 : " + Define.ins().pos);
                // 다이얼로그로 사용자가 즐겨찾기에 등록할껀지 확인
                favoriteCustomDialog.callFunction(mcountrylistview);
            }
        });

        // 즐겨찾기 activity로 이동
        final Button favoritebtn = (Button) aSetMain.findViewById(R.id.favorite_btn);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                favoritebtn.setEnabled(true);
            }
        }, 3000);
        favoritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(aSetMain, FavoriteActivity.class);
                aSetMain.startActivity(intent);
            }
        });

        // 여기부턴 은행별 버튼 클릭이벤트 (API를 이용해 리스트뷰를 바꿔줌)
        KEBhanabank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Define.ins().Fin = false;   // 리스트를 모두 추가하기 전까지 버튼 비활성화
                adapter.mitems.clear();     // 리스트 중복 추가를 막기 위함

                // 클릭시 버튼 선택됨을 알려주기 위한 색상 변경
                KEBhanabank.setTextColor(Color.parseColor("#FFFFFF"));
                Wooribank.setTextColor(Color.parseColor("#000000"));
                KBkookminbank.setTextColor(Color.parseColor("#000000"));
                shinhanbank.setTextColor(Color.parseColor("#000000"));
                NongHyupbank.setTextColor(Color.parseColor("#000000"));
                IBKbank.setTextColor(Color.parseColor("#000000"));
                citibank.setTextColor(Color.parseColor("#000000"));
                suhyupbank.setTextColor(Color.parseColor("#000000"));

                cFileIOStreamWrite.writeData("bank_memory", "KEBhana"); // 선택한 은행을 저장
            }
        });
        Wooribank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Define.ins().Fin = false;
                adapter.mitems.clear();

                KEBhanabank.setTextColor(Color.parseColor("#000000"));
                Wooribank.setTextColor(Color.parseColor("#FFFFFF"));
                KBkookminbank.setTextColor(Color.parseColor("#000000"));
                shinhanbank.setTextColor(Color.parseColor("#000000"));
                NongHyupbank.setTextColor(Color.parseColor("#000000"));
                IBKbank.setTextColor(Color.parseColor("#000000"));
                citibank.setTextColor(Color.parseColor("#000000"));
                suhyupbank.setTextColor(Color.parseColor("#000000"));

                cFileIOStreamWrite.writeData("bank_memory", "Woori");
            }
        });
        KBkookminbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Define.ins().Fin = false;
                adapter.mitems.clear();

                KEBhanabank.setTextColor(Color.parseColor("#000000"));
                Wooribank.setTextColor(Color.parseColor("#000000"));
                KBkookminbank.setTextColor(Color.parseColor("#FFFFFF"));
                shinhanbank.setTextColor(Color.parseColor("#000000"));
                NongHyupbank.setTextColor(Color.parseColor("#000000"));
                IBKbank.setTextColor(Color.parseColor("#000000"));
                citibank.setTextColor(Color.parseColor("#000000"));
                suhyupbank.setTextColor(Color.parseColor("#000000"));

                cFileIOStreamWrite.writeData("bank_memory", "KBkookmin");
            }
        });
        shinhanbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Define.ins().Fin = false;
                adapter.mitems.clear();

                KEBhanabank.setTextColor(Color.parseColor("#000000"));
                Wooribank.setTextColor(Color.parseColor("#000000"));
                KBkookminbank.setTextColor(Color.parseColor("#000000"));
                shinhanbank.setTextColor(Color.parseColor("#FFFFFF"));
                NongHyupbank.setTextColor(Color.parseColor("#000000"));
                IBKbank.setTextColor(Color.parseColor("#000000"));
                citibank.setTextColor(Color.parseColor("#000000"));
                suhyupbank.setTextColor(Color.parseColor("#000000"));

                cFileIOStreamWrite.writeData("bank_memory", "Shinhan");
            }
        });
        NongHyupbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Define.ins().Fin = false;
                adapter.mitems.clear();

                KEBhanabank.setTextColor(Color.parseColor("#000000"));
                Wooribank.setTextColor(Color.parseColor("#000000"));
                KBkookminbank.setTextColor(Color.parseColor("#000000"));
                shinhanbank.setTextColor(Color.parseColor("#000000"));
                NongHyupbank.setTextColor(Color.parseColor("#FFFFFF"));
                IBKbank.setTextColor(Color.parseColor("#000000"));
                citibank.setTextColor(Color.parseColor("#000000"));
                suhyupbank.setTextColor(Color.parseColor("#000000"));

                cFileIOStreamWrite.writeData("bank_memory", "NongHyup");
            }
        });
        IBKbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Define.ins().Fin = false;
                adapter.mitems.clear();

                KEBhanabank.setTextColor(Color.parseColor("#000000"));
                Wooribank.setTextColor(Color.parseColor("#000000"));
                KBkookminbank.setTextColor(Color.parseColor("#000000"));
                shinhanbank.setTextColor(Color.parseColor("#000000"));
                NongHyupbank.setTextColor(Color.parseColor("#000000"));
                IBKbank.setTextColor(Color.parseColor("#FFFFFF"));
                citibank.setTextColor(Color.parseColor("#000000"));
                suhyupbank.setTextColor(Color.parseColor("#000000"));

                cFileIOStreamWrite.writeData("bank_memory", "IBK");
            }
        });
        citibank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Define.ins().Fin = false;
                adapter.mitems.clear();

                KEBhanabank.setTextColor(Color.parseColor("#000000"));
                Wooribank.setTextColor(Color.parseColor("#000000"));
                KBkookminbank.setTextColor(Color.parseColor("#000000"));
                shinhanbank.setTextColor(Color.parseColor("#000000"));
                NongHyupbank.setTextColor(Color.parseColor("#000000"));
                IBKbank.setTextColor(Color.parseColor("#000000"));
                citibank.setTextColor(Color.parseColor("#FFFFFF"));
                suhyupbank.setTextColor(Color.parseColor("#000000"));

                cFileIOStreamWrite.writeData("bank_memory", "Citi");
            }
        });
        suhyupbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Define.ins().Fin = false;
                adapter.mitems.clear();

                KEBhanabank.setTextColor(Color.parseColor("#000000"));
                Wooribank.setTextColor(Color.parseColor("#000000"));
                KBkookminbank.setTextColor(Color.parseColor("#000000"));
                shinhanbank.setTextColor(Color.parseColor("#000000"));
                NongHyupbank.setTextColor(Color.parseColor("#000000"));
                IBKbank.setTextColor(Color.parseColor("#000000"));
                citibank.setTextColor(Color.parseColor("#000000"));
                suhyupbank.setTextColor(Color.parseColor("#FFFFFF"));

                cFileIOStreamWrite.writeData("bank_memory", "Suhyup");
            }
        });
    }

    // 나라 추가
    public void Addcountry() {
        // 리스트뷰에 계속 추가되지않기 위해 비워줌
        adapter.mitems.clear();
        System.out.println("은행파일읽기 : " + cFileIOStreamRead.readData("bank_memory"));

        // 앱을 실행할 때 선택한 은행 리스트를 띄워줌
        Define.ins().lstcountry.clear();    // 중복 추가를 방지함
        // 선택한 은행이 시티은행이라면
        if (cFileIOStreamRead.readData("bank_memory").equals("Citi")) {
            // 시티은행의 API를 싱글톤에 추가한다.
            Define.ins().lstcountry.addAll(CitiVariable.ins().lstCitiData);
            citibank.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (cFileIOStreamRead.readData("bank_memory").equals("IBK")) {
            Define.ins().lstcountry.addAll(IBKVariable.ins().lstIBKData);
            IBKbank.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (cFileIOStreamRead.readData("bank_memory").equals("KBkookmin")) {
            Define.ins().lstcountry.addAll(KBkookminVariable.ins().lstKookminData);
            KBkookminbank.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (cFileIOStreamRead.readData("bank_memory").equals("KEBhana")) {
            Define.ins().lstcountry.addAll(KEBhanaVariable.ins().lstKEBhanaData);
            KEBhanabank.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (cFileIOStreamRead.readData("bank_memory").equals("NongHyup")) {
            Define.ins().lstcountry.addAll(NongHyupVariable.ins().lstNongHyupData);
            NongHyupbank.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (cFileIOStreamRead.readData("bank_memory").equals("Shinhan")) {
            Define.ins().lstcountry.addAll(ShinhanVariable.ins().lstShinhanData);
            shinhanbank.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (cFileIOStreamRead.readData("bank_memory").equals("Suhyup")) {
            Define.ins().lstcountry.addAll(SuhyupVariable.ins().lstSuhyupData);
            suhyupbank.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (cFileIOStreamRead.readData("bank_memory").equals("Woori")) {
            Define.ins().lstcountry.addAll(WooriVariable.ins().lstWooriData);
            Wooribank.setTextColor(Color.parseColor("#FFFFFF"));
        }

        System.out.println("lstCountry : " + Define.ins().lstcountry);
        System.out.println("lstCountry size : " + Define.ins().lstcountry.size());

        // 은행별로 다른 리스트를 띄워준다.
        // 외화살때라면
        if (Define.ins().status.equals("buymoney")) {
            Split(cFileIOStreamRead.readData("country_name"));  // 외화살때 즐겨찾기 파일을 2번 split
            for (int i = 0; i < Define.ins().lstcountry.size(); i++) {  // API를 불러와 저장하고있는 싱글톤 사이즈만큼
                if (blstData.size() != 0) {     // 값이 있다면
                    for (int k = 0; k < blstData.size(); k++) { // 즐겨찾기 저장되어 있는 길이만큼
                        // 나라 국기 별로 설정
                        // 즐겨찾기의 등록된 나라이름, 은행과 리스트뷰의 나라이름, 은행이 같을 시
                        if (blstData.get(k).get(0).equals(Define.ins().lstcountry.get(i).get(0)) && blstData.get(k).get(1).equals(cAbbreviation.getBank(cFileIOStreamRead.readData("bank_memory")))) {
                            adapter.addItem(true,
                                    cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(0),
                                    cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(1),
                                    Define.ins().lstcountry.get(i).get(2));
                            break;  // 중복 추가를 방지
                        }
                        // 즐겨찾기에 저장되어있지 않은 나라라면
                        else if (k == blstData.size() - 1) {
                            adapter.addItem(false,
                                    cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(0),
                                    cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(1),
                                    Define.ins().lstcountry.get(i).get(2));
                        }
                    }
                }
                // 아예 즐겨찾기에 저장된게 없다면
                else if (blstData.size() == 0) {
                    adapter.addItem(false,
                            cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                            Define.ins().lstcountry.get(i).get(0),
                            cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                            Define.ins().lstcountry.get(i).get(1),
                            Define.ins().lstcountry.get(i).get(2));
                }
            }
        }
        // 외화팔때라면
        else if (Define.ins().status.equals("sellmoney")) {
            Split(cFileIOStreamRead.readData("sellcountry_name"));  // 외화팔때 즐겨찾기 파일을 2번 split
            for (int i = 0; i < Define.ins().lstcountry.size(); i++) {  // API를 불러와 저장하고있는 싱글톤 사이즈만큼
                if (slstData.size() != 0) {     // 값이 있다면
                    for (int k = 0; k < slstData.size(); k++) {     // 즐겨찾기 저장되어 있는 길이만큼
                        // 즐겨찾기의 등록된 나라이름, 은행과 리스트뷰의 나라이름, 은행이 같을 시
                        if (slstData.get(k).get(0).equals(Define.ins().lstcountry.get(i).get(0)) && slstData.get(k).get(1).equals(cAbbreviation.getBank(cFileIOStreamRead.readData("bank_memory")))) {
                            adapter.addItem(true,
                                    cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(0),
                                    cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(3),
                                    Define.ins().lstcountry.get(i).get(4));
                            break;  // 중복 추가를 방지
                        }
                        // 즐겨찾기에 저장되어있지 않은 나라라면
                        else if (k == slstData.size() - 1) {
                            adapter.addItem(false,
                                    cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(0),
                                    cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(3),
                                    Define.ins().lstcountry.get(i).get(4));
                        }
                    }
                }
                // 아예 즐겨찾기에 저장된게 없다면
                else if (slstData.size() == 0) {
                    adapter.addItem(false,
                            cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                            Define.ins().lstcountry.get(i).get(0),
                            cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                            Define.ins().lstcountry.get(i).get(3),
                            Define.ins().lstcountry.get(i).get(4));
                }
            }
        }
        // 외화송금이라면
        else if (Define.ins().status.equals("sendmoney")) {
            Split(cFileIOStreamRead.readData("sendcountry_name"));  // 외화팔때 즐겨찾기 파일을 2번 split
            for (int i = 0; i < Define.ins().lstcountry.size(); i++) {  // API를 불러와 저장하고있는 싱글톤 사이즈만큼
                if (dlstData.size() != 0) {     // 값이 있다면
                    for (int k = 0; k < dlstData.size(); k++) {     // 즐겨찾기 저장되어 있는 길이만큼
                        // 즐겨찾기의 등록된 나라이름, 은행과 리스트뷰의 나라이름, 은행이 같을 시
                        if (dlstData.get(k).get(0).equals(Define.ins().lstcountry.get(i).get(0)) && dlstData.get(k).get(1).equals(cAbbreviation.getBank(cFileIOStreamRead.readData("bank_memory")))) {
                            adapter.addItem(true,
                                    cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(0),
                                    cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(5),
                                    Define.ins().lstcountry.get(i).get(6));
                            break;  // 중복 추가를 방지
                        }
                        // 즐겨찾기에 저장되어있지 않은 나라라면
                        else if (k == dlstData.size() - 1){
                            adapter.addItem(false,
                                    cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(0),
                                    cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                                    Define.ins().lstcountry.get(i).get(5),
                                    Define.ins().lstcountry.get(i).get(6));
                        }
                    }
                }
                // 아예 즐겨찾기에 저장된게 없다면
                else if(dlstData.size() == 0){
                    adapter.addItem(false,
                            cAbbreviation.getFlag(Define.ins().lstcountry.get(i).get(0)),
                            Define.ins().lstcountry.get(i).get(0),
                            cAbbreviation.getAbbreviation(Define.ins().lstcountry.get(i).get(0)),
                            Define.ins().lstcountry.get(i).get(5),
                            Define.ins().lstcountry.get(i).get(6));
                }
            }
        }

        adapter.notifyDataSetChanged();     // 리스트 갱신
        System.out.println("Define.ins().lstcountry.size() : " + Define.ins().lstcountry.size());
        Define.ins().Fin = true;    // 리스트 아이템 추가가 완료되면 버튼을 다시 활성화
    }

    public void Addfavorite(int position) {
        listview_item mlist = adapter.mitems.get(position);
        // 외화살때라면
        if (Define.ins().status.equals("buymoney")) {

            // 싱글톤에 즐겨찾기에 추가할 나라의 포지션, 이름들을 저장
            Define.ins().posTemp = String.valueOf(position) + "#";
            Define.ins().Temp = mlist.getcountry_name() + "#" + cAbbreviation.getBank(cFileIOStreamRead.readData("bank_memory")) + "@";

            System.out.println("읽기시작");

            // 만약 앱 실행 후 처음 추가하는 것이라면 필요한 파일을 불러와 싱글톤에 저장
            if (Define.ins().country_list.equals("")) {
                Define.ins().country_list = cFileIOStreamRead.readData("country_name");
            }
            if (Define.ins().checkposition.equals("")) {
                Define.ins().checkposition = cFileIOStreamRead.readData("checkposition");
            }

            System.out.println("위치 : " + position + " 체크박스false");

            // 만약 이미 즐겨찾기에 저장되어있는 항목이라면
            if (Define.ins().country_list.contains(Define.ins().Temp)) {
                // 즐겨찾기 최하단에 띄우기 위해 해당 항목을 지운다.
                Define.ins().checkposition = Define.ins().checkposition.replaceFirst(Define.ins().posTemp, "");
                Define.ins().country_list = Define.ins().country_list.replaceFirst(Define.ins().Temp, "");
            }

            // 즐겨찾기에 추가하기 위해 싱글톤에 추가
            Define.ins().country_list += Define.ins().Temp;
            Define.ins().checkposition += Define.ins().posTemp;

            // 변경된 싱글톤을 파일에 저장
            cFileIOStreamWrite.writeData("checkposition", Define.ins().checkposition);
            cFileIOStreamWrite.writeData("country_name", Define.ins().country_list);

            System.out.println("country_list 확인 : " + cFileIOStreamRead.readData("country_name"));
        }
        // 외화팔때라면
        if (Define.ins().status.equals("sellmoney")) {
            // 싱글톤에 즐겨찾기에 추가할 나라의 포지션, 이름들을 저장
            Define.ins().posTemp = String.valueOf(position) + "#";
            Define.ins().Temp = mlist.getcountry_name() + "#" + cAbbreviation.getBank(cFileIOStreamRead.readData("bank_memory")) + "@";

            System.out.println("읽기시작");

            // 만약 앱 실행 후 처음 추가하는 것이라면 필요한 파일을 불러와 싱글톤에 저장
            if (Define.ins().sellcountry_list.equals("")) {
                Define.ins().sellcountry_list = cFileIOStreamRead.readData("sellcountry_name");
            }
            if (Define.ins().sellcheckposition.equals("")) {
                Define.ins().sellcheckposition = cFileIOStreamRead.readData("sellcheckposition");
            }

            System.out.println("위치 : " + position + " 체크박스false");

            // 만약 이미 즐겨찾기에 저장되어있는 항목이라면
            if (Define.ins().sellcountry_list.contains(Define.ins().Temp)) {
                // 즐겨찾기 최하단에 띄우기 위해 해당 항목을 지운다.
                Define.ins().sellcheckposition = Define.ins().sellcheckposition.replaceFirst(Define.ins().posTemp, "");
                Define.ins().sellcountry_list = Define.ins().sellcountry_list.replaceFirst(Define.ins().Temp, "");
            }

            // 즐겨찾기에 추가하기 위해 싱글톤에 추가
            Define.ins().sellcountry_list += Define.ins().Temp;
            Define.ins().sellcheckposition += Define.ins().posTemp;

            // 변경된 싱글톤을 파일에 저장
            cFileIOStreamWrite.writeData("sellcheckposition", Define.ins().sellcheckposition);
            cFileIOStreamWrite.writeData("sellcountry_name", Define.ins().sellcountry_list);

            System.out.println("sellcountry_list 확인 : " + cFileIOStreamRead.readData("sellcountry_name"));
        }
        // 외화송금이라면
        if (Define.ins().status.equals("sendmoney")) {
            // 싱글톤에 즐겨찾기에 추가할 나라의 포지션, 이름들을 저장
            Define.ins().posTemp = String.valueOf(position) + "#";
            Define.ins().Temp = mlist.getcountry_name() + "#" + cAbbreviation.getBank(cFileIOStreamRead.readData("bank_memory")) + "@";

            System.out.println("읽기시작");

            // 만약 앱 실행 후 처음 추가하는 것이라면 필요한 파일을 불러와 싱글톤에 저장
            if (Define.ins().sendcountry_list.equals("")) {
                Define.ins().sendcountry_list = cFileIOStreamRead.readData("sendcountry_name");
            }
            if (Define.ins().sendcheckposition.equals("")) {
                Define.ins().sendcheckposition = cFileIOStreamRead.readData("sendcheckposition");
            }

            System.out.println("위치 : " + position + " 체크박스false");

            // 만약 이미 즐겨찾기에 저장되어있는 항목이라면
            if (Define.ins().sendcountry_list.contains(Define.ins().Temp)) {
                // 즐겨찾기 최하단에 띄우기 위해 해당 항목을 지운다.
                Define.ins().sendcheckposition = Define.ins().sendcheckposition.replaceFirst(Define.ins().posTemp, "");
                Define.ins().sendcountry_list = Define.ins().sendcountry_list.replaceFirst(Define.ins().Temp, "");
            }

            // 즐겨찾기에 추가하기 위해 싱글톤에 추가
            Define.ins().sendcountry_list += Define.ins().Temp;
            Define.ins().sendcheckposition += Define.ins().posTemp;

            // 변경된 싱글톤을 파일에 저장
            cFileIOStreamWrite.writeData("sendcheckposition", Define.ins().sendcheckposition);
            cFileIOStreamWrite.writeData("sendcountry_name", Define.ins().sendcountry_list);

            System.out.println("sendcountry_list 확인 : " + cFileIOStreamRead.readData("sendcountry_name"));
        }
    }

    // split을 2번하여 이중Arraylist 변수에 추가하는 함수
    public void Split(String getData) {
        // 중복 추가 방지
        blstData.clear();
        slstData.clear();
        dlstData.clear();
        if (!getData.equals("")) {
            // 최초값 : 미국#국민은행@일본#우리은행@중국#농협@
            String sTempArr[] = getData.split("@");
            for (int i = 0; i < sTempArr.length; i++) {
                String sTemp[] = sTempArr[i].split("#");
                ArrayList<String> lstTemp = new ArrayList<String>();
                for (int j = 0; j < sTemp.length; j++) {
                    lstTemp.add(sTemp[j]);
                }
                // 외화살때라면
                if (Define.ins().status.equals("buymoney")) {
                    blstData.add(lstTemp);
                }
                // 외화팔때라면
                if (Define.ins().status.equals("sellmoney")) {
                    slstData.add(lstTemp);
                }
                // 외화송금이라면
                if (Define.ins().status.equals("sendmoney")) {
                    dlstData.add(lstTemp);
                }
            }
        }
//        System.out.println("blstData : " + blstData);
    }
}

