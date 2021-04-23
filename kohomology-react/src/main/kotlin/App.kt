import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

external interface AppProps : RProps
data class AppState(
    val display: List<String> = listOf(),
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
                        this@App.setState({ previousState ->
                            val newText = previousState.display + listOf(text)
                            AppState(newText)
                        })
                    }
                }
            }
            div {
                state.display.map {
                    div {
                        +it
                    }
                }
            }
        }
    }
}
