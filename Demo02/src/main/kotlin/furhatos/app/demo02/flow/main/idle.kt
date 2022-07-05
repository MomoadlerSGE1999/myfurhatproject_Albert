package furhatos.app.demo02vergleich.flow.main

import furhatos.app.demo02.flow.main.Greeting
import furhatos.flow.kotlin.*

val Idle: State = state {

    init {
        when {
            users.count > 0 -> {
                furhat.attend(users.random)
                goto(Greeting)
            }
            users.count == 0 && furhat.isVirtual() -> furhat.say("Ich kann niemanden sehen, füge bitte einen virtuellen Benutzer hinzu")
            users.count == 0 && !furhat.isVirtual() -> furhat.say("Ich kann niemanden sehen, komm bitte näher ")
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Greeting)
    }
}
