package dog.snow.androidrecruittest.ui.adapter

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.repository.model.RawPhoto
import java.util.*

class ListAdapter(
    private var items: MutableList<RawPhoto>,
    private val onClick: (rawPhoto: RawPhoto, photoView: View, titleView: View, albumTitleView: View) -> Unit
) :
    androidx.recyclerview.widget.ListAdapter<RawPhoto, ListAdapter.ViewHolder>(DIFF_CALLBACK),
    Filterable {
    var mFilteredList: MutableList<RawPhoto> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ViewHolder(
        itemView: View,
        private val onClick: (rawPhoto: RawPhoto, photoView: View, titleView: View, albumTitleView: View) -> Unit
    ) :
        RecyclerView.ViewHolder(itemView) {
        var placeholderRes: Int = getPlaceHolder(itemView.context)

        fun bind(item: RawPhoto) = with(itemView) {
            val ivThumb: ImageView = findViewById(R.id.iv_thumb)
            val tvTitle: TextView = findViewById(R.id.tv_photo_title)
            val tvAlbumTitle: TextView = findViewById(R.id.tv_album_title)
            tvTitle.text = item.title
            tvAlbumTitle.text = item.album.title
            Picasso.get()
                .load(item.thumbnailUrl)
                .placeholder(placeholderRes)
                .into(ivThumb)
            setOnClickListener { onClick(item, ivThumb, tvTitle, tvAlbumTitle) }
        }

        private fun getPlaceHolder(context: Context): Int {
            val nightModeFlags: Int = context.resources.configuration.uiMode and
                    Configuration.UI_MODE_NIGHT_MASK
            when (nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    return R.drawable.ic_placeholder_dark
                }
            }
            return R.drawable.ic_placeholder
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RawPhoto>() {
            override fun areItemsTheSame(oldItem: RawPhoto, newItem: RawPhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RawPhoto, newItem: RawPhoto): Boolean =
                oldItem == newItem
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {

                val charString = charSequence.toString()

                mFilteredList = if (charString.isEmpty()) {
                    items

                } else {

                    val filteredList = items
                        .filter {
                            (it.title.toLowerCase(Locale.ROOT)
                                .contains(charString)) || (it.album.title.toLowerCase(
                                Locale.ROOT
                            ).contains(charString))
                        }
                        .toMutableList()

                    filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                submitList(filterResults.values as MutableList<RawPhoto>)
                notifyDataSetChanged()
            }
        }
    }
}