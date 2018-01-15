package com.ndu.passwordstorage.data;

import com.ndu.passwordstorage.model.PasswordEntry;

import java.util.List;

public interface PasswordDatabase {
    List<PasswordEntry> select();
    boolean insert(PasswordEntry passwordEntry);
    boolean update(PasswordEntry passwordEntry);
    boolean delete(PasswordEntry passwordEntry);
    void closeDatabase();
}
