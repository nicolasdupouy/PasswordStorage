package com.ndu.passwordstorage.data;

import com.ndu.passwordstorage.model.PasswordEntry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class DbHelperImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    public DbHelper databaseMock;

    private static PasswordEntry PASSWORD_ENTRY_1 = PasswordEntry.makeNew("site_1", "login_1", "password_1");

    @Test
    public void should_add_entry_on_empty_database() {
        List<PasswordEntry> entriesBefore = databaseMock.getEntries();
        databaseMock.insertEntry(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesAfter = databaseMock.getEntries();

        assertThat(entriesBefore.size(), is(0));
        assertThat(entriesAfter.size(), is(1));
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1));
    }
}
