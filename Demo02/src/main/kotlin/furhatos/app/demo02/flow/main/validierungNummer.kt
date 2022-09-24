package furhatos.app.demo02.flow.main

import FrageWiederholen
import Ja
import ReadExcel2
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import nlu.Nein

val ValidierungNummerPatient : State = state() {

    onEntry {
        // Mit dem Ausdruck ${user!!.get("fullname")} wird der user und das field fullname angesprochen
        println("Ihre Patientennummer ist ${user!!.get("Patientennummer")}")

        //Hier wird der Name des Kunden nochmal wörtlich gesagt und somit eine fehleingabe vermieden
        furhat.ask() {
            +"Die Patientennummer ist"
            +voice.sayAs("${user!!.get("Patientennummer")}", Voice.SayAsType.DIGITS)
            +", stimmt das?"
            //,interruptable = true
        }
        furhat.gesture(Gestures.BigSmile)
    }

        onResponse<Ja> {
            furhat.attend(it.userId)
            //Nachdem der aktuelle user geantwortet hat, ist der Ausdruck it mit seiner userid gesetzt
            //Die Frage ist, ist der User der geantwortet hat, immer noch derselbe wie der dessen Namen gesetzt wurde
            // Ist dies der Fall geht es weiter mit der Interaktion, ist dies nicht der Fall, geht es nochmal von
            // vorne los und der neue User wird nach den namen und seinem Anliegen gefragt
            if (user!!.id == it.userId) {
                ReadExcel2(user!!)
                if (user!!.get("row") == -1) {
                    furhat.say(
                        "Sie stehen leider nicht auf dem Belegungsplan welcher mir vorliegt, bitte fragen Sie an der Rezeption nach. Ich wünsche Ihnen einen schönen Tag"
                    )
                    goto(Idle)
                }
                goto(Patientdialogue)
            }
            else {
                userwechsel(this, it.userId)
            }

        }

        onResponse<Nein> {
            furhat.attend(it.userId)
            goto(WennNummerFalschPatient)
        }
    onResponse<FrageWiederholen> {
        furhat.attend(it.userId)
        reentry()
    }
    }
