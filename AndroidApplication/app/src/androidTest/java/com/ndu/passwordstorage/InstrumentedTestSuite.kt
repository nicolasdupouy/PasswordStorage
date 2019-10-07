package com.ndu.passwordstorage

import com.ndu.passwordstorage.infrastructure.screen.activity.MainActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    ExampleInstrumentedTest::class
)
class InstrumentedTestSuite {
}