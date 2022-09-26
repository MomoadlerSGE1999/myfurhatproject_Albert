import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.demo02.flow.main.Greeting
import furhatos.app.demo02.flow.main.user
import furhatos.flow.kotlin.*
import furhatos.gestures.Gesture
import furhatos.gestures.Gestures
import furhatos.nlu.common.AskName
import furhatos.nlu.common.PersonName
import furhatos.nlu.common.Yes
import nlu.Nein


val Greetingname : State = state(Parent) {

    onEntry {
        furhat.attend(users.current)

        furhat.say() {
            +"Hallo mein name ist Vicki, ich bin der neue Serviceroboter in diesem Dialysezentrum ${furhat.voice.pause("700ms")}."
        }
        furhat.askFor<AskName>("Wie ist Ihr Name?")

        furhat.gesture(Gestures.Thoughtful)
        furhat.gesture(Gestures.BrowRaise)
    }

    onResponse<PersonName> {
       furhat.attend(it.userId)
        val NameDerPerson = it.intent
        user = users.current
        user!!.put("VornameGesprächspartner", NameDerPerson)
        furhat.say({
            random {
                +"$NameDerPerson ${furhat.voice.pause("700ms")}, ein schöner Vorname"
                +"$NameDerPerson ${furhat.voice.pause("700ms")}, ein wunderbarer Vorname"
                +"$NameDerPerson ${furhat.voice.pause("700ms")}, so hieß mal ein entfernter Verwandter von mir"
                +"Okay $NameDerPerson ${furhat.voice.pause("700ms")}, lassen Sie mich Ihnen sagen wo Sie heute hin müssen"
                +"Freut mich Sie kennenzulernen $NameDerPerson"
            }
            furhat.gesture(Gestures.BigSmile)
        }
        )
        goto(Greeting)
    }
    onResponse<Nein> {
        furhat.say("Das müssen Sie auch nicht, ich würde Ihnen trotzdem gerne helfen")
        furhat.gesture(Gestures.Smile)
        goto(Greeting)
    }
    onNoResponse {
        furhat.say("Wir machen einfach mal weiter")
        furhat.gesture(Gestures.Smile)
        goto(Greeting)
    }
    onResponse {
        furhat.say("Alles klar, wir machen einfach mal weiter")
        furhat.gesture(Gestures.Smile(duration = 2.0, strength = 2.0))
    }
}


