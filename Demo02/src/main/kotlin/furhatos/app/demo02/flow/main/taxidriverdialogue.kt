package furhatos.app.demo02.flow.main


import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes


val Taxidriverdialogue : State = state() {

    onEntry {
        furhat.ask (
            "Sind Sie hier um einen Kunden abzuholen?"
        )
    }
    onResponse<Yes> {
        if (user == null){      //User wird gesetzt
            user = users.getUser(it.userId)
        }
//Mit der Funktion stelle Frage wird die Frage nach den Namen des Gesprächspartners
        stellefrage(user!!, this.furhat, "Bitte geben Sie den Vornamen des Kunden ein", "vorname")
        stellefrage(user!!, this.furhat, "Bitte geben Sie den Nachnamen des Kunden ein", "name")

// der Name des Users wird mit den Eingabewerten des fields vorname sowie name gesetzt, der value beschreibt die Zusammensetzung des fields fullname
        user!!.put("fullname", "${user!!.get("name")}, ${user!!.get("vorname")}")

        goto(Taxidriverdialogue01)
    }

    onResponse<No> {
        furhat.say("Okay, melden sie sich bitte an unserer Rezeption an. Es war mir eine Ehre mit Ihnen zu reden.")
        furhat.gesture(Gestures.BigSmile(1.0, 2.0))
    }

}






