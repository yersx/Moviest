package kz.moviest.app.ui.movies

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.moviest.app.R
import kz.moviest.app.data.models.network.Status
import kz.moviest.app.databinding.FragmentMoviesBinding
import kz.moviest.app.ui.movies.filter.SharedMovieFilterViewModel
import kz.moviest.app.ui_common.base.BaseFragment
import kz.moviest.app.utils.navigation.getSlideLeftAnimBuilder
import kz.moviest.app.utils.navigation.getSlideUpAnimBuilder
import kz.moviest.app.utils.rv.MarginItemDecoration

class MoviesFragment: BaseFragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel

    private val sharedFilterViewModel: SharedMovieFilterViewModel by navGraphViewModels(R.id.navigation_movies) {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerView.addItemDecoration(
            MarginItemDecoration(
                context = context!!,
                topDimen = R.dimen.dp_10,
                bottomDimen = R.dimen.dp_10
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(MoviesViewModel::class.java!!)
        binding.viewModel = viewModel

        binding.recyclerView.adapter = MoviesPagedListAdapter(
            context!!,
            viewModel.recyclerViewItemClickCallback,
            viewModel.afterRetryCallback
        )

        observeViewModel()
        observeSharedShopsFilterViewModel()
    }

    private fun observeViewModel() {

        viewModel.moviesInitialResource.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        viewModel.onMoviesInitialResourceSuccess(it.data)
                    }
                    Status.ERROR -> {
                        handleExceptionView(it.exception)
                    }
                }
            }
        )

        viewModel.moviesAfterResource.observe(
            viewLifecycleOwner,
            Observer {
                when (it.status) {
                    Status.ERROR -> {
                        handleExceptionDialog(it.exception)
                    }
                }
            }
        )

        viewModel.moviesPagedList.observe(
            viewLifecycleOwner,
            Observer {
                viewModel.onMoviesResourceSuccess(it)
            }
        )

        viewModel.list.observe(
            viewLifecycleOwner,
            Observer {
                (binding.recyclerView.adapter as MoviesPagedListAdapter).submitList(it)
            }
        )

        viewModel.openMovie.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let {
                    val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(it)
                    findNavController().navigate(action, getSlideUpAnimBuilder().build())
                }
            }
        )

        viewModel.openFilter.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let {
                    val action = MoviesFragmentDirections.actionMoviesFragmentToMovieFilterFragment()
                    findNavController().navigate(action, getSlideLeftAnimBuilder().build())
                }
            }
        )
    }

    private fun observeSharedShopsFilterViewModel() {
        sharedFilterViewModel.applyBtnClick.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let {
                    viewModel.onApplyBtnClick(it)
                }
            }
        )
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movies_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_filter -> {
                viewModel.onFilterItemClick()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}