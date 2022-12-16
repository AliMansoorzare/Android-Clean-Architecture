package serat.maemaeen.data.db

import androidx.room.*
import serat.maemaeen.data.entities.FavoriteStoriesDbData
@Dao
interface FavoriteStoryDao {


    @Query("SELECT * FROM favorite_stories")
    fun getAll(): List<FavoriteStoriesDbData>

    @Query("SELECT * FROM favorite_stories where storyId=:storyId")
    fun get(storyId: Int): FavoriteStoriesDbData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favoriteMovieDbData: FavoriteStoriesDbData)

    @Delete
    fun remove(favoriteMovieDbData: FavoriteStoriesDbData)
}