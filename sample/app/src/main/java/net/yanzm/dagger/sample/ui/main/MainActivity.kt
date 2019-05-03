package net.yanzm.dagger.sample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import net.yanzm.dagger.sample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = AndroidVersionAdapter()

        adapter.listener = {
            Toast.makeText(this, it.value, Toast.LENGTH_LONG).show()
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        recyclerView.adapter = adapter

        adapter.submitList(
            listOf(
                AndroidVersionCode("1.5"),
                AndroidVersionCode("1.6"),
                AndroidVersionCode("2.0"),
                AndroidVersionCode("2.1"),
                AndroidVersionCode("2.2"),
                AndroidVersionCode("2.3"),
                AndroidVersionCode("3.0"),
                AndroidVersionCode("4.0"),
                AndroidVersionCode("4.3"),
                AndroidVersionCode("4.4"),
                AndroidVersionCode("5.0"),
                AndroidVersionCode("6.0"),
                AndroidVersionCode("7.0"),
                AndroidVersionCode("8.0"),
                AndroidVersionCode("9.0")
            )
        )
    }
}

class AndroidVersionAdapter : ListAdapter<AndroidVersionCode, ViewHolder>(itemCallback) {

    var listener: ((AndroidVersionCode) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(LayoutInflater.from(parent.context), parent).apply {
            itemView.setOnClickListener { listener?.invoke(getItem(adapterPosition)) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val itemCallback = object : DiffUtil.ItemCallback<AndroidVersionCode>() {
            override fun areItemsTheSame(
                oldItem: AndroidVersionCode,
                newItem: AndroidVersionCode
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: AndroidVersionCode,
                newItem: AndroidVersionCode
            ): Boolean {
                return oldItem.value == newItem.value
            }
        }
    }
}

class ViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(androidVersionCode: AndroidVersionCode) {
        (itemView as TextView).text = androidVersionCode.value
    }

    companion object {
        private const val LAYOUT_ID = R.layout.list_item

        fun create(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
            return ViewHolder(inflater.inflate(LAYOUT_ID, parent, false))
        }
    }
}

data class AndroidVersionCode(val value: String)
