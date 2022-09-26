package furhatos.app.demo02.flow.main

import Parent
import furhatos.flow.kotlin.*

val WennNameFalschTaxifahrer : State = state(Parent) {
    onEntry {//Mit der Funktion stelle Frage wird die Frage nach den Namen des Gesprächspartners
        //user kann nicht mehr null sein deswegen können wir schreiben user!!, da user bereits gesetzt
        //TODO hier reentry falls name falsch
        user!!.put("Patientennummer", null)
        GetDigitsTaxifahrer(user!!, this.furhat, "Patientennummer")
        goto(ValidierungNummerKunde)
    }
}