package kz.moviest.app.ui_common.base

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kz.moviest.app.R
import kz.moviest.app.utils.locale.LocaleUtils
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    /**
     * DI
     */
    @Inject
    protected lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return ViewModelProviders.of(
            this,
            viewModelFactory
        ).get(modelClass)
    }

    /**
     * Progress dialog
     */
    private var progressDialog: ProgressDialog? = null

    protected fun showProgressDialog() {
        if (progressDialog == null
            || !progressDialog!!.isShowing
        ) {
            progressDialog = ProgressDialog.show(this, "", getString(R.string.loading), true, false)
        }
    }

    protected fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    /**
     * Exception
     */
    protected fun handleException(exception: Exception?) {
        ExceptionHandler.handleException(this, exception)
    }

    protected fun handleExceptionView(exception: Exception?) {
        ExceptionHandler.handleExceptionView(this, exception)
    }

    protected fun handleExceptionDialog(exception: Exception?) {
        ExceptionHandler.handleExceptionDialog(this, exception)
    }

    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(newBase)
        super.attachBaseContext(LocaleUtils.setLocale(newBase))
    }

}