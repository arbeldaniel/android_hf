package hu.bme.aut.p5c1lf_android_hf.fragments

import android.app.Dialog
import android.content.Context
import android.opengl.ETC1.isValid
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.p5c1lf_android_hf.R
import hu.bme.aut.p5c1lf_android_hf.data.NoteItem
import hu.bme.aut.p5c1lf_android_hf.databinding.DialogNewNoteItemBinding
import java.text.SimpleDateFormat
import java.util.*

class NewNoteItemDialogFragment : DialogFragment() {
    interface NewNoteItemDialogListener {
        fun onNoteItemCreated(newItem: NoteItem)
    }

    private lateinit var listener: NewNoteItemDialogListener

    lateinit var binding: DialogNewNoteItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewNoteItemDialogListener
            ?: throw RuntimeException("Activity must implement the NewNoteItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewNoteItemBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_note_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onNoteItemCreated(getNoteItem())
                }
            }

            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    companion object {
        const val TAG = "NewNoteItemDialogFragment"
    }

    private fun isValid() = binding.etTitle.text.isNotEmpty()

    private fun getNoteItem() = NoteItem(
        title = binding.etTitle.text.toString(),
        note = binding.etNote.text.toString(),
        date = SimpleDateFormat("dd/M/yyyy hh:mm", Locale.GERMAN).format(Date())
    )

}
