package furhatos.app.demo02.flow.main

import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.User
import java.sql.Timestamp

//variable user muss gesetzt werden
var user: User? = null

val Greeting : State = state() {

    onEntry {
        furhat.ask("Hallo, ich bin furhat, der neue Serviceroboter hier im Dialysezentrum ${furhat.voice.pause("70ms")}, bist du Taxifahrer beziehungsweise Patient in diesem Dialysezentrum?"
        )
    }

    onResponse<Yes> {
        furhat.say{+"Okay, dann kann ich dir bei der Orientierung in diesem Dialysezentrum helfen."
            +Gestures.Smile}
//Der Gesprächspartner wird nach der initialen setzung einer userID von Furhat, also nach der ersten Frage durch User definiert
        //Der Parameter user kann nun genutzt werden um den Gesprächspartner in Codelogik einzubauen, zb von der Funktion stellefrage
        user = users.getUser(it.userId)
//Mit der Funktion stelle Frage wird die Frage nach den Namen des Gesprächspartners
        //user kann nicht mehr null sein deswegen können wir schreiben user!!, da user bereits gesetzt
        //TODO hier reentry falls name falsch
        stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Vornamen ein", "vorname")
        stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Nachnamen ein", "name")

// der Name des Users wird mit den Eingabewerten des fields vorname sowie name gesetzt, der value beschreibt die Zusammensetzung des fields fullname
        user!!.put("fullname", "${user!!.get("vorname")} ${user!!.get("name")}")

// Mit dem Ausdruck ${user!!.get("fullname")} wird der user und das field fullname angesprochen
        println("Der Name ist ${user!!.get("fullname")}")

        //Hier wird der Name des Kunden nochmal wörtlich gesagt
        furhat.say("der Name ist also: ${user!!.get("fullname")}")
        goto(Dialogue02) //Muss wieder geändert werden

        onResponse<Yes> {
            goto(Dialogue02)
        }
        onResponse<No> {
        reentry()
        }
        //TODO wenn der name richtig ist weiter ansonsten reentry, weil name falsch

    }
    onResponse<No> {
        furhat.say("Okay, hoffentlich sehen wir uns mal wieder. Bitte registrieren Sie sich an der Rezeption")
        furhat.attend(locationa)
    }

}
