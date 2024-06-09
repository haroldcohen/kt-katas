package com.haroldcohen.gameoflife

data class GameOfLifeSnapshot(val grid: Array<IntArray>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameOfLifeSnapshot

        return grid.contentDeepEquals(other.grid)
    }

    override fun hashCode(): Int {
        return grid.contentDeepHashCode()
    }
}