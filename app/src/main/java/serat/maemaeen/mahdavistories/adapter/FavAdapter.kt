package serat.maemaeen.mahdavistories.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import serat.maemaeen.mahdavistories.R
import serat.maemaeen.mahdavistories.databinding.ItemFavBinding
import serat.maemaeen.mahdavistories.entities.Story
import serat.maemaeen.mahdavistories.util.GlideLoading
import soup.neumorphism.NeumorphCardView
import soup.neumorphism.NeumorphImageView
import soup.neumorphism.ShapeType

class FavAdapter(

    private val glideLoading: GlideLoading,
    private val onStoryClicked: (storyId: Int) -> Unit,
) :
    RecyclerView.Adapter<FavAdapter.StoryViewHolder>() {
    private lateinit var data: List<Story>

    class StoryViewHolder(
        private val binding: ItemFavBinding,
        private val glideLoading: GlideLoading
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            items: Story,
            onStoryClicked: (storyId: Int) -> Unit,
        ) {
            binding.apply {
                glideLoading.load(this.itemImgMain, itemImgMain, items.link_img)
                itemImgMain.setOnClickListener {
                    onStoryClicked(items.id)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding: ItemFavBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_fav, parent, false
        )
        return StoryViewHolder(binding, glideLoading)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {

        holder.bind(data[position], onStoryClicked)
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<Story>) {
        this.data = data
    }
}