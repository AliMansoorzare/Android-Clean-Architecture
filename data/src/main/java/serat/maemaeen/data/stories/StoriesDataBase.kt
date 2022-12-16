package serat.maemaeen.data.stories

import androidx.room.Database
import androidx.room.RoomDatabase
import serat.maemaeen.data.entities.StoryDbData
@Database(entities = [StoryDbData::class], version = 1, exportSchema = false)
abstract class StoriesDataBase: RoomDatabase() {
    abstract fun storyDao():StoryDao
}