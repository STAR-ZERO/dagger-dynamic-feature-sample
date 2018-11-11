package com.star_zero.sampledynamicfeature

import android.app.Activity
import androidx.fragment.app.Fragment
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.star_zero.sampledynamicfeature.di.AppComponent
import com.star_zero.sampledynamicfeature.di.BaseModuleInjector
import com.star_zero.sampledynamicfeature.di.DaggerAppComponent
import com.star_zero.sampledynamicfeature.module_helper.FeatureModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

class App : SplitCompatApplication(), HasActivityInjector, HasSupportFragmentInjector {

    // メインmoduleで使用する ActivityInjector / FragmentInjector
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>

    // 各Feature moduleで使用する ActivityInjector / FragmentInjector のリスト
    private val moduleActivityInjectors = mutableListOf<DispatchingAndroidInjector<Activity>>()
    private val moduleFragmentInjectors = mutableListOf<DispatchingAndroidInjector<Fragment>>()

    // 実際にInjectを行う AndroidInjector<Activity>
    private val activityInjector = AndroidInjector<Activity> { instance ->
        // maybeInjectでtrueが返却されれればInject成功

        // メインmodule
        if (dispatchingActivityInjector.maybeInject(instance)) {
            return@AndroidInjector
        }

        // 各Feature module
        moduleActivityInjectors.forEach { injector ->
            if (injector.maybeInject(instance)) {
                return@AndroidInjector
            }
        }
        throw IllegalStateException("Injector not found for $instance")
    }

    // 実際にInjectを行う AndroidInjector<Fragment>
    private val fragmentInjector = AndroidInjector<Fragment> { instance ->
        // maybeInjectでtrueが返却されれればInject成功

        // メインmodule
        if (dispatchingFragmentInjector.maybeInject(instance)) {
            return@AndroidInjector
        }

        // 各Feature module
        moduleFragmentInjectors.forEach { injector ->
            if (injector.maybeInject(instance)) {
                return@AndroidInjector
            }
        }
        throw IllegalStateException("Injector not found for $instance")
    }

    // Feature moduleのInjectorを生成したかの判定用Set
    private val injectedModules = mutableSetOf<FeatureModule>()

    // AppComponentと各Feature moduleのComponentから使用される
    val appComponent by lazy {
        DaggerAppComponent.builder().create(this) as AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        // 実際のInjectorを返す
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        // 実際のInjectorを返す
        return fragmentInjector
    }

    // Feature moduleのInjectorを追加する
    // Feature moduleがインストール後に使用される直前で呼び出される
    fun addModuleInjector(module: FeatureModule) {
        if (injectedModules.contains(module)) {
            // 追加済みは何もしない
            return
        }

        // Feature moduleのInjectorを生成
        val clazz = Class.forName(module.injectorName)
        val moduleInjector = clazz.newInstance() as BaseModuleInjector
        // Feature moduleのInjectorのDispatchingAndroidInjectorをInject
        moduleInjector.inject(this)

        // リストに追加
        moduleActivityInjectors.add(moduleInjector.activityInjector())
        moduleFragmentInjectors.add(moduleInjector.fragmentInjector())

        injectedModules.add(module)
    }
}
