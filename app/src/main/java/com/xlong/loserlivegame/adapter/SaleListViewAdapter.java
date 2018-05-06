package com.xlong.loserlivegame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xlong.loserlivegame.R;
import com.xlong.loserlivegame.model.SaleModel;

import java.util.List;

/**
 * Created by SLP on 2017/3/17.
 */

public class SaleListViewAdapter extends BaseAdapter {
    private Context context;
    private List<SaleModel> list;

    public SaleListViewAdapter(Context context, List<SaleModel> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.sale_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.introTv = (TextView) convertView.findViewById(R.id.sale_listview_item_intro);
            viewHolder.prizeTv = (TextView) convertView.findViewById(R.id.sale_listview_item_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.introTv.setText(list.get(position).getName());
        viewHolder.prizeTv.setText(String.valueOf(list.get(position).getMoney()));
        return convertView;
    }

    static class ViewHolder {
        TextView introTv;
        TextView prizeTv;
    }

}
