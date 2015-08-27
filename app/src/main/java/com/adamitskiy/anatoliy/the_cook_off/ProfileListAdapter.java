package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anatoliy on 8/26/15.
 */
public class ProfileListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;

    public ProfileListAdapter (Context _mContext) {
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
            vi = inflater.inflate(R.layout.profile_list_item, null);

        ImageView userImage = (ImageView) vi.findViewById(R.id.profile_image_view);
        TextView achievement = (TextView) vi.findViewById(R.id.profile_text);

        return vi;
    }
}
