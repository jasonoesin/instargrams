package com.example.instargrams.utilities
import android.content.Context
import android.content.Intent

class NavigationUtil {
    fun replaceActivity(ctx: Context, activity: Class<*>?) {
        ctx.startActivity(Intent(ctx, activity))
    }
}