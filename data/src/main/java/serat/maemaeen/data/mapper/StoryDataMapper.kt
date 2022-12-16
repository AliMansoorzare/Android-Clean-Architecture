package serat.maemaeen.data.mapper

import serat.maemaeen.data.entities.ModelDataStoryResponseItem
import serat.maemaeen.data.entities.StoryDbData
import serat.maemaeen.domain.entiities.StoriesEntitiesItem

object StoryDataMapper {
    fun toDomain(modelDataStoryResponseItem: ModelDataStoryResponseItem): StoriesEntitiesItem =
        StoriesEntitiesItem(
            id = modelDataStoryResponseItem.id,
            link_img = modelDataStoryResponseItem.linkImg,
            link_music = modelDataStoryResponseItem.linkMusic,
            name = modelDataStoryResponseItem.name
        )

    fun toDomain(storyDbData: StoryDbData): StoriesEntitiesItem = StoriesEntitiesItem(
        id = storyDbData.id,
        link_img = storyDbData.linkImg,
        link_music = storyDbData.linkMusic,
        name = storyDbData.name
    )

    fun toDbData(storiesEntitiesItem: StoriesEntitiesItem): StoryDbData = StoryDbData(
        id = storiesEntitiesItem.id,
        linkImg = storiesEntitiesItem.link_img,
        linkMusic = storiesEntitiesItem.link_music,
        name = storiesEntitiesItem.name
    )
}