package furhatos.app.demo02.flow.main

import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val ValidierungName : State = state() {

    onEntry {
        // Mit dem Ausdruck ${user!!.get("fullname")} wird der user und das field fullname angesprochen
        println("Der Name Ihres Kunden ist ${user!!.get("fullname")}")

        //Hier wird der Name des Kunden nochmal wörtlich gesagt und somit eine fehleingabe vermieden
        furhat.ask("Also ist Ihr Name: ${user!!.get("fullname")}?")
    }

        onResponse<Yes> {
            goto(Dialogue02)
        }
        onResponse<No> {
            goto(Greeting)
        }
    }
