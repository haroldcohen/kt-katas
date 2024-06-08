package com.haroldcohen.bowling

class BowlingGame(
    private var score: Int = 0,
    private var frames: Array<IntArray> = Array(10) {IntArray(2) {0}},
    private var currentRoll: Int = 0,
    private var currentFrame: Int = 0,
) {

    fun roll(pinsKnockedDown: Int) {
        frames[currentFrame][currentRoll] = pinsKnockedDown
        when (currentRoll) {
            0 -> currentRoll = 1
            1 -> {
                currentRoll = 0
                currentFrame++
            }
        }
    }

    private fun computeScore() {
        for (frameNumber in frames.indices) {
            computeFrameScore(frameNumber = frameNumber)
        }
    }

    private fun computeFrameScore(frameNumber: Int) {
        val nextFrameNumber: Int = frameNumber + 1
        score += frames[frameNumber][0]
        score += frames[frameNumber][1]
        if (score == 10) score += (frames[nextFrameNumber][0] + frames[nextFrameNumber][1])
    }

    fun toSnapshot() : BowlingGameSnapshot {
        computeScore()
        return BowlingGameSnapshot(score = score)
    }
}