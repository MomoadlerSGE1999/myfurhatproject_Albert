import furhatos.app.demo02.flow.main.suchePatient
import furhatos.app.demo02.flow.main.user
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.skills.Skill
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream

val ReadExcel : State = state() {


        val filename: String = "C:\\data\\Belegungsplan_Nephro.xls"
        //Hier wird ein Woorkbook erstellt und über FileInputStream mit Variable "Filename" gefunden
        val wb = WorkbookFactory.create(FileInputStream(filename))
        //Hier wird die Arbeitsmappe in der Excel ausgewählt, der Index 0 sthet dabei für die erste Arbeitsmappe
        val sheet: Sheet = wb.getSheetAt(0)
        //Var Platzlist soll eine Liste mit allen Plätzen, Räumen und der jeweiligen Zeile darstellen
        var platzList = mutableListOf<Platz>()
        var raum: String? = null
        //Die erste Forschleife läuft einmal durch jede Zeile der Excell durch und bezieht sich in ihrer Taktung auf die einzelnen Zeilen der Excel,
        //dabei definieren die ifs und die zweite vorschleife wie die Zellen befüllt werden.
        // ausgegeben wird die anzahl an Zeilen, die eins höher als die letzte Reihennummer des Sheets.
        for (rowIndex in 0 until sheet.lastRowNum + 1) {
            //der val rowContent ist so definiert, dass er nullable sein kann, er da viele Zellen, der Excel den wert null haben, der RowIndex gibt die Spalte an
            val rowContent: Row? = sheet.getRow(rowIndex)
            //Wenn der Zeileninhalt der aktuellen zeile nicht Null ist, dann hole den Wert aus der jeweiligen zeilen aus den
            // Zellen mit dem Zellenindex 2 und 1, also C und B,
            // Der Inhalt der hier aus den Zellen gelesen wird ist entweder die Beschreibung des Raumes B oder die Beschreibung des Platzes C
            //Durch get cell wird eine Zelle in den jeweiligen Zellen mit dem Index 2 und 1 sprich der Spalten 2 und 1 erzeugt, wenn diese nicht null beträgt
            if (rowContent != null) {
                val cellPlatz: Cell? = rowContent.getCell(2)
                val cellRaum: Cell? = rowContent.getCell(1)

                //Ist die jeweilige Zelle der Spalte B nicht null, also besitzt Inhalt, erzeuge aus der ausgelesenen Zelle einen String
                if (cellRaum != null) {
                    raum = cellRaum.toString()
                }

                //Ist die Zelle Platz, also die Zellen der Spalte 2 nicht null, so wird aus der Zelle ein String erzeugt,
                // das ermöglicht uns dass wir mit dem Präfix der Zelle arbeiten können
                if (cellPlatz != null) {
                    val cellPlatzString: String = cellPlatz.toString()
                    //Wenn der String der Zelle aus der Spalte Platz, also C mit dem Präfix "Platz" beginnt, und die jeweilige Zelle der Spalte B den Wert Null hat,
                    // so werden die Zellen der Spalte B, die den Wert Null haben, mit dem Inhalt der Spalte B bzw cellnum 1 (Indexwert --> 0,1) der jeweiligen Spalte überschrieben
                    //Wenn die Zelle der Spalte Raum nicht den Wert Null hat, dann wird die Variable CellRaum zu einem String und anschließend die variable raum mit cellraum überschrieben
                    //Um dann der Platzliste den Raum des jeweiligen Platzes hinzuzufügen, asnchließend wird die Variable raum wieder genullt
                    if (cellPlatzString.startsWith("Platz")) {
                        if (raum == null) {
                            for (rowIndex2 in rowIndex until sheet.lastRowNum + 1) {
                                val cellRaum: Cell? = rowContent.getCell(1)
                                if (cellRaum != null) {
                                    raum = cellRaum.toString()
                                    platzList.add(Platz(raum, cellPlatzString, rowIndex))
                                    raum = null
                                    break
                                    //TODO Wird auch die leere Zelle unter Raum... überschrieben? Sowohl in CSV als auch xls, wird sie nicht, weil das nicht geregelt ist(nicht sicher)? Testen mit Carmen Picha
                                }
                            }
                        } else {
                            //Ist die Zelle Raum nicht null, so kann der Inhalt der Zelle direkt in die Liste Platz als Variable Raum übernommen werden
                            platzList.add(Platz(raum, cellPlatzString, rowIndex))
                        }
                    }
                }
            }
        }
        onEntry {
            var name: String = user!!.get("fullname").toString()
            //Die Funktion hat drei Variablen: sheet=Arbeitsmappe, col= Tag bzw Spalte des Sheets, name=Patientenname
            var ergebnis = suchePatient(sheet, 3, name)
//TODO time stamp für die Spalte in der gesucht wird
//wenn das ergebnis -1 ist, wurde der Name nicht gefunden
            for (platz in platzList) {
                if (ergebnis == -1) {
                    println("Patient: $name nicht gefunden")
                    break
                }
//Ist die Zeile der gefundenen Zelle gleich der Zeile des Platzes so kann für den tag X der Platz des Patienten bestimmt werden,
// da die Zeile des Patienten, die durch die Funktion gefunden wurde in der Excel auch die Information des Raumes enthält.
                if (platz.zeile == ergebnis) {
                    println("Patient: $name")
                    var platzfurhat: String = platz.platz
                    var raumfurhat: String =platz.raum.toString()
                    /*if (raumfurhat == "Z\Lich\EG\Dialyse 1 (BSNR: 410001400)") {
                        raumfurhat = "Dialyse 1 es klappt"
                    }
                    else {
                        raumfurhat == raumfurhat
                    }
                    */
                    println("Ich würde Sie bitten in $raumfurhat an den $platzfurhat zu gehen") // Ich würde gerne hier nur einzelne Teile der Variable platz ausgeben lassen, warum ist platz klein geschrieben,
                    furhat.say(" Ich würde Sie bitten in $raumfurhat an den $platzfurhat zu gehen") // wenn sich eigentlich die Liste mutablelistof<Platz> groß schreibt?
                terminate()
                }

            }
        }
    }




