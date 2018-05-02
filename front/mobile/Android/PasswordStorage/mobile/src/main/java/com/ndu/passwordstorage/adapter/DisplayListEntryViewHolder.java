package com.ndu.passwordstorage.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.model.PasswordEntry;
import com.ndu.passwordstorage.screen.DisplayListActivity;

public class DisplayListEntryViewHolder {
    private final ConstraintLayout linearLayout;
    private final EditText site;
    private final EditText login;
    private final EditText password;
    private int position;

    DisplayListEntryViewHolder(View convertView) {
        this.linearLayout = convertView.findViewById(R.id.passwordEntryLayout);
        this.site = convertView.findViewById(R.id.site);
        this.login = convertView.findViewById(R.id.login);
        this.password = convertView.findViewById(R.id.password);
    }

    void fill(PasswordEntry passwordEntry, int position) {
        this.site.setText(passwordEntry.getSite(), TextView.BufferType.NORMAL);
        this.login.setText(passwordEntry.getLogin(), TextView.BufferType.NORMAL);
        this.password.setText(passwordEntry.getPassword(), TextView.BufferType.NORMAL);
        this.position = position;
    }

    void setClickListener(DisplayListActivity displayListActivity, int position) {
        this.linearLayout.setOnClickListener(v -> displayListActivity.displayMemo(position));
    }

    public Integer getPosition() {
        return position;
    }
}
