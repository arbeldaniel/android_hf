package hu.bme.aut.p5c1lf_android_hf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.p5c1lf_android_hf.data.NoteItem
import hu.bme.aut.p5c1lf_android_hf.databinding.ItemNoteListBinding

class NoteAdapter(private val listener: NoteItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val items = mutableListOf<NoteItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        ItemNoteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteItem = items[position]

        holder.binding.tvTitle.text = noteItem.title
        holder.binding.tvDate.text = noteItem.date
        holder.binding.ibRemove.setOnClickListener(View.OnClickListener {
            listener.onItemChanged(noteItem)
        })
        holder.binding.ibEdit.setOnClickListener(View.OnClickListener {
            listener.onItemEdited(noteItem)
        })
        holder.binding.llNote.setOnClickListener(View.OnClickListener {
            listener.onItemClicked(noteItem)
        })
    }

    override fun getItemCount(): Int = items.size

    interface NoteItemClickListener {
        fun onItemChanged(item: NoteItem)
        fun onItemEdited(item: NoteItem)
        fun onItemClicked(item: NoteItem)
    }

    inner class NoteViewHolder(val binding: ItemNoteListBinding) : RecyclerView.ViewHolder(binding.root)

    fun addItem(item: NoteItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(noteItems: List<NoteItem>) {
        items.clear()
        items.addAll(noteItems)
        notifyDataSetChanged()
    }

    fun removeItem(item: NoteItem) {
        items.remove(item)
    }

    fun editItem(old: NoteItem, item: NoteItem) {
        items[items.indexOf(old)] = item;
    }
}
