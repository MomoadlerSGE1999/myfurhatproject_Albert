import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

fun main(args: Array<String>) {


    val filename: String = "C:/data/Schicht1.xlsx"
    //Hier wird ein Woorkbook erstellt und über FileInputStream mit Variable "Filename" gefunden
    val wb = WorkbookFactory.create(FileInputStream(filename))
    //Hier wird die Arbeitsmappe in der Excel ausgewählt, der Index 0 sthet dabei für die erste Arbeitsmappe
    val sheet: Sheet = wb.getSheetAt(0)
    //Var Platzlist soll eine Liste mit allen Plätzen, Räumen und der jeweiligen Zeile darstellen
    var platzList = mutableListOf<Platz>()
    var raum: String? = null
    //Über die Forschleife wird die gesamte Excel basierend auf den if Regeln erzeugt,
    // was nach den if Regeln übrig bleibt ist eine...(Liste mit allen Plätzen, die um den jeweiligen Raum sowie die jeweilige Zeile ergänzt worden sind)
    for (rowIndex in 0 until sheet.lastRowNum+1) {
        //der val rowContent ist so definiert, dass er nullable sein kann, da viele Zellen der Excel den wert null haben, der RowIndex gibt die Spalte an
        val rowContent: Row? = sheet.getRow(rowIndex)
        //Wenn der Spalteninhalt nicht Null ist, also in Spalte B(1) und Spalte C(2) ein wert steht, dann arbeitet der Code mit dem Inhalt der Spalten weiter, bzw erzeugt eine Spalte mit dem Inhalt der Spalte in der Excel
        if(rowContent!=null) {
            val cellPlatz: Cell? = rowContent.getCell(2)
            val cellRaum: Cell? = rowContent.getCell(1)
            //Ist die jeweilige Zelle der Spalte B nicht null, also besitzt Inhalt, erzeuge aus der ausgelesenen Zelle einen String, ist die Zelle Null was passiert dann??
            if(cellRaum!=null) {
                raum = cellRaum.toString()
            }
            //Ist die Zelle Platz, also die Zellen der Spalte 2 nicht null, so wird aus der Zelle ein String erzeugt, sodass wir dann mit dem Präfix des Strings arbeiten können
            if(cellPlatz!= null){
                val cellPlatzString: String = cellPlatz.toString()
                //Wenn der String der Zelle aus der Spalte Platz, also C mit dem Präfix "Platz" beginnt, und die jeweilige Zelle der Spalte B den Wert Null hat,
                // so werden die Zellen der Spalte B, die den Wert Null haben, mit dem Inhalt der Zellnummer 1 überschrieben
                // (Warum Zellnummer eins?, Wie klappt es dann, dass alle nullwerte der Spalte Räume immer korrekt mit dem jeweiligen Raum überschrieben werden...nachdneken)
                //Wenn die Zelle der Spalte Raum nicht den Wert Null haben, dann wird die Variable CellRaum zu einem String und anschließend die variable raum mit cellraum überschrieben
                //Um dann der Platzliste den Raum hinzuzufügen, asnchließend wird die Variable raum wieder genullt
                if(cellPlatzString.startsWith("Platz")){
                    if(raum==null){
                        for (rowIndex2 in rowIndex until sheet.lastRowNum+1) {
                            val cellRaum: Cell? = rowContent.getCell(1)
                            if(cellRaum!=null) {
                                raum = cellRaum.toString()
                                platzList.add(Platz(cellPlatzString, rowIndex, raum))
                                raum=null
                                break
                            }
                        }
                    } else {
                        //Ist die Zelle Raum nicht null, so kann der Inhalt der Zelle direkt in die Liste Platz als Variable Raum übernommen werden
                        platzList.add(Platz(cellPlatzString, rowIndex, raum))
                    }
                }
            }
        }
    }

    var name = "Sack"
    //Die Funktion hat drei Variablen: sheet=Arbeitsmappe, col= Tag bzw Spalte des Sheets, name=Patientenname
    var ergebnis = suchePatient(sheet, 10, name)
//wenn das ergebnis -1 ist, wurde der Name nicht gefunden, -1 weil alle Ereignisse, die den Patienten finden sind abgedeckt, das Szenario, dass der Patient nicht gefunden wird beschreibt das Ergebniss -1
        for(platz in platzList){
        if (ergebnis == -1) {
            println("Patient: $name nicht gefunden")
            break
        }
//Ist die Zeile des gefundenen Platzes gleich der Zeile des Matches so stimmt das ergebniss und der Name des Patienten sowie der Platz werden geprinted
        if(platz.zeile == ergebnis) {
            println("Patient: $name")
            println(platz)
        }
    }
}


//Spalte col = tag
fun suchePatient(sheet: Sheet, col: Int, searchName: String): Int {
   //die Variable PatientName ist ein nullable String, hier beträgt der noch nicht überschriebene Wert null
    var patientName: String? = null
    //For Schleife, um die Inhalte der Zellen mit Zeilen zu taggen
    for (rowIndex in 0 until sheet.lastRowNum + 1) {
        val rowContent: Row? = sheet.getRow(rowIndex)
//Wenn der Zeileninhalt nicht null ist, dann formatiere den Inhalt der Zelle in der Spalte mit Index 10, die sich auf den Tag bezieht zu einer Zelle und tagge sie mit einem Tag bzw. col
        if (rowContent != null) {
            val cellDay: Cell? = rowContent.getCell(col)
            //Wenn der Inhalt dieser Zelle nicht null ist, was er auch nicht sein kann, da das vorherige if diesen Fall schon abdeckt, dann generiere aus der Zelle eine Variable die den Inhalt zu einem String transformiert
            if(cellDay!=null) {
                patientName = cellDay.toString()
                if(searchName == patientName){
                    return rowIndex
                }
            }
        }
    }
    return -1
}