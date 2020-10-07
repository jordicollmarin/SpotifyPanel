package cat.jorcollmar.spotifypanel.commons.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import cat.jorcollmar.spotifypanel.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadImage(uri: Uri?) {
    Picasso
        .get()
        .load(uri)
        .placeholder(R.drawable.ic_gallery)
        .into(this, object : Callback {
            override fun onSuccess() {}

            override fun onError(e: Exception) {
                setImageDrawable(ContextCompat.getDrawable(context, R.drawable.error))
            }
        })
}