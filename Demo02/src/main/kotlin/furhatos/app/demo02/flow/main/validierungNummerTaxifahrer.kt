package furhatos.app.demo02.flow.main

import Ja
import ReadExcel2
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.gestures.Gestures
import furhatos.nlu.common.No

val ValidierungNummerKunde : State = state() {

    onEntry {
        // Mit dem Ausdruck ${user!!.get("fullname")} wird der user und das field fullname angesprochen
        println("Die Patientennummer Ihres Kunden ist ${user!!.get("Patientennummer")}")

        //Hier wird der Name des Kunden nochmal wörtlich gesagt und somit eine fehleingabe vermieden
        furhat.ask(
            "Die Patientennummer Ihres Kunden ist ${user!!.get("Patientennummer")}, stimmt das?"
        //, interruptable = true
        )
        furhat.gesture(Gestures.BigSmile)
    }

    onResponse<Ja> {
        //Nachdem der aktuelle user geantwortet hat, ist der Ausdruck it mit seiner userid gesetzt
        //Die Frage ist, ist der User der geantwortet hat, immer noch derselbe wie der dessen Namen gesetzt wurde
        // Ist dies der Fall geht es weiter mit der Interaktion, ist dies nicht der Fall, geht es nochmal von
        // vorne los und der neue User wird nach den namen und seinem Anliegen gefragt
        if (user!!.id == it.userId) {
            ReadExcel2(user!!)

            if (user!!.get("row") == -1) {
                furhat.say(
                    "Ihr Kunde steht leider nicht auf dem Belegungsplan welcher mir vorliegt, bitte fragen Sie an der Rezeption nach. Ich wünsche Ihnen einen schönen Tag"
                )
                goto(Idle)
            }
        }
        else {
            userwechsel(this, it.userId)
        }
        goto(Taxidriverdialogue01)
    }
    onResponse<No> {
        goto(WennNameFalschTaxifahrer)
    }
}