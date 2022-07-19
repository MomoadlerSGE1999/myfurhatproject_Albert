package furhatos.app.demo02.flow.main

import Patient
import Taxidriver
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location
import furhatos.records.User

var user: User? = null
val locationa= Location (3.0, 0.0, 2.0)
val Greeting : State = state() {

    onEntry {
        furhat.attend(users.current)
        furhat.ask("Hallo, ich bin Hannah, der neue Serviceroboter hier in unserem Dialysezentrum. Können Sie mir sagen, ob sie ein " +
                "${furhat.voice.pause("200")} Taxifahrer oder ein ${furhat.voice.pause("200")} Dialysepatient in diesem Dialysezentrum sind?"
        )
    }

    onResponse<Patient> {
        furhat.say {
            +"Okay, dann kann ich Ihnen weiterhelfen."
            furhat.gesture(Gestures.BigSmile)
        }
        //Der Gesprächspartner wird nach der initialen setzung einer userID von Furhat, also nach der ersten Frage durch User definiert
        //Der Parameter user kann nun genutzt werden um den Gesprächspartner in Codelogik einzubauen, zb von der Funktion stellefrage
        user = users.getUser(it.userId)
        furhat.attend(it.userId)
        //Mit der Funktion stelle Frage wird die Frage nach den Namen des Gesprächspartners
        //user kann nicht mehr null sein deswegen können wir schreiben user!!, da user bereits gesetzt
        //TODO hier reentry falls name falsch
        stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Vornamen ein", "vorname")
        stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Nachnamen ein", "name")

        // der Name des Users wird mit den Eingabewerten des fields vorname sowie name gesetzt, der value beschreibt die Zusammensetzung des fields fullname
        user!!.put("fullname", "${user!!.get("name")}, ${user!!.get("vorname")}")
        //TODO wenn der name richtig ist weiter ansonsten reentry, weil name falsch
    goto(ValidierungNamePatient)

    }
    onResponse<Taxidriver> {
        furhat.say {
            +"Okay, dann kann ich Ihnen weiterhelfen."
            furhat.gesture(Gestures.BigSmile)
        }
        //Der Gesprächspartner wird nach der initialen setzung einer userID von Furhat, also nach der ersten Frage durch User definiert
        //Der Parameter user kann nun genutzt werden um den Gesprächspartner in Codelogik einzubauen, zb von der Funktion stellefrage
        user = users.getUser(it.userId)
        furhat.attend(it.userId)
        //Mit der Funktion stelle Frage wird die Frage nach den Namen des Gesprächspartners
        //user kann nicht mehr null sein deswegen können wir schreiben user!!, da user bereits gesetzt
        //TODO hier reentry falls name falsch
        stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Vornamen ein", "vorname")
        stellefrage(user!!, this.furhat, "Bitte geben Sie Ihren Nachnamen ein", "name")

        // der Name des Users wird mit den Eingabewerten des fields vorname sowie name gesetzt, der value beschreibt die Zusammensetzung des fields fullname
        user!!.put("fullname", "${user!!.get("name")}, ${user!!.get("vorname")}")
        //TODO wenn der name richtig ist weiter ansonsten reentry, weil name falsch
        goto(Taxidriverdialogue)
    }
    onResponse<No> {
        furhat.say("Okay, hoffentlich sehen wir uns mal wieder. Bitte registrieren Sie sich an der Rezeption, dazu gehen sie bitte direkt links")
        //Der Kopf soll richtung Rezeption schauen, locationa soll die Rezeption sein

        furhat.attend(locationa)
        //10 Sekunden warten, dann geht Furhat zum State IDle um mit dem nächsten zu sprechen
        delay(10000)
        goto(Idle)
    }

}
