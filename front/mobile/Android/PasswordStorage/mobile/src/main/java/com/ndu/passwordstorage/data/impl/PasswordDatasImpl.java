package com.ndu.passwordstorage.data.impl;

import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PasswordDatasImpl implements PasswordDatas {

    @Inject
    public PasswordDatasImpl() {

    }

    @Override
    public List<PasswordEntry> readDatas() {
        List<PasswordEntry> datas = new ArrayList<>();
        datas.add(PasswordEntry.makeNew("Site Nicolas", "Nicolas", "password_1"));
        datas.add(PasswordEntry.makeNew("Site Hanane", "Hanane", "password_2"));
        datas.add(PasswordEntry.makeNew("Site Nora", "Nora", "password_3"));
        datas.add(PasswordEntry.makeNew("Site Test", "Test", "password_4"));

        return datas;
    }
}
