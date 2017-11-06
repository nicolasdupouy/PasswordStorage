package com.ndu.passwordstorage.model;

import android.content.Intent;

import com.ndu.passwordstorage.data.DataContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PasswordEntry {
    private String key;
    private String site;
    private String login;
    private String password;

    private static Random random = new Random(1);

    private PasswordEntry() {}

    public static PasswordEntry makeNew(String site, String login, String password) {
        PasswordEntry passwordEntry = PasswordEntry.makeNew();
        passwordEntry.update(site, login, password);

        return passwordEntry;
    }

    private static PasswordEntry makeNew() {
        PasswordEntry passwordEntry = new PasswordEntry();
        passwordEntry.key = createKey();

        return passwordEntry;
    }

    private static String createKey() {
        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);

        String pattern = "yyyy-MM-dd HH:mm:ss Z";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
        return formatter.format(new Date()) + random.nextInt();
    }

    public void update(String site, String login, String password) {
        this.site = site;
        this.login = login;
        this.password = password;
    }

    public void update(PasswordEntry passwordEntry) {
        this.site = passwordEntry.site;
        this.login = passwordEntry.login;
        this.password = passwordEntry.password;
    }

    public Intent giveInfos() {
        Intent intent = new Intent();
        putInfos(intent);

        return intent;
    }

    public void putInfos(Intent intent) {
        intent.putExtra(DataContract.DataEntry.COLUMN_NAME_KEY, this.key);
        intent.putExtra(DataContract.DataEntry.COLUMN_NAME_SITE, this.site);
        intent.putExtra(DataContract.DataEntry.COLUMN_NAME_LOGIN, this.login);
        intent.putExtra(DataContract.DataEntry.COLUMN_NAME_PASSWORD, this.password);
    }

    public static PasswordEntry readInfos(Intent intent) {
        PasswordEntry passwordEntry = new PasswordEntry();
        passwordEntry.key = intent.getStringExtra(DataContract.DataEntry.COLUMN_NAME_KEY);
        passwordEntry.site = intent.getStringExtra(DataContract.DataEntry.COLUMN_NAME_SITE);
        passwordEntry.login = intent.getStringExtra(DataContract.DataEntry.COLUMN_NAME_LOGIN);
        passwordEntry.password = intent.getStringExtra(DataContract.DataEntry.COLUMN_NAME_PASSWORD);

        return passwordEntry;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
