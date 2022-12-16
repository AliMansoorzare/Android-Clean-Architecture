package serat.maemaeen.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "Stroies")
data class StoryDbData(
    @SerializedName("id")
    @PrimaryKey val id: Int,
    @SerializedName("link_img")
    val linkImg: String,
    @SerializedName("link_music")
    val linkMusic: String,
    @SerializedName("name")
    val name: String
) {
    var isFavorite:Boolean = false
}