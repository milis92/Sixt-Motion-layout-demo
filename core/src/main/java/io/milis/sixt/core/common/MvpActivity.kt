package io.milis.sixt.core.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class MvpActivity : AppCompatActivity(), MvpView {

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