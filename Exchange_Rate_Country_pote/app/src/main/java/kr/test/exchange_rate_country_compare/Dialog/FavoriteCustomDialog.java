package kr.test.exchange_rate_country_compare.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import kr.test.exchange_rate_country_compare.Define;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamRead;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamWrite;
import kr.test.exchange_rate_country_compare.MainListView.listview_adapter;
import kr.test.exchange_rate_country_compare.R;
import kr.test.exchange_rate_country_compare.SetMain;

public class FavoriteCustomDialog {

    private AppCompatActivity aFavoriteCustomDialog;
    FileIOStreamWrite cFileIOStreamWrite;
    Context context;
    SetMain csetmain;

    public FavoriteCustomDialog(AppCompatActivity appCompatActivity, Context context, SetMain setMain) {
        aFavoriteCustomDialog = appCompatActivity;
        this.context = context;
        csetmain = setMain;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final ListView listView) {

        cFileIOStreamWrite = new FileIOStreamWrite(aFavoriteCustomDialog);

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.favorite_custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        if(!aFavoriteCustomDialog.isFinishing()) {
            dlg.show();
        }

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button yes = (Button) dlg.findViewById(R.id.yes);
        final Button no = (Button) dlg.findViewById(R.id.no);

        // 확인 버튼 이벤트
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("yes 눌림");
                csetmain.Addfavorite(Define.ins().pos);
                dlg.dismiss();
            }
        });
        // 취소 버튼 이벤트
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("no 눌림");
                dlg.dismiss();
            }
        });
    }
}
