package furhatos.app.demo02.flow

import furhatos.app.demo02.flow.main.Idle
import furhatos.flow.kotlin.*

val Parent: State = state {

    onUserLeave(instant = true) {
        when {
            users.count == 0 -> goto(Idle)
            it == users.current -> furhat.attend(users.other)
          }
        furhat.say("Good Bye and have a good day")
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }
}