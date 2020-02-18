package com.ibrahim.testproject.mainActivity.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ibrahim.testproject.R
import com.ibrahim.testproject.mainActivity.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mainActivityPresenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityPresenter = MainActivityPresenter()
        mainActivityPresenter.getData(this, rvData)

    }
}
