package com.example.parkinggent.ui

import androidx.annotation.StringRes
import com.example.parkinggent.R

enum class NavigationRoutes(@StringRes val title: Int) {
    HOME(title = R.string.home_title),
    ABOUT(title = R.string.about_title);
}