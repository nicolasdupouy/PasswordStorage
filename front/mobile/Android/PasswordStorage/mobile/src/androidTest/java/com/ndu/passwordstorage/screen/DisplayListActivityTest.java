package com.ndu.passwordstorage.screen;

import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.model.PasswordEntry;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class DisplayListActivityTest {

    private static final PasswordEntry NON_EXISTENT_PASSWORD_ENTRY = PasswordEntry.makeNew(
            "Non Existent Site",
            "Non Existent Login",
            "Non Existent Password");
    private static final PasswordEntry NON_EXISTENT_PASSWORD_ENTRY_UPDATED;
    static {
        NON_EXISTENT_PASSWORD_ENTRY_UPDATED = PasswordEntry.get(NON_EXISTENT_PASSWORD_ENTRY);
        NON_EXISTENT_PASSWORD_ENTRY_UPDATED.update(
                "Non Existent Site_updated",
                "Non Existent Login_updated",
                "Non Existent Password_updated");
    }
    private static final String UPDATE_BUTTON_CONTENT = "Update";

    @Rule
    public ActivityTestRule<DisplayListActivity> mActivityRule = new ActivityTestRule<>(DisplayListActivity.class);

    @Test
    public void should_add_and_delete_inexistent_entry() {
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY);

        createEntry(NON_EXISTENT_PASSWORD_ENTRY);
        checkEntryExists(NON_EXISTENT_PASSWORD_ENTRY);

        deleteEntry(NON_EXISTENT_PASSWORD_ENTRY);
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY);
    }

    @Test
    public void should_fail_to_add_existant_entry() {

    }

    @Test
    public void should_update_entry() {
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY);

        createEntry(NON_EXISTENT_PASSWORD_ENTRY);
        updateEntry(NON_EXISTENT_PASSWORD_ENTRY, NON_EXISTENT_PASSWORD_ENTRY_UPDATED);

        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY);
        checkEntryExists(NON_EXISTENT_PASSWORD_ENTRY_UPDATED);

        deleteEntry(NON_EXISTENT_PASSWORD_ENTRY_UPDATED);
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY_UPDATED);
    }

    private void createEntry(PasswordEntry passwordEntry) {
        onView(withId(R.id.fab)).perform(click());
        typeFields(passwordEntry);
        onView(withText(UPDATE_BUTTON_CONTENT)).perform(click());
    }

    private void updateEntry(PasswordEntry passwordEntrySource, PasswordEntry passwordEntryUpdate) {
        findAndClickEntry(passwordEntrySource);
        cleanFields();
        typeFields(passwordEntryUpdate);
        onView(withText(UPDATE_BUTTON_CONTENT)).perform(click());
    }

    private void cleanFields() {
        onView(withId(R.id.site)).perform(clearText());
        onView(withId(R.id.login)).perform(clearText());
        onView(withId(R.id.password)).perform(clearText());
    }

    private void typeFields(PasswordEntry passwordEntry) {
        onView(withId(R.id.site)).perform(typeText(passwordEntry.getSite()));
        onView(withId(R.id.login)).perform(typeText(passwordEntry.getLogin()));
        onView(withId(R.id.password)).perform(typeText(passwordEntry.getPassword()));
    }

    private void deleteEntry(PasswordEntry passwordEntry) {
        findAndLongClickEntry(passwordEntry);
        onView(withText(R.string.delete)).perform(click());
    }
    private void findAndLongClickEntry(PasswordEntry passwordEntry) {
        onData(allOf(is(instanceOf(String.class)), is(passwordEntry.toString())))
                .perform(scrollTo(), longClick());
    }

    private void findAndClickEntry(PasswordEntry passwordEntry) {
        onData(allOf(is(instanceOf(String.class)), is(passwordEntry.toString())))
                .perform(scrollTo(), click());
    }

    private void checkEntryExists(PasswordEntry passwordEntry) {
        boolean exists = doesListContains(passwordEntry);
        Assert.assertTrue(exists);
    }

    private void checkEntryDoesNotExist(PasswordEntry passwordEntry) {
        boolean exists = doesListContains(passwordEntry);
        Assert.assertFalse(exists);
    }

    private boolean doesListContains(PasswordEntry passwordEntry) {
        ListView listView = mActivityRule.getActivity().findViewById(android.R.id.list);
        ListAdapter adapter = listView.getAdapter();

        if (adapter.isEmpty())
            return false;

        for (int i = 0; i < adapter.getCount(); i++) {
            PasswordEntry item = (PasswordEntry) adapter.getItem(i);
            if (passwordEntry.equals(item)) {
                return true;
            }
        }
        return false;
    }
}
