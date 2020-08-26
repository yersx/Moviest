package kz.moviest.app.ui.activities.main

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.moviest.app.R
import kz.moviest.app.databinding.ActivityMainBinding
import kz.moviest.app.ui_common.base.BaseActivity

class MainActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context?) = Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = getViewModel(MainViewModel::class.java!!)
        binding.viewModel = viewModel

        initView()
    }

    private fun initView() {
//        binding.constraintLayout.layoutTransition?.enableTransitionType(LayoutTransition.CHANGING)

        navController = findNavController(R.id.nav_host_fragment)

        initToolbar()
        initOnDestinationChangedListener()
        initNavigationView()
    }

    private fun initToolbar() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.aboutFragment,
                R.id.moviesFragment
            ),
            binding.drawerLayout
        )

        setSupportActionBar(this.toolbar)
        with(binding) {

            setSupportActionBar(toolbar)
            toolbar.setupWithNavController(navController, appBarConfiguration)
        }
    }

    private fun initOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.aboutFragment
                -> {
                    binding.appBarLayout.setBackgroundResource(R.color.transparent)
                    binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    val colorFilter =
                        PorterDuffColorFilter(ContextCompat.getColor(this, R.color._03B7BC), PorterDuff.Mode.MULTIPLY)
                    val navigationIcon = binding.toolbar.navigationIcon
                    if (navigationIcon != null) {
                        navigationIcon!!.colorFilter = colorFilter
                        binding.toolbar.navigationIcon = navigationIcon
                    }
                }
                R.id.moviesFragment -> {
                    binding.appBarLayout.setBackgroundResource(R.color.transparent)
                    binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color._03B7BC))
                    val colorFilter =
                        PorterDuffColorFilter(ContextCompat.getColor(this, R.color._03B7BC), PorterDuff.Mode.MULTIPLY)
                    val navigationIcon = binding.toolbar.navigationIcon
                    if (navigationIcon != null) {
                        navigationIcon!!.colorFilter = colorFilter
                        binding.toolbar.navigationIcon = navigationIcon
                    }

                }
            }
        }
    }

    private fun initNavigationView() {
        binding.navigationView.background.alpha = 90
        binding.navigationView.setupWithNavController(navController)
        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                else -> {
                    closeDrawer()
                    val result = NavigationUI.onNavDestinationSelected(it, navController)
                    binding.navigationView.setCheckedItem(it.itemId)
                    result
                }
            }
        }
    }

    private fun openDrawer() {
        binding.drawerLayout.openDrawer(binding.navigationView, true)
    }

    private fun closeDrawer() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            binding.drawerLayout.closeDrawer(binding.navigationView, true)
        }
    }

}