package io.milis.sixt.home.ui.all_cars

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import io.milis.sixt.core.common.mvp.MvpActivity
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.home.R
import kotlinx.android.synthetic.main.activity_all_cars.*
import javax.inject.Inject

class AllCarsActivity : MvpActivity(), AllCarsView {


    @Inject
    lateinit var adapter: AllCarsAdapter

    override val presenter by presenterProvider(AllCarsPresenter::class.java, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_cars)

        inject()

        toolbar.initToolbar()
        toolbar.title = getString(R.string.all_cars_title)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        presenter.onCreated()
    }

    override fun onCarsLoaded(cars: List<Car>) {
        adapter.submitList(cars)
    }
}

