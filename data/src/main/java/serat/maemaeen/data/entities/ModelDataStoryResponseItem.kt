package serat.maemaeen.data.entities


import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ModelDataStoryResponseItem(
    @SerializedName("id")
    @PrimaryKey val id: Int,
    @SerializedName("link_img")
    val linkImg: String,
    @SerializedName("link_music")
    val linkMusic: String,
    @SerializedName("name")
    val name: String
)