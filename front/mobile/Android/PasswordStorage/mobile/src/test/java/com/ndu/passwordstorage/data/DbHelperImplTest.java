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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class DbHelperImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static PasswordEntry PASSWORD_ENTRY_1 = PasswordEntry.makeNew(
            "site_1",
            "login_1",
            "password_1");
    private static PasswordEntry PASSWORD_ENTRY_2 = PasswordEntry.makeNew(
            "site_2",
            "login_2",
            "password_2");
    private static PasswordEntry PASSWORD_ENTRY_3 = PasswordEntry.makeNew(
            "site_3",
            "login_3",
            "password_3");

    private static PasswordEntry PASSWORD_ENTRY_1_UPDATED;
    private static PasswordEntry PASSWORD_ENTRY_2_UPDATED;
    private static PasswordEntry PASSWORD_ENTRY_3_UPDATED;

    static {
        PASSWORD_ENTRY_1_UPDATED = PasswordEntry.get(PASSWORD_ENTRY_1);
        PASSWORD_ENTRY_1_UPDATED.update(
                "site_1_updated",
                "login_1_updated",
                "password_1_updated");
        PASSWORD_ENTRY_2_UPDATED = PasswordEntry.get(PASSWORD_ENTRY_2);
        PASSWORD_ENTRY_2_UPDATED.update(
                "site_2_updated",
                "login_2_updated",
                "password_2_updated");
        PASSWORD_ENTRY_3_UPDATED = PasswordEntry.get(PASSWORD_ENTRY_3);
        PASSWORD_ENTRY_3_UPDATED.update(
                "site_3_updated",
                "login_3_updated",
                "password_3_updated");
    }

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
        boolean insertFirstPasswordEntry = dbHelper.insertEntry(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesAfter = dbHelper.getEntries();

        // Then
        assertThat(entriesBefore.size(), is(0));
        assertThat(entriesAfter.size(), is(1));
        assertTrue(insertFirstPasswordEntry);
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1));
    }

    @Test
    public void should_add_an_entry_only_once() {
        // When
        List<PasswordEntry> entriesBefore = dbHelper.getEntries();
        boolean insertFirstPasswordEntryFirstTime = dbHelper.insertEntry(PASSWORD_ENTRY_1);
        boolean insertSecondPasswordEntry = dbHelper.insertEntry(PASSWORD_ENTRY_2);
        boolean insertFirstPasswordEntrySecondTime = dbHelper.insertEntry(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesAfter = dbHelper.getEntries();

        // Then
        assertThat(entriesBefore.size(), is(0));
        assertThat(entriesAfter.size(), is(2));
        assertTrue(insertFirstPasswordEntryFirstTime);
        assertTrue(insertSecondPasswordEntry);
        assertFalse(insertFirstPasswordEntrySecondTime);
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1));
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_2));
    }

    @Test
    public void should_update_entry_if_alone() {
        // When
        List<PasswordEntry> entriesBefore = dbHelper.getEntries();
        boolean insertFirstPasswordEntry = dbHelper.insertEntry(PASSWORD_ENTRY_1);

        boolean updateFirstPasswordEntry = dbHelper.updateEntry(PASSWORD_ENTRY_1_UPDATED);
        List<PasswordEntry> entriesAfter = dbHelper.getEntries();

        // Then
        assertThat(entriesBefore.size(), is(0));
        assertThat(entriesAfter.size(), is(1));
        assertTrue(insertFirstPasswordEntry);
        assertTrue(updateFirstPasswordEntry);

        assertFalse(entriesAfter.contains(PASSWORD_ENTRY_1));
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1_UPDATED));
    }

    @Test
    public void should_update_entry_if_not_alone() {
        // When
        boolean insertFirstPasswordEntry = dbHelper.insertEntry(PASSWORD_ENTRY_1);
        boolean insertSecondPasswordEntry = dbHelper.insertEntry(PASSWORD_ENTRY_2);
        boolean insertThirdPasswordEntry = dbHelper.insertEntry(PASSWORD_ENTRY_3);
        List<PasswordEntry> entriesBeforeUpdates = dbHelper.getEntries();

        boolean updateSecondPasswordEntry = dbHelper.updateEntry(PASSWORD_ENTRY_2_UPDATED);
        List<PasswordEntry> entriesAfterFirstUpdate = dbHelper.getEntries();

        boolean updateThirdPasswordEntry = dbHelper.updateEntry(PASSWORD_ENTRY_3_UPDATED);
        List<PasswordEntry> entriesAfterSecondUpdate = dbHelper.getEntries();

        boolean updateFirstPasswordEntry = dbHelper.updateEntry(PASSWORD_ENTRY_1_UPDATED);
        List<PasswordEntry> entriesAfterThirdUpdate = dbHelper.getEntries();

        // Then
        assertThat(entriesBeforeUpdates.size(), is(3));
        assertThat(entriesAfterFirstUpdate.size(), is(3));
        assertThat(entriesAfterSecondUpdate.size(), is(3));
        assertThat(entriesAfterThirdUpdate.size(), is(3));

        assertTrue(insertFirstPasswordEntry);
        assertTrue(insertSecondPasswordEntry);
        assertTrue(insertThirdPasswordEntry);

        assertTrue(updateSecondPasswordEntry);
        assertTrue(updateThirdPasswordEntry);
        assertTrue(updateFirstPasswordEntry);

        assertTrue(entriesBeforeUpdates.contains(PASSWORD_ENTRY_1));
        assertTrue(entriesBeforeUpdates.contains(PASSWORD_ENTRY_2));
        assertTrue(entriesBeforeUpdates.contains(PASSWORD_ENTRY_3));
        assertFalse(entriesBeforeUpdates.contains(PASSWORD_ENTRY_1_UPDATED));
        assertFalse(entriesBeforeUpdates.contains(PASSWORD_ENTRY_2_UPDATED));
        assertFalse(entriesBeforeUpdates.contains(PASSWORD_ENTRY_3_UPDATED));

        assertFalse(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_1));
        assertFalse(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_2));
        assertFalse(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_3));
        assertTrue(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_1_UPDATED));
        assertTrue(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_2_UPDATED));
        assertTrue(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_3_UPDATED));
    }

    @Test
    public void should_delete_existent_entry_if_alone() {
        // When
        dbHelper.insertEntry(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesBeforeDelete = dbHelper.getEntries();

        // Then
        boolean deleteExistentEntry = dbHelper.deleteEntry(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesAfterDelete = dbHelper.getEntries();

        assertThat(entriesBeforeDelete.size(), is(1));
        assertThat(entriesAfterDelete.size(), is(0));
        assertTrue(deleteExistentEntry);

        assertFalse(entriesAfterDelete.contains(PASSWORD_ENTRY_1));
    }

    @Test
    public void should_delete_existent_entry_if_not_alone() {
        // When
        dbHelper.insertEntry(PASSWORD_ENTRY_1);
        dbHelper.insertEntry(PASSWORD_ENTRY_2);
        dbHelper.insertEntry(PASSWORD_ENTRY_3);
        List<PasswordEntry> entriesBeforeDelete = dbHelper.getEntries();

        // Then
        boolean deleteExistentEntry = dbHelper.deleteEntry(PASSWORD_ENTRY_2);
        List<PasswordEntry> entriesAfterDelete = dbHelper.getEntries();

        assertThat(entriesBeforeDelete.size(), is(3));
        assertThat(entriesAfterDelete.size(), is(2));
        assertTrue(deleteExistentEntry);

        assertFalse(entriesAfterDelete.contains(PASSWORD_ENTRY_2));
    }

    @Test
    public void should_not_delete_inexistent_entry_if_alone() {
        // When
        dbHelper.insertEntry(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesBeforeDelete = dbHelper.getEntries();

        // Then
        boolean deleteInexistentEntry = dbHelper.deleteEntry(PASSWORD_ENTRY_2);
        List<PasswordEntry> entriesAfterDelete = dbHelper.getEntries();

        assertThat(entriesBeforeDelete.size(), is(1));
        assertThat(entriesAfterDelete.size(), is(1));
        assertFalse(deleteInexistentEntry);

        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_1));
    }

    @Test
    public void should_not_delete_inexistent_entry_if_not_alone() {
        // When
        dbHelper.insertEntry(PASSWORD_ENTRY_1);
        dbHelper.insertEntry(PASSWORD_ENTRY_2);
        List<PasswordEntry> entriesBeforeDelete = dbHelper.getEntries();

        // Then
        boolean deleteInexistentEntry = dbHelper.deleteEntry(PASSWORD_ENTRY_3);
        List<PasswordEntry> entriesAfterDelete = dbHelper.getEntries();

        assertThat(entriesBeforeDelete.size(), is(2));
        assertThat(entriesAfterDelete.size(), is(2));
        assertFalse(deleteInexistentEntry);

        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_1));
        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_2));
    }
}
