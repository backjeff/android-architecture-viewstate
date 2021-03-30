package com.backjeff.android.architecture.viewstate.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.backjeff.android.architecture.viewstate.ViewStateListener

class MainActivity : AppCompatActivity(), ViewStateListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
