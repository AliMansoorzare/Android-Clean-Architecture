package serat.maemaeen.data.stories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import serat.maemaeen.data.entities.StoryDbData

@Dao
interface StoryDao {
    @Query("SELECT * FROM Stroies")
    fun getStories(): List<StoryDbData>

    @Query("SELECT * FROM Stroies WHERE id = :storyId")
    fun getStory(storyId: Int): StoryDbData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveStories(stories: List<StoryDbData>)

    @Query("DELETE FROM stroies")
    fun deleteStories()

    @Query("SELECT * FROM stroies WHERE id IN (:storyIds)")
    fun getFavoriteStories(storyIds: List<Int>): List<StoryDbData>
}