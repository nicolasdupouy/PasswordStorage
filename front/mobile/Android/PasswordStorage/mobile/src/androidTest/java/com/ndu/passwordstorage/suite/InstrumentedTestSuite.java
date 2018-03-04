package com.ndu.passwordstorage.suite;

import com.ndu.passwordstorage.ExampleInstrumentedTest;
import com.ndu.passwordstorage.screen.DisplayListActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({DisplayListActivityTest.class, ExampleInstrumentedTest.class})
public class InstrumentedTestSuite {
}
