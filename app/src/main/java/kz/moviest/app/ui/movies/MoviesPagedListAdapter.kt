package kz.moviest.app.ui.movies

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kz.moviest.app.R
import kz.moviest.app.data.models.movies.Movie
import kz.moviest.app.databinding.AdapterMovieBinding
import androidx.paging.PagedListAdapter
import kz.moviest.app.data.models.network.Resource
import kz.moviest.app.databinding.AdapterLoadingBinding
import kz.moviest.app.ui_common.callbacks.RecyclerViewItemClickCallback
import kz.moviest.app.ui_common.callbacks.RetryCallback

class MoviesPagedListAdapter(
    val context : Context,
    private val recyclerViewItemClickCallback: RecyclerViewItemClickCallback,
    private val afterRetryCallback: RetryCallback
): PagedListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val VIEW_TYPE_MOVIE = 0
        private const val VIEW_TYPE_LOADING = 1

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    private var afterResource: Resource<Unit>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_MOVIE -> {
                val binding: AdapterMovieBinding =
                    DataBindingUtil.inflate(inflater, R.layout.adapter_movie, parent, false)
                ProductViewHolder(binding)
            }
            VIEW_TYPE_LOADING -> {
                val binding: AdapterLoadingBinding =
                    DataBindingUtil.inflate(inflater, R.layout.adapter_loading, parent, false)
                LoadingViewHolder(binding)
            }
            else -> {
                throw IllegalStateException("Incorrect ViewType found")
            }
        }
    }

    inner class ProductViewHolder(var binding: AdapterMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initContent(movie: Movie) {
            binding.movie = movie
            var displayMetrics = DisplayMetrics()

            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            binding.ivLogo.layoutParams.height = displayMetrics.widthPixels / 2

            binding.recyclerViewItemClickCallback = recyclerViewItemClickCallback
            binding.executePendingBindings()
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_MOVIE -> {
                var viewHolder = holder as ProductViewHolder
                getItem(position)?.let {
                    viewHolder.initContent(it)
                }
            }
        }
    }

    inner class LoadingViewHolder(var binding: AdapterLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun initContent() {
            binding.resource = afterResource
            binding.retryCallback = afterRetryCallback
            binding.executePendingBindings()
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is Movie) {
            VIEW_TYPE_MOVIE
        } else {
            throw IllegalStateException("Incorrect ViewType found")
        }
    }

}