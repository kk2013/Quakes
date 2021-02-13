package com.quakes.quakeslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.quakes.R
import com.quakes.data.NetworkState
import com.quakes.di.ViewModelFactory
import com.quakes.model.Earthquake
import com.quakes.quakemap.MapsMarkerActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_quakes_list.*
import javax.inject.Inject

class QuakesListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val quakesAdapter =
        QuakesAdapter(onClickListener = { quake -> showQuakeLocation(quake) })
    private lateinit var layoutManager: LinearLayoutManager

    private val quakesViewModel: QuakesListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quakes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        quakes_recycler_view.layoutManager = layoutManager
        quakes_recycler_view.adapter = quakesAdapter

        quakesViewModel.quakes.observe(this, Observer {
            quakesAdapter.submitList(it)
        })

        quakesViewModel.networkState.observe(this, Observer {
            when (it) {
                NetworkState.Loading -> progress_bar.visibility = View.VISIBLE
                NetworkState.Success -> progress_bar.visibility = View.GONE
                NetworkState.Failed -> progress_bar.visibility = View.GONE
            }
        })

//        quakesViewModel
    }

    private fun showQuakeLocation(quake: Earthquake) {
        val intent = Intent(activity, MapsMarkerActivity::class.java)
        intent.putExtra(QUAKE_DETAILS, quake)
        startActivity(intent)
    }

    companion object {
        const val QUAKE_DETAILS = "quake_details"
    }
}