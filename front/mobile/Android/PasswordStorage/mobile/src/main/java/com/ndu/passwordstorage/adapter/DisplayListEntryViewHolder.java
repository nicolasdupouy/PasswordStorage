package com.ndu.passwordstorage.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.model.PasswordEntry;
import com.ndu.passwordstorage.screen.DisplayListActivity;

public class DisplayListEntryViewHolder {
    private final ConstraintLayout linearLayout;
    private final EditText site;
    private final EditText login;
    private final EditText password;
    private final ImageButton optionButton;
    private int position;

    DisplayListEntryViewHolder(View convertView) {
        this.linearLayout = convertView.findViewById(R.id.passwordEntryLayout);
        this.site = convertView.findViewById(R.id.site);
        this.login = convertView.findViewById(R.id.login);
        this.password = convertView.findViewById(R.id.password);
        optionButton = convertView.findViewById(R.id.optionButton);
    }

    void fill(DisplayListActivity displayListActivity, DisplayListEntryAdapter displayListEntryAdapter, PasswordEntry passwordEntry, int position) {
        this.position = position;
        this.fillFields(passwordEntry);
        this.setMainClickListener(displayListActivity);
        this.setLongClickListener();
        this.setOptionButtonClickListener(displayListEntryAdapter);
    }

    private void fillFields(PasswordEntry passwordEntry) {
        this.site.setText(passwordEntry.getSite(), TextView.BufferType.NORMAL);
        this.login.setText(passwordEntry.getLogin(), TextView.BufferType.NORMAL);
        this.password.setText(passwordEntry.getPassword(), TextView.BufferType.NORMAL);
    }

    private void setMainClickListener(DisplayListActivity displayListActivity) {
        this.linearLayout.setOnClickListener(v -> displayListActivity.displayMemo(this.position));
    }

    private void setLongClickListener() {
        this.linearLayout.setOnLongClickListener(v -> false);
    }

    private void setOptionButtonClickListener(DisplayListEntryAdapter displayListEntryAdapter) {
        optionButton.setOnClickListener(v -> {
            PasswordEntry entryItem = displayListEntryAdapter.getItem(position);
            displayListEntryAdapter.displayAlert(entryItem);
        });
    }
}
