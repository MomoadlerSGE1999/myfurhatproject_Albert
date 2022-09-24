import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.demo02.flow.main.Greeting
import furhatos.app.demo02.flow.main.user
import furhatos.flow.kotlin.*
import furhatos.gestures.Gesture
import furhatos.gestures.Gestures
import furhatos.nlu.common.PersonName
import furhatos.nlu.common.Yes
import nlu.Nein


val Greetingname : State = state() {

    onEntry {
        furhat.attend(users.current)

        furhat.say() {
            +"Hallo mein name ist Hannah, ich bin der neue Serviceroboter in diesem Dialysezentrum ${furhat.voice.pause("700ms")}."
        }
        furhat.ask() {
            +"Wie ist Ihr name?"
            random {
                furhat.gesture(Gestures.Thoughtful)
                furhat.gesture(Gestures.BrowRaise)
                furhat.gesture(Gestures.Roll(duration = 1.5, strength = 1.2, iterations = 1))
            }
        }
    }
    onResponse<PersonName> {
        val NameDerPerson = it.intent
        user = users.current
        user!!.put("VornameGesprächspartner", NameDerPerson)
        furhat.say({
            random {
                +"$NameDerPerson, ein schöner Vorname"
                +"$NameDerPerson, ein wunderbarer Vorname"
                +"$NameDerPerson, so hieß mal ein entfernter Verwandter von mir"
                +"Okay $NameDerPerson, lassen Sie mich Ihnen sagen wo Sie heute hin müssen"
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


