package kz.moviest.app.ui.activities.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kz.moviest.app.R
import kz.moviest.app.databinding.ActivitySplashBinding
import kz.moviest.app.ui_common.base.BaseActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this

        initView()
    }

    private fun initView() {
        navController = findNavController(R.id.nav_host_fragment)
    }

}