package com.initted.slidingImageDemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.initted.slidingimage.SlidingImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val slidingImageView: SlidingImageView = findViewById(R.id.slidingImageView)
        val urls =
            listOf(
                "https://demo.enhance.diagnal.com/resources/images/link/b4f58914-c832-3664-95ca-2846e862cb7b/a0827e4f-a6bf-5d7b-aea3-47a9a53569e0/1737375129088/0:0:956:540/960*540/0fdfa906-23a3-40ac-8387-312da4a89742_169-lg-13.png",
                "https://demo.enhance.diagnal.com/resources/images/link/b042eebe-cc88-332f-97e3-1c4290381478/a0827e4f-a6bf-5d7b-aea3-47a9a53569e0/1737044645124/0:0:956:540/960*540/6a783626-0fa8-4933-90e1-d10c449c4d78_169-lg.png",
                "https://demo.enhance.diagnal.com/resources/images/link/6a6ebfef-ec86-378d-b838-06ef769882e7/a0827e4f-a6bf-5d7b-aea3-47a9a53569e0/1743082965050/0:0:1434:810/960*540/f2cb23c1-380a-477c-8de6-a81be5d8e49f_details-page.jpg",
                "https://demo.enhance.diagnal.com/resources/images/link/06a367e7-58b5-337a-84a7-cdf9ee7bca22/a0827e4f-a6bf-5d7b-aea3-47a9a53569e0/1737372137676/0:0:956:540/960*540/9422805d-6eee-4f92-b499-9a7fea8907f6_169-lg-4.png"
            )
        slidingImageView.setDirection(SlidingImageView.Direction.CENTERED)
        slidingImageView.setImageUrls(urls)
    }
}