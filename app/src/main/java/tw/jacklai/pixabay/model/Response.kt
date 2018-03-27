package tw.jacklai.pixabay.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jacklai on 27/03/2018.
 */

data class Response(
        val total: Int,

        @SerializedName("hits")
        val images: List<Image>
)