package com.ndu.passwordstorage.screen;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.data.impl.PasswordDatasImpl;
import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayListActivity extends ListActivity {

    private static final PasswordDatas passwordDatas = new PasswordDatasImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        ListView listView = getListView();

        List<PasswordEntry> passwordEntries = passwordDatas.readDatas();
        List<String> names = new ArrayList<>();
        for (PasswordEntry entry: passwordEntries) {
            names.add(entry.getLogin());
        }

        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(namesAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.v("TEST", "position = " + position + " / id = " + id);
    }
}
