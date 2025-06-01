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

/**
 * A custom view that slides through a list of images with smooth animation.
 *
 * Supports three directions: LEFT_ONLY, RIGHT_ONLY, and CENTERED (ping-pong).
 */
class SlidingImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    /**
     * Enum to define the sliding direction of the animation.
     */
    enum class Direction {
        LEFT_ONLY, RIGHT_ONLY, CENTERED
    }

    private val imageView: ImageView = ImageView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).also {
            it.marginStart = -200
            it.marginEnd = -200
        }
        scaleType = ImageView.ScaleType.CENTER_CROP
    }

    private var direction: Direction = Direction.CENTERED
    private var translationValue = -120f

    private val imageQueue: Queue<String> = LinkedList()
    private val handler = Handler(Looper.getMainLooper())

    private var animationDuration = 9000L
    private var imageSwitchDelay = 11000L

    init {
        addView(imageView)
    }

    /**
     * Set the image URLs to be displayed. Starts the slideshow.
     * @param imageUrls A list of image URLs.
     */
    fun setImageUrls(imageUrls: List<String>) {
        imageUrls.forEach {
            imageQueue.add(it)
            preloadImage(it)
        }
        imageQueue.poll()?.let { startSlideshow(it) }
    }

    /**
     * Set the slide direction.
     * @param direction One of LEFT_ONLY, RIGHT_ONLY, CENTERED.
     */
    fun setDirection(direction: Direction) {
        this.direction = direction
        if (direction != Direction.CENTERED) {
            imageSwitchDelay = animationDuration
        }
        translationValue = if (direction == Direction.LEFT_ONLY) -120f else 120f
    }

    /**
     * Set the slide animation duration.
     * @param duration Duration in milliseconds.
     */
    fun setAnimationDuration(duration: Long) {
        this.animationDuration = duration
    }

    /**
     * Set the delay between image switches.
     * @param delay Delay in milliseconds.
     */
    fun setImageSwitchDelay(delay: Long) {
        this.imageSwitchDelay = delay
    }

    /**
     * Starts the slideshow with a specific image, triggering animation and scheduling the next switch.
     *
     * @param url The image URL to display.
     */
    private fun startSlideshow(url: String) {
        imageQueue.add(url)
        loadImage(url)
        animateImage()
        scheduleNextImage()
    }

    /** update the image with the url provided
     * @param url The image URL to display.
     */
    private fun updateImage(url: String) {
        imageQueue.add(url)
        loadImage(url)
        scheduleNextImage()
    }

    /**
     * Starts the slide animation on the ImageView based on the current direction.
     * Repeats continuously using a recursive `withEndAction`.
     */
    private fun animateImage() {
        imageView.animate()
            .translationX(translationValue)
            .setDuration(animationDuration)
            .withEndAction {
                if (direction == Direction.CENTERED) {
                    translationValue *= -1
                } else {
                    imageView.translationX = 0f
                }
                animateImage()
            }
            .start()
    }

    /**
     * Schedules the next image to be displayed after a delay using a Handler.
     */
    private fun scheduleNextImage() {
        handler.postDelayed({
            imageQueue.poll()?.let { updateImage(it) }
        }, imageSwitchDelay)
    }

    /**
     * Loads the image into the ImageView using Glide.
     *
     * @param url Image URL to load.
     */
    private fun loadImage(url: String) {
        Glide.with(context)
            .load(url.toUri())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }

    /**
     * Preloads the image to cache it before it is displayed.
     *
     * @param url Image URL to preload.
     */
    private fun preloadImage(url: String) {
        Glide.with(context)
            .load(url.toUri())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .preload()
    }

    /**
     * Removes any pending callbacks when the view is detached to avoid memory leaks.
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacksAndMessages(null)
    }
}
