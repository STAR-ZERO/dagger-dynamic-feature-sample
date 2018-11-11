package com.star_zero.sampledynamicfeature.module_helper

const val PACKAGE_NAME = "com.star_zero.sampledynamicfeature"

interface FeatureModule {
    val name: String
    val injectorName: String
}

object Modules {

    private val modules = listOf(
        FeatureOne,
        FeatureTwo
    )

    fun getModuleFromName(moduleName: String): FeatureModule {
        modules.forEach {
            if (it.name == moduleName) {
                return it
            }
        }
        throw IllegalArgumentException("$moduleName is not found")
    }

    object FeatureOne: FeatureModule {
        override val name = "feature_one"
        override val injectorName = "$PACKAGE_NAME.feature_one.di.FeatureOneInjector"
    }

    object FeatureTwo: FeatureModule {
        override val name = "feature_two"
        override val injectorName = "$PACKAGE_NAME.feature_two.di.FeatureTwoInjector"
    }
}
