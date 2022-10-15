/*


import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.DontKnow
import furhatos.nlu.common.Number

/*
val NummerErfragen : State = state() {
    onEntry {
        furhat.run {
            setVoice(Language.GERMAN, Gender.FEMALE, false)
            setInputLanguage(Language.GERMAN)
        }
furhat.askFor<Number>(
    "Können Sie mir bitte Ihre Patientennummer sagen?"
)
        onResponse<Number> {
            furhat.say("${it.intent}")
        }
    }
}


 */
val NummerErfragen = state {
    onEntry {
        var Patientennummer = furhat.askFor<Number>("Was ist ihre Patientennummer") {
            onResponse<DontKnow> {
                furhat.say("Das sollten sie wissen")
                reentry()
            }
        }
        furhat.say("Ihre Patientennummer ist ${Patientennummer}")
        // TODO Test: Patientennummer muss fünfstellig sein und darf nur aus Zahlen bestehen
    }
}


/*val NummerErfragen : State = state() {
    onEntry {
        furhat.run {
            setVoice(Language.GERMAN, Gender.FEMALE, false)
            setInputLanguage(Language.GERMAN)

            var Code = furhat.askFor<furhatos.nlu.common.Number>("Erste Zahl der Patientennummer, bitte")
            furhat.say("$Code")
            var Code2 = furhat.askFor<furhatos.nlu.common.Number>("Zweite Zahl der Patientennummer, bitte")
            furhat.say("$Code2")
            var Code3 = furhat.askFor<furhatos.nlu.common.Number>("Dritte Zahl der Patientennummer, bitte")
            furhat.say("$Code3")
            var Code4 = furhat.askFor<furhatos.nlu.common.Number>("Vierte Zahl der Patientennummer, bitte")
            furhat.say("$Code4")
            var Code5 = furhat.askFor<furhatos.nlu.common.Number>("Letzte Zahl der Patientennummer, bitte")
            furhat.say("$Code5")
        var Patientennummer2: String = Code.toString()+Code2.toString()+Code3.toString()+Code4.toString()+Code5.toString()
            var PatientennummerInt: Int = Patientennummer2.toInt()


            furhat.say("$Code")
        }

    }
}

 */

/*val NummerErfragen : State = state() {

    onEntry {
        furhat.run {
            setVoice(Language.GERMAN, Gender.FEMALE, false)
            setInputLanguage(Language.GERMAN)
    }
        /*furhat.say(
            "Können Sie mir bitte langsam und verständlich Ihre Patientennummer sagen? Ich wiederhole "
        )

         */
        furhat.listen(timeout = 10000, endSil = 1800)
    }
    onResponse<Patientennummer> {
        furhat.say("${it.intent.Patientencodenummer}, alles klar")
    }


 */




 */

/*fun stellefrage(user: User, furhat: Furhat, question: String, field: String) {
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

 */

//Fallunterscheidung wenn user wechselt, in else wird das szenario des Userwechsels abgedeckt und der State Greeting gecalled
/*
fun userwechsel (flow: FlowControlRunner, userid: String){
//Dem User der auf keinen Fall den Wert Null hat, wird ein Feld gelabelt mit dem Wert Dialogue02, somit ist Status der einzelnen userID bekannt (wurde dem User schon eine Patientennummer zugeordnet?
    Benutzer!!.put("Dialogue", "Dialogue02")
    //Hier wird eine neue Userid gesetzt
    Benutzer=flow.users.getUser(userid)
    // Wenn das field Dialogue den Wert Null hat, gehe zu Greeting und starte einen neuen Lauf, um auch den namen des neuen users zu haben
    if (Benutzer!!.get("Dialogue")==null) {
        flow.goto(Greeting)
    }
    //Trifft nichts davon ein, zb durch einen unerwarteten Fehler, so wird der Skill von vorne mit dem ersten call des States gestartet
    else {
        flow.goto(Greeting)
        //TODO Auswertung von Field dialogue als Einstiegspunkt in den Dialog
    }
}

 */

//Spalte col = tag
/* fun suchePatient(sheet: Sheet, col: Int, searchNum: String): Int {
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

 */

