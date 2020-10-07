package cat.jorcollmar.spotifypanel.commons.utils

import android.content.Intent

object IntentUtils {
    private const val CONTENT_TYPE = "text/plain"

    fun getChooserIntent(textToShare: String, title: String): Intent {
        return Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                type = CONTENT_TYPE
                putExtra(Intent.EXTRA_TEXT, textToShare)
            }, title
        )
    }
}