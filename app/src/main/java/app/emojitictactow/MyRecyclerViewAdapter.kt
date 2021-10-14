import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.emojitictactow.R

data class ItemsViewModel(val text: String) {
}

class MyRecyclerViewAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_cell, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text

    }

    // return the number of the items in the list
    private val limit = 9


    override fun getItemCount(): Int {
        return if (mList.size > limit) {
            limit
        } else {
            mList.size
        }
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.rv_cell)
    }
}

