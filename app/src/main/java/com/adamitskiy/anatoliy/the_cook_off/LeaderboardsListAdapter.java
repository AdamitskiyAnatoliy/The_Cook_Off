package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Anatoliy on 8/23/15.
 */
public class LeaderboardsListAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;
    List<ParseUser> users;

    public LeaderboardsListAdapter (Context _mContext, List<ParseUser> _users) {
        mContext = _mContext;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        users = _users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.leaderboards_list_item, null);

        TextView rank = (TextView) vi.findViewById(R.id.leaderboard_rank);
        rank.setText(position + 1 + ".");

        TextView username = (TextView) vi.findViewById(R.id.leaderboard_name_text);
        username.setText(users.get(position).getUsername());

        TextView points = (TextView) vi.findViewById(R.id.leaderboard_score_text);
        points.setText("Score: " + users.get(position).getString("Points"));

//        ImageView userImage = (ImageView) vi.findViewById(R.id.main_feed_image_view);
//        TextView achievement = (TextView) vi.findViewById(R.id.main_feed_achievement_text);
//        Button likeButton = (Button) vi.findViewById(R.id.main_feed_like_button);

        return vi;
    }
}
