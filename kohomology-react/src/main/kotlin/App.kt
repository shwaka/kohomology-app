import kotlinx.css.Color
import kotlinx.css.color
import kotlinx.html.classes
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.style
import styled.css
import styled.styledDiv

external interface AppProps : RProps
data class AppState(
    val display: String = "",
    val error: String = ""
) : RState

@JsExport
class App(props: AppProps) : RComponent<AppProps, AppState>(props) {
    init {
        state = AppState()
    }

    override fun RBuilder.render() {
        div {
            child(Computer::class) {
                attrs {
                    printlnFun = { text ->
                        this@App.setState(AppState(text)) {
                            eval("MathJax.typeset()")
                        }
                    }
                    printErrorFun = { text ->
                        this@App.setState(AppState(error = text))
                    }
                }
            }
            styledDiv {
                css {
                    color = Color.red
                }
                attrs {
                    classes = setOf("error")
                }
                state.error.lines().map {
                    div {
                        +it
                    }
                }
            }
            div {
                state.display.lines().map {
                    div {
                        +it
                    }
                }
            }
        }
    }
}
