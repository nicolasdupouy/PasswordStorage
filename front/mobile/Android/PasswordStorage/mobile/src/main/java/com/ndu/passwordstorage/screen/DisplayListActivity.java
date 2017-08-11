package com.ndu.passwordstorage.screen;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ndu.passwordstorage.MainApp;
import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.data.impl.PasswordDatasImpl;
import com.ndu.passwordstorage.di.Injectable;
import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DisplayListActivity extends ListActivity implements Injectable {

    @Inject
    PasswordDatasImpl passwordDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectMe();

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

    @Override
    public void injectMe() {
        ((MainApp) getApplication()).getAppComponent().inject(this);
    }
}
