package com.ndu.passwordstorage.data;

import com.ndu.passwordstorage.model.PasswordEntry;
import com.ndu.passwordstorage.screen.DisplayListActivity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class DbHelperImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static PasswordEntry PASSWORD_ENTRY_1 = PasswordEntry.makeNew("site_1", "login_1", "password_1");
    private static PasswordEntry PASSWORD_ENTRY_2 = PasswordEntry.makeNew("site_2", "login_2", "password_2");

    private DbHelper dbHelper;

    @Before
    public void setUp() {
        // Given
        DisplayListActivity displayListActivity = Robolectric.setupActivity(DisplayListActivity.class);
        dbHelper = new DbHelperImpl(displayListActivity.getApplicationContext());
    }

    @Test
    public void should_add_entry_on_empty_database() {
        // When
        List<PasswordEntry> entriesBefore = dbHelper.getEntries();
        dbHelper.insertEntry(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesAfter = dbHelper.getEntries();

        // Then
        assertThat(entriesBefore.size(), is(0));
        assertThat(entriesAfter.size(), is(1));
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1));
    }

    @Test
    public void should_add_an_entry_only_once() {
        // When
        List<PasswordEntry> entriesBefore = dbHelper.getEntries();
        dbHelper.insertEntry(PASSWORD_ENTRY_1);
        dbHelper.insertEntry(PASSWORD_ENTRY_2);
        dbHelper.insertEntry(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesAfter = dbHelper.getEntries();

        // Then
        assertThat(entriesBefore.size(), is(0));
        assertThat(entriesAfter.size(), is(2));
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1));
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_2));
    }
}
