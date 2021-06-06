package com.example.tinkofftest.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.tinkofftest.MyApp
import com.example.tinkofftest.R
import com.example.tinkofftest.model.ResponseEntry
import com.example.tinkofftest.network.NetworkHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.util.*


class MainFragment : Fragment() {

    private var listener: NetworkErrorListener? = null

    private var cachedEntries = LinkedList<ResponseEntry>()
    private var entryIndicator = -1
    lateinit var imageView: ImageView
    lateinit var gifDescription: TextView
    lateinit var backButton: FloatingActionButton
    lateinit var nextButton: FloatingActionButton
    private lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        imageView = root.findViewById(R.id.gif)
        gifDescription = root.findViewById(R.id.gif_description)
        backButton = root.findViewById(R.id.back_button)
        nextButton = root.findViewById(R.id.next_button)
        circularProgressDrawable = context?.let {
            CircularProgressDrawable(it).apply {
                setColorSchemeColors(R.color.black)
                centerRadius = 50f
                strokeWidth = 5f
                start()
            }
        }!!

        loadGIF()

       nextButton.setOnClickListener {
            loadGIF()
        }

        backButton.setOnClickListener {
            Log.d("LINKIND", entryIndicator.toString())
            if (entryIndicator > 0) {
                val entry = cachedEntries[--entryIndicator]
                updateGIF(entry.gifURL, entry.description)
            }

        }

        return root
    }

    private fun loadGIF() {
        if ((entryIndicator == -1) || (entryIndicator == cachedEntries.size-1)) {
            if (context?.let { NetworkHelper.checkConnection(it) }!!) {
                lifecycleScope.launch {
                    val response = MyApp.INSTANCE.api.getEntry()
                    if (response.isSuccessful) {
                        val entry = response.body()
                        var URL = entry?.gifURL ?: ""
                        var description = entry?.description ?: ""
                        updateGIF(URL, description)

                        entryIndicator++
                        cachedEntries.add(entry!!)
                    }
                }
            } else {
                listener?.onNetworkError()
            }
        } else {
            val entry = cachedEntries[++entryIndicator]

            updateGIF(entry.gifURL, entry.description)
        }
    }

    private fun updateGIF(url: String, description: String) {
        Glide
                .with(requireContext())
                .asGif()
                .load(url)
                .placeholder(circularProgressDrawable)
                .listener(object : RequestListener<GifDrawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                        loadGIF()
                        return false
                    }
                    override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(imageView)

        gifDescription.text = description
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NetworkErrorListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}