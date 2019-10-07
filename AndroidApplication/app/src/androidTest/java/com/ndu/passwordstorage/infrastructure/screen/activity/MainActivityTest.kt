package com.ndu.passwordstorage.infrastructure.screen.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.domain.PasswordEntry
import com.ndu.passwordstorage.infrastructure.screen.adapter.ListEntryAdapter
import junit.framework.Assert
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest {

    companion object {
        private val NON_EXISTENT_PASSWORD_ENTRY = PasswordEntry(
            Triple(
                "Non Existent Site",
                "Non Existent Login",
                "Non Existent Password"))
        private var NON_EXISTENT_PASSWORD_ENTRY_UPDATED = NON_EXISTENT_PASSWORD_ENTRY.copy(
            site = "Non Existent Site_updated",
            login = "Non Existent Login_updated",
            password = "Non Existent Password_updated")

        private val UPDATE_BUTTON_CONTENT = "Update"
    }


    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)
    
    @Test
    fun should_add_and_delete_inexistent_entry() {
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY)

        createEntry(NON_EXISTENT_PASSWORD_ENTRY)
        checkEntryExists(NON_EXISTENT_PASSWORD_ENTRY)

        deleteEntry(NON_EXISTENT_PASSWORD_ENTRY)
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY)
    }

    @Test
    fun should_fail_to_add_existant_entry() {

    }

    @Test
    fun should_update_entry() {
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY)

        createEntry(NON_EXISTENT_PASSWORD_ENTRY)
        updateEntry(NON_EXISTENT_PASSWORD_ENTRY, NON_EXISTENT_PASSWORD_ENTRY_UPDATED)

        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY)
        checkEntryExists(NON_EXISTENT_PASSWORD_ENTRY_UPDATED)

        deleteEntry(NON_EXISTENT_PASSWORD_ENTRY_UPDATED)
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY_UPDATED)
    }


    private fun createEntry(passwordEntry: PasswordEntry) {
        onView(withId(R.id.fab)).perform(click())
        typeFields(passwordEntry)
        onView(withText(UPDATE_BUTTON_CONTENT)).perform(click())
    }

    private fun updateEntry(
        passwordEntrySource: PasswordEntry,
        passwordEntryUpdate: PasswordEntry
    ) {
        findAndClickEntry(passwordEntrySource)
        cleanFields()
        typeFields(passwordEntryUpdate)
        onView(withText(UPDATE_BUTTON_CONTENT)).perform(click())
    }

    private fun cleanFields() {
        onView(withId(R.id.site)).perform(clearText())
        onView(withId(R.id.login)).perform(clearText())
        onView(withId(R.id.password)).perform(clearText())
    }

    private fun typeFields(passwordEntry: PasswordEntry) {
        onView(withId(R.id.site)).perform(typeText(passwordEntry.site))
        onView(withId(R.id.login)).perform(typeText(passwordEntry.login))
        onView(withId(R.id.password)).perform(typeText(passwordEntry.password))
    }

    private fun deleteEntry(passwordEntry: PasswordEntry) {
        findAndLongClickEntry(passwordEntry)
        onView(withText(R.string.delete)).perform(click())
    }

    private fun findAndLongClickEntry(passwordEntry: PasswordEntry) {
        onData(
            allOf(
                `is`<Any>(instanceOf<Any>(PasswordEntry::class.java)),
                `is`<PasswordEntry>(passwordEntry)
            )
        )
            .perform(scrollTo(), longClick())
    }

    private fun findAndClickEntry(passwordEntry: PasswordEntry) {
        onData(
            allOf(
                `is`<Any>(instanceOf<Any>(PasswordEntry::class.java)),
                `is`<PasswordEntry>(passwordEntry)
            )
        )
            .perform(scrollTo(), click())
    }

    private fun checkEntryExists(passwordEntry: PasswordEntry) {
        val exists = doesListContains(passwordEntry)
        Assert.assertTrue(exists)
    }

    private fun checkEntryDoesNotExist(passwordEntry: PasswordEntry) {
        val exists = doesListContains(passwordEntry)
        Assert.assertFalse(exists)
    }

    private fun doesListContains(passwordEntry: PasswordEntry): Boolean {
        val recyclerView: RecyclerView = activityRule.activity.findViewById(R.id.storage_list)
        val adapter = recyclerView.adapter as ListEntryAdapter

        adapter.let {
            for (i in 0 until adapter.itemCount) {
                val item = adapter.getItem(i)
                if (passwordEntry == item) {
                    return true
                }
            }
        }
        return false
    }
}