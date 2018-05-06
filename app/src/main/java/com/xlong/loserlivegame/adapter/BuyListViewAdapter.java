package com.xlong.loserlivegame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xlong.loserlivegame.R;
import com.xlong.loserlivegame.model.BuyModel;
import com.xlong.loserlivegame.model.SaleModel;

import java.util.List;

/**
 * Created by SLP on 2017/3/17.
 */

public class BuyListViewAdapter extends BaseAdapter {
    private Context context;
    private List<BuyModel> list;

    public BuyListViewAdapter(Context context, List<BuyModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.buy_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.introTv = (TextView) convertView.findViewById(R.id.buy_listview_item_intro);
            viewHolder.prizeTv = (TextView) convertView.findViewById(R.id.buy_listview_item_price);
            viewHolder.numTv = (TextView) convertView.findViewById(R.id.buy_listview_item_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.introTv.setText(list.get(position).getName());
        viewHolder.prizeTv.setText(String.valueOf(list.get(position).getPrize()));
        viewHolder.numTv.setText(String.valueOf(list.get(position).getNum()));
        return convertView;
    }

    static class ViewHolder {
        TextView introTv;
        TextView prizeTv;
        TextView numTv;
    }

}
