package furhatos.app.demo02.flow.main

import FrageWiederholen
import Ja
import WelcherPlatzRaum
import furhatos.app.demo02.flow.Parent
import furhatos.app.demo02.flow.main.general.Idle
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import nlu.Danke
import nlu.Nein

//Vergleiche Patientdialogue
val Taxidriverdialogue01 : State = state(Parent){
    onEntry {
        var raumx: Any? = Benutzer!!.get("raum")
        var platzy: Any? = Benutzer!!.get("platz")
        var dialyseende: Any? = furhat.voice.sayAs(Benutzer!!.get("dialyseende").toString(), Voice.SayAsType.TIME)
        furhat.say("${Benutzer!!.get("VornameGesprächspartner")}, Ich würde Sie bitten ihren Kunden ${Benutzer!!.get("name")} " +
                "um $dialyseende im ${furhat.voice.emphasis("$raumx")} am ${furhat.voice.emphasis("$platzy")} abzuholen")
        furhat.say("ich wünsche Ihnen einen schönen Tag")
        furhat.ledStrip.solid(java.awt.Color.GREEN)
        furhat.gesture(Gestures.Nod())
        furhat.gesture(Gestures.BigSmile)
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
    onResponse<Danke> {
        furhat.say("Ich danke ebenfalls, es war mir eine Freude")
        delay(4000)
        goto(Idle)
    }
    onResponse<Ja> {
        goto(Idle)
    }
    onResponse<Nein> {
        goto(Idle)
    }
    onReentry {
        var raumy: Any? = Benutzer!!.get("raum")
        var platzx: Any? = Benutzer!!.get("platz")
        furhat.say(" In den $raumy, an den $platzx")
        goto(Idle)
    }
}