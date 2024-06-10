package bowling

import io.kotest.core.spec.style.FunSpec

import com.haroldcohen.bowling.BowlingGame
import com.haroldcohen.bowling.BowlingGameSnapshot
import io.kotest.matchers.shouldBe

class BowlingGameTests : FunSpec({
    test(name = "Knock 0 pins should score 0"){
        val game = BowlingGame(rolls = IntArray(20))
        val expectedGame = BowlingGameSnapshot()
        for (i in 0..9){
            game.roll(pinsKnockedDown = 0)
        }
        game.toSnapshot() shouldBe expectedGame
    }

    test(name = "Knock 2 pins should score 2"){
        val game = BowlingGame(rolls = IntArray(20))
        val expectedGame = BowlingGameSnapshot(score = 2)
        for (i in 0..9){
            when (i){
                0 -> game.roll(pinsKnockedDown = 1)
                1 -> game.roll(pinsKnockedDown = 1)
                else -> game.roll(pinsKnockedDown = 0)
            }
        }
        game.toSnapshot() shouldBe expectedGame
    }
})