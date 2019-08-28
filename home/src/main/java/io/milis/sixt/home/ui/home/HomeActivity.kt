package io.milis.sixt.home.ui.home

import android.os.Bundle
import com.mancj.materialsearchbar.MaterialSearchBar
import io.milis.sixt.core.common.MvpActivity
import io.milis.sixt.home.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : MvpActivity(), HomeView, MaterialSearchBar.OnSearchActionListener {

    override val presenter by presenterProvider(HomePresenter::class.java, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        inject()

        searchBar.text = "Find cars"
        searchBar.setCardViewElevation(12)
        searchBar.setOnSearchActionListener(this)
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> {
            }
            MaterialSearchBar.BUTTON_BACK -> {
                searchBar.disableSearch()
            }
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
    }

}

