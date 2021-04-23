
import com.github.shwaka.kohomology.free.FreeDGAlgebra
import com.github.shwaka.kohomology.free.GeneratorOfFreeDGA
import com.github.shwaka.kohomology.specific.SparseMatrixSpaceOverBigRational
import com.github.shwaka.kohomology.util.Degree
import kotlinx.browser.window
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonTransformingSerializer
import org.w3c.dom.HTMLTextAreaElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import react.dom.div
import react.dom.form
import react.dom.input
import react.dom.textArea
import react.dom.value
import react.setState

external interface ComputerProps : RProps {
    // var json: String
    var printlnFun: (String) -> Unit
}

data class ComputerState(
    var json: String,
    // val display: String = "",
) : RState

@Serializable
data class SerializableGenerator(val name: String, val degree: Degree, val differentialValue: String)

object GeneratorSerializer : JsonTransformingSerializer<SerializableGenerator>(SerializableGenerator.serializer()) {
    override fun transformDeserialize(element: JsonElement): JsonElement {
        return if (element is JsonArray) {
            JsonObject(
                mapOf(
                    "name" to element[0],
                    "degree" to element[1],
                    "differentialValue" to element[2],
                )
            )
        } else {
            element
        }
    }
}

private fun evenSphere(n: Int) = """
    [
      ["x", ${n}, "zero"], 
      ["y", ${2 * n - 1}, "x^2"]
    ]
""".trimIndent()

private fun cpn(n: Int) = """
    [
      ["c", 2, "zero"],
      ["x", ${2 * n + 1}, "c^${n + 1}"]
    ]
""".trimIndent()

private val example = """
    [
      ["a", 2, "zero"],
      ["b", 2, "zero"],
      ["x", 3, "a^2"],
      ["y", 3, "a*b"],
      ["z", 3, "b^2"]
    ]
""".trimIndent()

@ExperimentalJsExport
@JsExport
class Computer(props: ComputerProps) : RComponent<ComputerProps, ComputerState>(props) {

    init {
        state = ComputerState(evenSphere(2))
    }

    private fun RBuilder.createButton(valueString: String, jsonString: String): ReactElement {
        return input {
            attrs {
                type = InputType.button
                value = valueString
                onClickFunction = {
                    this@Computer.setState {
                        json = jsonString
                    }
                }
            }
        }
    }

    override fun RBuilder.render() {
        div {
            createButton("S^2", evenSphere(2))
            createButton("CP^4", cpn(4))
            createButton("example", example)
            form {
                textArea {
                    attrs {
                        rows = "20"
                        cols = "80"
                        value = state.json
                        onChangeFunction = { event ->
                            setState(
                                ComputerState(json = (event.target as HTMLTextAreaElement).value)
                            )
                        }
                    }
                }
                input {
                    attrs {
                        type = InputType.button
                        value = "Compute"
                        onClickFunction = { _ ->
                            val generatorList: List<SerializableGenerator> =
                                Json.decodeFromString(ListSerializer(GeneratorSerializer), state.json)
                            this@Computer.computeCohomology(generatorList)
                            window.setTimeout({ eval("MathJax.typeset()") }, 300)
                        }
                    }
                }
            }
        }
    }

    private fun computeCohomology(
        serializableGeneratorList: List<SerializableGenerator>,
    ) {
        val generatorList = serializableGeneratorList.map {
            GeneratorOfFreeDGA(it.name, it.degree, it.differentialValue)
        }
        val freeDGAlgebra = FreeDGAlgebra(SparseMatrixSpaceOverBigRational, generatorList)
        for (degree in 0 until 20) {
            val basis = freeDGAlgebra.cohomology.getBasis(degree)
            val vectorSpaceString = if (basis.isEmpty()) "0" else {
                val basisString = basis.joinToString(", ") { it.toString() }
                "\\mathbb{Q}\\{$basisString\\}"
            }
            this.props.printlnFun("\\(H^{$degree} = $vectorSpaceString\\)")
        }
    }
}

// typealias CurrentContext = FreeGAlgebraContext<StringIndeterminateName, BigRational, SparseNumVector<BigRational>, SparseMatrix<BigRational>>
// typealias CurrentGVector = GVector<Monomial<StringIndeterminateName>, BigRational, SparseNumVector<BigRational>>
// typealias CurrentGVectorOrZero = GVectorOrZero<Monomial<StringIndeterminateName>, BigRational, SparseNumVector<BigRational>>
// typealias GetDifferentialValueArray = CurrentContext.(Array<CurrentGVector>, CurrentGVectorOrZero) -> Array<CurrentGVectorOrZero>
// typealias GetDifferentialValueList = CurrentContext.(List<CurrentGVector>) -> List<CurrentGVectorOrZero>
