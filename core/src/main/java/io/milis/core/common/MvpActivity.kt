package io.milis.core.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class MvpActivity : DaggerAppCompatActivity(), MvpView {

    abstract val presenter: MvpPresenter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}