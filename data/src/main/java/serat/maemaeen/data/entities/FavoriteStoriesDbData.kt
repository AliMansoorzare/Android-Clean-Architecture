package serat.maemaeen.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_stories")
data class FavoriteStoriesDbData(@PrimaryKey val storyId:Int)

