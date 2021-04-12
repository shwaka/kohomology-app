import com.github.shwaka.kohomology.dg.GAlgebra
import com.github.shwaka.kohomology.dg.GAlgebraContext
import com.github.shwaka.kohomology.dg.GVector
import com.github.shwaka.kohomology.dg.GVectorOrZero
import com.github.shwaka.kohomology.free.FreeDGAlgebra
import com.github.shwaka.kohomology.free.Indeterminate
import com.github.shwaka.kohomology.free.Monomial
import com.github.shwaka.kohomology.linalg.SparseMatrix
import com.github.shwaka.kohomology.linalg.SparseNumVector
import com.github.shwaka.kohomology.specific.BigRational
import com.github.shwaka.kohomology.specific.SparseMatrixSpaceOverBigRational
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.form
import react.dom.input

external interface ComputerProps : RProps {
    var json: String
}

data class ComputerState(
    val json: String,
    val display: String = "",
) : RState

@JsExport
class Computer(props: ComputerProps) : RComponent<ComputerProps, ComputerState>(props) {

    init {
        state = ComputerState(props.json)
    }

    override fun RBuilder.render() {
        form {
            input {
                attrs {
                    type = InputType.text
                    value = state.json
                    onChangeFunction = { event ->
                        setState(
                            ComputerState(json = (event.target as HTMLInputElement).value, display = state.display)
                        )
                    }
                }
            }
            input {
                attrs {
                    type = InputType.button
                    value = "Submit"
                    onClickFunction = { _ ->
                        val getDifferentialValueArray = eval("""
                            function getDifferentialValueArray(gAlgebraContext, generatorList, zeroGVector) {
                              const x = generatorList[0];
                              // const y = generatorList[1];
                              return [ zeroGVector, myMultiply(x, x) ];
                            };
                            getDifferentialValueArray
                        """.trimIndent()) as GetDifferentialValueArray
                        val indeterminateList: List<SerializableIndeterminate> = Json.decodeFromString(state.json)
                        computeCohomology(indeterminateList, getDifferentialValueArray)
                        val display = indeterminateList.toString()
                        setState(
                            ComputerState(json = state.json, display = display)
                        )
                    }
                }
            }
        }
        div {
            +state.display
        }
    }
}

fun myMultiply(
    x: GVector<String, BigRational, SparseNumVector<BigRational>>,
    y: GVector<String, BigRational, SparseNumVector<BigRational>>
) : GVector<String, BigRational, SparseNumVector<BigRational>> {
    val gAlgebra = x.gVectorSpace as GAlgebra<String, BigRational, SparseNumVector<BigRational>, SparseMatrix<BigRational>>
    return gAlgebra.context.run {
        this.multiply(x, y)
    }
}

@Serializable
data class SerializableIndeterminate(val name: String, val degree: Int)

typealias CurrentContext = GAlgebraContext<Monomial<String>, BigRational, SparseNumVector<BigRational>, SparseMatrix<BigRational>>
typealias CurrentGVector = GVector<Monomial<String>, BigRational, SparseNumVector<BigRational>>
typealias CurrentGVectorOrZero = GVectorOrZero<Monomial<String>, BigRational, SparseNumVector<BigRational>>
typealias GetDifferentialValueArray = CurrentContext.(Array<CurrentGVector>, CurrentGVectorOrZero) -> Array<CurrentGVectorOrZero>
typealias GetDifferentialValueList = CurrentContext.(List<CurrentGVector>) -> List<CurrentGVectorOrZero>

fun computeCohomology(
    serializableIndeterminateList: List<SerializableIndeterminate>,
    getDifferentialValueArray: GetDifferentialValueArray
) {
    val indeterminateList: List<Indeterminate<String>> = serializableIndeterminateList.map {
        Indeterminate(it.name, it.degree)
    }

    val getDifferentialValueList: GetDifferentialValueList = { valueList ->
        getDifferentialValueArray(
            valueList.toTypedArray(),
            zeroGVector
        ).toList()
    }
    val freeDGAlgebra = FreeDGAlgebra(SparseMatrixSpaceOverBigRational, indeterminateList, getDifferentialValueList)
    for (degree in 0 until 20) {
        val basis = freeDGAlgebra.cohomology.getBasis(degree)
        println("H^$degree = Q$basis")
    }
}
