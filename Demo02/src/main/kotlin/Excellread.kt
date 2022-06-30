/*package furhatos.app.demo02.flow.main

import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import java.io.FileInputStream
import java.io.IOException


fun main(args: Array<String>) {
    val filepath = "C:/data/Schicht1.xlsx"

    //Instantiate Excel workbook using existing file:
    val myworkbook = readFile(filepath)



// Return first sheet from the XLSX workbook
// Return first sheet from the XLSX workbook
    val mySheet: HSSFSheet? = myworkbook?.getSheetAt(0)
// Get iterator to all the rows in current sheet
// Get iterator to all the rows in current sheet
    val rowIterator: Iterator<Row> = mySheet!!.iterator()
// Traversing over each row of XLSX file
// Traversing over each row of XLSX file
    while (rowIterator.hasNext()) {
        val row: Row = rowIterator.next()
        // For each row, iterate through each columns
        val cellIterator: Iterator<Cell> = row.cellIterator()
        while (cellIterator.hasNext()) {
            val cell: Cell = cellIterator.next()
            when (cell.getCellType()) {
                Cell.CELL_TYPE_STRING -> print(cell.getStringCellValue() + "\t")
                Cell.CELL_TYPE_NUMERIC -> print(cell.getNumericCellValue() + "\t")
                Cell.CELL_TYPE_BOOLEAN -> print(cell.getBooleanCellValue() + "\t")
                else -> {}
            }
        }
        println("")
    }
}


@Throws(IOException::class)
private fun readFile(filename: String): HSSFWorkbook? {
    FileInputStream(filename).use { fis ->
        return HSSFWorkbook(fis) // NOSONAR - should not be closed here
    }
}


*/



