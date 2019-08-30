package io.milis.sixt.home.ui.home

import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import com.bumptech.glide.request.target.Target
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.materialsearchbar.MaterialSearchBar
import io.milis.sixt.GlideApp
import io.milis.sixt.core.common.mvp.MvpActivity
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.ext.afterTextChanged
import io.milis.sixt.ext.location
import io.milis.sixt.ext.marker
import io.milis.sixt.home.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_home_details.*
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

        with(searchBar) {
            setPlaceHolder(getString(R.string.home_search_placeholder))
            setOnSearchActionListener(this@HomeActivity)
            setCustomSuggestionAdapter(suggestionsAdapter)
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
        searchBar.setPlaceHolder(text)
        presenter.onSearchConfirmed(text.toString(), text.toString())
    }

    override fun onBackPressed() {
        if (motionLayout.progress == 1f) {
            motionLayout.transitionToStart()
        } else {
            super.onBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))
        googleMap.setOnMarkerClickListener {
            val car: Car = it.tag as Car

            fillDetails(car)

            true
        }
        presenter.onMapCreated()
    }

    override fun onCarsLoaded(cars: List<Car>) {
        googleMap.clear()
        suggestionsAdapter.clearSuggestions()
        suggestionsAdapter.suggestions = cars
        searchBar.hideSuggestionsList()
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

            googleMap.addMarker(marker).apply {
                tag = car
            }

            if (index == 0) {
                fillDetails(car)
            }
        }
    }

    private fun fillDetails(car: Car) {
        GlideApp.with(this)
                .load(car.carImageUrl)
                .override(motionLayout.width, Target.SIZE_ORIGINAL)
                .into(topImage)

        make.text = car.make
        modelName.text = car.modelName
    }

    private fun MaterialSearchBar.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.mt_editText.afterTextChanged(afterTextChanged)
    }
}

