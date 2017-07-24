package com.ndu.passwordstorage.data.impl;

import com.ndu.passwordstorage.data.PasswordDatas;
import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PasswordDatasImpl implements PasswordDatas {

    @Override
    public List<PasswordEntry> readDatas() {
        List<PasswordEntry> datas = new ArrayList<>();
        datas.add(new PasswordEntry("Site 1", "NicolasD", "password_1"));
        datas.add(new PasswordEntry("Site 1", "Hanane", "password_2"));
        datas.add(new PasswordEntry("Site 1", "Nora", "password_3"));

        return datas;
    }
}
