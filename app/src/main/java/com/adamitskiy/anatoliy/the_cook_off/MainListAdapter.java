package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anatoliy on 8/23/15.
 */
public class MainListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;

    public MainListAdapter (Context _mContext) {
        mContext = _mContext;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.main_feed_list_item, null);

        ImageView userImage = (ImageView) vi.findViewById(R.id.main_feed_image_view);
        TextView achievement = (TextView) vi.findViewById(R.id.main_feed_achievement_text);
        Button likeButton = (Button) vi.findViewById(R.id.main_feed_like_button);

        return vi;
    }

}

