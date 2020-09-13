package dog.snow.androidrecruittest.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dog.snow.androidrecruittest.R
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.ui.adapter.ListAdapter
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.android.synthetic.main.list_fragment.*

private const val PHOTOS_PARAM = "param1"

class ListFragment : Fragment(R.layout.list_fragment) {
    private var photos: ArrayList<RawPhoto>? = null
    private var mListener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(requireContext().toString() + " must implement OnFragmentInteractionListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            photos = it.getParcelableArrayList(PHOTOS_PARAM)
        }
        val listAdapter = photos?.let { ListAdapter(it) { rawPhoto: RawPhoto, view: View, view1: View, view2: View -> onClick(rawPhoto,view,view1,view2) } }
        rv_items.apply {
            rv_items.layoutManager = LinearLayoutManager(activity);
            adapter = listAdapter
        }
        listAdapter?.submitList(photos)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listAdapter?.filter?.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun onClick(rawPhoto: RawPhoto, photoView: View, titleView:View, albumTitleView:View) {
        mListener?.onItemClick(rawPhoto,photoView, titleView, albumTitleView)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: ArrayList<RawPhoto>) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PHOTOS_PARAM, param1)
                }
            }
    }
    interface OnFragmentInteractionListener {
        fun onItemClick(rawPhoto: RawPhoto, photoView:View, titleView:View, albumTitleView:View)
    }

}