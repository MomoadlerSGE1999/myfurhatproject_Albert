package furhatos.app.demo02.flow.main

import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.*

val WennNummerFalschPatient : State = state(Parent) {
    onEntry {
        //Bevor das field Patientennummer neu überschrieben wird, wird es genullt
        Benutzer!!.put("Patientennummer", null)
        GetDigitsPatientnochmal(Benutzer!!, this.furhat, "Patientennummer")
        goto(ValidierungNummerPatient)
    }
}
