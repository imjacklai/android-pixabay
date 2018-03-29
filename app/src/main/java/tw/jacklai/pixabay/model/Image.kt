package tw.jacklai.pixabay.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by jacklai on 27/03/2018.
 */

@Parcelize
data class Image(
        val id: Int,

        @SerializedName("largeImageURL")
        val largeImageUrl: String,

        @SerializedName("webformatURL")
        val webFormatUrl: String,

        @SerializedName("webformatWidth")
        val webFormatWidth: Int,

        @SerializedName("webformatHeight")
        val webFormatHeight: Int,

        val user: String,

        @SerializedName("userImageURL")
        val userImageUrl: String,

        val likes: Int,
        val favorites: Int
) : Parcelable