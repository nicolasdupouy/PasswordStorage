package com.ndu.passwordstorage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.model.PasswordEntry;
import com.ndu.passwordstorage.screen.DisplayListActivity;

import java.util.List;

public class DisplayListEntryAdapter extends ArrayAdapter<PasswordEntry> {

    private DisplayListActivity displayListActivity;

    public DisplayListEntryAdapter(@NonNull DisplayListActivity displayListActivity, @NonNull List<PasswordEntry> passwordEntries) {
        super(displayListActivity, 0, passwordEntries);
        this.displayListActivity = displayListActivity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DisplayListEntryViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (DisplayListEntryViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_display_list_entry, parent, false);
            viewHolder = new DisplayListEntryViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        PasswordEntry passwordEntry = getItem(position);
        viewHolder.fill(passwordEntry);
        viewHolder.setClickListener(this.displayListActivity, position);

        return convertView;
    }
}
