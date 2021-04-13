package tv.superawesome.sdk.publisher.common.ui.video

import android.content.Context
import android.util.AttributeSet
import android.widget.*
import org.koin.java.KoinJavaComponent.inject
import tv.superawesome.lib.savideoplayer.IVideoPlayerControllerView

class AdVideoPlayerControllerView
@JvmOverloads
constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr), IVideoPlayerControllerView {
    private val videoComponentFactory: VideoComponentFactory by inject(VideoComponentFactory::class.java)

    // mask, chronograph & the "show more" button
    private val mask: ImageView = videoComponentFactory.getMask(MASK_ID, context)
    private var chronoBg: ImageView
    private var chronograph: TextView?
    var showMore: Button
    var smallShowMore: Button
    var padlock: ImageButton

    fun setShouldShowSmallClickButton(value: Boolean) {
        if (value) {
            smallShowMore.visibility = VISIBLE
            showMore.visibility = GONE
        } else {
            smallShowMore.visibility = GONE
            showMore.visibility = VISIBLE
        }
    }

    fun shouldShowPadlock(value: Boolean) {
        if (value) {
            padlock.visibility = VISIBLE
        } else {
            padlock.visibility = GONE
        }
    }

    fun setClickListener(listener: OnClickListener?) {
        showMore.setOnClickListener(listener)
        smallShowMore.setOnClickListener(listener)
    }

    override fun setPlaying() { /* N/A */
    }

    override fun setPaused() { /* N/A */
    }

    override fun setCompleted() { /* N/A */
    }

    override fun setError(error: Throwable?) { /* N/A */
    }

    override fun setTime(time: Int, duration: Int) {
        val remaining = (duration - time) / 1000
        if (chronograph != null) {
            val text = CRONO_DEF_TXT + remaining
            chronograph!!.text = text
        }
    }

    override fun isPlaying(): Boolean = true

    override fun show() { /* N/A */
    }

    override fun hide() { /* N/A */
    }

    override fun setMinimised() { /* N/A */
    }

    override fun setMaximised() { /* N/A */
    }

    override fun isMaximised(): Boolean = false

    override fun close() { /* N/A */
    }

    override fun setListener(p0: IVideoPlayerControllerView.Listener?) {
    }

    companion object {
        // constants
        private const val CRONO_DEF_TXT = "Ad: "
        private const val CRONO_INIT_TXT = CRONO_DEF_TXT + "0"
        private const val FIND_OUT_MORE_TXT = "Find out more »"
        const val MASK_ID = 0x1110
        const val CRONO_BG_ID = 0x1111
        const val CRONO_ID = 0x1112
        const val SHOW_MORE_ID = 0x1113
        const val SMALL_SHOW_MORE_ID = 0x1114
        const val CLOSE_ID = 0x1115
        const val PADLOCK_ID = 0x1116
    }

    init {
        // call to super

        // create the background image
        addView(mask)

        // create the chronograph Bg
        chronoBg = videoComponentFactory.getChronographBackground(CRONO_BG_ID, context)
        addView(chronoBg)

        // create the timer label
        chronograph = videoComponentFactory.getChronograph(CRONO_ID, context)
        chronograph!!.text = CRONO_INIT_TXT
        addView(chronograph)

        // create the show more button
        showMore = videoComponentFactory.getClick(SHOW_MORE_ID, context)
        addView(showMore)
        smallShowMore = videoComponentFactory.getSmallClick(SMALL_SHOW_MORE_ID, context)
        smallShowMore.text = FIND_OUT_MORE_TXT
        smallShowMore.visibility = GONE
        addView(smallShowMore)
        padlock = videoComponentFactory.getPadlock(PADLOCK_ID, context)
        addView(padlock)
    }
}
