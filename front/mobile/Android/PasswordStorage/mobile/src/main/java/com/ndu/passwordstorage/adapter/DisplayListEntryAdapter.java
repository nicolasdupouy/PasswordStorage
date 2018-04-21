package com.ndu.passwordstorage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_display_list_entry, parent, false);
        }

        PasswordEntry passwordEntry = getItem(position);
        EditText site = convertView.findViewById(R.id.site);
        EditText login = convertView.findViewById(R.id.login);
        EditText password = convertView.findViewById(R.id.password);

        site.setText(passwordEntry.getSite(), TextView.BufferType.NORMAL);
        login.setText(passwordEntry.getLogin(), TextView.BufferType.NORMAL);
        password.setText(passwordEntry.getPassword(), TextView.BufferType.NORMAL);

        LinearLayout linearLayout = convertView.findViewById(R.id.lineLayout);
        linearLayout.setOnClickListener(v -> this.displayListActivity.displayMemo(position));

        return convertView;
    }
}
