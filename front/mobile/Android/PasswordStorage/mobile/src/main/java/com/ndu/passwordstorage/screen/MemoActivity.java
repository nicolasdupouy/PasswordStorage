package com.ndu.passwordstorage.screen;

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

import butterknife.Bind;
import butterknife.ButterKnife;

public class MemoActivity extends AppCompatActivity {

    public static final int DISPLAY_MEMO = 1;
    public static final int CREATE_MEMO = 2;

    @Bind(R.id.site) EditText site;
    @Bind(R.id.login) EditText login;
    @Bind(R.id.password) EditText password;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Bundle bundleExtra = intent.getBundleExtra(PasswordEntry.BUNDLE);
        PasswordEntry pe = PasswordEntry.readFromBundle(bundleExtra);

        this.site.setText(pe.getSite(), TextView.BufferType.EDITABLE);
        this.login.setText(pe.getLogin(), TextView.BufferType.EDITABLE);
        this.password.setText(pe.getPassword(), TextView.BufferType.EDITABLE);

        setToolbar();
        setCreateAction();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setCreateAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ButterKnife.unbind(this);
    }

    public void cancel(View view) {

    }

    public void update(View view) {

    }
}
