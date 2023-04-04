package com.darth.rockpaperscissorslizardspock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darth.rockpaperscissorslizardspock.db.Game
import com.darth.rockpaperscissorslizardspock.R
import com.darth.rockpaperscissorslizardspock.databinding.CustomRowBinding
import com.darth.rockpaperscissorslizardspock.db.GameEntity

class GameAdapter(var gameList: ArrayList<Game>) : RecyclerView.Adapter<GameAdapter.GameHolder>() {

    class GameHolder(val binding: CustomRowBinding) : RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val binding = CustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameHolder(binding)
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {

        holder.binding.rowId.text = gameList[position].id.toString()
        holder.binding.rowWinner.text = gameList[position].winner
        holder.binding.rowLoser.text = gameList[position].loser

        if (position % 2 == 0) {
            holder.itemView.setBackgroundResource(R.color.light_grey)
        } else {
            holder.itemView.setBackgroundResource(R.color.medium_grey)
        }
    }

    fun setGame(games: List<GameEntity>) {
        this.gameList = games.map { Game(it.id, it.winner, it.loser) } as ArrayList<Game>
        notifyDataSetChanged()
    }
}