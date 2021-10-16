package app.emojitictactow

import ItemsViewModel
import MyRecyclerViewAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class GameLogic constructor(numberOfPlayers: String,playerOneEmoji: String,playerTwoEmoji:String,context: Context) {

    var context = context
    var numberOfPlayers: String = numberOfPlayers
    var playerOneEmoji: String = playerOneEmoji
    var playerTwoEmoji: String = playerTwoEmoji
    var players = arrayOf("human", "computer")
    var selectPlayer = players.random()
    var numberOfMoves = 0
    var playersMoves = arrayOf("", "", "", "", "", "", "", "", "")
    var results = ""


    fun twoPlayers(
        position: Int,
        data: ArrayList<ItemsViewModel>,
        myRecyclerViewAdapter: MyRecyclerViewAdapter,
        rv: RecyclerView) {
        println(playersMoves)
        when (selectPlayer) {
            players[0] -> {
                if (playersMoves[position] == "") {
                    data[position] = ItemsViewModel(playerOneEmoji)

                    rv.adapter = myRecyclerViewAdapter

                    playersMoves[position] = "playerOne"
                    numberOfMoves += 1
                    checkWin("playerOne")
                    selectPlayer = players[1]
                }
            }
            players[1] -> {
                if (playersMoves[position] == "") {
                    data[position] = ItemsViewModel(playerTwoEmoji)

                    rv.adapter = myRecyclerViewAdapter

                    playersMoves[position] = "playerTwo"
                    numberOfMoves += 1
                    checkWin("playerTwo")
                    selectPlayer = players[0]
                }
            }
            else -> {
                println("error")
            }
        }

    }

    fun onePlayer(
        position: Int,
        data: ArrayList<ItemsViewModel>,
        myRecyclerViewAdapter: MyRecyclerViewAdapter,
        rv: RecyclerView) {
        println(playersMoves)
        if (playersMoves[position] == "" && selectPlayer == players[0]) {
            data[position] = ItemsViewModel(playerOneEmoji)

            rv.adapter = myRecyclerViewAdapter

            playersMoves[position] = "human"
            numberOfMoves += 1
            checkWin("human")
            selectPlayer = players[1]
            if (results == "") {
                nextMove(position, data, myRecyclerViewAdapter, rv)
            }
        }


    }

    fun nextMove(position: Int,
                 data: ArrayList<ItemsViewModel>,
                 myRecyclerViewAdapter: MyRecyclerViewAdapter,
                 rv: RecyclerView) {
        //place move in centre of the board
        if (numberOfMoves == 1 && playersMoves[4] == "") {
            placeComputerMove(data,myRecyclerViewAdapter,rv,4)
        }

        //check if computer can win
        playNextMove(position,data,myRecyclerViewAdapter,rv, players[1])

        //check to block human player
        playNextMove(position,data,myRecyclerViewAdapter,rv, players[0])

        //play a move if both win and block do not play a move
        if (selectPlayer == players[1]) {

            for (i in 0..playersMoves.count()) {
                if (playersMoves[i] == "") {
                    placeComputerMove(data,myRecyclerViewAdapter,rv,i)
                    break
                }
            }
        }

    }

    fun playNextMove(position: Int,
                     data: ArrayList<ItemsViewModel>,
                     myRecyclerViewAdapter: MyRecyclerViewAdapter,
                     rv: RecyclerView, playersTag: String) {
        //columns
        checkForNextMove(data,myRecyclerViewAdapter,rv, 0, 1, 2, playersTag)
        checkForNextMove(data,myRecyclerViewAdapter,rv, 3, 4,  5, playersTag)
        checkForNextMove(data,myRecyclerViewAdapter,rv, 6, 7, 8, playersTag)
        //row
        checkForNextMove(data,myRecyclerViewAdapter,rv, 0, 3, 6, playersTag)
        checkForNextMove(data,myRecyclerViewAdapter,rv, 1, 4, 7, playersTag)
        checkForNextMove(data,myRecyclerViewAdapter,rv, 2, 5, 8, playersTag)
        //diagonal
        checkForNextMove(data,myRecyclerViewAdapter,rv, 0, 4, 8, playersTag)
        checkForNextMove(data,myRecyclerViewAdapter,rv, 2, 4, 6, playersTag)
    }

    fun checkForNextMove(data: ArrayList<ItemsViewModel>,
    myRecyclerViewAdapter: MyRecyclerViewAdapter,
    rv: RecyclerView, playersMoveOne: Int,playersMoveTwo: Int, playersMoveThree: Int, playersTag: String) {
        if (playersMoves[playersMoveOne] == playersTag &&
                playersMoves[playersMoveTwo] == "" &&
                playersMoves[playersMoveThree] == playersTag) {
            placeComputerMove(data,myRecyclerViewAdapter,rv,playersMoveTwo)
        }
        else if (playersMoves[playersMoveOne] == "" &&
                playersMoves[playersMoveTwo] == playersTag &&
                playersMoves[playersMoveThree] == playersTag) {
            placeComputerMove(data,myRecyclerViewAdapter,rv, playersMoveOne)
        }
        else if (playersMoves[playersMoveOne] == playersTag &&
                playersMoves[playersMoveTwo] == playersTag &&
                playersMoves[playersMoveThree] == "") {
            placeComputerMove(data,myRecyclerViewAdapter,rv, playersMoveThree)
        }
    }


    fun placeComputerMove(
                          data: ArrayList<ItemsViewModel>,
                          myRecyclerViewAdapter: MyRecyclerViewAdapter,
                          rv: RecyclerView, item: Int) {
        //delayWithSeconds(0.5) {
            if (selectPlayer == players[1]) {

                data[item] = ItemsViewModel(playerTwoEmoji)

                rv.adapter = myRecyclerViewAdapter


                playersMoves[item] = selectPlayer
                numberOfMoves += 1
                selectPlayer = players[0]
                if (results == "") { checkWin(players[1]) }

            }
    //}

    }


    fun checkWin(playersTag: String) {
        //check for row win
        checkForThreeOfTheSame(0, 1, 2, playersTag)
        checkForThreeOfTheSame(3, 4,  5,  playersTag)
        checkForThreeOfTheSame(6, 7,  8,  playersTag)

        //check for column win
        checkForThreeOfTheSame( 0,  3,  6,  playersTag)
        checkForThreeOfTheSame( 1,  4,  7,  playersTag)
        checkForThreeOfTheSame( 2,  5,  8,  playersTag)

        //check for diagonal win
        checkForThreeOfTheSame(0, 4, 8, playersTag)
        checkForThreeOfTheSame(2, 4, 6, playersTag)

    }

    fun checkForThreeOfTheSame(placeOne: Int, placeTwo: Int, placeThree: Int, playersTag: String) {

        if (playersMoves[placeOne].equals(playersTag) &&
            playersMoves[placeTwo].equals(playersTag) &&
            playersMoves[placeThree].equals(playersTag)
        ) {

            if (numberOfPlayers.equals("1")) {
                if (playersTag == players[0]) {
                    results = "win"
                } else if (playersTag == players[1]) {
                    results = "lose"
                }
            } else if (numberOfPlayers == "2") {
                if (playersTag == "playerOne") {
                    results = "playerOne"
                } else if (playersTag == "playerTwo") {
                    results = "playerTwo"
                }
            }
            goToGameResults(results)
        }
            else if (numberOfMoves == 9 && results == "") {
                results = "tie"
                println(results)
            goToGameResults(results)
            }



    }

    fun goToGameResults(results: String) {
        var gameResults = Intent(context,GameResults::class.java)
        gameResults.putExtra("numberOfMoves", numberOfMoves.toString())
        gameResults.putExtra("results", results)
        gameResults.putExtra("emojiPlayer1",playerOneEmoji)
        gameResults.putExtra("emojiPlayer2",playerTwoEmoji)
        gameResults.putExtra("numberOfPlayers", numberOfPlayers)

        context.startActivity(gameResults)
    }


    fun selectPlayerOne(
                        data: ArrayList<ItemsViewModel>,
                        myRecyclerViewAdapter: MyRecyclerViewAdapter,
                        rv: RecyclerView, item: Int) {
        var alertMessage = ""

        if (numberOfPlayers == "2") {
            players = arrayOf("playerOne","playerTwo")
            selectPlayer = players.random()

            if (selectPlayer == "playerTwo") {
                alertMessage = "Player Two goes first"
            } else {alertMessage = "Player One go first"}
        }

        else if (numberOfPlayers == "1") {

            if (selectPlayer == "computer") {
                alertMessage = "Computer goes first"
            } else {alertMessage = "You go first"}
        }
        val alertdialog = AlertDialog.Builder(context)


        alertdialog.setTitle(alertMessage)
        alertdialog.setMessage("")
        alertdialog.setPositiveButton("ok") { dialog, which ->
            if (selectPlayer == "computer") run {
                placeComputerMove(data, myRecyclerViewAdapter, rv, 4)
            }
        }

        alertdialog.create()
        alertdialog.show()
    }

fun restartGame(data: ArrayList<ItemsViewModel>,
                myRecyclerViewAdapter: MyRecyclerViewAdapter,
                rv: RecyclerView, item: Int){
    numberOfMoves = 0
    playersMoves = arrayOf("", "", "", "", "", "", "", "", "")
    selectPlayerOne(data,myRecyclerViewAdapter,rv,item)
    results = ""
    data.clear()
    for (i in 1..9) {
        data.add(ItemsViewModel(""))
    }
    rv.adapter = myRecyclerViewAdapter
}



    }




