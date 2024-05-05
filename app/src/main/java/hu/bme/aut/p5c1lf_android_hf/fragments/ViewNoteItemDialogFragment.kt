package hu.bme.aut.p5c1lf_android_hf.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.p5c1lf_android_hf.R
import hu.bme.aut.p5c1lf_android_hf.data.NoteItem
import hu.bme.aut.p5c1lf_android_hf.data.NoteItemDao
import hu.bme.aut.p5c1lf_android_hf.databinding.DialogNewNoteItemBinding
import hu.bme.aut.p5c1lf_android_hf.databinding.DialogViewNoteItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ViewNoteItemDialogFragment : DialogFragment() {
    interface ViewNoteItemDialogListener {
        fun getNoteDetails() :NoteItem
    }

    private lateinit var listener: ViewNoteItemDialogListener

    lateinit var binding: DialogViewNoteItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? ViewNoteItemDialogListener
            ?: throw RuntimeException("Activity must implement the ViewNoteItemDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogViewNoteItemBinding.inflate(LayoutInflater.from(context))
        binding.tvNote.setText(listener.getNoteDetails().note)
        return AlertDialog.Builder(requireContext())
            .setTitle(listener.getNoteDetails().title)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i -> }
            .create()
    }

    companion object {
        const val TAG = "ViewNoteItemDialogFragment"
    }
}