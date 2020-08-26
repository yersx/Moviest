package kz.moviest.app.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kz.moviest.app.R
import kz.moviest.app.databinding.FragmentSplashBinding
import kz.moviest.app.ui.activities.main.MainActivity
import kz.moviest.app.ui_common.base.BaseFragment

class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(SplashViewModel::class.java!!)
        binding.viewModel = viewModel

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.openMain.observe(
            viewLifecycleOwner,
            Observer {
                it.getContentIfNotHandled()?.let {
                    val intent = MainActivity.getIntent(context)
                    startActivity(intent)

                    activity?.finish()
                }
            }
        )
    }

}