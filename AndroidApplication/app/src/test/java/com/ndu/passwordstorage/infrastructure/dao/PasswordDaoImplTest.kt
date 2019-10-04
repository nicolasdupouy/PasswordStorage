package com.ndu.passwordstorage.domain.dao

import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.application.utils.Injector
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class PasswordDaoImplTest {

    private lateinit var passwordDao: PasswordDao

    companion object {
        val PASSWORD_ENTRY_1 = PasswordEntry(1, "site_1","login_1", "password_1")
        val PASSWORD_ENTRY_2 = PasswordEntry(2, "site_2","login_2", "password_2")
        val PASSWORD_ENTRY_3 = PasswordEntry(3, "site_3","login_3", "password_3")

        val PASSWORD_ENTRY_1_UPDATED = PasswordEntry(1, "site_1_updated","login_1_updated", "password_1_updated")
        val PASSWORD_ENTRY_2_UPDATED = PasswordEntry(2, "site_2_updated","login_2_updated", "password_2_updated")
        val PASSWORD_ENTRY_3_UPDATED = PasswordEntry(3, "site_3_updated","login_3_updated", "password_3_updated")
    }

    @Before
    fun setUp() {
        // Given
        passwordDao = Injector.getPasswordDao(RuntimeEnvironment.application)
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

    @Test
    fun `should add an entry only once`() {
        // When
        val entriesBefore = passwordDao.selectAll()
        val insertFirstPasswordEntryFirstTime = passwordDao.insert(PASSWORD_ENTRY_1)
        val insertSecondPasswordEntry = passwordDao.insert(PASSWORD_ENTRY_2)
        val insertFirstPasswordEntrySecondTime = passwordDao.insert(PASSWORD_ENTRY_1)
        val entriesAfter = passwordDao.selectAll()

        // Then
        assertThat(entriesBefore.size, `is`<Int>(0))
        assertThat(entriesAfter.size, `is`<Int>(2))
        assertTrue(insertFirstPasswordEntryFirstTime)
        assertTrue(insertSecondPasswordEntry)
        assertFalse(insertFirstPasswordEntrySecondTime)
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1))
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_2))
    }

    @Test
    fun `should update entry if alone`() {
        // When
        val entriesBefore = passwordDao.selectAll()
        val insertFirstPasswordEntry = passwordDao.insert(PASSWORD_ENTRY_1)

        val updateFirstPasswordEntry = passwordDao.update(PASSWORD_ENTRY_1_UPDATED)
        val entriesAfter = passwordDao.selectAll()

        // Then
        assertThat(entriesBefore.size, `is`<Int>(0))
        assertThat(entriesAfter.size, `is`<Int>(1))
        assertTrue(insertFirstPasswordEntry)
        assertTrue(updateFirstPasswordEntry)

        assertFalse(entriesAfter.contains(PASSWORD_ENTRY_1))
        assertTrue(entriesAfter.contains(PASSWORD_ENTRY_1_UPDATED))
    }

    @Test
    fun `should update entry if not alone`() {
        // When
        val insertFirstPasswordEntry = passwordDao.insert(PASSWORD_ENTRY_1)
        val insertSecondPasswordEntry = passwordDao.insert(PASSWORD_ENTRY_2)
        val insertThirdPasswordEntry = passwordDao.insert(PASSWORD_ENTRY_3)
        val entriesBeforeUpdates = passwordDao.selectAll()

        val updateSecondPasswordEntry = passwordDao.update(PASSWORD_ENTRY_2_UPDATED)
        val entriesAfterFirstUpdate = passwordDao.selectAll()

        val updateThirdPasswordEntry = passwordDao.update(PASSWORD_ENTRY_3_UPDATED)
        val entriesAfterSecondUpdate = passwordDao.selectAll()

        val updateFirstPasswordEntry = passwordDao.update(PASSWORD_ENTRY_1_UPDATED)
        val entriesAfterThirdUpdate = passwordDao.selectAll()

        // Then
        assertThat(entriesBeforeUpdates.size, `is`<Int>(3))
        assertThat(entriesAfterFirstUpdate.size, `is`<Int>(3))
        assertThat(entriesAfterSecondUpdate.size, `is`<Int>(3))
        assertThat(entriesAfterThirdUpdate.size, `is`<Int>(3))

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
    fun `should delete existent entry if alone`() {
        // When
        passwordDao.insert(PASSWORD_ENTRY_1)
        val entriesBeforeDelete = passwordDao.selectAll()

        // Then
        val deleteExistentEntry = passwordDao.delete(PASSWORD_ENTRY_1)
        val entriesAfterDelete = passwordDao.selectAll()

        assertThat(entriesBeforeDelete.size, `is`<Int>(1))
        assertThat(entriesAfterDelete.size, `is`<Int>(0))
        assertTrue(deleteExistentEntry)

        assertFalse(entriesAfterDelete.contains(PASSWORD_ENTRY_1))
    }

    @Test
    fun `should delete existent entry if not alone`() {
        // When
        passwordDao.insert(PASSWORD_ENTRY_1)
        passwordDao.insert(PASSWORD_ENTRY_2)
        passwordDao.insert(PASSWORD_ENTRY_3)
        val entriesBeforeDelete = passwordDao.selectAll()

        // Then
        val deleteExistentEntry = passwordDao.delete(PASSWORD_ENTRY_2)
        val entriesAfterDelete = passwordDao.selectAll()

        assertThat(entriesBeforeDelete.size, `is`<Int>(3))
        assertThat(entriesAfterDelete.size, `is`<Int>(2))
        assertTrue(deleteExistentEntry)

        assertFalse(entriesAfterDelete.contains(PASSWORD_ENTRY_2))
    }

    @Test
    fun `should not delete inexistent entry if alone`() {
        // When
        passwordDao.insert(PASSWORD_ENTRY_1)
        val entriesBeforeDelete = passwordDao.selectAll()

        // Then
        val deleteInexistentEntry = passwordDao.delete(PASSWORD_ENTRY_2)
        val entriesAfterDelete = passwordDao.selectAll()

        assertThat(entriesBeforeDelete.size, `is`<Int>(1))
        assertThat(entriesAfterDelete.size, `is`<Int>(1))
        assertFalse(deleteInexistentEntry)

        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_1))
    }

    @Test
    fun `should not delete inexistent entry if not alone`() {
        // When
        passwordDao.insert(PASSWORD_ENTRY_1)
        passwordDao.insert(PASSWORD_ENTRY_2)
        val entriesBeforeDelete = passwordDao.selectAll()

        // Then
        val deleteInexistentEntry = passwordDao.delete(PASSWORD_ENTRY_3)
        val entriesAfterDelete = passwordDao.selectAll()

        assertThat(entriesBeforeDelete.size, `is`<Int>(2))
        assertThat(entriesAfterDelete.size, `is`<Int>(2))
        assertFalse(deleteInexistentEntry)

        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_1))
        assertTrue(entriesAfterDelete.contains(PASSWORD_ENTRY_2))
    }
}