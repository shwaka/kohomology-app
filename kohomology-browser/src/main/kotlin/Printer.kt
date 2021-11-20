import kotlinx.browser.document
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Printer {
    fun printToBrowser(obj: Any) {
        val text = obj.toString()
        println("[myPrint] $text")
        val root = document.getElementById("root") ?: throw Exception("root not found!")
        val p = document.createElement("pre")
        p.textContent = text
        root.appendChild(p)
    }

    suspend fun printToBrowserImmediately(obj: Any) {
        this.printToBrowser(obj)
        delay(10)
    }

    fun execute(block: suspend CoroutineScope.() -> Unit) {
        GlobalScope.launch { block() }
    }
}
