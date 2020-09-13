package dog.snow.androidrecruittest.ui.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dog.snow.androidrecruittest.PREFS_NAME
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.SPLASHINTENTEXTRAKEY
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.viewModels.SplashViewModel
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.splash_activity.*


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) handelError()

        })
        viewModel.loadingVisibility.observe(this, Observer { visible ->
            if (visible == View.VISIBLE) progressbar.visibility =
                View.VISIBLE else progressbar.visibility = View.GONE
        })
        viewModel.photos.observe(this, Observer { photos ->
            if (photos != null) {
                saveListInSP(photos)
                startMainActivity(photos)
            }
        })
        slideImageToCenter()
    }

    private fun startMainActivity(photos: List<RawPhoto>) {
        slideImageOut()
        Handler().postDelayed({
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra(SPLASHINTENTEXTRAKEY, ArrayList(photos))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }, 500)
    }

    private fun handelError() {
        if (readSpList() != null) {
            Handler().postDelayed({
                readSpList()?.let { startMainActivity(it) }
            }, 1500)
        } else {
            showError()
        }
    }

    private fun saveListInSP(photos: List<RawPhoto>) {
        val mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(photos)
        prefsEditor.putString("Photos", json)
        prefsEditor.apply()
    }

    private fun readSpList(): List<RawPhoto>? {
        val groupListType = object : TypeToken<List<RawPhoto>>() {
        }.type
        val mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val gson = Gson()
        val json: String? = mPrefs.getString("Photos", null)
        return gson.fromJson<List<RawPhoto>>(json, groupListType)
    }

    private fun showError() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.cant_download_dialog_title)
            .setMessage(getString(R.string.cant_download_dialog_message))
            .setPositiveButton(R.string.cant_download_dialog_btn_positive) { _, _ -> viewModel.loadPhotos() }
            .setNegativeButton(R.string.cant_download_dialog_btn_negative) { _, _ -> finish() }
            .create()
            .apply { setCanceledOnTouchOutside(false) }
            .show()
    }

    private fun slideImageToCenter() {
        val mWidth = this.resources.displayMetrics.widthPixels
        val animation2 = ObjectAnimator.ofFloat(
            iv_logo_sd_text,
            "translationX",
            -mWidth / 2.0f + 120
        )
        animation2.start()

        val animation = ObjectAnimator.ofFloat(
            iv_logo_sd_symbol,
            "translationX",
            mWidth / 2.0f - 280
        )
        animation.start()
    }

    private fun slideImageOut() {
        val mHeight = this.resources.displayMetrics.heightPixels
        val animation2 = ObjectAnimator.ofFloat(
            iv_logo_sd_text,
            "translationY",
            -mHeight / 1.0f
        )
        animation2.start()

        val animation = ObjectAnimator.ofFloat(
            iv_logo_sd_symbol,
            "translationY",
            -mHeight / 1.0f
        )
        animation.start()
    }
}