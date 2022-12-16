package serat.maemaeen.mahdavistories.mapper

import serat.maemaeen.domain.entiities.StoriesEntitiesItem
import serat.maemaeen.mahdavistories.entities.Story

object StoryEntityMapper {
    fun toStoryListItem(storiesEntitiesItem: StoriesEntitiesItem) = Story(
        id = storiesEntitiesItem.id,
        link_img = storiesEntitiesItem.link_img,
        link_music = storiesEntitiesItem.link_music,
        name = storiesEntitiesItem.name
    )
}