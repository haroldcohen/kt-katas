package bowling

import io.kotest.core.spec.style.FunSpec

import com.haroldcohen.bowling.BowlingGame
import com.haroldcohen.bowling.BowlingGameSnapshot
import io.kotest.matchers.shouldBe

class BowlingGameTests : FunSpec({

    var game = BowlingGame()

    beforeEach(){
        game = BowlingGame()
    }

    fun rollSpare(){
        game.roll(pinsKnockedDown = 5)
        game.roll(pinsKnockedDown = 5)
    }

    fun rollN(n: Int, pinsKnockedDown: Int){
        for (i in 0 until n){
            game.roll(pinsKnockedDown = pinsKnockedDown)
        }
    }

    test(name = "Knock 0 pins should score 0"){
        val expectedGame = BowlingGameSnapshot()
        rollN(n = 20, pinsKnockedDown = 0)
        game.toSnapshot() shouldBe expectedGame
    }

    test(name = "Knock 20 pins should score 20"){
        val expectedGame = BowlingGameSnapshot(score = 20)
        rollN(n = 20, pinsKnockedDown = 1)
        game.toSnapshot() shouldBe expectedGame
    }

    listOf(
        5,
        6,
    ).forEach { rollFollowingSpare ->
        val expectedScore = 10 + rollFollowingSpare * 2
        test(name = "Roll a spare should score $expectedScore") {
            val expectedGame = BowlingGameSnapshot(score = expectedScore)
            rollSpare()
            rollN(n = 1, pinsKnockedDown = rollFollowingSpare)
            rollN(n = 17, pinsKnockedDown = 0)
            game.toSnapshot() shouldBe expectedGame
        }
    }

    listOf(
        5,
    ).forEach { rollFollowingSpare ->
        val expectedScore = 10 + rollFollowingSpare*2 + 17
        test(name = "Roll a spare should score $expectedScore") {
            val expectedGame = BowlingGameSnapshot(score = expectedScore)
            rollN(n = 17, pinsKnockedDown = 1)
            rollSpare()
            rollN(n = 1, pinsKnockedDown = rollFollowingSpare)
            game.toSnapshot() shouldBe expectedGame
        }
    }

})