package org.wm.explore

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etg.core.di.coreModule
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.wm.explore.ui.adapter.CountriesAdapter


class MainActivity : ComponentActivity() {
    var recyclerView: RecyclerView? = null
    private var adapter: CountriesAdapter? = null

    private val viewModel: CountriesViewModel by viewModel<CountriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView

        adapter = CountriesAdapter(emptyList())
        recyclerView?.setAdapter(adapter)
        recyclerView?.setLayoutManager(
            LinearLayoutManager(this)
        )

        observeState()

    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.countriesState.collect { state ->
                when (state) {
                    is CountriesViewModel.CountriesState.Loading -> {
                        // Show loading
                    }
                    is CountriesViewModel.CountriesState.Success -> {
                        adapter?.updateItems(state.countries)
                        adapter?.notifyDataSetChanged()
                    }
                    is CountriesViewModel.CountriesState.Error -> {
                        Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
