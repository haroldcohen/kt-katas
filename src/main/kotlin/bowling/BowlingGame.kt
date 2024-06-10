package com.haroldcohen.bowling

class BowlingGame(
    private var rolls: IntArray,
    private var currentRollIndex: Int = 0,
) {
    fun roll(pinsKnockedDown: Int){
        rolls[currentRollIndex++] = pinsKnockedDown
    }

    fun toSnapshot(): BowlingGameSnapshot{
        return BowlingGameSnapshot(score = rolls.sum())
    }
}