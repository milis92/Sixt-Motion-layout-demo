package io.milis.sixt.core.mvp

import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import io.milis.sixt.core.common.mvp.MvpPresenter
import io.milis.sixt.core.common.mvp.MvpPresenterFactory
import io.milis.sixt.core.common.mvp.MvpView
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import java.lang.IllegalArgumentException
import javax.inject.Provider

@RunWith(MockitoJUnitRunner::class)
class MvpPresenterFactoryTest : TestCase() {

    private val provider = mapOf<Class<out MvpPresenter<*>>,
            @JvmSuppressWildcards
            Provider<MvpPresenter<*>>>(Pair(MvpPresenter::class.java, Provider<MvpPresenter<*>> { mock() }))

    @Spy
    var presenterFactory = MvpPresenterFactory(provider)

    @Test
    fun `presenter factory provides instance`() {
        val actual = presenterFactory.create(MvpPresenter::class.java)
        Truth.assertThat(actual).isNotNull()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `presenter factory fails incorrect instance`() {
        presenterFactory.create(DummyPresenter::class.java)
    }

    internal class DummyPresenter : MvpPresenter<MvpView>()
}