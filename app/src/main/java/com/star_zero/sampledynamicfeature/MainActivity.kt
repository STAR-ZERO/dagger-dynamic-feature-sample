package com.star_zero.sampledynamicfeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.star_zero.sampledynamicfeature.module_helper.*
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(this)
    }

    private val listener = SplitInstallStateUpdatedListener { state ->
        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                Timber.d("DOWNLOADING")
                text_status.text = "DOWNLOADING"
            }
            SplitInstallSessionStatus.INSTALLING -> {
                text_status.text = "INSTALLING"
            }
            SplitInstallSessionStatus.INSTALLED -> {
                Timber.d("INSTALLED")

                // Enable module immediately
                SplitCompat.install(this)

                text_status.text = "INSTALLED"
                navigateModule(state.moduleNames()[0])
            }
            SplitInstallSessionStatus.FAILED -> {
                Timber.d("FAILED")
                text_status.text = "FAILED"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = viewModel.getData()
        Timber.d("Main: $data")

        button_feature_one.setOnClickListener {
            loadModule(Modules.FeatureOne)
        }

        button_feature_two.setOnClickListener {
            loadModule(Modules.FeatureTwo)
        }
    }

    override fun onResume() {
        super.onResume()
        splitInstallManager.registerListener(listener)
    }

    override fun onPause() {
        splitInstallManager.unregisterListener(listener)
        super.onPause()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        // FragmentのInjectorはApplicationクラスのを使うようにする
        // 必要があればAppクラスと同様にAndroidInjectorを使ってやる（今回はやってない）
        return (application as App).supportFragmentInjector()
    }

    private fun loadModule(module: FeatureModule) {
        if (splitInstallManager.installedModules.contains(module.name)) {
            Timber.d("${module.name} already installed")
            text_status.text = "${module.name} already installed"
            navigateModule(module.name)
            return
        }

        val request = SplitInstallRequest
            .newBuilder()
            .addModule(module.name)
            .build()

        splitInstallManager.startInstall(request)
        Timber.d("Start install for ${module.name}")
        text_status.text = "Start install for $module.name"
    }

    private fun navigateModule(moduleName: String) {
        val app = application as App
        val module = Modules.getModuleFromName(moduleName)

        when (module) {
            Modules.FeatureOne -> {
                app.addModuleInjector(module)
                startActivity(intentTo(Activities.FeatureOne))
            }
            Modules.FeatureTwo -> {
                app.addModuleInjector(module)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, newFragment(Fragments.FeatureTwo))
                    .commit()
            }
        }
    }
}
