package kz.moviest.app.ui.movies.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kz.moviest.app.R
import kz.moviest.app.data.models.network.Status
import kz.moviest.app.databinding.FragmentMovieDetailBinding
import kz.moviest.app.ui_common.base.BaseFragment

class MovieDetailFragment : BaseFragment() {

    private val args: MovieDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel(MovieDetailViewModel::class.java!!)
        viewModel.setArgs(args.movieId)
        binding.viewModel = viewModel

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.movieDetailResource.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let {
                    when (it.status) {
                        Status.SUCCESS -> {
                            viewModel.onMovieDetailSuccess(it.data)
                        }
                        Status.ERROR -> {
                            handleExceptionDialog(it.exception)
                        }
                    }
                }
            }
        )
    }

}