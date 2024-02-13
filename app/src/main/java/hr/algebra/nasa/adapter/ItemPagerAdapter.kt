package hr.algebra.nasa.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.nasa.R
import hr.algebra.nasa.model.Item
import hr.algebra.nasa.provider.NASA_PROVIDER_CONTENT_URI
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.item_pager.view.*
import java.io.File

class ItemPagerAdapter(private val items: MutableList<Item>, private val context: Context) :
    RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        private val ivRead: ImageView = itemView.findViewById(R.id.ivRead)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvExplanation: TextView = itemView.findViewById(R.id.tvExplanation)

        fun bind(item: Item) {
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.flat_about)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
            ivRead.setImageResource(if (item.read) R.drawable.green_flag else R.drawable.red_flag)
            tvDate.text = item.date
            tvTitle.text = item.title
            tvExplanation.text = item.explanation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context).inflate(R.layout.item_pager, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.ivRead.setOnClickListener {
            item.read = !item.read
            context.contentResolver.update(
                ContentUris.withAppendedId(NASA_PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(Item::read.name, item.read)
                },
                null,
                null
            )
            notifyItemChanged(position)
        }

        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}