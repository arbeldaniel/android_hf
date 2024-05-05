package hu.bme.aut.p5c1lf_android_hf

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.p5c1lf_android_hf.adapter.NoteAdapter
import hu.bme.aut.p5c1lf_android_hf.data.NoteDatabase
import hu.bme.aut.p5c1lf_android_hf.data.NoteItem
import hu.bme.aut.p5c1lf_android_hf.databinding.ActivityMainBinding
import hu.bme.aut.p5c1lf_android_hf.fragments.EditNoteItemDialogFragment
import hu.bme.aut.p5c1lf_android_hf.fragments.NewNoteItemDialogFragment
import hu.bme.aut.p5c1lf_android_hf.fragments.ViewNoteItemDialogFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), NoteAdapter.NoteItemClickListener, NewNoteItemDialogFragment.NewNoteItemDialogListener,
        ViewNoteItemDialogFragment.ViewNoteItemDialogListener, EditNoteItemDialogFragment.EditNoteItemDialogListener{
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: NoteDatabase
    private lateinit var adapter: NoteAdapter

    lateinit var noteItem: NoteItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = NoteDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            NewNoteItemDialogFragment().show(
                supportFragmentManager,
                NewNoteItemDialogFragment.TAG
            )
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = NoteAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.NoteItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: NoteItem) {
        thread {
            database.NoteItemDao().deleteItem(item)
            runOnUiThread {
                adapter.removeItem(item)
            }
            loadItemsInBackground()
        }
    }

    override fun onNoteItemCreated(newItem: NoteItem) {
        thread {
            val insertId = database.NoteItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    override fun getNoteDetails() :NoteItem {
        return noteItem
    }

    override fun onItemEdited(item: NoteItem) {
        noteItem = item
        onItemChanged(item)
        EditNoteItemDialogFragment().show(
            supportFragmentManager,
            ViewNoteItemDialogFragment.TAG
        )
    }

    override fun onItemClicked(item: NoteItem) {
        noteItem = item
        ViewNoteItemDialogFragment().show(
            supportFragmentManager,
            ViewNoteItemDialogFragment.TAG
        )
    }
}