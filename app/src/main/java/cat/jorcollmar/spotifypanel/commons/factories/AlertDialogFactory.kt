package cat.jorcollmar.spotifypanel.commons.factories

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import cat.jorcollmar.spotifypanel.R

object AlertDialogFactory {
    fun createAlertDialog(
        context: Context, cancelable: Boolean,
        message: String, buttonText: String, buttonAction: (dialog: DialogInterface) -> Unit
    ): AlertDialog {
        return AlertDialog.Builder(context, R.style.Theme_MaterialComponents_Light_Dialog)
            .setCancelable(cancelable)
            .setMessage(message)
            .setPositiveButton(buttonText) { dialog, _ -> buttonAction(dialog) }
            .create()
    }
}