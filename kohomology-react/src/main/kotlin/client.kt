import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(Computer::class) {
                attrs {
                    json = """
                        [
                          { "name": "x", "degree": 2 },
                          { "name": "y", "degree": 3 }
                        ]
                    """.trimIndent()
                }
            }
        }
    }
}
