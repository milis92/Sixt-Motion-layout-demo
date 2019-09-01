package io.milis.sixt.core.mvp

import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import io.milis.sixt.core.common.mvp.MvpPresenter
import io.milis.sixt.core.common.mvp.MvpRxPresenter
import io.milis.sixt.core.common.mvp.MvpView
import io.reactivex.disposables.Disposable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MvpPresenterTest {

    @InjectMocks
    lateinit var presenter : MvpPresenter<MvpView>

    @Before
    fun setUp() {
        presenter.onCreate(mock())
    }

    @Test
    fun `destroy clears reference`() {
        presenter.onDestroy()

        Assert.assertNull(presenter.viewReference?.get())
        Assert.assertNull(presenter.view)
    }
}

@RunWith(MockitoJUnitRunner::class)
class MvpRxPresenterTest {

    @InjectMocks
    lateinit var presenter: MvpRxPresenter<MvpView>

    @Before
    fun setUp() {
        presenter.onCreate(mock())
    }

    @Test
    fun `destroy clears default disposable`() {
        presenter.compositeDisposable.add(mock())

        presenter.onDestroy()

        Truth.assertThat(presenter.compositeDisposable.isDisposed).isTrue()
        Truth.assertThat(presenter.compositeDisposable.size()).isEqualTo(0)
    }

}