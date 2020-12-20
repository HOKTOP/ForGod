package com.hok.forgod.pojo.Adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hok.forgod.R
import com.hok.forgod.pojo.model.ImageItem
import com.squareup.picasso.Picasso
import java.util.*


class ImageAdapter(
    private val mContext: Context,
    exampleList: ArrayList<ImageItem>
) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder?>() {
    private val mExampleList: ArrayList<ImageItem>
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val v: View =
            LayoutInflater.from(mContext).inflate(R.layout.wallpaperimage, parent, false)
        return ImageViewHolder(v)
    }

  override  fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem: ImageItem = mExampleList[position]
        val imageUrl: String = currentItem.imageUrl
        Picasso.get().load(imageUrl).centerInside().noFade().resize(350, 350)
            .into(holder.itemView.findViewById(R.id.image_view) as ImageView)
    }

    override   fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }

    inner class ImageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        init {
            val mImageView =
                itemView.findViewById<ImageView>(R.id.image_view)
            mImageView.setOnClickListener {
                if (mListener != null) {
                    val position: Int = getAdapterPosition()
                    if (position != RecyclerView.NO_POSITION) {
                        mListener!!.onItemClick(position)
                    }
                }
            }
        }
    }

    init {
        mExampleList = exampleList
    }


}
