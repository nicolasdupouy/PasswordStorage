package com.ndu.passwordstorage

import com.ndu.passwordstorage.domain.dao.PasswordDaoImplTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    PasswordDaoImplTest::class,
    ExampleUnitTest::class
)
class UnitTestSuite