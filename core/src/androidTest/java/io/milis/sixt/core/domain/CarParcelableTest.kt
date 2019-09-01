package io.milis.sixt.core.domain

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.milis.core.TestData
import io.milis.core.testParcel
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CarParcelableTest {

    @Test
    fun carSavedAsParcelable() {
        val actual = TestData.car1
        actual.testParcel().apply {
            Truth.assertThat(this).isEqualTo(actual)
            Truth.assertThat(this).isEqualTo(actual)
        }
    }

}
