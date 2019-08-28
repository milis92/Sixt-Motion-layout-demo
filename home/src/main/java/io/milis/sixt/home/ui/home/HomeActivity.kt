package io.milis.sixt.home.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.mancj.materialsearchbar.MaterialSearchBar
import io.milis.sixt.core.common.mvp.MvpActivity
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.ext.afterTextChanged
import io.milis.sixt.ext.location
import io.milis.sixt.ext.marker
import io.milis.sixt.home.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.material_searchbar.view.*
import javax.inject.Inject


class HomeActivity : MvpActivity(), HomeView, MaterialSearchBar.OnSearchActionListener, OnMapReadyCallback {

    @Inject
    internal lateinit var suggestionsAdapter: CarsSuggestionAdapter

    override val presenter by presenterProvider(HomePresenter::class.java, this)

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        inject()

        motionLayout.transitionToEnd()

        with(searchBar) {
            setPlaceHolder(getString(R.string.home_search_placeholder))
            setOnSearchActionListener(this@HomeActivity)
            setCustomSuggestionAdapter(suggestionsAdapter)
            isSearchEnabled
            afterTextChanged {
                suggestionsAdapter.filter.filter(it)
            }
            suggestionsAdapter.onItemSelected = {
                setPlaceHolder(it.make)
                presenter.onSearchConfirmed(it.make, it.modelName)
            }
        }

        (map as SupportMapFragment).getMapAsync(this)
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> {
                drawerLayout.openDrawer(navigation)
            }
            MaterialSearchBar.BUTTON_BACK -> {
                searchBar.setPlaceHolder(getString(R.string.home_search_placeholder))
                searchBar.disableSearch()
            }
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        presenter.onMapCreated()
    }

    override fun onCarsLoaded(cars: List<Car>) {
        googleMap.clear()
        suggestionsAdapter.suggestions = cars
        searchBar.disableSearch()

        cars.forEachIndexed { index, car ->
            val marker = marker {
                position {
                    latitude { car.latitude }
                    longitude { car.longitude }
                }
                name { "asdf" }
                snippet { "" }
            }
            googleMap.addMarker(marker)
            if (index == 0) {
                googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                location {
                                    latitude { car.latitude }
                                    longitude { car.longitude }
                                }, 10f))
            }
        }

    }

    private fun MaterialSearchBar.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.mt_editText.afterTextChanged(afterTextChanged)
    }
}

