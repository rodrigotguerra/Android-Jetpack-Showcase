package com.rodrigotguerra.dogsapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigotguerra.dogsapp.R
import com.rodrigotguerra.dogsapp.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val dogsListAdapter = DogListAdapter(ArrayList())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        rv_dogs_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter
        }

        refresh_layout.setOnRefreshListener {
            rv_dogs_list.visibility = View.GONE
            tv_list_error.visibility = View.GONE
            pb_loading_data.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refresh_layout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.dogs.observe(this, Observer { dogs ->
            dogs?.let {
                rv_dogs_list.visibility = View.VISIBLE
                dogsListAdapter.upgradeDogList(dogs)
            }
        })
        viewModel.dogsLoadError.observe(this, Observer { isError ->
            isError?.let {
                tv_list_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                pb_loading_data.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tv_list_error.visibility = View.GONE
                    rv_dogs_list.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(ListFragmentDirections.actionSettingsFragment())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}