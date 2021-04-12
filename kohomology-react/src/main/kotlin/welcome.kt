import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.form
import react.dom.input

external interface WelcomeProps : RProps {
    var name: String
}

data class WelcomeState(
    val name: String,
    val display: String = "",
) : RState

@JsExport
class Welcome(props: WelcomeProps) : RComponent<WelcomeProps, WelcomeState>(props) {

    init {
        state = WelcomeState(props.name)
    }

    override fun RBuilder.render() {
        form {
            input {
                attrs {
                    type = InputType.text
                    value = state.name
                    onChangeFunction = { event ->
                        setState(
                            WelcomeState(name = (event.target as HTMLInputElement).value)
                        )
                    }
                }
            }
            input {
                attrs {
                    type = InputType.button
                    value = "Submit"
                    onClickFunction = { _ ->
                        val display = "Submitted name is ${state.name}"
                        setState(
                            WelcomeState(name = state.name, display = display)
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
