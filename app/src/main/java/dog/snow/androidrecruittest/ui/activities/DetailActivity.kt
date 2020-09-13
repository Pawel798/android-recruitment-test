package dog.snow.androidrecruittest.ui.activities

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.RAW_PHOTO_EXTRA
import dog.snow.androidrecruittest.repository.model.RawPhoto
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.splash_activity.*

class DetailActivity : AppCompatActivity() {
     var placeholderRes:Int = R.drawable.ic_placeholder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        changePlaceholderDependOnDarkMode()
        val rawPhoto = intent.getParcelableExtra<RawPhoto>(RAW_PHOTO_EXTRA)
        setSupportActionBar(toolbar)
        supportActionBar?.title = rawPhoto!!.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Picasso.get()
            .load(rawPhoto.thumbnailUrl)
            .placeholder(placeholderRes)
            .into(iv_photo)
        tv_photo_title.text = rawPhoto.title
        tv_album_title.text = rawPhoto.album.title
        tv_email.text = rawPhoto.album.user.email
        tv_phone.text = rawPhoto.album.user.phone
        tv_username.text = rawPhoto.album.user.username
    }
    private fun changePlaceholderDependOnDarkMode(){
        val nightModeFlags: Int = this.resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
               placeholderRes = R.drawable.ic_placeholder_dark
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}