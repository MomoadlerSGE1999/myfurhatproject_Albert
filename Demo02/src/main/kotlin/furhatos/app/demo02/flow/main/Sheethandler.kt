package furhatos.app.demo02.flow.main


import org.apache.poi.xssf.model.SharedStrings
import org.xml.sax.SAXException
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler


/**
 * See org.xml.sax.helpers.DefaultHandler javadocs
 */
class SheetHandler(private val sst: SharedStrings?) : DefaultHandler() {
    private var lastContents: String? = null
    private var nextIsString = false
    @Throws(SAXException::class)
    override fun startElement(
        uri: String?, localName: String?, name: String,
        attributes: Attributes
    ) {
        // c => cell
        if (name == "c") {
            // Print the cell reference
            print(attributes.getValue("r") + " - ")
            // Figure out if the value is an index in the SST
            val cellType: String? = attributes.getValue("t")
            nextIsString = cellType != null && cellType == "s"
        }
        // Clear contents cache
        lastContents = ""
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String?, localName: String?, name: String) {
        // Process the last contents as required.
        // Do now, as characters() may be called more than once
        if (nextIsString) {
            val idx = lastContents!!.toInt()
            lastContents = sst!!.getItemAt(idx).string
            nextIsString = false
        }
        // v => contents of a cell
        // Output after we've seen the string contents
        if (name == "v") {
            println(lastContents)
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        lastContents += String(ch!!, start, length)
    }
}


