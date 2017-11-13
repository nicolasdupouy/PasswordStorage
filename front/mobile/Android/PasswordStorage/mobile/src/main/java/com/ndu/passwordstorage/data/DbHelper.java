package com.ndu.passwordstorage.data;

import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.List;

public interface DbHelper {
    List<PasswordEntry> getEntries();
    void insertEntry();
    void updateEntry();
    void deleteEntry();
}
