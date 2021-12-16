package kr.test.exchange_rate_country_compare.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import kr.test.exchange_rate_country_compare.Define;
import kr.test.exchange_rate_country_compare.R;

public class CustomDialog {

    private Context context;

    public CustomDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final Button button) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final Button buybutton = (Button) dlg.findViewById(R.id.buymoney);
        final Button sellbutton = (Button) dlg.findViewById(R.id.sellmoney);
        final Button sendmoneybutton = (Button) dlg.findViewById(R.id.sendmoney);
        final Button cancelbutton = (Button) dlg.findViewById(R.id.cencelbutton);

        // 외화살때 버튼 이벤트
        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText(buybutton.getText().toString());
                Define.ins().status = "buymoney";
                dlg.dismiss();
            }
        });
        // 외화팔때 버튼 이벤트
        sellbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText(sellbutton.getText().toString());
                Define.ins().status = "sellmoney";
                dlg.dismiss();
            }
        });
        // 외화송금 버튼 이벤트
        sendmoneybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText(sendmoneybutton.getText().toString());
                Define.ins().status = "sendmoney";
                dlg.dismiss();
            }
        });
        // 취소 버튼 이벤트
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
        // 다이얼로그 외부클릭시 이벤트
        dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dlg.dismiss();
            }
        });
    }
}

