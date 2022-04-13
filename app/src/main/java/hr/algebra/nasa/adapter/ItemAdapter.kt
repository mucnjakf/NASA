package hr.algebra.nasa.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.nasa.ITEM_POSITION
import hr.algebra.nasa.ItemPagerActivity
import hr.algebra.nasa.R
import hr.algebra.nasa.framework.startActivity
import hr.algebra.nasa.model.Item
import hr.algebra.nasa.provider.NASA_PROVIDER_CONTENT_URI
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ItemAdapter(private val items: MutableList<Item>, private val context: Context) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem: ImageView = itemView.findViewById(R.id.ivItem)
        private val tvItem: TextView = itemView.findViewById(R.id.tvItem)

        fun bind(item: Item) {
            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.nasa)
                .transform(RoundedCornersTransformation(50, 5))
                .into(ivItem)
            tvItem.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item, parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            context.startActivity<ItemPagerActivity>(ITEM_POSITION, position)
        }

        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(context).apply {
                setTitle(R.string.delete)
                setMessage(context.getString(R.string.delete_confirmation) + " ${items[position].title}")
                setCancelable(true)
                setIcon(R.drawable.delete)
                setPositiveButton(R.string.positive_button_ok) { _, _ -> deleteItem(position) }
                setNegativeButton(context.getString(R.string.negative_button_cancel), null)
                show()
            }
            true
        }

        holder.bind(items[position])
    }

    private fun deleteItem(position: Int) {
        val item = items[position]

        context.contentResolver.delete(
            ContentUris.withAppendedId(
                NASA_PROVIDER_CONTENT_URI,
                item._id!!
            ), null, null
        )

        File(item.picturePath).delete()
        items.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}