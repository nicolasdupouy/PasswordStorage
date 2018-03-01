package com.ndu.passwordstorage.screen;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.data.PasswordDatabase;
import com.ndu.passwordstorage.data.PasswordDatabaseImpl;
import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DisplayListActivity extends ListActivity {

    private static final int CONTEXT_MENU_DELETE_ID = 1;

    private PasswordDatabase passwordDatabase;
    private Unbinder unbinder;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        unbinder = ButterKnife.bind(this);
        passwordDatabase = new PasswordDatabaseImpl(getApplicationContext());

        registerForContextMenu(getListView());
        refreshDisplay();
        setCreateAction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        passwordDatabase.closeDatabase();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        displayMemo(position);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.add(0, CONTEXT_MENU_DELETE_ID, 0, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CONTEXT_MENU_DELETE_ID) {
            int currentPositionInList = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
            deleteMemo(currentPositionInList);
            refreshDisplay();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PasswordEntry passwordEntryUpdated = PasswordEntry.readInfos(data);

            if (requestCode == MemoActivity.CREATE_MEMO) {
                passwordDatabase.insert(passwordEntryUpdated);
            } else if (requestCode == MemoActivity.DISPLAY_MEMO) {
                passwordDatabase.update(passwordEntryUpdated);
            }

            refreshDisplay();
        }
    }

    private void refreshDisplay() {
        ListView listView = getListView();
        List<String> names = fillMemoList();
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(namesAdapter);
    }

    @NonNull
    private List<String> fillMemoList() {
        List<PasswordEntry> passwordEntries = passwordDatabase.select();
        List<String> names = new ArrayList<>();
        for (PasswordEntry entry : passwordEntries) {
            names.add(entry.toString());
        }
        return names;
    }

    private void setCreateAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMemo();
            }
        });
    }

    private void createMemo() {
        Intent memoActivityIntent = new Intent(this, MemoActivity.class);

        PasswordEntry passwordEntry = PasswordEntry.makeNew();
        passwordEntry.putInfos(memoActivityIntent);

        startActivityForResult(memoActivityIntent, MemoActivity.CREATE_MEMO);
    }

    private void deleteMemo(int position) {
        List<PasswordEntry> passwordEntries = passwordDatabase.select();
        PasswordEntry passwordEntry = passwordEntries.get(position);
        passwordDatabase.delete(passwordEntry);
    }

    private void displayMemo(int position) {
        Intent memoActivityIntent = new Intent(this, MemoActivity.class);

        List<PasswordEntry> passwordEntries = passwordDatabase.select();
        PasswordEntry passwordEntry = passwordEntries.get(position);
        passwordEntry.putInfos(memoActivityIntent);

        startActivityForResult(memoActivityIntent, MemoActivity.DISPLAY_MEMO);
    }
}
