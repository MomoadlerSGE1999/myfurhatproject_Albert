import furhatos.app.demo02.flow.main.suchePatient
import furhatos.app.demo02.flow.main.user
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.nlu.common.Date
import furhatos.nlu.common.en.weekDaysEn
import furhatos.records.User
import furhatos.skills.Skill
import org.apache.commons.lang3.StringUtils
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun ReadExcel2(user: User) {


        val filename: String = "C:\\data\\Nephro_Nachname_Vorname_Patientennummer_1.xls"
        //Hier wird ein Woorkbook erstellt und über FileInputStream mit Variable "Filename" gefunden
        val wb = WorkbookFactory.create(FileInputStream(filename))
        //Hier wird die Arbeitsmappe in der Excel ausgewählt, der Index 0 sthet dabei für die erste Arbeitsmappe
        val sheet: Sheet = wb.getSheetAt(0)
        //Var Platzlist soll eine Liste mit allen Plätzen, Räumen und der jeweiligen Zeile darstellen
        var platzList = mutableListOf<Platz>()
        var raum: String? = null
        //Die erste Forschleife läuft einmal durch jede Zeile der Excell durch und bezieht sich in ihrer Taktung auf die einzelnen Zeilen der Excel,
        //dabei definieren die ifs und die zweite vorschleife wie die Zellen befüllt werden.
        // ausgegeben wird die anzahl an Zeilen, die eins höher als die letzte Reihennummer des Sheets ist.
        for (rowIndex in 0 until sheet.lastRowNum + 1) {
            //der val rowContent ist so definiert, dass er nullable sein kann, der RowIndex gibt die Zeile an
            val rowContent: Row? = sheet.getRow(rowIndex)
            //Wenn der Zeileninhalt der aktuellen zeile nicht Null ist, dann hole den Wert aus der jeweiligen zeilen
            // Cellnum 2 bzw 1 = Zellen mit dem Zellenindex 2 und 1, also Spalte C und B,
            // Der Inhalt der hier aus den Zellen gelesen wird ist entweder die Beschreibung des Raumes B oder die Beschreibung des Platzes C
            //Durch get cell wird eine Zelle in den jeweiligen Zeilen mit dem Index 2 und 1 sprich der Spalten 2 und 1 erzeugt, wenn diese nicht null beträgt
            if (rowContent != null) {
                val cellPlatz: Cell? = rowContent.getCell(2)
                val cellRaum: Cell? = rowContent.getCell(1)

                //Ist die jeweilige Zelle der Spalte B nicht null, also besitzt Inhalt, erzeuge aus der ausgelesenen Zelle einen String
                if (cellRaum != null && cellRaum.toString().trim().contains("Raum")) {
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
                               //Name shadowing means that you are using variables with the same name in different scopes, making it more likely that you by accident refer to the wrong one. The solution is to rename them to be different.
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


    var searchNum: String = user!!.get("Patientennummer").toString()

    var spalte: Int? = null

    var daten: List<String> = LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)).split(",")

    if(daten[0] == "Montag") {
        spalte = 3
    }
    if (daten[0] == "Dienstag") {
        spalte = 4
    }
    if (daten[0] == "Mittwoch") {
        spalte = 5
    }
    if (daten[0] == "Donnerstag") {
        spalte = 6
    }
    if (daten[0] == "Freitag") {
        spalte = 7
    }
    if (daten[0] == "Samstag") {
        spalte = 4
    }

    if (daten[0] == "Sonntag") {
        spalte = 9
    }

    var col = spalte

    //TODO Mit Var Wochentag einen zeitstempel setzen, der basierend auf dem Wochentag die Col als Int setzt
    //TODO var Wochentag = Col "Muss dann hier den aktuellen Wochentag ausgeben und entsprechend der Spaltenlogik einen Int setzen"
    var foundrowIndex = -1

        //die Variable PatientName ist ein nullable String, hier beträgt der noch nicht überschriebene Wert null
        var patientNum: String? = null
        var patientname: String? = ""
        //For Schleife, um die Zeilen auszugeben
        for (rowIndex in 0 until sheet.lastRowNum + 1) {
            val rowContent: Row? = sheet.getRow(rowIndex)
        //Wenn der Zeileninhalt nicht null ist, dann erzeuge einen Value in Form einer Zelle mit dem Inhalt der Zelle in Zeile X und Spalte (col)
            if (rowContent != null) {
                val cellDay: Cell? = rowContent.getCell(col!!)
                //Wenn der Inhalt des Values CellDay nicht null ist, was er auch nicht sein kann, da das vorherige if diesen Fall schon abdeckt, §§ nur ausführen wenn in der zelle die Zahlenkombination (searchnum) steht
                // dann überschreibe die Variable patientNum mit dem Inhalt dem Values CellDay
                if(cellDay!=null && cellDay.toString().contains(searchNum)) {
                    //Nach jedem Komma erzeugt einen neuen weret
                    val temp = cellDay.toString().split(',')

                    //Value beschreibt erstmal jeden einzelnen wert innerhalb der list, jede zelle wird ausgegeben die gefunden wird und da ab erfolgreichen abgleich keine neue zelle mehr gefunden wird
                    for (value in temp){
                        //Wenn wert in Liste numeric = true, trim eliminiert leerzeichen, wenn numerischer wert dann wird patientennummer gesetzt, BSP: Haas Helmut
                        if (StringUtils.isNumeric(value.trim())){
                            if (value.trim().length == 5) {
                                patientNum = Integer.parseInt(value.trim()).toString()
                                println(patientNum)
                                                           }
                            //ab hier kein nummerischer wert mehr
                        } else {
                            if (value.trim().length > 0){
                                patientname += " " + value.trim()
                            }
                        }
                    }
                    //patientNum = cellDay.toString().substring(startIndex = 0, endIndex = 5)
                    // TODO patientname = cellDay.toString().substring(startIndex = 7)
                    //Ist die gesuchte Patientennummer = der gefundenen Patientennummer (Searchnum) dann wird der RowIndex ausgegeben und über die Reihe Platz und Raum
                    if(searchNum == patientNum){
                        //foundrowindex ist die Zeile, die den namen enthält der abgeglichen werden muss, im excelsheet
                        foundrowIndex = rowIndex
                        user!!.put("name", patientname)
                        //Excel durchsucht, Patnum rowrichtig gefunden, die den patient enthält oder nicht
                    }
                }
            }
        }





//wenn das ergebnis -1 ist, wurde der Name nicht gefunden
    user!!.put("row", foundrowIndex)
    println("${user!!.get("name")} - ${user!!.get("Patientennummer")}")
    for (platz in platzList) {
                if (foundrowIndex == -1) {
                    println("Patient: $searchNum nicht gefunden")
                    break
                }
//Ist die Zeile der gefundenen Zelle gleich der Zeile des Platzes so kann für den tag X der Platz des Patienten bestimmt werden,
// da die Zeile des Patienten, die durch die Funktion gefunden wurde in der Excel auch die Information des Raumes enthält.
                if (platz.zeile == foundrowIndex) {
                    println("Patient: $searchNum")
                    var platzfurhat: String = platz.platz
                    var raumfurhat: String = platz.raum.toString().substring(startIndex = 10, endIndex = 18)

                    user!!.put("raum", raumfurhat)
                    user!!.put("platz", platzfurhat)
                }
            }
        }





//TODO Fehler dialog und model trenne, also auslesecode von dialog trennen,



