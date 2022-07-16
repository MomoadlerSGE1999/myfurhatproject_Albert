package furhatos.app.demo02vergleich.flow.main

import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.demo02.flow.main.Greeting
import furhatos.app.demo02.flow.main.user
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
        furhat.attendClosestUser()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Greeting)
    }
}
