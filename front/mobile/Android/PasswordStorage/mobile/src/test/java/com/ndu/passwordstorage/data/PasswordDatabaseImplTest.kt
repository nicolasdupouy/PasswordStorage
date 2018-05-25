package com.ndu.passwordstorage.data

import com.ndu.passwordstorage.model.PasswordEntry

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue

@RunWith(RobolectricTestRunner::class)
class PasswordDatabaseImplTest {

    private var passwordDatabase: PasswordDatabase? = null

    @Before
    fun setUp() {
        // Given
        passwordDatabase = PasswordDatabaseImpl(RuntimeEnvironment.application)
    }

    @Test
    fun should_add_entry_on_empty_database() {
        // When
        val entriesBefore = passwordDatabase!!.select()
        val insertFirstPasswordEntry = passwordDatabase!!.insert(PASSWORD_ENTRY_1)
        val entriesAfter = passwordDatabase!!.select()

        // Then
        assertThat(entriesBefore.size, `is`(0))
        assertThat(entriesAfter.size, `is`(1))
        assertTrue(insertFirstPasswordEntry)
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1))
    }

    @Test
    fun should_add_an_entry_only_once() {
        // When
        val entriesBefore = passwordDatabase!!.select()
        val insertFirstPasswordEntryFirstTime = passwordDatabase!!.insert(PASSWORD_ENTRY_1)
        val insertSecondPasswordEntry = passwordDatabase!!.insert(PASSWORD_ENTRY_2)
        val insertFirstPasswordEntrySecondTime = passwordDatabase!!.insert(PASSWORD_ENTRY_1)
        val entriesAfter = passwordDatabase!!.select()

        // Then
        assertThat(entriesBefore.size, `is`(0))
        assertThat(entriesAfter.size, `is`(2))
        assertTrue(insertFirstPasswordEntryFirstTime)
        assertTrue(insertSecondPasswordEntry)
        assertFalse(insertFirstPasswordEntrySecondTime)
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1))
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_2))
    }

    @Test
    fun should_update_entry_if_alone() {
        // When
        val entriesBefore = passwordDatabase!!.select()
        val insertFirstPasswordEntry = passwordDatabase!!.insert(PASSWORD_ENTRY_1)

        val updateFirstPasswordEntry = passwordDatabase!!.update(PASSWORD_ENTRY_1_UPDATED!!)
        val entriesAfter = passwordDatabase!!.select()

        // Then
        assertThat(entriesBefore.size, `is`(0))
        assertThat(entriesAfter.size, `is`(1))
        assertTrue(insertFirstPasswordEntry)
        assertTrue(updateFirstPasswordEntry)

        assertFalse(entriesAfter.contains(PASSWORD_ENTRY_1))
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1_UPDATED))
    }

    @Test
    fun should_update_entry_if_not_alone() {
        // When
        val insertFirstPasswordEntry = passwordDatabase!!.insert(PASSWORD_ENTRY_1)
        val insertSecondPasswordEntry = passwordDatabase!!.insert(PASSWORD_ENTRY_2)
        val insertThirdPasswordEntry = passwordDatabase!!.insert(PASSWORD_ENTRY_3)
        val entriesBeforeUpdates = passwordDatabase!!.select()

        val updateSecondPasswordEntry = passwordDatabase!!.update(PASSWORD_ENTRY_2_UPDATED!!)
        val entriesAfterFirstUpdate = passwordDatabase!!.select()

        val updateThirdPasswordEntry = passwordDatabase!!.update(PASSWORD_ENTRY_3_UPDATED!!)
        val entriesAfterSecondUpdate = passwordDatabase!!.select()

        val updateFirstPasswordEntry = passwordDatabase!!.update(PASSWORD_ENTRY_1_UPDATED!!)
        val entriesAfterThirdUpdate = passwordDatabase!!.select()

        // Then
        assertThat(entriesBeforeUpdates.size, `is`(3))
        assertThat(entriesAfterFirstUpdate.size, `is`(3))
        assertThat(entriesAfterSecondUpdate.size, `is`(3))
        assertThat(entriesAfterThirdUpdate.size, `is`(3))

        assertTrue(insertFirstPasswordEntry)
        assertTrue(insertSecondPasswordEntry)
        assertTrue(insertThirdPasswordEntry)

        assertTrue(updateSecondPasswordEntry)
        assertTrue(updateThirdPasswordEntry)
        assertTrue(updateFirstPasswordEntry)

        assertTrue(entriesBeforeUpdates.contains(PASSWORD_ENTRY_1))
        assertTrue(entriesBeforeUpdates.contains(PASSWORD_ENTRY_2))
        assertTrue(entriesBeforeUpdates.contains(PASSWORD_ENTRY_3))
        assertFalse(entriesBeforeUpdates.contains(PASSWORD_ENTRY_1_UPDATED))
        assertFalse(entriesBeforeUpdates.contains(PASSWORD_ENTRY_2_UPDATED))
        assertFalse(entriesBeforeUpdates.contains(PASSWORD_ENTRY_3_UPDATED))

        assertFalse(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_1))
        assertFalse(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_2))
        assertFalse(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_3))
        assertTrue(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_1_UPDATED))
        assertTrue(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_2_UPDATED))
        assertTrue(entriesAfterThirdUpdate.contains(PASSWORD_ENTRY_3_UPDATED))
    }

    @Test
    fun should_delete_existent_entry_if_alone() {
        // When
        passwordDatabase!!.insert(PASSWORD_ENTRY_1)
        val entriesBeforeDelete = passwordDatabase!!.select()

        // Then
        val deleteExistentEntry = passwordDatabase!!.delete(PASSWORD_ENTRY_1)
        val entriesAfterDelete = passwordDatabase!!.select()

        assertThat(entriesBeforeDelete.size, `is`(1))
        assertThat(entriesAfterDelete.size, `is`(0))
        assertTrue(deleteExistentEntry)

        assertFalse(entriesAfterDelete.contains(PASSWORD_ENTRY_1))
    }

    @Test
    fun should_delete_existent_entry_if_not_alone() {
        // When
        passwordDatabase!!.insert(PASSWORD_ENTRY_1)
        passwordDatabase!!.insert(PASSWORD_ENTRY_2)
        passwordDatabase!!.insert(PASSWORD_ENTRY_3)
        val entriesBeforeDelete = passwordDatabase!!.select()

        // Then
        val deleteExistentEntry = passwordDatabase!!.delete(PASSWORD_ENTRY_2)
        val entriesAfterDelete = passwordDatabase!!.select()

        assertThat(entriesBeforeDelete.size, `is`(3))
        assertThat(entriesAfterDelete.size, `is`(2))
        assertTrue(deleteExistentEntry)

        assertFalse(entriesAfterDelete.contains(PASSWORD_ENTRY_2))
    }

    @Test
    fun should_not_delete_inexistent_entry_if_alone() {
        // When
        passwordDatabase!!.insert(PASSWORD_ENTRY_1)
        val entriesBeforeDelete = passwordDatabase!!.select()

        // Then
        val deleteInexistentEntry = passwordDatabase!!.delete(PASSWORD_ENTRY_2)
        val entriesAfterDelete = passwordDatabase!!.select()

        assertThat(entriesBeforeDelete.size, `is`(1))
        assertThat(entriesAfterDelete.size, `is`(1))
        assertFalse(deleteInexistentEntry)

        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_1))
    }

    @Test
    fun should_not_delete_inexistent_entry_if_not_alone() {
        // When
        passwordDatabase!!.insert(PASSWORD_ENTRY_1)
        passwordDatabase!!.insert(PASSWORD_ENTRY_2)
        val entriesBeforeDelete = passwordDatabase!!.select()

        // Then
        val deleteInexistentEntry = passwordDatabase!!.delete(PASSWORD_ENTRY_3)
        val entriesAfterDelete = passwordDatabase!!.select()

        assertThat(entriesBeforeDelete.size, `is`(2))
        assertThat(entriesAfterDelete.size, `is`(2))
        assertFalse(deleteInexistentEntry)

        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_1))
        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_2))
    }

    companion object {

        private val PASSWORD_ENTRY_1 = PasswordEntry.makeNew(
                "site_1",
                "login_1",
                "password_1")
        private val PASSWORD_ENTRY_2 = PasswordEntry.makeNew(
                "site_2",
                "login_2",
                "password_2")
        private val PASSWORD_ENTRY_3 = PasswordEntry.makeNew(
                "site_3",
                "login_3",
                "password_3")

        private var PASSWORD_ENTRY_1_UPDATED: PasswordEntry
        private var PASSWORD_ENTRY_2_UPDATED: PasswordEntry
        private var PASSWORD_ENTRY_3_UPDATED: PasswordEntry

        init {
            PASSWORD_ENTRY_1_UPDATED = PasswordEntry[PASSWORD_ENTRY_1]
            PASSWORD_ENTRY_1_UPDATED!!.update(
                    "site_1_updated",
                    "login_1_updated",
                    "password_1_updated")
            PASSWORD_ENTRY_2_UPDATED = PasswordEntry[PASSWORD_ENTRY_2]
            PASSWORD_ENTRY_2_UPDATED!!.update(
                    "site_2_updated",
                    "login_2_updated",
                    "password_2_updated")
            PASSWORD_ENTRY_3_UPDATED = PasswordEntry[PASSWORD_ENTRY_3]
            PASSWORD_ENTRY_3_UPDATED!!.update(
                    "site_3_updated",
                    "login_3_updated",
                    "password_3_updated")
        }
    }
}
