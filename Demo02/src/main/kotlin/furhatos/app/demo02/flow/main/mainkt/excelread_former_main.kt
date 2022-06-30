import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.openxml4j.opc.PackageAccess
import org.apache.poi.util.XMLHelper
import org.apache.poi.xssf.eventusermodel.XSSFReader
import org.apache.poi.xssf.model.SharedStrings
import org.xml.sax.ContentHandler
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.XMLReader
import javax.xml.parsers.ParserConfigurationException

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    // read xlsx-file
    // C:\projects\kotlin-example\src\main\resources
    val filename: String = "C:/projects/kotlin-example/src/main/resources/Schicht1.xlsx"
    processAllSheets(filename)

}

@Throws(Exception::class)
fun processAllSheets(filename: String?) {
    OPCPackage.open(filename, PackageAccess.READ).use { pkg ->
        val r = XSSFReader(pkg)
        val sst: SharedStrings = r.sharedStringsTable
        val parser: XMLReader = fetchSheetParser(sst)
        val sheets: Iterator<java.io.InputStream> = r.getSheetsData()
        while (sheets.hasNext()) {
            println("Processing new sheet:\n")
            sheets.next().use { sheet ->
                val sheetSource = InputSource(sheet)
                parser.parse(sheetSource)
            }
            println()
        }
    }
}

@Throws(SAXException::class, ParserConfigurationException::class)
fun fetchSheetParser(sst: SharedStrings?): XMLReader {
    val parser = XMLHelper.newXMLReader()
    val handler: ContentHandler = SheetHandler(sst)
    parser.contentHandler = handler
    return parser
}
