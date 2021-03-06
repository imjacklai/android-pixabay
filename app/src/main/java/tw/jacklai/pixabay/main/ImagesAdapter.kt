package tw.jacklai.pixabay.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_list_image.view.*
import tw.jacklai.pixabay.GlideApp
import tw.jacklai.pixabay.R
import tw.jacklai.pixabay.widget.ScaleImageView
import tw.jacklai.pixabay.ViewType
import tw.jacklai.pixabay.model.Image

/**
 * Created by jacklai on 27/03/2018.
 */

class ImagesAdapter(private val itemClick: (Image) -> Unit) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {
    private val images = ArrayList<Image>()

    private var viewType: ViewType = ViewType.LIST

    fun addData(images: List<Image>) {
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    fun removeData() {
        images.clear()
        notifyDataSetChanged()
    }

    fun setViewType(viewType: ViewType) {
        this.viewType = viewType
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layout = when (viewType) {
            0 -> R.layout.item_list_image
            1 -> R.layout.item_grid_image
            2 -> R.layout.item_staggered_grid_image
            else -> R.layout.item_list_image
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ImageViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType.value
    }

    inner class ImageViewHolder(view: View, private val itemClick: (Image) -> Unit) : RecyclerView.ViewHolder(view) {
        fun bindView(image: Image) {
            with (itemView) {
                if (viewType == ViewType.STAGGERED_GRID) {
                    (imageView as ScaleImageView).setInitSize(image.webFormatWidth, image.webFormatHeight)
                }

                GlideApp.with(imageView.context)
                        .load(image.webFormatUrl)
                        .placeholder(R.mipmap.image_loading)
                        .error(R.mipmap.image_not_found)
                        .fallback(R.mipmap.image_not_found)
                        .override((image.webFormatWidth * 0.6).toInt(), (image.webFormatHeight * 0.6).toInt())
                        .into(imageView)

                setOnClickListener { itemClick(image) }
            }
        }
    }
}