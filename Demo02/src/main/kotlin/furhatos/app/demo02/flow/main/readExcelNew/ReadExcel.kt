import furhatos.records.User
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

//Die Funktion ReadExcel wurde extra für das Excelformat des Patientenbelegungsplans geschrieben, sodass es beim
//Verstehen des Codes absolut notwendig ist, das Format des Patientenbelegungsplans zu kennen

fun ReadExcel(Benutzer: User) {

// Zunächst wird der Pfad definiert in dem die jeweils relevante Excel-Datei abgelegt ist
    val filename: String = "C:\\data\\Belegungsplan_Finales_Format.xls"
    //val filename: String = "N:\\Vicki_Taxi.xls"

        //Hier wird ein Woorkbook erstellt. Die Excel-Datei wird FileInputStream mit der Variable "Filename" gefunden
        val wb = WorkbookFactory.create(FileInputStream(filename))

        //Hier wird die Arbeitsmappe in der Excel ausgewählt, der Index 0 steht dabei für die erste Arbeitsmappe
        val sheet: Sheet = wb.getSheetAt(0)

        //Var Platzlist soll eine Liste mit allen Plätzen, Räumen und der jeweiligen Zeile darstellen. Die Parameter
        // der platzlist sind in der Platz.kt definiert
        val platzList = mutableListOf<Platz>()

        //Die Variable raum wird initial gesetzt
        var raum: String? = null

    //Die erste For-Schleife iteriert durch jede Zeile der Excel und bezieht sich in ihrer Taktung auf
    //die einzelnen Zeilen der Excel. Dabei definieren die ifs und die zweite und dritte vorschleife wie die Zellen
    //befüllt werden. Ausgegeben wird die Anzahl an Zeilen, die eins höher als die letzte Reihennummer ist.
    //Die Schleife endet mit der letzten befüllten zeile der Excel.
        for (rowIndex in 0 until sheet.lastRowNum + 1) {

            //Der Wert rowContent ist so definiert, dass er nullable sein kann, der RowIndex gibt die Zeile an und
            //nimmt jeweils den Indexwert der Zeile an, die gerade iteriert wird
            val rowContent: Row? = sheet.getRow(rowIndex)

            //Ist Zeileninhalt der aktuellen Zeile nicht Null, wird der Code hinter dem if ausgeführt
            if (rowContent != null) {

                //Cellnum 1 bzw. 2 = Zellen mit dem Zellenindex 2 und 1, also Spalte B (Raum) und C (Platz),
                //Der Inhalt der hier aus den Zellen gelesen wird, ist entweder die Beschreibung des Raumes B oder die
                //Beschreibung des Platzes C.
                val cellPlatz: Cell? = rowContent.getCell(2)
                val cellRaum: Cell? = rowContent.getCell(1)

                //Ist die jeweilige Zelle der Spalte B nicht null und die enthält das Wort "Raum" ,
                //wird die ausgelesene Zelle zu einem String konvertiert und alle Leerzeichen werden entfernt

                if (cellRaum != null && cellRaum.toString().trim().contains("Raum")) {
                    //der String wird dann der var raum zugeordnet
                    raum = cellRaum.toString()
                }

                //Der Wert der ausgelesenen Zelle muss Inhalt haben --> Darf nicht null sein
                if (cellPlatz != null) {

                    //Aus cellPlatz wird ein String erzeugt
                    val cellPlatzString: String = cellPlatz.toString()

                    //Startet cellPlatzString mit dem Wort Platz
                    if (cellPlatzString.startsWith("Platz")) {

                        //Und ist der raum, also der Wert aus der gleichen Reihe in Spalte B null
                        if (raum == null) {

                        //So wird ab der jeweiligen Zeile in der sich die Schleife gerade befindet
                        //jede Zeile erneut iteriert bis zum Ende der Arbeitsmappe oder bis die If-Bedingung zutriftt
                            for (rowIndex2 in rowIndex until sheet.lastRowNum + 1) {

                                //Der Wert CellRaum wird so lange mit dem Wert des jeweiligen Raums aus der
                                //Spalte B befüllt bis...
                                val cellRaum: Cell? = rowContent.getCell(1)

                                //...die Zelle der jeweiligen Zeile der Spalte B wieder einen Wert hat, also ein anderer
                                //Raum nun in Spalte B steht (wird klar, wenn man sich die Excel anschaut)
                                if (cellRaum != null) {
                                    //Dann wird die variable raum mit diesem Wert als String befüllt
                                    raum = cellRaum.toString()

                                    //und der Liste in der Platz.kt hinzugefügt. Ebenso wird cellPlatzstring
                                    //der entsprechenden Zeile hinzugefügt
                                    platzList.add(Platz(raum, cellPlatzString, rowIndex))

                                    //Bevor die erste Forschleife dann in die nächste zeile springt,
                                    //wird der Wert raum genulled
                                    raum = null
                                    break
                                }
                            }
                        } else {
                            //Ist die Zelle der jeweiligen Reihe(Zeile) in Spalte B nicht null, so wird die Zelle mit
                            //dem jeweiligen raum befüllt, der in der Spalte B als letztes definiert wurde.
                            //Der Sinn dahinter ist die leeren zellen unter den Zellen die, den Raum angegeben
                            //mit der Information des Raumes zu füllen, solange bis eben der nächste Raum in Spalte B
                            // definiert wird.
                            platzList.add(Platz(raum, cellPlatzString, rowIndex))
                        }
                    }
                }
            }
        }

//Die Variable searchNum wird durch die Antwort des Gesprächspartners als Field "Patientennummer" definiert.
    var searchNum: String = Benutzer.get("Patientennummer").toString()

//Var Spalte wird initial gesetzt, sodass dieser Variable auf Basis des Wochentages überschrieben werden kann.
//Für den jeweiligen Wochentag ist dann die korrekte Spalte (als Index, also A=0, B=1 etc.) im Patientenplan hinterlegt
    var spalte: Int? = null

    //Die Variable Daten wird gesplittet in mehrere Werte mit dem
    // delimiter "," --> Der erste "Split" umfasst immer den Wochentag
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

// Die Variable Spalte überschreibt dann die Variable Col, die definiert in welcher Spalte der Excel die
// patientennummer gesucht wird.Die verscheidenden Spalten der Excel sind nach Wochentagen aufgeteilt
    var col = 3

    //Information: Wird foundrowindex nicht mit der zeile überschrieben in der die gesuchte Patientennummer gefunden
    //wurde, so existiert die gesuchte Patientennummer nicht im Patientenbelegungsplan. foundrowindex bleibt dann -1
    var foundrowIndex = -1

        //die Variablen patientNum, patientname und dialysebeginn1 ist ein nullable String und werden initial gesetzt
        //Durch den Split-operator hat die Variable dialysebeginn den Typ List<String>?
        var patientNum: String? = null
        var patientname: String? = null
        var dialysezeitraum: List<String>? = null
        var dialysebeginn: String? = null
        var dialyseende: String? = null

        //For Schleife itteriert durch alle zeilen der Excel
        for (rowIndex in 0 until sheet.lastRowNum + 1) {

            //Die Variable rowContent wird mit dem Inhalt der Zeile, in dem sich die For-Schleife befindet definiert
            val rowContent: Row? = sheet.getRow(rowIndex)

            //Ist der Zeileninhalt nicht null..
            if (rowContent != null) {

                //wird die Variable cellDay mit der vom Tag abhängigen Spalte der jeweiligen Zeile definiert
                val cellDay: Cell? = rowContent.getCell(col!!)

                //Ist der Inhalt des Values CellDay nicht null und enthält die jeweilige Zelle die Patientennummer,
                //wird die If-Bedingung erfüllt. Wird die If-Bedingung nicht erfüllt, so startet die For-Schleife erneut
                if(cellDay!=null && cellDay.toString().contains(searchNum)) {

                    //Der Wert temp stellt die bereits über die Patientennummer gematchte Zelle dar, die die relevanten
                    //Diaylseinformationen des Gesprächspartners enthält. Die einzelnen Informationen der Zelle werden
                    //durch ein Komma voneinander getrennt und können so mit dem Delimiter ',' einzeln
                    // verarbeitet werden. di[0]=Patientennummer; di[1]=Nachname, di[2]=Vorname; di[3]=Dialysezeitraum
                    val di = cellDay.toString().split(',')

                    //Die Patientennummer stellt die erste information der Zelle dar
                    patientNum = Integer.parseInt(di[0].trim()).toString()

                    //patientname stellt den Vor- und nachnamen des Patienten dar,
                    // temp[2] ist der Vorname, temp[1] der Nachname
                    patientname = di[2].trim() + " " + di[1].trim()

                    //Die Information der Terminierung des Dialysetermins stellt temp[3] in folgendem Format dar:
                    //Bsp. 10:30 - 12:30; Die beiden Uhrzeiten werden durch den Delimiter "-" voneinander getrennt
                    //So können die Informationen über den Dialysebeginn (dialysezeitraum[0])
                    //und das Dialyseende (dialysezeitraum[1]) getrennt voneinander verarbeitet werden
                    dialysezeitraum = di[3].trim().split('-')
                    dialysebeginn = dialysezeitraum[0].trim()
                    dialyseende = dialysezeitraum[1].trim()

                    //Stimmt die gesucht Pateintennummer(searchNum) mit der in der Excel gefundenen (patientNum)
                    //überein, so wird die If-Bedingung erfüllt und ausgelöst
                    if(searchNum == patientNum){
                        //der rowindex, der die Zeile darstellt, in der die gesuchte patientennummer gefunden wurde,
                        //wird dann überschrieben und ist somit nicht mehr -1
                        foundrowIndex = rowIndex
                        Benutzer!!.put("name", patientname)
                        Benutzer!!.put("dialysebeginn", dialysebeginn)
                        Benutzer!!.put("dialyseende", dialyseende)
                        //Excel durchsucht, Patnum rowrichtig gefunden, die den patient enthält oder nicht
                    }
                }
            }
        }





//Foundrowindex befüllt das field row. foundrowindex entspricht entweder -1 oder, falls die Patientennummer
// gefunden wurde der zeile in der die Patientennummer gefunden wurde
    Benutzer!!.put("row", foundrowIndex)

    //Auf die Konsole wird der gefundene Name sowie die gesuchte Patientennummer ausgegeben
    println("${Benutzer!!.get("name")} - ${Benutzer!!.get("Patientennummer")}")

    //Mit der For-Schleife wird durch die Zeilen der erzeugten platzlist iteriert
    for (platz in platzList) {

                //Entspricht foundrowindex -1, steht der Patient nicht auf dem Belegungsplan
                if (foundrowIndex == -1) {
                    println("Patient: $searchNum nicht gefunden")
                    break
                }

                //entspricht foundrowindex gleich einer zeile in der Platz.kt wird die If-Bedingung erfüllt
                if (platz.zeile == foundrowIndex) {
                    println("Patient: $searchNum")
                    //Aufgrund der If-Bedingung und der For-Schleife müssen nur noch die zu der Zeile gehörigen Platz-
                   //und Rauminformationen der Platz.kt ausgegeben werden und einer passenden Variable zugeordnet werden
                    var platzfurhat: String = platz.platz
                    var raumfurhat: String = platz.raum.toString().substring(startIndex = 10, endIndex = 18)

                    //die beiden Variablen überschreiben dann ein passendes field für den jeweiligen Nutzer
                    //Nun können die korrekten Platz- und Rauminformation über die beiden fields ausgegeben werden
                    Benutzer!!.put("raum", raumfurhat)
                    Benutzer!!.put("platz", platzfurhat)
                }
            }
        }




