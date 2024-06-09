package com.haroldcohen.gameoflife

class GameOfLife {
    fun play(){

    }

    fun toSnapshot(): GameOfLifeSnapshot {
        return GameOfLifeSnapshot(grid = Array(3) {IntArray(3) {0}})
    }
}