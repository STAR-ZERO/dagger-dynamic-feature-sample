package com.star_zero.sampledynamicfeature.module_helper

import android.content.Intent

fun intentTo(addressableActivity: AddressableActivity): Intent {
    return Intent(Intent.ACTION_VIEW).setClassName(
        PACKAGE_NAME,
        addressableActivity.className
    )
}

interface AddressableActivity {
    val className: String
}

object Activities {

    object FeatureOne : AddressableActivity {
        override val className = "$PACKAGE_NAME.feature_one.FeatureOneActivity"
    }
}
