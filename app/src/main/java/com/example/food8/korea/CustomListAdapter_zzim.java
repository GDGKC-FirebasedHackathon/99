package com.example.food8.korea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tozzim.korea.R;

import java.util.ArrayList;

/**
 * Created by hch on 16. 11. 14.
 */
public class CustomListAdapter_zzim extends BaseAdapter {
    Context ctx;
    int layout;
    ArrayList<ListItem> listData;
    LayoutInflater layoutInflater;

    public CustomListAdapter_zzim(Context ctx, int layout, ArrayList<ListItem> listData) {
        this.ctx = ctx;
        this.layout = layout;
        this.listData = listData;

        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CustomListAdapter_zzim(Context context, ArrayList<ListItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_zzim, null);
            holder = new ViewHolder();
            holder.txtTextView = (TextView) convertView.findViewById(R.id.txtTitle_zzim);
            holder.addrView = (TextView) convertView.findViewById(R.id.addr_zzim);
            holder.zipcodeView = (TextView) convertView.findViewById(R.id.zipcode_zzim);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListItem newsItem = listData.get(position);
        holder.txtTextView.setText(newsItem.gettxtTitle());
        holder.addrView.setText(newsItem.getaddr());
        holder.zipcodeView.setText(newsItem.getzipecode());
        holder.telView.setText(newsItem.gettel());


        return convertView;
    }

    static class ViewHolder {
        TextView txtTextView;
        TextView addrView;
        TextView zipcodeView;
        TextView telView;
    }
}
