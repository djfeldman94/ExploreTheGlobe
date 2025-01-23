package org.wm.explore

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.wm.explore.ui.adapter.CountriesAdapter


class MainActivity : ComponentActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: CountriesAdapter? = null
    private var loadingView: ProgressBar? = null
    private var errorTextView: TextView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    private val viewModel: CountriesViewModel by viewModel<CountriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        loadingView = findViewById<View>(R.id.progressBar) as ProgressBar
        errorTextView = findViewById<View>(R.id.errorText) as TextView
        swipeRefreshLayout = findViewById<View>(R.id.swipeRefreshLayout) as SwipeRefreshLayout

        // Initialize recycler view
        adapter = CountriesAdapter(emptyList())
        recyclerView?.setAdapter(adapter)
        recyclerView?.setLayoutManager(
            LinearLayoutManager(this)
        )

        // Setup the swipe refresh layout
        swipeRefreshLayout?.setOnRefreshListener {
            viewModel.getCountries()
            swipeRefreshLayout?.isRefreshing = false
        }

        // Observe the state
        observeState()
    }

    /**
     * Observe the state from the ViewModel for the countries data
     */
    private fun observeState() {
        lifecycleScope.launch {
            viewModel.countriesState.collect { state ->
                when (state) {
                    is CountriesViewModel.CountriesState.Loading -> {
                        // Show loading
                        loadingView?.visibility = View.VISIBLE
                        // Hide recycler view
                        recyclerView?.visibility = View.GONE
                        // Hide error message
                        errorTextView?.visibility = View.GONE

                    }
                    is CountriesViewModel.CountriesState.Success -> {
                        // Hide loading
                        loadingView?.visibility = View.GONE
                        // Hide error message
                        errorTextView?.visibility = View.GONE
                        // Show recycler view
                        recyclerView?.visibility = View.VISIBLE
                        adapter?.updateItems(state.countries)
                        adapter?.notifyDataSetChanged()
                    }
                    is CountriesViewModel.CountriesState.Error -> {
                        // Hide loading
                        loadingView?.visibility = View.GONE
                        // Hide recycler view
                        recyclerView?.visibility = View.GONE
                        // Show error message
                        errorTextView?.visibility = View.VISIBLE
                        errorTextView?.text = state.message

                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
