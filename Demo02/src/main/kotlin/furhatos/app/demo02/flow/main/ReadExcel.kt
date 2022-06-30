import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

fun main(args: Array<String>) {
    println("Hello World!")

    // Use an InputStream, needs more memory
    val filename: String = "C:/projects/kotlin-example/src/main/resources/Schicht1.xlsx"
    val wb = WorkbookFactory.create(FileInputStream(filename))

    val sheet: Sheet = wb.getSheetAt(0)

    for (rowIndex in 0 until sheet.lastRowNum+1) {
        val rowContent: Row? = sheet.getRow(rowIndex)

        if(rowContent!=null) {
            for (cellIndex in 0 until rowContent!!.lastCellNum+1) {
                print("$rowIndex/$cellIndex: ${rowContent.getCell(cellIndex)} \n")
            }
        }
    }
}