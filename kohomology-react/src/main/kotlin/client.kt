import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(Computer::class) {
                attrs {
                    json = """[["x", 2, "zero"], ["y", 3, "x^2"]]"""
                }
            }
        }
    }
}
