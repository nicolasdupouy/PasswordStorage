package com.ndu.passwordstorage.adapter;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

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
        viewHolder.fill(this.displayListActivity, passwordEntry, position);

        ImageButton optionButton = convertView.findViewById(R.id.optionButton);
        optionButton.setOnClickListener(v -> {
            int myPosition = viewHolder.getPosition();
            PasswordEntry entryItem = getItem(myPosition);
            displayAlert(entryItem);
        });

        return convertView;
    }


    private void displayAlert(PasswordEntry entryItem) {
        final CharSequence[] items = {"delete", "show password"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose an action");
        builder.setItems(items, (dialog, item) -> Toast.makeText(getContext(),items[item] + " " + entryItem.toString(), Toast.LENGTH_SHORT).show());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
