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

        val gameResult = determineGameResult(player, computerChoice)

        when (gameResult) {
            "player" -> playerWin()
            "computer" -> computerWin()
            else -> draw()
        }

        gameCounter++

        //if (gameCounter == 1) {
        endSession()
        //}
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
        // resultTextView.text = "You Win!"
        sessionScorePlayer++
    }

    private fun computerWin() {
        // resultTextView.text = "Computer Wins!"
        sessionScoreComputer++
    }

    private fun draw() {
        // resultTextView.text = "Draw!"
    }

    private fun endSession() {

        val playerName = "Player"
        val computerName = "Computer"

//        val database = this.openOrCreateDatabase("Game", AppCompatActivity.MODE_PRIVATE, null)
//        database.execSQL("CREATE TABLE IF NOT EXISTS game (id INTEGER PRIMARY KEY, winner VARCHAR, loser VARCHAR, date VARCHAR)")


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
/*
    private fun gameResult(winnerName: String, loserName: String, database: SQLiteDatabase): Long {
        val currentDateTime = Calendar.getInstance().time
        val sqlDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val sqlString = "INSERT INTO game (winner, loser, date) VALUES (?, ?, ?)"
        val sqlStringStatement = database.compileStatement(sqlString)
        sqlStringStatement.bindString(1, winnerName)
        sqlStringStatement.bindString(2, loserName)
        sqlStringStatement.bindString(3, sqlDateFormat.format(currentDateTime))

        return try {
            sqlStringStatement.executeInsert()
        } catch (e: Exception) {
            Log.e("TAG", "Error inserting game result: ${e.localizedMessage}")
            -1L
        }
    }

 */

    /*
    private fun getGameResult(){
        try {
            val database = this.openOrCreateDatabase("Game", MODE_PRIVATE, null)
            database.execSQL("CREATE TABLE IF NOT EXISTS game (id INTEGER PRIMARY KEY, winner VARCHAR, loser VARCHAR, date VARCHAR)")

            val cursor = database.rawQuery("SELECT * FROM game ORDER BY id DESC", null)
            val getID = cursor.getColumnIndex("id")
            val getWinner = cursor.getColumnIndex("winner")
            val getLoser = cursor.getColumnIndex("loser")

            while (cursor.moveToNext()){
                val gameId = cursor.getInt(getID)
                val gameWinner = cursor.getString(getWinner)
                val gameLoser = cursor.getString(getLoser)

                val game = Game(gameId, gameWinner, gameLoser)
                gameList.add(game)
            }

            gameAdapter.notifyDataSetChanged()
            cursor.close()

        }catch (e: java.lang.Exception){
            Log.e("TAG", "Error inserting game result: ${e.localizedMessage}")
        }
    }

     */



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