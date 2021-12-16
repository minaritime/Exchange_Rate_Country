package kr.test.exchange_rate_country_compare.FavoriteListView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.test.exchange_rate_country_compare.Define;
import kr.test.exchange_rate_country_compare.R;

public class favoritelistview_adapter extends BaseAdapter {

    public ArrayList<favoritelistview_item> bfitem = new ArrayList<favoritelistview_item>();
    public ArrayList<favoritelistview_item> sfitem = new ArrayList<favoritelistview_item>();
    public ArrayList<favoritelistview_item> dfitem = new ArrayList<favoritelistview_item>();
    public favoritelistview_item mlist;

    // 사용자가 선택한 환율방식에 따라 즐겨찾기리스트를 변경하기위해 리스트의 길이를 각각 설정한다.
    @Override
    public int getCount() {
        if(Define.ins().status.equals("buymoney")) {
            return bfitem.size();
        }
        if(Define.ins().status.equals("sellmoney")){
            return sfitem.size();
        }
        if(Define.ins().status.equals("sendmoney")){
            return dfitem.size();
        }
        return 0;
    }

    // 사용자가 선택한 환율방식에 따라 다른 아이템이기때문에 각각 다른 아이템을 반환한다.
    @Override
    public favoritelistview_item getItem(int pos) {
        if(Define.ins().status.equals("buymoney")) {
            return bfitem.get(pos);
        }
        if(Define.ins().status.equals("sellmoney")){
            return sfitem.get(pos);
        }
        if(Define.ins().status.equals("sendmoney")){
            return dfitem.get(pos);
        }
        return null;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        // 뷰가 null이라면
        if (convertView == null) {
            // 뷰를 설정해준다.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favorites_countrylistview_item, parent, false);
        }

        // id연결
        ImageView flag = (ImageView)convertView.findViewById(R.id.favorite_flag);
        TextView country_name = (TextView)convertView.findViewById(R.id.favorite_country_name);
        TextView bank_name = (TextView)convertView.findViewById(R.id.favorite_bank_name);
        TextView exchange = (TextView)convertView.findViewById(R.id.favorite_exchange);
        TextView fees = (TextView)convertView.findViewById(R.id.favorite_fees);

        // 사용자가 선택한 환율방식에 따라 다른 리스트를 넣어준다.
        if(Define.ins().status.equals("buymoney")) {
            mlist = bfitem.get(postion);
        }
        if(Define.ins().status.equals("sellmoney")){
            mlist = sfitem.get(postion);
        }
        if(Define.ins().status.equals("sendmoney")){
            mlist = dfitem.get(postion);
        }

        flag.setImageDrawable(mlist.getFavorite_flag());
        country_name.setText(mlist.getFavorite_country_name());
        bank_name.setText(mlist.getFavorite_bank_name());
        exchange.setText(mlist.getFavorite_exchange());
        fees.setText(mlist.getFavorite_susuryo());

        return convertView;
    }

    // 외부 코드에서 리스트에 직접 아이템을 추가할 수 있는 메소드
    public void additem(Drawable flag, String country_name, String bank_name, String exchange, String fees){
        favoritelistview_item ListView = new favoritelistview_item();

        ListView.setFavorite_flag(flag);
        ListView.setFavorite_country_name(country_name);
        ListView.setFavorite_bank_name(bank_name);
        ListView.setFavorite_exchange(exchange);
        ListView.setFavorite_susuryo(fees);

        // 사용자가 선택한 환율방식에 따라 각각 다른 리스트에 추가해준다.
        if(Define.ins().status.equals("buymoney")) {
            bfitem.add(ListView);
        }
        if(Define.ins().status.equals("sellmoney")){
            sfitem.add(ListView);
        }
        if(Define.ins().status.equals("sendmoney")){
            dfitem.add(ListView);
        }
    }
}
