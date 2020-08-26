package kz.moviest.app.ui.movies.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import kz.moviest.app.R
import kz.moviest.app.databinding.FragmentMoviesFilterBinding
import kz.moviest.app.ui_common.base.BaseFragment

class MovieFilterFragment: BaseFragment() {

    private lateinit var binding: FragmentMoviesFilterBinding
    private lateinit var viewModel: MovieFilterViewModel

    private val sharedFilterViewModel: SharedMovieFilterViewModel by navGraphViewModels(R.id.navigation_movies) {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_filter, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(MovieFilterViewModel::class.java)

        viewModel.setArgs(
            callback = sharedFilterViewModel.callback
        )

        binding.viewModel = viewModel

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.popBackStack.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let {
                    findNavController().popBackStack()
                }
            }
        )
    }
}