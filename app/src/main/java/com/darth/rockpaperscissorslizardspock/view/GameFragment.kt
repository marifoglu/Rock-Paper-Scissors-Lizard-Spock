package com.darth.rockpaperscissorslizardspock.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.darth.rockpaperscissorslizardspock.db.Game
import com.darth.rockpaperscissorslizardspock.adapter.GameAdapter
import com.darth.rockpaperscissorslizardspock.R
import com.darth.rockpaperscissorslizardspock.databinding.FragmentGameBinding
import com.darth.rockpaperscissorslizardspock.db.GameEntity
import com.darth.rockpaperscissorslizardspock.viewmodel.GameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var gameViewModel: GameViewModel

    private lateinit var gameList: ArrayList<Game>
    private lateinit var gameAdapter: GameAdapter

    private lateinit var playerChoiceImageView: ImageView
    private lateinit var computerChoiceImageView: ImageView

    private lateinit var playerChoiceText: TextView
    private lateinit var computerChoiceText: TextView

    private lateinit var rockButton: ImageView
    private lateinit var paperButton: ImageView
    private lateinit var scissorsButton: ImageView
    private lateinit var lizardButton: ImageView
    private lateinit var spockButton: ImageView

    private var playerChoice: String = ""
    private var computerChoice: String = ""
    private var sessionScorePlayer: Int = 0
    private var sessionScoreComputer: Int = 0
    private var gameCounter: Int = 0

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        playerChoiceImageView = binding.playerChoiceImageView
        computerChoiceImageView = binding.computerChoiceImageView

        playerChoiceText = binding.playerChoiceText
        computerChoiceText= binding.computerChoiceText

        rockButton = binding.rockIcon
        paperButton = binding.paperIcon
        scissorsButton = binding.scissorsIcon
        lizardButton = binding.lizardIcon
        spockButton = binding.spockIcon

        rockButton.setOnClickListener { playGame("rock") }
        paperButton.setOnClickListener { playGame("paper") }
        scissorsButton.setOnClickListener { playGame("scissors") }
        lizardButton.setOnClickListener { playGame("lizard") }
        spockButton.setOnClickListener { playGame("spock") }

        gameList = ArrayList<Game>()
        gameAdapter = GameAdapter(gameList)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
    }

    private fun playGame(player: String) {
        playerChoice = player
        setPlayerChoiceImage(player)

        computerChoice = generateComputerChoice()
        setComputerChoiceImage(computerChoice)

        scope.launch {

            when (determineGameResult(player, computerChoice)) {
                "player" -> playerWin()
                "computer" -> computerWin()
                else -> draw()
            }
        }

        gameCounter++

        endSession()
    }

    private fun generateComputerChoice(): String {
        val choices = arrayOf("rock", "paper", "scissors", "lizard", "spock")
        return choices.random()
    }

    private fun determineGameResult(playerChoice: String, computerChoice: String): String {
        if (playerChoice == computerChoice) {
            return "draw"
        }
        when (playerChoice) {
            "rock" -> return when (computerChoice) {
                "scissors", "lizard" -> "player"
                else -> "computer"
            }
            "paper" -> when (computerChoice) {
                "rock", "spock" -> return "player"
                else -> return "computer"
            }
            "scissors" -> when (computerChoice) {
                "paper", "lizard" -> return "player"
                else -> return "computer"
            }
            "lizard" -> when (computerChoice) {
                "paper", "spock" -> return "player"
                else -> return "computer"
            }
            "spock" -> when (computerChoice) {
                "rock", "scissors" -> return "player"
                else -> return "computer"
            }
            else -> return "invalid"
        }
    }

    private fun setPlayerChoiceImage(choice: String) {
        when (choice) {
            "rock" -> {
                playerChoiceImageView.setImageResource(R.drawable.rock)
                playerChoiceText.text = "Rock"
            }
            "paper" -> {
                playerChoiceImageView.setImageResource(R.drawable.paper)
                playerChoiceText.text = "Paper"
            }
            "scissors" -> {
                playerChoiceImageView.setImageResource(R.drawable.scissors)
                playerChoiceText.text = "Scissors"
            }
            "lizard" -> {
                playerChoiceImageView.setImageResource(R.drawable.lizard)
                playerChoiceText.text = "Lizard"
            }
            "spock" -> {
                playerChoiceImageView.setImageResource(R.drawable.spock)
                playerChoiceText.text = "Spock"
            }
        }
    }

    private fun setComputerChoiceImage(choice: String) {
        when (choice) {
            "rock" -> {
                computerChoiceImageView.setImageResource(R.drawable.rock)
                computerChoiceText.text = "Rock"
            }
            "paper" -> {
                computerChoiceImageView.setImageResource(R.drawable.paper)
                computerChoiceText.text = "Paper"
            }
            "scissors" -> {
                computerChoiceImageView.setImageResource(R.drawable.scissors)
                computerChoiceText.text = "Scissors"
            }
            "lizard" -> {
                computerChoiceImageView.setImageResource(R.drawable.lizard)
                computerChoiceText.text = "Lizard"
            }
            "spock" -> {
                computerChoiceImageView.setImageResource(R.drawable.spock)
                computerChoiceText.text = "Spock"
            }
        }
    }

    private fun playerWin() {
        sessionScorePlayer++
    }

    private fun computerWin() {
        sessionScoreComputer++
    }

    private fun draw() {
    }

    private fun endSession() {

        val playerName = "Player"
        val computerName = "Computer"

        if (sessionScorePlayer > sessionScoreComputer) {
            // Room Insert
            insertGame(playerName, computerName)

        } else if (sessionScoreComputer > sessionScorePlayer) {
            // Room Insert
            insertGame(computerName, playerName)
        } else {
            // Room Insert
            insertGame("Draw", "Draw")
        }

        // Reset the session scores and game counter
        sessionScorePlayer = 0
        sessionScoreComputer = 0
        gameCounter = 0
    }

    private fun insertGame(winnerName: String, loserName: String){

        try {
            val game = GameEntity(0, winnerName, loserName)
            gameViewModel.addGame(game)
        } catch (e: Exception) {
            Log.e("TAG", "Error inserting game result: ${e.localizedMessage}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}