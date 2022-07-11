package furhatos.app.demo02.flow.main

import Patient
import Taxidriver
import bsh.This
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.User
import kotlinx.coroutines.withTimeout
import kotlin.concurrent.timer

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
        user!!.put("fullname", "${user!!.get("vorname")} ${user!!.get("name")}")

//Ausgegeben wird dem Gesprächspartner der Name des Kunden, so kann der Eingabewert überprüft werden
        println("Der Name Ihres Kunden ist ${user!!.get("fullname")}")
//Der Name wird sprachlich wiederholt
        furhat.ask("Also ist der Name Ihres Kunden: ${user!!.get("fullname")}?", 7000)
        //Nun wird der State Taxidriverdialogue01 zur Validierung gecalled
        goto(Taxidriverdialogue01)
    }
    onResponse<No> {
        furhat.say("Okay, melden sie sich bitte an unserer Rezeption an. Es war mir eine Ehre mit Ihnen zu reden.")
        furhat.gesture(Gestures.BigSmile(1.0, 2.0))
    }

}






