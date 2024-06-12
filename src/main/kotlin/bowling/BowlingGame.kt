package com.haroldcohen.bowling

class BowlingGame(
    private var rolls: IntArray = IntArray(20) { 0 },
    private var currentRollIndex: Int = 0,
) {
    fun roll(pinsKnockedDown: Int){
        rolls[currentRollIndex] = pinsKnockedDown
        when(currentRollIndex){
            0, 1 -> currentRollIndex++
            else -> {
                if(previousRollIsSpare()) rolls[currentRollIndex - 2] += pinsKnockedDown
                currentRollIndex++
            }
        }
    }

    private fun previousRollIsSpare(): Boolean{
        return rolls[currentRollIndex - 2] + rolls[currentRollIndex - 1] == 10
    }

    fun toSnapshot(): BowlingGameSnapshot {
        return BowlingGameSnapshot(score = rolls.sum())
    }
}