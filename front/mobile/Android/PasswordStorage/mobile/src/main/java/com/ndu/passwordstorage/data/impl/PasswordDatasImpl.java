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
        datas.add(new PasswordEntry("Site 1", "NicolasD", "password_1"));
        datas.add(new PasswordEntry("Site 1", "Hanane", "password_2"));
        datas.add(new PasswordEntry("Site 1", "Nora", "password_3"));
        datas.add(new PasswordEntry("Site 1", "Test", "password_4"));

        return datas;
    }
}
