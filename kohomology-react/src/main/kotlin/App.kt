import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

external interface AppProps : RProps
data class AppState(
    val display: String = "",
) : RState

@JsExport
class App(props: AppProps) : RComponent<AppProps, AppState>(props) {
    init {
        state = AppState("")
    }

    override fun RBuilder.render() {
        div {
            child(Computer::class) {
                attrs {
                    json = """
                        [
                          ["x", 2, "zero"], 
                          ["y", 3, "x^2"]
                        ]
                    """.trimIndent()
                    printlnFun = { text ->
                        this@App.setState({ previousState ->
                            val newText = previousState.display + text + "\n"
                            AppState(newText)
                        })
                    }
                }
            }
            div {
                +state.display
            }
        }
    }
}
