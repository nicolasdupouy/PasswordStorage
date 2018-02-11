package com.ndu.passwordstorage.screen;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ndu.passwordstorage.R;
import com.ndu.passwordstorage.model.PasswordEntry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DisplayListActivityTest {

    private static PasswordEntry NON_EXISTENT_PASSWORD_ENTRY = PasswordEntry.makeNew(
            "Non Existent Site",
            "Non Existent Login",
            "Non Existent Password");
    private static PasswordEntry EXISTENT_PASSWORD_ENTRY = PasswordEntry.makeNew(
            "Existent Site",
            "Existent Login",
            "Existent Password");
    private static final String UPDATE_BUTTON_CONTENT = "Update";

    @Rule
    public ActivityTestRule<DisplayListActivity> mActivityRule = new ActivityTestRule<>(DisplayListActivity.class);

    @Test
    public void test_is_displayed() {
        // OK
        checkEntryDisplayed(EXISTENT_PASSWORD_ENTRY);
        // KO
        //checkEntryDisplayed(NON_EXISTENT_PASSWORD_ENTRY);
    }

    @Test
    public void test_exists() {
        // OK
        checkEntryExists(EXISTENT_PASSWORD_ENTRY);
        // KO
        //checkEntryExists(NON_EXISTENT_PASSWORD_ENTRY);
    }

    @Test
    public void test_dont_exists() {
        // OK
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY);
        // KO
        //checkEntryDoesNotExist(EXISTENT_PASSWORD_ENTRY);

    }

    @Test
    public void should_add_and_delete_inexistent_entry() {
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY);
        addEntry(NON_EXISTENT_PASSWORD_ENTRY);

        checkEntryExists(NON_EXISTENT_PASSWORD_ENTRY);

        deleteEntry(NON_EXISTENT_PASSWORD_ENTRY);
        checkEntryDoesNotExist(NON_EXISTENT_PASSWORD_ENTRY);
    }

    @Test
    public void should_fail_to_add_existant_entry() {

    }

    @Test
    public void should_update_entry() {

    }

    private void addEntry(PasswordEntry passwordEntry) {
        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.site)).perform(typeText(passwordEntry.getSite()));
        onView(withId(R.id.login)).perform(typeText(passwordEntry.getLogin()));
        onView(withId(R.id.password)).perform(typeText(passwordEntry.getPassword()));

        onView(withText(UPDATE_BUTTON_CONTENT)).perform(click());
    }

    private void deleteEntry(PasswordEntry passwordEntry) {

    }

    private void checkEntryDisplayed(PasswordEntry passwordEntry) {
        onData(allOf(is(instanceOf(String.class)), is(passwordEntry.toString()))).check(matches(isDisplayed()));
    }

    private void checkEntryExists(PasswordEntry passwordEntry) {
        onData(anything())
                .check(matches(withText(passwordEntry.toString())));
    }

    private void checkEntryDoesNotExist(PasswordEntry passwordEntry) {
        onData(anything())
                .check(matches(not(withText(passwordEntry.toString()))));
    }
}
