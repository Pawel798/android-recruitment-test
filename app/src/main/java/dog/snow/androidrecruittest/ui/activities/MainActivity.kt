package dog.snow.androidrecruittest.ui.activities

import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import dog.snow.androidrecruittest.PREFS_NAME
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.RAW_PHOTO_EXTRA
import dog.snow.androidrecruittest.SPLASHINTENTEXTRAKEY
import dog.snow.androidrecruittest.receivers.ConnectivityReceiver
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.ui.ListFragment
import kotlinx.android.synthetic.main.layout_banner.*


class MainActivity : AppCompatActivity(R.layout.main_activity),
    ListFragment.OnFragmentInteractionListener, ConnectivityReceiver.ConnectivityReceiverListener {
    lateinit var photos: ArrayList<RawPhoto>
    lateinit var listFragment: ListFragment
    var isConnected: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        timer.start()
        photos = intent.getParcelableArrayListExtra(SPLASHINTENTEXTRAKEY)
        listFragment = ListFragment.newInstance(photos)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, listFragment)
                .commitAllowingStateLoss()
        }
    }

    override fun onItemClick(
        rawPhoto: RawPhoto,
        photoView: View,
        titleView: View,
        albumTitleView: View
    ) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(RAW_PHOTO_EXTRA, rawPhoto)
        val p1: Pair<View, String> = Pair.create(photoView as View?, "transitionPhoto")
        val p2: Pair<View, String> = Pair.create(titleView, "transitionTitle")
        val p3: Pair<View, String> = Pair.create(albumTitleView as View?, "transitionAlbumTitle")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3)
        startActivity(intent, options.toBundle())
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            banner.visibility = View.VISIBLE
            timer.start()
        } else {
            banner.visibility = View.GONE
            timer.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        this.isConnected = isConnected
        showNetworkMessage(isConnected)
    }

    val timer = object : CountDownTimer(600000, 1000) {
        override fun onTick(p0: Long) {
        }

        override fun onFinish() {
            //back to splashActivity, show alert
            resetListInSP()
            val intent: Intent = Intent(this@MainActivity, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    private fun resetListInSP() {
        val editor: SharedPreferences.Editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
        editor.clear()
        editor.commit()
    }
}