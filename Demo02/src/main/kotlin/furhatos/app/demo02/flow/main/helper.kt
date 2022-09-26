package furhatos.app.demo02.flow.main

import FrageWiederholen
import furhatos.flow.kotlin.*
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.Number
import furhatos.records.User
import org.apache.commons.lang3.StringUtils
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet



fun stellefrage(user: User, furhat: Furhat, question: String, field: String) {
//Furhat sagt die eingegebene Frage als Value mit dem field question,
        furhat.say(question)
        // furhat.attend(locationb) // Timer einfügen, da er nur für eine gewisse Zeit zur Tastatur schauen soll

        //delay(3000)
//er schaut den user an, der geantwortet hat und für den der name durch diese Funktion hinterlegt werden wird
        furhat.attend(user.id)
    var input: String? = readLine()
//Ist der Input nicht null
        if (input!=null) {
//So wird field mit der variable input gefüllt
            user.put(field, input)
        }
        else {
            user.put(field, "")
        }

    }
//Fallunterscheidung wenn user wechselt, in else wird das szenario des Userwechsels abgedeckt und der State Greeting gecalled

fun userwechsel (flow: FlowControlRunner, userid: String){
//Dem User der auf keinen Fall den Wert Null hat, wird ein Feld gelabelt mit dem Namen Dialogue02, somit ist Status der einzelnen userID bekannt (Wurde ine Name schon vergeben?,
// ist der State Dialogue02 schon durchlaufen worden? -->Status,
    user!!.put("Dialogue", "Dialogue02")
    //Hier wird eine neue Userid gesetzt
    user=flow.users.getUser(userid)
    // Wenn das field Dialogue den Wert Null hat, gehe zu Greeting und starte einen neuen Lauf, um auch den namen des neuen users zu haben
    if (user!!.get("Dialogue")==null) {
        flow.goto(Greeting)
    }
    //Trifft nichts davon ein, zb durch einen unerwarteten Fehler, so wird der Skill von vorne mit dem ersten call des States gestartet
    else {
        flow.goto(Greeting)
        //TODO Auswertung von Field dialogue als Einstiegspunkt in den Dialog
    }
}

//Spalte col = tag
fun suchePatient(sheet: Sheet, col: Int, searchNum: String): Int {
    //die Variable PatientName ist ein nullable String, hier beträgt der noch nicht überschriebene Wert null
    var patientNum: String? = null
    var patientname: String? = ""
    //For Schleife, um die Zeilen auszugeben
    for (rowIndex in 0 until sheet.lastRowNum + 1) {
        val rowContent: Row? = sheet.getRow(rowIndex)
//Wenn der Zeileninhalt nicht null ist, dann erzeuge einen Value in Form einer Zelle mit dem Inhalt der Zelle in Zeile X und Spalte (col)
        if (rowContent != null) {
            val cellDay: Cell? = rowContent.getCell(col)
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
                            //!= heißt das etwas nicht übereinstimmt
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
                    println("$patientname $patientNum")
                    return rowIndex
                }
            }
        }
    }
    return -1
}

fun GetDigitsPatient (user: User, furhat: Furhat, field: String) {
//Furhat sagt die eingegebene Frage als Value mit dem field question,

    //delay(3000)
//er schaut den user an, der geantwortet hat und für den der name durch diese Funktion hinterlegt werden wird


    var Patientennummer: Number? = furhat.askFor<furhatos.nlu.common.Number>("Wie lautet ihre Patientennummer", timeout = 20000, endSil = 4000) {
        onResponse<DontKnow> {
            furhat.say("Das sollten sie wissen")
            reentry()
        }
        onResponse<furhatos.nlu.common.Number> {
            var x: String = it.text.toString().replace(" ".toRegex(), "")
            var resultx: String = x.filter { it.isDigit() }
            user.put("Patientennummer", resultx)
            goto(ValidierungNummerPatient)
        }
        onResponse<FrageWiederholen> {
            reentry()
        }
    }
}
   // furhat.say("Ihre Patientennummer ist ${Patientennummer}")


/*
//Ist der Input nicht null
   if (Patientennummer!=null) {
//So wird das field mit der variable Patientennummer gefüllt
        user.put("Patientennummer", Patientennummer)
   }
   else {
       user.put("Patientennummer", "")
   }

}

 */
fun GetDigitsTaxifahrer(user: User, furhat: Furhat, field: String) {
//Furhat sagt die eingegebene Frage als Value mit dem field question,

    //delay(3000)
//er schaut den user an, der geantwortet hat und für den der name durch diese Funktion hinterlegt werden wird

    var Patientennummer: Number? = furhat.askFor<furhatos.nlu.common.Number>("Was ist die Patientennummer ihres Kunden beziehungsweise Angehörigen", timeout = 20000, endSil = 4000) {
        onResponse<DontKnow> {
            furhat.say("Das sollten sie wissen")
            reentry()
        }
        onResponse<furhatos.nlu.common.Number> {
            var x: String = it.text.toString().replace(" ".toRegex(), "")
            var resultx: String = x.filter { it.isDigit() }
            var resulty: String = resultx.substring(startIndex = 0, endIndex = 5)
            user.put("Patientennummer", resulty)
            goto(ValidierungNummerKunde)
        }
    }
}
// furhat.say("Ihre Patientennummer ist ${Patientennummer}")


/*
//Ist der Input nicht null
   if (Patientennummer!=null) {
//So wird das field mit der variable Patientennummer gefüllt
        user.put("Patientennummer", Patientennummer)
   }
   else {
       user.put("Patientennummer", "")
   }

}

 */



