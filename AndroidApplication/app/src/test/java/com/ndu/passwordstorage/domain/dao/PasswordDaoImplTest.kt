package com.ndu.passwordstorage.domain.dao

import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.infrastructure.dao.PasswordDaoImpl
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class PasswordDaoImplTest {

    private lateinit var passwordDao: PasswordDaoImpl

    companion object {
        val PASSWORD_ENTRY_1 = PasswordEntry(1, "site_1","login_1", "password_1")
        val PASSWORD_ENTRY_2 = PasswordEntry(2, "site_1","login_1", "password_1")
        val PASSWORD_ENTRY_3 = PasswordEntry(3, "site_1","login_1", "password_1")

        val PASSWORD_ENTRY_1_UPDATED = PasswordEntry(1, "site_1_updated","login_1_updated", "password_1_updated")
        val PASSWORD_ENTRY_2_UPDATED = PasswordEntry(2, "site_1_updated","login_1_updated", "password_1_updated")
        val PASSWORD_ENTRY_3_UPDATED = PasswordEntry(3, "site_1_updated","login_1_updated", "password_1_updated")
    }

    @Before
    fun setUp() {
        // Given
        passwordDao = PasswordDaoImpl(RuntimeEnvironment.application)
    }

    @Test
    fun `should add entry on empty database`() {
        // When
        val entriesBefore = passwordDao.selectAll()
        val insertFirstPasswordEntry = passwordDao.insert(PASSWORD_ENTRY_1)
        val entriesAfter = passwordDao.selectAll()

        // Then
        assertThat<Int>(entriesBefore.size, `is`<Int>(0))
        assertThat<Int>(entriesAfter.size, `is`<Int>(1))
        assertTrue(insertFirstPasswordEntry)
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1))
    }

    /*
    @Test
    public void should_add_an_entry_only_once() {
        // When
        List<PasswordEntry> entriesBefore = passwordDatabase.select();
        boolean insertFirstPasswordEntryFirstTime = passwordDatabase.insert(PASSWORD_ENTRY_1);
        boolean insertSecondPasswordEntry = passwordDatabase.insert(PASSWORD_ENTRY_2);
        boolean insertFirstPasswordEntrySecondTime = passwordDatabase.insert(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesAfter = passwordDatabase.select();

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
        List<PasswordEntry> entriesBefore = passwordDatabase.select();
        boolean insertFirstPasswordEntry = passwordDatabase.insert(PASSWORD_ENTRY_1);

        boolean updateFirstPasswordEntry = passwordDatabase.update(PASSWORD_ENTRY_1_UPDATED);
        List<PasswordEntry> entriesAfter = passwordDatabase.select();

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
        boolean insertFirstPasswordEntry = passwordDatabase.insert(PASSWORD_ENTRY_1);
        boolean insertSecondPasswordEntry = passwordDatabase.insert(PASSWORD_ENTRY_2);
        boolean insertThirdPasswordEntry = passwordDatabase.insert(PASSWORD_ENTRY_3);
        List<PasswordEntry> entriesBeforeUpdates = passwordDatabase.select();

        boolean updateSecondPasswordEntry = passwordDatabase.update(PASSWORD_ENTRY_2_UPDATED);
        List<PasswordEntry> entriesAfterFirstUpdate = passwordDatabase.select();

        boolean updateThirdPasswordEntry = passwordDatabase.update(PASSWORD_ENTRY_3_UPDATED);
        List<PasswordEntry> entriesAfterSecondUpdate = passwordDatabase.select();

        boolean updateFirstPasswordEntry = passwordDatabase.update(PASSWORD_ENTRY_1_UPDATED);
        List<PasswordEntry> entriesAfterThirdUpdate = passwordDatabase.select();

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
        passwordDatabase.insert(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesBeforeDelete = passwordDatabase.select();

        // Then
        boolean deleteExistentEntry = passwordDatabase.delete(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesAfterDelete = passwordDatabase.select();

        assertThat(entriesBeforeDelete.size(), is(1));
        assertThat(entriesAfterDelete.size(), is(0));
        assertTrue(deleteExistentEntry);

        assertFalse(entriesAfterDelete.contains(PASSWORD_ENTRY_1));
    }

    @Test
    public void should_delete_existent_entry_if_not_alone() {
        // When
        passwordDatabase.insert(PASSWORD_ENTRY_1);
        passwordDatabase.insert(PASSWORD_ENTRY_2);
        passwordDatabase.insert(PASSWORD_ENTRY_3);
        List<PasswordEntry> entriesBeforeDelete = passwordDatabase.select();

        // Then
        boolean deleteExistentEntry = passwordDatabase.delete(PASSWORD_ENTRY_2);
        List<PasswordEntry> entriesAfterDelete = passwordDatabase.select();

        assertThat(entriesBeforeDelete.size(), is(3));
        assertThat(entriesAfterDelete.size(), is(2));
        assertTrue(deleteExistentEntry);

        assertFalse(entriesAfterDelete.contains(PASSWORD_ENTRY_2));
    }

    @Test
    public void should_not_delete_inexistent_entry_if_alone() {
        // When
        passwordDatabase.insert(PASSWORD_ENTRY_1);
        List<PasswordEntry> entriesBeforeDelete = passwordDatabase.select();

        // Then
        boolean deleteInexistentEntry = passwordDatabase.delete(PASSWORD_ENTRY_2);
        List<PasswordEntry> entriesAfterDelete = passwordDatabase.select();

        assertThat(entriesBeforeDelete.size(), is(1));
        assertThat(entriesAfterDelete.size(), is(1));
        assertFalse(deleteInexistentEntry);

        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_1));
    }

    @Test
    public void should_not_delete_inexistent_entry_if_not_alone() {
        // When
        passwordDatabase.insert(PASSWORD_ENTRY_1);
        passwordDatabase.insert(PASSWORD_ENTRY_2);
        List<PasswordEntry> entriesBeforeDelete = passwordDatabase.select();

        // Then
        boolean deleteInexistentEntry = passwordDatabase.delete(PASSWORD_ENTRY_3);
        List<PasswordEntry> entriesAfterDelete = passwordDatabase.select();

        assertThat(entriesBeforeDelete.size(), is(2));
        assertThat(entriesAfterDelete.size(), is(2));
        assertFalse(deleteInexistentEntry);

        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_1));
        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_2));
    }
     */
}