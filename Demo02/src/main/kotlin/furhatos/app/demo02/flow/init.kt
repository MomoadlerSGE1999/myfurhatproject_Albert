package furhatos.app.demo02.flow

import furhatos.app.demo02.flow.main.Idle
import furhatos.app.demo02.setting.distanceToEngage
import furhatos.app.demo02.setting.maxNumberOfUsers
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice

val Init : State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Matthew")
        /** start the interaction */
        goto(Idle)
    }
}
