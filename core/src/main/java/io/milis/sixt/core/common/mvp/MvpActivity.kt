package io.milis.sixt.core.common.mvp

import android.view.MenuItem
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.milis.sixt.core.R
import javax.inject.Inject

abstract class MvpActivity : AppCompatActivity(), MvpView {

    @Inject
    lateinit var presenterFactory: MvpPresenterProvider.Factory

    abstract val presenter: MvpPresenter<*>

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    protected fun Toolbar.initToolbar(showBack: Boolean = true) {
        setSupportActionBar(this)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (showBack) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inline fun <reified V : MvpView, reified P : MvpPresenter<V>> presenterProvider(
            clazz: Class<P>,
            view: V
    ) = lazy {
        this.presenterFactory.create(clazz).apply {
            onCreate(view)
        }
    }
}