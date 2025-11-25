package ch.heigvd.iict.daa.labo5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(private val imageIds: List<Int>)
    : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val progressBar: ProgressBar = itemView.findViewById(R.id.image_progress_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageId = imageIds[position]

        // TODO: Step 3 https://daa.iict.ch/images/$imageId.jpg
        holder.progressBar.visibility = View.VISIBLE
        holder.imageView.setImageDrawable(null)
    }

    override fun getItemCount(): Int = imageIds.size
}