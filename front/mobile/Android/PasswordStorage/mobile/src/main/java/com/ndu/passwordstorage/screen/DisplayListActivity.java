package com.ndu.passwordstorage.screen;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

    private List<PasswordEntry> passwordEntries;

    @Override
    public void injectMe() {
        ((MainApp) getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectMe();

        setContentView(R.layout.activity_display_list);
        ListView listView = getListView();

        List<String> names = fillMemoList();
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(namesAdapter);
    }

    @NonNull
    private List<String> fillMemoList() {
        this.passwordEntries = passwordDatas.readDatas();
        List<String> names = new ArrayList<>();
        for (PasswordEntry entry : this.passwordEntries) {
            names.add(entry.getSite() + "/"
                    + entry.getLogin() + "/"
                    + entry.getPassword());
        }
        return names;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        displayMemo(position);
    }

    private void displayMemo(int position) {
        Intent memoActivityIntent = new Intent(this, MemoActivity.class);

        PasswordEntry passwordEntry = this.passwordEntries.get(position);
        passwordEntry.putInfos(memoActivityIntent);

        startActivityForResult(memoActivityIntent, MemoActivity.DISPLAY_MEMO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MemoActivity.DISPLAY_MEMO
                && resultCode == Activity.RESULT_OK) {
            PasswordEntry passwordEntry = PasswordEntry.readInfos(data);
            Log.v("TODO", "Return is not exploited yet.");
        }
    }
}
