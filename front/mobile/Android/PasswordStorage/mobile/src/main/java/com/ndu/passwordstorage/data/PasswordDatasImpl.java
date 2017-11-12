package com.ndu.passwordstorage.data;

import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.ArrayList;
import java.util.List;

public class PasswordDatasImpl implements PasswordDatas {

    private List<PasswordEntry> datas = new ArrayList<>();

    public  PasswordDatasImpl() {
        fillDatasIfEmpty();
    }

    private void fillDatasIfEmpty() {
        datas.add(PasswordEntry.makeNew("Site 1", "Login 1", "password 1"));
        datas.add(PasswordEntry.makeNew("Site 2", "Login 2", "password 2"));
        datas.add(PasswordEntry.makeNew("Site 3", "Login 3", "password 3"));
        datas.add(PasswordEntry.makeNew("Site 4", "Login 4", "password 4"));
    }

    @Override
    public List<PasswordEntry> readDatas() {
        return datas;
    }

    @Override
    public void update(PasswordEntry passwordEntryUpdated) {
        for(PasswordEntry storedPasswordEntry: readDatas()) {
            if (storedPasswordEntry.getKey().equals(passwordEntryUpdated.getKey())) {
                storedPasswordEntry.update(passwordEntryUpdated);
            }
        }
    }
}
