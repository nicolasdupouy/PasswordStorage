package com.ndu.passwordstorage.model;

import android.content.Intent;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PasswordEntry {
    private static final String KEY = "key";
    private static final String SITE = "site";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

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

    public Intent giveInfos() {
        Intent intent = new Intent();
        putInfos(intent);

        return intent;
    }

    public void putInfos(Intent intent) {
        intent.putExtra(KEY, this.key);
        intent.putExtra(SITE, this.site);
        intent.putExtra(LOGIN, this.login);
        intent.putExtra(PASSWORD, this.password);
    }

    public static PasswordEntry readInfos(Intent intent) {
        PasswordEntry passwordEntry = new PasswordEntry();
        passwordEntry.key = intent.getStringExtra(KEY);
        passwordEntry.site = intent.getStringExtra(SITE);
        passwordEntry.login = intent.getStringExtra(LOGIN);
        passwordEntry.password = intent.getStringExtra(PASSWORD);

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
