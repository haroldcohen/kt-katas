package bowling

import io.kotest.core.spec.style.FunSpec

import com.haroldcohen.bowling.BowlingGame
import com.haroldcohen.bowling.BowlingGameSnapshot
import io.kotest.matchers.shouldBe

class BowlingGameTests : FunSpec({

    var game = BowlingGame()

    beforeEach {
        game = BowlingGame()
    }

    test(name = "Knock down 0 pins should score 0") {
        for (frameNumber in 0..9) {
            game.roll(pinsKnockedDown = 0)
            game.roll(pinsKnockedDown = 0)
        }
        val expectedGame = BowlingGameSnapshot(score = 0)
        game.toSnapshot() shouldBe expectedGame
    }

    test(name = "Knock down 2 pins should score 2") {
        game.roll(pinsKnockedDown = 1)
        game.roll(pinsKnockedDown = 1)
        for (frameNumber in 0..8) {
            game.roll(pinsKnockedDown = 0)
            game.roll(pinsKnockedDown = 0)
        }
        val expectedGame = BowlingGameSnapshot(score = 2)
        game.toSnapshot() shouldBe expectedGame
    }

    test(name = "Knock down 2 pins twice should score 4") {
        for (frameNumber in 0..1) {
            game.roll(pinsKnockedDown = 1)
            game.roll(pinsKnockedDown = 1)
        }
        for (frameNumber in 0..7) {
            game.roll(pinsKnockedDown = 0)
            game.roll(pinsKnockedDown = 0)
        }
        val expectedGame = BowlingGameSnapshot(score = 4)
        game.toSnapshot() shouldBe expectedGame
    }

    test(name = "Knock down 2 pins in the last frame should score 2") {
        for (frameNumber in 0..9) {
            when(frameNumber) {
                9 -> {
                    game.roll(pinsKnockedDown = 1)
                    game.roll(pinsKnockedDown = 1)
                }

                else -> {
                    game.roll(pinsKnockedDown = 0)
                    game.roll(pinsKnockedDown = 0)
                }
            }
        }
        val expectedGame = BowlingGameSnapshot(score = 2)
        game.toSnapshot() shouldBe expectedGame
    }

    mapOf(
        0 to 1,
        1 to 2,
        2 to 3,
    ).forEach { (spareFrame, rollFollowingSpare) ->
        val expectedScore = 10 + rollFollowingSpare * 2
        test(
            name = "Roll a spare in frame #${spareFrame + 1} " +
                    "and then knock down $rollFollowingSpare pins should score $expectedScore"
        ) {
            for (i in 0..9) {
                when (i) {
                    spareFrame -> {
                        game.roll(pinsKnockedDown = 5)
                        game.roll(pinsKnockedDown = 5)
                    }

                    spareFrame + 1 -> {
                        game.roll(pinsKnockedDown = rollFollowingSpare)
                        game.roll(pinsKnockedDown = 0)
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

    mapOf(
        0 to intArrayOf(1, 1),
        1 to intArrayOf(1, 2),
        2 to intArrayOf(3, 2),
    ).forEach { (strikeFrame, rollsFollowingStrike) ->
        val expectedScore = 10 + rollsFollowingStrike.sum() * 2
        val roll1 = rollsFollowingStrike[0]
        val roll2 = rollsFollowingStrike[1]
        test(
            name = "Roll a strike in frame #${strikeFrame + 1}, " +
                    "knock down ${rollsFollowingStrike[0]} pins" +
                    "and then ${rollsFollowingStrike[1]} pins should score $expectedScore",
        ) {
            for (i in 0..9) {
                when (i) {
                    strikeFrame -> {
                        game.roll(pinsKnockedDown = 10)
                    }

                    strikeFrame + 1 -> {
                        game.roll(pinsKnockedDown = roll1)
                        game.roll(pinsKnockedDown = roll2)
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

    test(name = "Knock down 10 pins on the 2nd roll of frame #1 then knock down 1 pin should score 11") {
        for (i in 0..9) {
            when (i) {
                0 -> {
                    game.roll(pinsKnockedDown = 0)
                    game.roll(pinsKnockedDown = 10)
                }

                1 -> {
                    game.roll(pinsKnockedDown = 1)
                    game.roll(pinsKnockedDown = 0)
                }

                else -> {
                    game.roll(pinsKnockedDown = 0)
                    game.roll(pinsKnockedDown = 0)
                }
            }
        }
        val expectedGame = BowlingGameSnapshot(score = 12)
        game.toSnapshot() shouldBe expectedGame
    }


    listOf(
        intArrayOf(1, 1),
        intArrayOf(10, 10),
    ).forEach{
        val expectedScore = 10 + it.sum()
        test(name = "Roll a strike in the tenth frame should award the player with 2 extra balls" +
                "and score $expectedScore") {
            for (i in 0..9) {
                when (i) {
                    9 -> {
                        game.roll(pinsKnockedDown = 10)
                        game.roll(pinsKnockedDown = it[0])
                        game.roll(pinsKnockedDown = it[1])
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

    listOf(
        5,
        7,
    ).forEach{
        val expectedScore = 10 + it
        test(name = "Roll a spare in the tenth frame should award the player with 1 extra ball" +
                "and score $expectedScore") {
            for (i in 0..9) {
                when (i) {
                    9 -> {
                        game.roll(pinsKnockedDown = 5)
                        game.roll(pinsKnockedDown = 5)
                        game.roll(pinsKnockedDown = it)
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

    test(name = "Roll an open frame in the tenth frame should not award the player with one extra ball"){
        for (i in 0..9) {
            when (i) {
                9 -> {
                    game.roll(pinsKnockedDown = 5)
                    game.roll(pinsKnockedDown = 2)
                    game.roll(pinsKnockedDown = 2)
                }

                else -> {
                    game.roll(pinsKnockedDown = 0)
                    game.roll(pinsKnockedDown = 0)
                }
            }
        }
        val expectedGame = BowlingGameSnapshot(score = 7)
        game.toSnapshot() shouldBe expectedGame
    }

})