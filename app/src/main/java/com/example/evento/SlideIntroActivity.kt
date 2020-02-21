package com.example.evento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.evento.adapter.IntroSlideAdapter
import com.example.evento.models.IntroSlide
import kotlinx.android.synthetic.main.activity_slide_intro.*

class SlideIntroActivity : AppCompatActivity() {

    private val introSlideAdapter = IntroSlideAdapter(
        listOf(
            IntroSlide(
                "User Friendly",
                "The application is designed to be easy to use",
                R.drawable.simple_design
            ), IntroSlide(
                "Payment",
                "Easy and fast payment",
                R.drawable.simple_payment
            ), IntroSlide(
                "Happiness",
                "Join us and feel his happiness",
                R.drawable.simple_join
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_intro)
        introSlideViewPager.adapter = introSlideAdapter
        setupIndicator()
        setCurrentIndicator(0)
        introSlideViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(index = position)
                if (introSlideViewPager.currentItem == 0) {
                    btnBack.visibility = View.GONE
                } else {
                    btnBack.visibility = View.VISIBLE
                }
            }
        })

        btnNext.setOnClickListener {
            if (introSlideViewPager.currentItem + 1 < introSlideAdapter.itemCount) {
                introSlideViewPager.currentItem += 1
            } else {
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }

        btnBack.setOnClickListener {
            if (introSlideViewPager.currentItem > 0) {
                introSlideViewPager.currentItem -= 1
            }
        }

//        txtSkipIntro.setOnClickListener {
//            startActivity(Intent(this,LoginActivity::class.java))
//            finish()
//        }
    }

    fun setupIndicator() {
        val indicators = arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainers.addView(indicators[i])
        }
    }

    fun setCurrentIndicator(index: Int) {
        val childCount = indicatorsContainers.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainers.get(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}
