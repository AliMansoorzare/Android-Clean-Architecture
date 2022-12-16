package serat.maemaeen.mahdavistories.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ListAdapter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import serat.maemaeen.mahdavistories.R
import serat.maemaeen.mahdavistories.databinding.ItemAdapterFeedfragBinding
import serat.maemaeen.mahdavistories.entities.Story
import serat.maemaeen.mahdavistories.util.GlideLoading
import soup.neumorphism.NeumorphCardView
import soup.neumorphism.NeumorphImageView
import soup.neumorphism.ShapeType

class StoryAdapter(

    private val glideLoading: GlideLoading,
    private val onStoryClicked: (storyId: Int,mus:String) -> Unit,
) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    private lateinit var data: List<Story>

    class StoryViewHolder(
        private val binding: ItemAdapterFeedfragBinding,
        private val glideLoading: GlideLoading
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            items: Story,
            onStoryClicked: (storyId: Int,mus:String) -> Unit,
        ) {
            binding.apply {
                glideLoading.load(this.itemImgMain, itemImgMain, items.link_img)
                parent.setOnClickListener {
                    val shapeType = (it as NeumorphCardView).getShapeType()
                    if (shapeType == ShapeType.PRESSED) {
                        it.setShapeType(ShapeType.FLAT)
                    } else {
                        onStoryClicked(items.id,items.link_music)
                        Log.i("kk", "bind: ${items.id}")
                        it.setShapeType(ShapeType.PRESSED)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding: ItemAdapterFeedfragBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_adapter_feedfrag, parent, false
        )
        return StoryViewHolder(binding, glideLoading)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,R.anim.anim_two))
        holder.bind(data[position], onStoryClicked)
    }

    override fun getItemCount(): Int = data.size

    fun setData(data: List<Story>) {
        this.data = data
    }
}