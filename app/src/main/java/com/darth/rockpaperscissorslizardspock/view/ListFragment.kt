package com.darth.rockpaperscissorslizardspock.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ListAdapter
import com.darth.rockpaperscissorslizardspock.adapter.GameAdapter
import com.darth.rockpaperscissorslizardspock.databinding.FragmentListBinding
import com.darth.rockpaperscissorslizardspock.viewmodel.GameViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private  val binding get() = _binding!!

    private lateinit var gameViewModel: GameViewModel
    private val gameAdapter = GameAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root


        // Set up RecyclerView
        val recyclerView = binding.recyclerGameView
        recyclerView.adapter = gameAdapter

        // Set up ViewModel
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        gameViewModel.getGame.observe(viewLifecycleOwner, Observer {   games ->
            gameAdapter.setGame(games)
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}