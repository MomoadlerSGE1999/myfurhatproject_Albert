package furhatos.app.demo02.flow.main

import FrageWiederholen
import Ja
import Parent
import WelcherPlatzRaum
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.event.responses.ResponseFace
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import nlu.Nein
import javax.xml.stream.events.Characters

val Taxidriverdialogue01 : State = state(Parent){
    onEntry {
        var raumx: Any? = user!!.get("raum")
        var platzy: Any? = user!!.get("platz")
        furhat.say(" Lieber ${user!!.get("VornameGesprächspartner")}, Ich würde Sie bitten ${user!!.get("name")} im $raumx am $platzy abzuholen")
        furhat.say("Es war mir eine Freude Ihnen zu helfen")
        furhat.ledStrip.solid(java.awt.Color.GREEN)
        furhat.gesture(Gestures.Nod())
        furhat.gesture(Gestures.BigSmile)
        furhat.attendNobody()
        furhat.listen(timeout = 10000)
    }
    onResponse<FrageWiederholen> {
        furhat.attend(it.userId)
        reentry()
    }

    onResponse<WelcherPlatzRaum> {
        furhat.attend(it.userId)
        reentry()
    }
    onNoResponse {
        delay(6000)
        furhat.attendNobody()
        goto(Idle)
    }
    onReentry {
        var raumy: Any? = user!!.get("raum")
        var platzx: Any? = user!!.get("platz")
        furhat.say(" In den $raumy, an den $platzx")
        furhat.attendNobody()
        goto(Idle)
    }
}