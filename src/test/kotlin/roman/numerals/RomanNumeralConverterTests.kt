package roman.numerals

import io.kotest.core.spec.style.FunSpec
import kotlin.test.assertEquals

import com.haroldcohen.roman.numerals.NumberConverter

class RomanNumeralConverterTests : FunSpec({
    mapOf<Int, String>(
        1 to "I",
        2 to "II",
        3 to "III",
        5 to "V",
        6 to "VI",
        7 to "VII",
        10 to "X",
        11 to "XI",
        12 to "XII",
        20 to "XX",
        21 to "XXI",
        30 to "XXX",
        50 to "L",
        51 to "LI",
        60 to "LX",
        70 to "LXX",
        100 to "C",
        110 to "CX",
        111 to "CXI",
        120 to "CXX",
        200 to "CC",
        210 to "CCX",
        211 to "CCXI",
        220 to "CCXX",
        500 to "D",
        600 to "DC",
        710 to "DCCX",
        711 to "DCCXI",
        1000 to "M",
        2000 to "MM",
        1500 to "MD",
        2500 to "MMD",
        3760 to "MMMDCCLX",
        4 to "IV",
        40 to "XL",
        41 to "XLI",
        400 to "CD",
        410 to "CDX",
        9 to "IX",
        90 to "XC",
        91 to "XCI",
        900 to "CM",
        910 to "CMX",
        1910 to "MCMX",
    ).forEach { (number, expected) ->
        test(name="Convert $number should return matching Roman numeral $expected") {
            val converter = NumberConverter(number=number)
            val result: String = converter.convert()
            assertEquals(expected, result)
        }
    }
})