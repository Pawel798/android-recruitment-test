package dog.snow.androidrecruittest.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.RAW_PHOTO_EXTRA
import dog.snow.androidrecruittest.repository.model.RawPhoto
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val rawPhoto = intent.getParcelableExtra<RawPhoto>(RAW_PHOTO_EXTRA)
        setSupportActionBar(toolbar)
        supportActionBar?.title = rawPhoto!!.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Picasso.get()
            .load(rawPhoto.thumbnailUrl)
            .placeholder(R.drawable.ic_placeholder)
            .into(iv_photo)
        tv_photo_title.text = rawPhoto.title
        tv_album_title.text = rawPhoto.album.title
        tv_email.text = rawPhoto.album.user.email
        tv_phone.text = rawPhoto.album.user.phone
        tv_username.text = rawPhoto.album.user.username
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