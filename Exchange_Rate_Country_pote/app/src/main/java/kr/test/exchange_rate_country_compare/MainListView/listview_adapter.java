package kr.test.exchange_rate_country_compare.MainListView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import kr.test.exchange_rate_country_compare.Define;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamCheckDir;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamCheckFile;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamRead;
import kr.test.exchange_rate_country_compare.FileIOStream.FileIOStreamWrite;
import kr.test.exchange_rate_country_compare.R;

public class listview_adapter extends BaseAdapter {

    FileIOStreamWrite cFileIOStreamWrite;
    FileIOStreamRead cFileIOStreamRead;
    AppCompatActivity alistview_adapter;

    public listview_adapter(AppCompatActivity appCompatActivity) {
        alistview_adapter = appCompatActivity;
        cFileIOStreamWrite = new FileIOStreamWrite(alistview_adapter);
        cFileIOStreamRead = new FileIOStreamRead(alistview_adapter);
    }

    public ArrayList<listview_item> mitems = new ArrayList<listview_item>();

    @Override
    public int getCount() {
        return mitems.size();
    }

    @Override
    public listview_item getItem(int pos) {
        return mitems.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(final int postion, View convertView, ViewGroup parent) {
        try {
            Context context = parent.getContext();

            // 뷰가 null 이라면
            if (convertView == null) {
                // 뷰를 설정해준다.
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.contrylistview_item, parent, false);
            }

            // id 연결
            ImageView favorite_image = (ImageView) convertView.findViewById(R.id.favorite_image);
            ImageView flag = (ImageView) convertView.findViewById(R.id.flag);
            TextView country_name = (TextView) convertView.findViewById(R.id.country_name);
            TextView abbreviation = (TextView) convertView.findViewById(R.id.abbreviation);
            TextView exchange_rate = (TextView) convertView.findViewById(R.id.exchange_rate);
            TextView fees = (TextView) convertView.findViewById(R.id.fees);

            listview_item mlist = mitems.get(postion);

            // 즐겨찾기가 되어있는 아이템에 경우 다른 이미지를 넣어줌
            if(mlist.getBfavorite()) {
                favorite_image.setImageDrawable(ContextCompat.getDrawable(alistview_adapter,R.drawable.star_24dp));
            }
            else {
                favorite_image.setImageDrawable(ContextCompat.getDrawable(alistview_adapter,R.drawable.star_border_24dp));
            }
            flag.setImageDrawable(mlist.getflag());
            country_name.setText(mlist.getcountry_name());
            abbreviation.setText(mlist.getabbreviation());
            exchange_rate.setText(mlist.getexchange_rate());
            fees.setText(mlist.getfees());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    // 외부 코드에서 리스트에 직접 아이템을 추가할 수 있는 메소드
    public void addItem(boolean favorite, Drawable flag, String country_name, String abbreviation, String exchange_rate, String fees) {
        listview_item mlistview_item = new listview_item();

        mlistview_item.setBfavorite(favorite);
        mlistview_item.setflag(flag);
        mlistview_item.setcountry_name(country_name);
        mlistview_item.setabbreviation(abbreviation);
        mlistview_item.setexchange_rate(exchange_rate);
        mlistview_item.setfees(fees);

        mitems.add(mlistview_item);
    }
}