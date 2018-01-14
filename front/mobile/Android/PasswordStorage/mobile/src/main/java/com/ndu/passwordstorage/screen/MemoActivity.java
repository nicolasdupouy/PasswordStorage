package com.ndu.passwordstorage.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.model.PasswordEntry;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MemoActivity extends AppCompatActivity {

    public static final int DISPLAY_MEMO = 1;
    public static final int CREATE_MEMO = 2;

    @BindView(R.id.site)
    EditText site;
    @BindView(R.id.login)
    EditText login;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private PasswordEntry passwordEntry;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        unbinder = ButterKnife.bind(this);

        readInfos();
        setToolbar();
    }

    private void readInfos() {
        Intent intent = this.getIntent();
        passwordEntry = PasswordEntry.readInfos(intent);

        this.site.setText(passwordEntry.getSite(), TextView.BufferType.EDITABLE);
        this.login.setText(passwordEntry.getLogin(), TextView.BufferType.EDITABLE);
        this.password.setText(passwordEntry.getPassword(), TextView.BufferType.EDITABLE);
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbinder.unbind();
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void update(View view) {
        saveMemo();
        Intent intent = passwordEntry.giveInfos();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void saveMemo() {
        passwordEntry.update(
                this.site.getText().toString(),
                this.login.getText().toString(),
                this.password.getText().toString());
    }
}
