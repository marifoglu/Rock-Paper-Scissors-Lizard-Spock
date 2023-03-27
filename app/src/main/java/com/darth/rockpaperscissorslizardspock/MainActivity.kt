package com.darth.rockpaperscissorslizardspock

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.darth.rockpaperscissorslizardspock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var playerChoiceImageView: ImageView
    private lateinit var computerChoiceImageView: ImageView

    private lateinit var resultTextView: TextView
    private lateinit var sessionResultTextView: TextView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerChoiceImageView = binding.playerChoiceImageView
        computerChoiceImageView = binding.computerChoiceImageView

        playerChoiceText = binding.playerChoiceText
        computerChoiceText= binding.computerChoiceText

        rockButton = binding.rockIcon
        paperButton = binding.paperIcon
        scissorsButton = binding.scissorsIcon
        lizardButton = binding.lizardIcon
        spockButton = binding.spockIcon
        resultTextView = binding.resultTextView
        sessionResultTextView = binding.sessionResultTextView

        rockButton.setOnClickListener { playGame("rock") }
        paperButton.setOnClickListener { playGame("paper") }
        scissorsButton.setOnClickListener { playGame("scissors") }
        lizardButton.setOnClickListener { playGame("lizard") }
        spockButton.setOnClickListener { playGame("spock") }
    }

    private fun playGame(player: String) {
        playerChoice = player
        setPlayerChoiceImage(player)

        computerChoice = generateComputerChoice()
        setComputerChoiceImage(computerChoice)

        val gameResult = determineGameResult(player, computerChoice)

        when (gameResult) {
            "player" -> playerWin()
            "computer" -> computerWin()
            else -> draw()
        }

        gameCounter++

        if (gameCounter == 3) {
            endSession()
        }
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
            "rock" -> when (computerChoice) {
                "scissors", "lizard" -> return "player"
                else -> return "computer"
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
        resultTextView.text = "You Win!"
        sessionScorePlayer++
    }

    private fun computerWin() {
        resultTextView.text = "Computer Wins!"
        sessionScoreComputer++
    }

    private fun draw() {
        resultTextView.text = "Draw!"
    }

    private fun endSession() {
        if (sessionScorePlayer > sessionScoreComputer) {
            sessionResultTextView.text = "You Win!"
        } else if (sessionScoreComputer > sessionScorePlayer) {
            sessionResultTextView.text = "Computer Wins!"
        } else {
            sessionResultTextView.text = "Draw!"
        }

        // Reset the session scores and game counter
        sessionScorePlayer = 0
        sessionScoreComputer = 0
        gameCounter = 0
    }
}