package com.hemanth.cricbuzz.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hemanth.cricbuzz.R
import com.hemanth.cricbuzz.databinding.ActivitySplashBinding
import com.hemanth.cricbuzz.ui.home.HomeActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


/**
 *<h1>SplashActivity</h1>
 *<p>this is class for splash wait for 4 sec</p>
 * @author : Hemanth
 * @since : 11 Nov 2020
 * @version : 1.0
 */
class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel.initStart()
        setupObserver()
        runAnimation()
    }

    /**
     * <h2>setupObserver</h2>
     * <p>this is the method for observing the duration</p>
     */
    private fun setupObserver() {
        viewModel.routeEvent.observe(this, Observer {
            if (it) {
                launchHomeScreen()
            }
        })
    }

    private fun launchHomeScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun runAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.tvSplashTitle.startAnimation(animation)
    }
}