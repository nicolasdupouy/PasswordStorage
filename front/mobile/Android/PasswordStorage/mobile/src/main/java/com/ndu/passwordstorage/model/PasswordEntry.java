package com.ndu.passwordstorage.model;

import android.os.Bundle;

public class PasswordEntry {
    public static final String BUNDLE = "PasswordEntryBundle";
    private static final String SITE = "site";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private String site;
    private String login;
    private String password;

    public PasswordEntry(String site, String login, String password) {
        this.site = site;
        this.login = login;
        this.password = password;
    }

    public Bundle createBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(SITE, this.site);
        bundle.putString(LOGIN, this.login);
        bundle.putString(PASSWORD, this.password);

        return bundle;
    }

    public static PasswordEntry readFromBundle(Bundle bundle) {
        return new PasswordEntry(
                bundle.getString(SITE),
                bundle.getString(LOGIN),
                bundle.getString(PASSWORD));
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
