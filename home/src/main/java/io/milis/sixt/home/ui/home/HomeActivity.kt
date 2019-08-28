package io.milis.sixt.home.ui.home

import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.mancj.materialsearchbar.MaterialSearchBar
import io.milis.sixt.core.common.mvp.MvpActivity
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.ext.marker
import io.milis.sixt.home.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : MvpActivity(), HomeView, MaterialSearchBar.OnSearchActionListener, OnMapReadyCallback {

    override val presenter by presenterProvider(HomePresenter::class.java, this)

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        inject()

        motionLayout.transitionToEnd()

        searchBar.setPlaceHolder(getString(R.string.home_search_placeholder))
        searchBar.setCardViewElevation(12)
        searchBar.setOnSearchActionListener(this)

        (map as SupportMapFragment).getMapAsync(this)
    }

    override fun onButtonClicked(buttonCode: Int) {
        when (buttonCode) {
            MaterialSearchBar.BUTTON_NAVIGATION -> {
                drawerLayout.openDrawer(navigation)
            }
            MaterialSearchBar.BUTTON_BACK -> {
                searchBar.disableSearch()
            }
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        presenter.onSearchConfirmed(text.toString())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        presenter.onMapCreated()
    }

    override fun onCarsLoaded(cars: List<Car>) {
        cars.forEach {
            val marker = marker {
                position {
                    latitude { plus(it.latitude) }
                    longitude { plus(it.longitude) }
                }
                name {
                    plus("asdf")
                }
                snippet {
                    plus("")
                }
            }
            googleMap.addMarker(marker)
        }
    }
}

