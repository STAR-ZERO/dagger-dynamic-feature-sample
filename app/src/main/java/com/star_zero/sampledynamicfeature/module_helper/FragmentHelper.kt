package com.star_zero.sampledynamicfeature.module_helper

import androidx.fragment.app.Fragment

fun newFragment(addressableFragment: AddressableFragment): Fragment {
    return Class.forName(addressableFragment.className).newInstance() as Fragment
}

interface AddressableFragment {
    val className: String
}

object Fragments {

    object FeatureTwo : AddressableFragment {
        override val className = "$PACKAGE_NAME.feature_two.FeatureTwoFragment"
    }
}
