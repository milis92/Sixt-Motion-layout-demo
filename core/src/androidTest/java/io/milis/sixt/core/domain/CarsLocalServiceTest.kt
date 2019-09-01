package io.milis.sixt.core.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.EmptyResultSetException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import io.milis.core.TestData
import io.milis.sixt.core.domain.services.local.Database
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class CarsLocalServiceTest /*We could go with : TestCase but annotations are better*/ {

    @JvmField
    @Rule
    var rule: TestRule = InstantTaskExecutorRule()

    companion object {

        private lateinit var database: Database

        @JvmStatic
        @BeforeClass
        fun setUp() {
            database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), Database::class.java).build()
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            database.close()
        }
    }

    @Before
    fun beforeEach() {
        database.clearAllTables()
    }

    @Test
    fun getAllObservesData() {
        database.cars().insert(*TestData.cars.toTypedArray())

        val disposable = database.cars()
                .getAll()
                .test()
                .assertNotTerminated()
                .assertValue(TestData.cars)
                .assertNoErrors()

        disposable.dispose()
    }

    @Test
    fun getSingleReturnsData() {
        database.cars().insert(*TestData.cars.toTypedArray())

        val disposable = database.cars()
                .getSingle(TestData.car1.id)
                .test()
                .assertValue(TestData.car1)
                .assertNoErrors()
                .assertTerminated()

        disposable.dispose()
    }

    @Test
    fun getSingleFailsOnEmptyDb() {
        val disposable = database.cars()
                .getSingle(TestData.car1.id)
                .test()
                .assertError(EmptyResultSetException::class.java)
                .assertTerminated()

        disposable.dispose()
    }

    @Test
    fun getFilterReturnsData() {
        database.cars().insert(*TestData.cars.toTypedArray())

        val disposable = database.cars()
                .filter(TestData.car1.make, TestData.car1.modelName)
                .test()
                .assertValue(TestData.cars)
                .assertNoErrors()
                .assertTerminated()

        disposable.dispose()
    }

    @Test
    fun getFilterNotThrowingOnEmptyDb() {
        val disposable = database.cars()
                .filter(TestData.car1.make, TestData.car1.modelName)
                .test()
                .assertValue(emptyList())
                .assertNoErrors()
                .assertTerminated()

        disposable.dispose()
    }

    @Test
    fun getFilterNotThrowingOnNoResults() {
        database.cars().insert(*TestData.cars.toTypedArray())

        val disposable = database.cars()
                .filter("fake", "news")
                .test()
                .assertValue(emptyList())
                .assertNoErrors()
                .assertTerminated()

        disposable.dispose()
    }


    @Test
    fun deleteRemovesData() {
        database.cars().insert(*TestData.cars.toTypedArray())

        val disposable = database.cars()
                .delete(TestData.car1)
                .test()
                .assertNoErrors()
                .assertTerminated()

        val actual = database.cars()
                .getSingle(TestData.car1.id)
                .test()
                .assertError(EmptyResultSetException::class.java)
                .assertTerminated()

        actual.dispose()
        disposable.dispose()
    }

    @Test
    fun deleteDoesNotFailOnEmtpyDb() {
        val disposable = database.cars()
                .delete(TestData.car1)
                .test()
                .assertNoErrors()
                .assertTerminated()

        disposable.dispose()
    }

    @Test
    fun pruneRemovesAllData() {
        database.cars().insert(*TestData.cars.toTypedArray())

        database.cars().delete()

        val disposable = database.cars()
                .getAll()
                .test()
                .assertValue(emptyList())
                .assertNoErrors()
                .assertNotTerminated()

        disposable.dispose()
    }

    @Test
    fun syncPrunesAndInsertsData() {
        database.cars().insert(*TestData.cars.toTypedArray())

        val testData = listOf(TestData.car1.copy(id = "test1"), TestData.car2.copy("test2"))
        database.cars().sync(testData)

        val disposable = database.cars()
                .getAll()
                .test()
                .assertValue { it.size == 2 && it[0].id == "test1" && it[1].id == "test2" }
                .assertNoErrors()
                .assertNotTerminated()

        disposable.dispose()
    }

}