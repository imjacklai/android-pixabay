package tw.jacklai.pixabay.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jacklai on 27/03/2018.
 */

data class Image(
        val id: Int,

        @SerializedName("webformatURL")
        val webFormatUrl: String,

        @SerializedName("largeImageURL")
        val largeImageUrl: String,

        @SerializedName("webformatWidth")
        val webFormatWidth: Int,

        @SerializedName("webformatHeight")
        val webFormatHeight: Int
)