package tw.jacklai.pixabay.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import tw.jacklai.pixabay.GlideApp
import tw.jacklai.pixabay.R
import tw.jacklai.pixabay.widget.ScaleImageView
import tw.jacklai.pixabay.model.Image

/**
 * Created by jacklai on 29/03/2018.
 */

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val image = intent.extras.getParcelable<Image>("data")

        (imageView as ScaleImageView).setInitSize(image.webFormatWidth, image.webFormatHeight)

        userLabel.text = image.user
        likes.text = "${image.likes}"
        favorites.text = "${image.favorites}"
        tags.text = image.tags

        GlideApp.with(userImageView.context)
                .load(image.userImageUrl)
                .placeholder(R.mipmap.image_loading)
                .error(R.mipmap.image_not_found)
                .fallback(R.mipmap.image_not_found)
                .override((image.webFormatWidth * 0.6).toInt(), (image.webFormatHeight * 0.6).toInt())
                .into(userImageView)

        GlideApp.with(imageView.context)
                .load(image.webFormatUrl)
                .placeholder(R.mipmap.image_loading)
                .error(R.mipmap.image_not_found)
                .fallback(R.mipmap.image_not_found)
                .into(imageView)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}