package com.ndu.passwordstorage.data;

import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.List;

public interface DbHelper {
    List<PasswordEntry> getEntries();
    void insertEntry(PasswordEntry passwordEntry);
    void updateEntry(PasswordEntry passwordEntry);
    void deleteEntry(PasswordEntry passwordEntry);
}
