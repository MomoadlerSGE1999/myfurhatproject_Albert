package furhatos.app.demo02.flow.main

import AngehörigeUndTaxifahrer
import FrageWiederholen
import Ja
import Patient
import Taxidriver
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.Greeting
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location
import furhatos.records.User
import furhatos.nlu.common.Number
import nlu.Nein

var user: User? = null
val locationa= Location (3.0, 0.0, 2.0)
val Greeting : State = state() {

    onEntry {
        furhat.attend(users.current)

        furhat.ask (timeout = 20000) {
            +"Hallo, ich bin Carla, der neue Serviceroboter hier in unserem Dialysezentrum. Sind sie Dialysepatient in diesem Dialysezentrum?"
            +blocking {
                furhat.gesture(Gestures.BrowRaise, async = false)
            }
        }
    }
    onResponse<Ja> {
        furhat.say {
            +"Gut, dann kann ich ${furhat.voice.emphasis("Ihnen")} weiterhelfen."
            +blocking {
                furhat.gesture(Gestures.BigSmile, async = false)
            }
        }
        //Der Gesprächspartner wird nach der initialen setzung einer userID von Furhat, also nach der ersten Frage durch User definiert
        //Der Parameter user kann nun genutzt werden um den Gesprächspartner in Codelogik einzubauen, zb von der Funktion stellefrage
        user = users.getUser(it.userId)
        furhat.attend(it.userId)
        //Mit der Funktion stelle Frage wird die Frage nach den Namen des Gesprächspartners
        //user kann nicht mehr null sein deswegen können wir schreiben user!!, da user bereits gesetzt
        //TODO hier reentry falls name falsch
        GetDigitsPatient(user!!, this.furhat, "Patientennummer")
        //TODO wenn der name richtig ist weiter ansonsten reentry, weil name falsch
        goto(ValidierungNummerPatient)

    }
    onResponse<Nein> {
        goto(AngehörigeUndTaxifahrer)
    }
    onResponse<FrageWiederholen> {
        reentry()
    }
}




