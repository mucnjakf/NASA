package hr.algebra.nasa.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.nasa.adapter.ItemAdapter
import hr.algebra.nasa.R
import hr.algebra.nasa.framework.fetchItems
import hr.algebra.nasa.model.Item
import kotlinx.android.synthetic.main.fragment_items.*


class ItemsFragment : Fragment() {

    private lateinit var items: MutableList<Item>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        items = requireContext().fetchItems()
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemAdapter = ItemAdapter(items, requireContext())
        rvItems.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = itemAdapter
        }
    }
}