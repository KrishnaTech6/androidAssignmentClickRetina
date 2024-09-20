package com.example.androidassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.databinding.TitleItemBinding
import com.example.androidassignment.utility.Utils

class TitleAdapter(private val context: Context) : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {
    private var titles: List<String> = emptyList()


    class TitleViewHolder(private val binding: TitleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title: String, context: Context) {
            binding.text.text = title

            binding.btnCopy.setOnClickListener {
                Utils.copyToClipboard(title,context)
            }
            binding.btnShare.setOnClickListener {
                Utils.shareText(title,context)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding = TitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.bind(titles[position],context)
    }

    fun submitList(newTitles: List<String>) {
        titles = newTitles
        notifyDataSetChanged()
    }
}

