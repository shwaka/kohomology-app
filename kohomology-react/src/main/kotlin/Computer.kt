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
                        val indeterminateList: List<SerializableIndeterminate> = Json.decodeFromString(state.json)
                        val display = indeterminateList.toString()
                        setState(
                            ComputerState(json = state.json, display = display)
                        )
                    }
                }
            }
        }
        div {
            +"${state.display}"
        }
    }
}

@Serializable
data class SerializableIndeterminate(val name: String, val degree: Int)
