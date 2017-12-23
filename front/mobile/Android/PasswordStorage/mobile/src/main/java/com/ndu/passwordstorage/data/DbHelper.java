package com.ndu.passwordstorage.data;

import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.List;

public interface DbHelper {
    List<PasswordEntry> getEntries();
    boolean insertEntry(PasswordEntry passwordEntry);
    boolean updateEntry(PasswordEntry passwordEntry);
    boolean deleteEntry(PasswordEntry passwordEntry);
}
