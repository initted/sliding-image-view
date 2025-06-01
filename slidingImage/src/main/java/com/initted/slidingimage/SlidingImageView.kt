package com.initted.slidingimage

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.LinkedList
import java.util.Queue

class SlidingImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var internalImageView: ImageView = ImageView(context)
    private var imageUrlQueue: Queue<String> = LinkedList()
    private val mHandler = Handler(Looper.myLooper() ?: Looper.getMainLooper())
    private var translationValue = -120f

    init {
        internalImageView.layoutParams =
            MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
                marginStart = -200
                marginEnd = -200
            }
        internalImageView.scaleType = ImageView.ScaleType.CENTER_CROP
        addView(internalImageView)
    }

    fun setImageUrls(imageUrlList: List<String>) {
        imageUrlList.forEach {
            imageUrlQueue.add(it)
            loadImage(item = it, preload = true)
        }
        val item = imageUrlQueue.poll()
        item?.let { start(item = it) }
    }

    private fun start(item: String) {
        imageUrlQueue.add(item)
        loadImage(item = item)
        startSlidingAnimation()
        startSwitchingAnimation()
    }

    private fun startSlidingAnimation() {
        internalImageView.animate()
            .translationX(translationValue)
            .setDuration(8000)
            .withEndAction {
                translationValue *= -1
                startSlidingAnimation()
            }
            .start()
    }

    private fun startSwitchingAnimation() {
        mHandler.postDelayed({
            val item = imageUrlQueue.poll()
            item?.let { start(item = it) }
        }, 9500)
    }

    private fun loadImage(item: String, preload: Boolean = false) {
        val builder =
            Glide.with(context).load(item.toUri()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        if (!preload) {
            builder.into(internalImageView)
        } else {
            builder.preload()
        }
    }

}