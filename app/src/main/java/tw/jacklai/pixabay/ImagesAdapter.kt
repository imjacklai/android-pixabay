package tw.jacklai.pixabay

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_image.view.*
import tw.jacklai.pixabay.model.Image

/**
 * Created by jacklai on 27/03/2018.
 */

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {
    private val images = ArrayList<Image>()

    fun setData(images: List<Image>) {
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bindView(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(image: Image) {
            with (itemView) {
                Glide.with(imageView.context)
                        .load(image.webFormatUrl)
                        .apply(RequestOptions()
                                .placeholder(R.mipmap.ic_launcher_round)
                                .error(R.mipmap.ic_launcher))
                        .into(imageView)
            }
        }
    }
}