import com.github.shwaka.kohomology.free.FreeDGAlgebra
import com.github.shwaka.kohomology.free.Indeterminate
import com.github.shwaka.kohomology.specific.BigRationalField
import com.github.shwaka.kohomology.specific.SparseMatrixSpaceOverBigRational
import com.github.shwaka.kohomology.specific.SparseNumVectorSpaceOverBigRational

fun main() {
    Printer.execute {
        val foo = BigRationalField.context.run {
            one / two + one / three
        }
        Printer.printToBrowserImmediately(foo)
        numVectorTest()
        cohomologyTest()
    }
}

suspend fun numVectorTest() {
    val vectorSpace = SparseNumVectorSpaceOverBigRational
    val result = vectorSpace.context.run {
        val v = vectorSpace.fromValues(listOf(one, zero))
        "2 * (1, 0) = ${two * v}"
    }
    Printer.printToBrowserImmediately(result)
}

suspend fun cohomologyTest() {
    val matrixSpace = SparseMatrixSpaceOverBigRational
    val indeterminateList = listOf(
        Indeterminate("a", 2),
        Indeterminate("b", 2),
        Indeterminate("x", 3),
        Indeterminate("y", 3),
        Indeterminate("z", 3),
    )
    val freeDGAlgebra = FreeDGAlgebra(matrixSpace, indeterminateList) { (a, b, _, _, _) ->
        listOf(zeroGVector, zeroGVector, a.pow(2), a * b, b.pow(2))
    }
    val (a, b, x, y, z) = freeDGAlgebra.gAlgebra.generatorList
    freeDGAlgebra.context.run {
        Printer.printToBrowser(d(x * y))
        Printer.printToBrowser(d(x * y * z))
    }
    val cohomologyStringList = mutableListOf<String>()
    for (n in 0 until 12) {
        val basis = freeDGAlgebra.cohomology[n].getBasis()
        cohomologyStringList.add("H^$n = Q$basis")
    }
    Printer.printToBrowserImmediately(cohomologyStringList.joinToString("\n"))
}
