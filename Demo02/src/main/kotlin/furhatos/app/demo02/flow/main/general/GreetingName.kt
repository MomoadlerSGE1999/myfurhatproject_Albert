import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.demo02.flow.Parent
import furhatos.app.demo02.flow.main.Greeting

import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.AskName
import furhatos.nlu.common.PersonName
import furhatos.records.User
import nlu.Nein

//Die Variabel "Benutzer" wird initial gesetzt
var Benutzer: User? = null

val Greetingname : State = state(Parent) {

    onEntry {
        furhat.attendClosestUser()
        //Furhat stellt sich vor
        furhat.say ("Hallo mein name ist James, ich bin der neue Serviceroboter in diesem Dialysezentrum ${furhat.voice.pause("700ms")}.")

        //Furhat fragt nach dem Namen seines Gegenübers
        furhat.ask("Wie ist Ihr Name?")
        //Im Anschluss an seine Frage werden beide Gestures ausgelöst
        furhat.gesture(Gestures.Thoughtful)
        furhat.gesture(Gestures.BrowRaise)
    }

    //Antwortet der User auf die Frage mit seinem Namen wird, OnResponse<PersonName> getriggert
    onResponse<PersonName> {
       //Furhat schaut den user an, der ihm geantwortet hat
        furhat.attend(it.userId)
        //Die Variabel NameDerPerson wird gesetzt, um später mit dieser Variabel weiterarbeiten zu können
        val NameDerPerson = it.intent
        //Um dem User einen Wert zuzuweisen, wird dieser gesetzt, der Benutzer ist immer der nutzer, der geantwortet hat
        Benutzer = users.current
        //Nun wird dem User, der gleichzeitig auch der Benutzer ist, ein Field namens VornameGesprächspartner zugewiesen
        //Dieses Field stellt den Vornamen des Users dar, Furhat kann den User, solange die Interaktion läuft mit seinem vornamen ansprechen
        Benutzer!!.put("VornameGesprächspartner", NameDerPerson)
        furhat.say{
            //random {} sorgt dafür, dass ein zufälliger Satz als Kommentar zum Namen des users gewählt wird
            random {
                +"$NameDerPerson ${furhat.voice.pause("700ms")}, ein schöner Vorname"
                +"$NameDerPerson ${furhat.voice.pause("700ms")}, ein wunderbarer Vorname"
                +"$NameDerPerson ${furhat.voice.pause("700ms")}, so hieß mal ein entfernter Verwandter von mir"
                +"Okay $NameDerPerson ${furhat.voice.pause("700ms")}, lassen Sie mich Ihnen sagen wo Sie heute hin müssen"
                +"Okay $NameDerPerson ${furhat.voice.pause("700ms")}, lassen Sie mich Ihnen helfen"
                +"Freut mich Sie kennenzulernen $NameDerPerson"
            }
            furhat.gesture(Gestures.Smile)
            furhat.gesture((Gestures.Nod))
        }

        goto(Greeting)
    }
    onResponse<Nein> {
        furhat.attend(it.userId)
        furhat.say("Das müssen Sie auch nicht, ich würde Ihnen trotzdem gerne helfen")
        furhat.gesture(Gestures.Smile)
        goto(Greeting)
    }
    onNoResponse {
        furhat.attend(it.userId)
        furhat.say("Wir machen einfach mal weiter")
        furhat.gesture(Gestures.Smile)
        goto(Greeting)
    }
    onResponse {
        furhat.attend(it.userId)
        furhat.say("Alles klar, wir machen einfach mal weiter")
        furhat.gesture(Gestures.Smile(duration = 2.0, strength = 2.0))
        goto(Greeting)
    }
    onResponseFailed {
        reentry()
    }

//der Trigger onReentry wird ausgelöst, wenn ein State durch den befehl reentry() neu ausgelöst wird,
//definiert man onReentry wird nur der Teil des Codes ausgelöst, der im onReentry Befehl steht
//in diesem Fall ist der Code im onReentry-Trigger nur marginal abweichend vom eigentlich State
onReentry {
    furhat.askFor<AskName>("Wie ist Ihr Name?")

    furhat.gesture(Gestures.Thoughtful)
    furhat.gesture(Gestures.BrowRaise)


onResponse<PersonName> {
    furhat.attend(it.userId)
    val NameDerPerson = it.intent
    Benutzer = users.getUser(it.userId)
    Benutzer!!.put("VornameGesprächspartner", NameDerPerson)
    furhat.say({
        random {
            +"$NameDerPerson ${furhat.voice.pause("700ms")}, ein schöner Vorname"
            +"$NameDerPerson ${furhat.voice.pause("700ms")}, ein wunderbarer Vorname"
            +"$NameDerPerson ${furhat.voice.pause("700ms")}, so hieß mal ein entfernter Verwandter von mir"
            +"Okay $NameDerPerson ${furhat.voice.pause("700ms")}, lassen Sie mich Ihnen sagen wo Sie heute hin müssen"
            +"Freut mich Sie kennenzulernen $NameDerPerson"
        }
        furhat.gesture(Gestures.Smile)
        furhat.gesture((Gestures.Nod))
    }
    )
    goto(Greeting)
}
onResponse<Nein> {
    furhat.attend(it.userId)
    furhat.say("Das müssen Sie auch nicht, ich würde Ihnen trotzdem gerne helfen")
    furhat.gesture(Gestures.Smile)
    goto(Greeting)
}
onNoResponse {
    furhat.attend(it.userId)
    furhat.say("Wir machen einfach mal weiter")
    furhat.gesture(Gestures.Smile)
    goto(Greeting)
}
onResponse {
    furhat.attend(it.userId)
    furhat.say("Alles klar, wir machen einfach mal weiter")
    furhat.gesture(Gestures.Smile(duration = 2.0, strength = 2.0))
    goto(Greeting)
}
onResponseFailed {
    reentry()
}

}
}



