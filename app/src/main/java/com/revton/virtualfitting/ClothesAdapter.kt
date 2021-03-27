package com.revton.virtualfitting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.revton.virtualfitting.model.ClothesModel


internal class ClothesAdapter(private var clothesList: List<ClothesModel>,
                              val context: Context,
                              private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<ClothesAdapter.MyViewHolder>()
{

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var fileName: ImageView = view.findViewById(R.id.file_name)
    }



    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.clothes_list,
            parent,
            false)

        return MyViewHolder(itemView)
    }



    interface CellClickListener{
        fun onCellClickListener(cloth:ClothesModel)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {

        val cloth  = clothesList[position]
        holder.fileName.setImageResource(context.resources.getIdentifier("@drawable/"+cloth.getId(),null,context.packageName))

        holder.fileName.setOnClickListener{
            cellClickListener.onCellClickListener(cloth)
        }

    }



    override fun getItemCount(): Int
    {
        return clothesList.size
    }


}