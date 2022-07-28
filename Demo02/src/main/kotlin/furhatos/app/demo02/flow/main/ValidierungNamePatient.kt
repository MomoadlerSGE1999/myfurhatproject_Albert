package furhatos.app.demo02.flow.main

import Ja
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val ValidierungNamePatient : State = state() {

    onEntry {
        // Mit dem Ausdruck ${user!!.get("fullname")} wird der user und das field fullname angesprochen
        println("Ihr Name ist ${user!!.get("vorname")} ${user!!.get("name")}")

        //Hier wird der Name des Kunden nochmal wörtlich gesagt und somit eine fehleingabe vermieden
        furhat.ask(
            "Also ist Ihr Name: ${user!!.get("vorname")} ${user!!.get("name")}, stimmt das?"
        )
        furhat.gesture(Gestures.BigSmile)
    }

        onResponse<Ja> {
            //Nachdem der aktuelle user geantwortet hat, ist der Ausdruck it mit seiner userid gesetzt
            //Die Frage ist, ist der User der geantwortet hat, immer noch derselbe wie der dessen Namen gesetzt wurde
            // Ist dies der Fall geht es weiter mit der Interaktion, ist dies nicht der Fall, geht es nochmal von
            // vorne los und der neue User wird nach den namen und seinem Anliegen gefragt
            if (user!!.id == it.userId) {
                goto(Patientdialogue)
            }
            else {
                userwechsel(this, it.userId)
            }
            goto(Patientdialogue)
        }

        onResponse<No> {
            goto(Greeting)
        }
    }
