package xyz.moratech.marvelheroes.base.view

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.View


/**
 * @author Juan Mora
 * @since 1.0.0
 */
abstract class BaseActivity : AppCompatActivity() {
    protected fun <T> observe(liveData: MutableLiveData<T>, observer: Observer<T>) {
        liveData.observe(this, observer)
    }

    protected fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    protected fun showMessage(view: View, message: Int) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    protected fun showMessageWithAction(view: View, message: String, actionMessage: String,
                                        listener: SnackBarDismissListener) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionMessage) {
                    listener.onDismiss()
                }
                .show()
    }

    protected fun showMessageWithAction(view: View, message: Int, actionMessage: String,
                                        listener: SnackBarDismissListener) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionMessage) {
                    listener.onDismiss()
                }
                .show()
    }

    protected fun showMessageWithAction(view: View, message: Int, actionMessage: Int,
                                        listener: SnackBarDismissListener) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionMessage) {
                    listener.onDismiss()
                }
                .show()
    }

    interface SnackBarDismissListener {
        fun onDismiss()
    }
}