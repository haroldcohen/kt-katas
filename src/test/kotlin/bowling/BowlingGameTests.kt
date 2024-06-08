package bowling

import io.kotest.core.spec.style.FunSpec

import com.haroldcohen.bowling.BowlingGame
import com.haroldcohen.bowling.BowlingGameSnapshot
import io.kotest.matchers.shouldBe

class BowlingGameTests : FunSpec ({

    var game = BowlingGame()

    beforeEach {
        game = BowlingGame()
    }

    test(name = "Knock down 0 pins should score 0") {
        for (frameNumber in 0..9){
            game.roll(pinsKnockedDown = 0)
            game.roll(pinsKnockedDown = 0)
        }
        val expectedGame = BowlingGameSnapshot(score = 0)
        game.toSnapshot() shouldBe expectedGame
    }

    test(name = "Knock down 2 pins should score 2") {
        game.roll(pinsKnockedDown = 1)
        game.roll(pinsKnockedDown = 1)
        for (frameNumber in 0..8){
            game.roll(pinsKnockedDown = 0)
            game.roll(pinsKnockedDown = 0)
        }
        val expectedGame = BowlingGameSnapshot(score = 2)
        game.toSnapshot() shouldBe expectedGame
    }

    test(name = "Knock down 2 pins twice should score 4") {
        for (frameNumber in 0..1){
            game.roll(pinsKnockedDown = 1)
            game.roll(pinsKnockedDown = 1)
        }
        for (frameNumber in 0..7){
            game.roll(pinsKnockedDown = 0)
            game.roll(pinsKnockedDown = 0)
        }
        val expectedGame = BowlingGameSnapshot(score = 4)
        game.toSnapshot() shouldBe expectedGame
    }

    mapOf(
        0 to 12,
    ).forEach { (spareFrame, expectedScore) ->
        test(name = "Roll a spare on the 1st frame and roll 1 in the next frame should score $expectedScore") {
            for (i in 0..9) {
                when (i){
                    spareFrame -> {
                        game.roll(5)
                        game.roll(5)
                    }
                    spareFrame + 1 -> {
                        game.roll(0)
                        game.roll(1)
                    }
                    else -> {
                        game.roll(pinsKnockedDown = 0)
                        game.roll(pinsKnockedDown = 0)
                    }
                }
            }
            val expectedGame = BowlingGameSnapshot(score = expectedScore)
            game.toSnapshot() shouldBe expectedGame
        }
    }

})