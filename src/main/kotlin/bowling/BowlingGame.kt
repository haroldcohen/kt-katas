package com.haroldcohen.bowling

class BowlingGame(
    private var score: Int = 0,
    private var frames: Array<IntArray> = Array(10) {IntArray(2) {0}},
    private var lastFrame: IntArray = IntArray(3){0},
    private var currentRoll: Int = 0,
    private var currentFrameIndex: Int = 0,
) {

    private companion object {
        fun rollIsStrike(frame: IntArray): Boolean {
            return frame[0] == 10
        }
    }

    fun roll(pinsKnockedDown: Int) {
        when (currentRoll){
            0 -> firstRoll(pinsKnockedDown)
            1 -> secondRoll(pinsKnockedDown)
            2 -> thirdRoll(pinsKnockedDown)
        }
    }

    private fun firstRoll(pinsKnockedDown: Int) {
        currentFrame()[0] = pinsKnockedDown
        currentRoll++
        if (rollIsStrike(frame = frames[currentFrameIndex])) strike()
    }

    private fun secondRoll(pinsKnockedDown: Int) {
        currentFrame()[1] = pinsKnockedDown
        when(currentFrameIndex){
            9 -> currentRoll++
            else ->  {
                currentRoll = 0
                currentFrameIndex++
            }
        }
    }

    private fun thirdRoll(pinsKnockedDown: Int) {
        currentFrame()[2] = pinsKnockedDown
    }

    private fun currentFrame(): IntArray {
        if (currentFrameIndex != 9){
            return frames[currentFrameIndex]
        }
        return lastFrame
    }

    private fun strike() {
        when (currentFrameIndex){
            9 -> {}
            else -> {
                currentRoll = 0
                currentFrameIndex++
            }
        }
    }

    private fun computeScore() {
        for (frameNumber in frames.indices) {
            score += when(frameNumber) {
                9 -> computeLastFrameScore()
                else -> computeFrameScore(frameNumber = frameNumber)
            }
        }
    }

    private fun computeFrameScore(frameNumber: Int): Int {
        val nextFrameNumber: Int = frameNumber + 1
        val frameScore = frames[frameNumber].sum()
        if (rollIsStrike(frame = frames[frameNumber])) return frameScore + frames[nextFrameNumber].sum()
        if (frameScore == 10) return frameScore + frames[nextFrameNumber][0]
        return frameScore
    }

    private fun computeLastFrameScore(): Int {
        val twoFirstRollsScore = lastFrame.slice(0..1).sum()
        if (rollIsStrike(frame = lastFrame) or (twoFirstRollsScore == 10)) return lastFrame.sum()
        return twoFirstRollsScore
    }

    fun toSnapshot() : BowlingGameSnapshot {
        computeScore()
        return BowlingGameSnapshot(score = score)
    }
}