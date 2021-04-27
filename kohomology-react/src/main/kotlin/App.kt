import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

external interface AppProps : RProps
data class AppState(
    val display: String = ""
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
