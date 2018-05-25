package com.ndu.passwordstorage.screen

import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.ListAdapter
import android.widget.ListView

import com.ndu.passwordstorage.R
import com.ndu.passwordstorage.model.PasswordEntry

import junit.framework.Assert

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.clearText
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.longClick
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`

@RunWith(AndroidJUnit4::class)
@SmallTest
class DisplayListActivityTest {

    @Rule
    var mActivityRule = ActivityTestRule(DisplayListActivity::class.java)

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

    private fun updateEntry(passwordEntrySource: PasswordEntry, passwordEntryUpdate: PasswordEntry) {
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
        onView(withId(R.id.site)).perform(typeText(passwordEntry.site!!))
        onView(withId(R.id.login)).perform(typeText(passwordEntry.login!!))
        onView(withId(R.id.password)).perform(typeText(passwordEntry.password!!))
    }

    private fun deleteEntry(passwordEntry: PasswordEntry) {
        findAndLongClickEntry(passwordEntry)
        onView(withText(R.string.delete)).perform(click())
    }

    private fun findAndLongClickEntry(passwordEntry: PasswordEntry) {
        onData(allOf(`is`(instanceOf<Any>(PasswordEntry::class.java)), `is`(passwordEntry)))
                .perform(scrollTo(), longClick())
    }

    private fun findAndClickEntry(passwordEntry: PasswordEntry) {
        onData(allOf(`is`(instanceOf<Any>(PasswordEntry::class.java)), `is`(passwordEntry)))
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
        val listView = mActivityRule.activity.findViewById<ListView>(android.R.id.list)
        val adapter = listView.adapter

        if (adapter.isEmpty)
            return false

        for (i in 0 until adapter.count) {
            val item = adapter.getItem(i) as PasswordEntry
            if (passwordEntry == item) {
                return true
            }
        }
        return false
    }

    companion object {

        private val NON_EXISTENT_PASSWORD_ENTRY = PasswordEntry.makeNew(
                "Non Existent Site",
                "Non Existent Login",
                "Non Existent Password")
        private val NON_EXISTENT_PASSWORD_ENTRY_UPDATED: PasswordEntry

        init {
            NON_EXISTENT_PASSWORD_ENTRY_UPDATED = PasswordEntry[NON_EXISTENT_PASSWORD_ENTRY]
            NON_EXISTENT_PASSWORD_ENTRY_UPDATED.update(
                    "Non Existent Site_updated",
                    "Non Existent Login_updated",
                    "Non Existent Password_updated")
        }

        private val UPDATE_BUTTON_CONTENT = "Update"
    }
}
