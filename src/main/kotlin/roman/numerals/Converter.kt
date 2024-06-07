package com.haroldcohen.roman.numerals

class NumberConverter(private var number: Int) {

    private companion object {
        fun toSymbol(numeration: Int): String {
            return when (numeration) {
                1 -> "I"
                10 -> "X"
                100 -> "C"
                1000 -> "M"
                else -> throw RuntimeException("Unrecognized numeration")
            }
        }

        fun toHalfSymbol(numeration: Int): String {
            return when (numeration) {
                1 -> "V"
                10 -> "L"
                100 -> "D"
                else -> throw RuntimeException("Unrecognized half symbol")
            }
        }

        fun nextNumeration(numeration: Int): Int {
            return when (numeration) {
                1 -> 10
                10 -> 100
                100 -> 1000
                1000 -> 10000
                else -> throw RuntimeException("Unrecognized next numeration")
            }
        }

        fun half(numeration: Int): Int {
            return when (numeration) {
                1 -> 5
                10 -> 50
                100 -> 500
                1000 -> 5000
                else -> throw RuntimeException("Unrecognized half numeration")
            }
        }
    }

    fun convert(): String {
        return this
            .convertThousands()
            .plus(this.convertNumeration(numeration = 100))
            .plus(this.convertNumeration(numeration = 10))
            .plus(this.convertNumeration(numeration = 1))
    }

    private fun convertThousands(): String {
        val num: Int = number
        number %= 1000
        return this.toRoman(num = num, numeration = 1000)
    }

    private fun convertNumeration(numeration: Int): String {
        val num: Int = number
        number %= numeration
        return when (num) {
            in half(numeration = numeration)..<nextNumeration(numeration = numeration) -> this.convertUpperHalf(
                num = num,
                numeration = numeration
            )

            4 * numeration + num % (4 * numeration) -> this.convertPreHalf(numeration = numeration)
            else -> this.toRoman(num = num, numeration = numeration)
        }
    }

    private fun convertUpperHalf(num: Int, numeration: Int): String {
        if (num in 9 * numeration..<nextNumeration(numeration = numeration)) {
            return toSymbol(numeration = numeration)
                .plus(toSymbol(numeration = nextNumeration(numeration = numeration)))
        }
        return toHalfSymbol(numeration = numeration)
            .plus(
                toSymbol(numeration = numeration).repeat(
                    (num % half(numeration = numeration)) / numeration
                )
            )
    }

    private fun convertPreHalf(numeration: Int): String {
        return toSymbol(numeration = numeration).plus(toHalfSymbol(numeration = numeration))
    }

    private fun toRoman(num: Int, numeration: Int): String {
        return toSymbol(numeration = numeration).repeat(num / numeration % half(numeration = numeration))
    }

}