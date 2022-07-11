package furhatos.app.demo02.flow.main

import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.users
import furhatos.records.User


    fun stellefrage(user: User, furhat: Furhat, question: String, field: String) {
//Er sagt eingegebene Frage als Value mit dem field question,
        furhat.say(question)
        // furhat.attend(locationb) // Timer einfügen, da er nur für eine gewisse Zeit zur Tastatur schauen soll

        //delay(3000)
//er schaut den user an für den er den namen hinterlegt hat
        furhat.attend(user.id)


        val input = readLine()
        if (input!=null) {

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
    //Hier wird eine Userid gesetzt
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
