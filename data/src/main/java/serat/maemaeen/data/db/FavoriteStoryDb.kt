package serat.maemaeen.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import serat.maemaeen.data.entities.FavoriteStoriesDbData

@Database(entities = [FavoriteStoriesDbData::class], version = 1, exportSchema = false)
abstract class FavoriteStoryDb:RoomDatabase() {
    abstract fun favoriteStoryDao():FavoriteStoryDao

}

