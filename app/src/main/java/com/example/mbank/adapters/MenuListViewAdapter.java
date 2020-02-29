package com.example.mbank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mbank.R;
import java.util.List;

public class MenuListViewAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> menuTitles;
    private List<Integer> menuImages;

    public MenuListViewAdapter(@NonNull Context context, @NonNull List<String> menuTitles, @NonNull List<Integer> menuImages) {
        super(context, R.layout.menu_item_layout, menuTitles);
        mContext = context;
        this.menuImages = menuImages;
        this.menuTitles = menuTitles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.menu_item_layout, parent, false);
            holder = new ViewHolder();

            holder.menuImage = convertView.findViewById(R.id.menuImage);
            holder.menuTitle = convertView.findViewById(R.id.menuTitle);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.menuImage.setImageResource(menuImages.get(position));
        holder.menuTitle.setText(menuTitles.get(position));

        return convertView;
    }

    private class ViewHolder {
        TextView menuTitle;
        ImageView menuImage;
    }
}
