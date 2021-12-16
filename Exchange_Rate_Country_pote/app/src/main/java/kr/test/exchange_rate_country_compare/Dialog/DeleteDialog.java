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
import kr.test.exchange_rate_country_compare.FavoriteListView.FavoriteActivity;
import kr.test.exchange_rate_country_compare.FavoriteListView.favoritelistview_adapter;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamWrite;
import kr.test.exchange_rate_country_compare.R;

public class DeleteDialog {

    private AppCompatActivity aDeleteDialog;
    FileIOStreamWrite cFileIOStreamWrite;
    favoritelistview_adapter adapter;
    FavoriteActivity cfavoriteactivity;
    ListView mlist;

    public DeleteDialog(AppCompatActivity appCompatActivity, FavoriteActivity favoriteActivity, favoritelistview_adapter adapter) {
        aDeleteDialog = appCompatActivity;
        cfavoriteactivity = favoriteActivity;
        this.adapter = adapter;
    }

    public void callFunction() {

        cFileIOStreamWrite = new FileIOStreamWrite(aDeleteDialog);
        mlist = (ListView) aDeleteDialog.findViewById(R.id.favorite_country_list);
        mlist.setAdapter(adapter);

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(aDeleteDialog);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.delete_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button yes = (Button) dlg.findViewById(R.id.deleteyes);
        final Button no = (Button) dlg.findViewById(R.id.deleteno);

        // 확인 버튼 이벤트
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("네 눌림");
                cfavoriteactivity.setDelete(Define.ins().delpos);
                dlg.dismiss();
                adapter.notifyDataSetChanged();
            }
        });
        // 취소 버튼 이벤트
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("아니요 눌림");
                dlg.dismiss();
            }
        });
        // 다이얼로그 외부 클릭 이벤트
        dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dlg.dismiss();
            }
        });
    }
}
