package gameoflife

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import com.haroldcohen.gameoflife.GameOfLife
import com.haroldcohen.gameoflife.GameOfLifeSnapshot

class GameOfLifeTests : FunSpec ({
    test(name = "A living cell with less than two neighbours dies"){
        val expectedGameOfLife = GameOfLifeSnapshot(grid = Array(3) {IntArray(3) {0}})
        val gameOfLife = GameOfLife()
        gameOfLife.play()

        gameOfLife.toSnapshot() shouldBe expectedGameOfLife
    }
})