package furhatos.app.demo02.flow.main

import FrageWiederholen
import Ja
import ReadExcel
import furhatos.app.demo02.flow.Parent
import furhatos.app.demo02.flow.main.general.Idle
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import nlu.Nein

val ValidierungNummerPatient : State = state(Parent) {

    onEntry {
        furhat.attend(users.current)

        //Hier fragt Furhat den gesprächspartner, ob seine verstandene Patientennummer korrekt ist
        furhat.ask {
            +"Die Patientennummer ist"
            //Furhat spricht dabei die Patientennummer als 5 separate Ziffern
            +voice.sayAs("${Benutzer!!.get("Patientennummer")}", Voice.SayAsType.DIGITS)
            +", stimmt das?"
            furhat.gesture(Gestures.BigSmile)
        }

    }

        onResponse<Ja> {
            //Der User, der antwortet, kann mit der Variable it angesprochen werden
            furhat.attend(it.userId)

            //Die Funktion ReadExcel wird aufgerufen und auf den Benutzer angewendet
            ReadExcel(Benutzer!!)

            //Hat der Benutzer keinen termin, so wird das field "Row" des Benutzers mit -1 beschrieben
            //sollte der Benutzer keinen Termin haben wird er darüber informiert, die Interaktion ist dann zu Ende
            if (Benutzer!!.get("row") == -1) {
                furhat.say(
                    "Sie stehen leider nicht auf dem Belegungsplan welcher mir vorliegt, bitte fragen Sie am" +
                            " Empfang nach. Ich wünsche Ihnen einen schönen Tag"
                )
                goto(Idle)
            }
            //Ist das field "row" nicht -1, so läuft die interaktion weiter mit dem State Patientdialogue
            else goto(Patientdialogue)
        }

        onResponse<Nein> {
            furhat.attend(it.userId)
            //Ist die Patientennummer falsch, so wird im State WennNummerFalsch das field patientennummer genullt und
            // Furhat fragt erneut nach der Patientennummer
            goto(WennNummerFalschPatient)
        }
        onResponse<FrageWiederholen> {
        furhat.attend(it.userId)
        reentry()
        }
    }
