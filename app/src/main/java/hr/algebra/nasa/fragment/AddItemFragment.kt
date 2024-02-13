package hr.algebra.nasa.fragment

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.algebra.nasa.R
import hr.algebra.nasa.framework.getCurrentDateTime
import hr.algebra.nasa.framework.toString
import hr.algebra.nasa.model.Item
import hr.algebra.nasa.provider.NASA_PROVIDER_CONTENT_URI
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.fragment_add_item.view.*
import java.io.File

class AddItemFragment : Fragment() {

    private val PICK_IMAGE_CODE = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_add_item, container, false)
        setButtonListeners(view)
        return view
    }

    private fun setButtonListeners(view: View) {
        view.btnInsertImage.setOnClickListener { addImage() }
        view.btnAddItem.setOnClickListener { addItem() }
    }

    private fun addImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                tvImagePath.text = data!!.dataString!!.replace("%2F", "/", false)
            }
        }
    }

    private fun addItem() {
        val title: String = etTitle.text.toString()
        val explanation: String = etExplanation.text.toString()

        val imageName: String = tvImagePath.text.toString().substringAfterLast("/")
        val imagePathDl = "/storage/emulated/0/Download/$imageName"
        // val imagePath = "/storage/emulated/0/Android/data/hr.algebra.nasa/files/$imageName"

        // File(imagePathDl).copyTo(File(imagePath), false, DEFAULT_BUFFER_SIZE)

        val newItem = ContentValues().apply {
            put(Item::title.name, title)
            put(Item::explanation.name, explanation)
            put(Item::picturePath.name, imagePathDl) // todo: insert image
            put(Item::date.name, getCurrentDateTime().toString("yyyy-MM-dd"))
            put(Item::read.name, false)
        }

        context?.contentResolver?.insert(NASA_PROVIDER_CONTENT_URI, newItem)
    }
}