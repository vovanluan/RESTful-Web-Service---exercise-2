package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.luan.activity.R;

import java.util.ArrayList;

import entity.User;

/**
 * Created by Luan on 3/30/2016.
 */
public class UserAdapter extends ArrayAdapter<User> {
    private ArrayList<User> userList;
    public UserAdapter(Context context, int view, ArrayList<User> users) {
        super(context, view, users);
        this.userList = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.short_info, parent, false);
        }

        // Get the data item for this position
        User user = userList.get(position);

        if (user != null) {
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView email = (TextView) convertView.findViewById(R.id.email);

            name.setText(user.getName());
            email.setText(user.getEmail());
        }
        return convertView;
    }
}
