package furhatos.app.demo02.flow


import furhatos.app.demo02.setting.distanceToEngage
import furhatos.app.demo02.setting.maxNumberOfUsers
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.util.Gender
import furhatos.util.Language

val Init : State = state() {
    init {
        furhat.setVoice(Language.GERMAN, Gender.FEMALE, false)
        furhat.setInputLanguage(Language.GERMAN)
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Hannah")
        /** start the interaction */
        goto(Idle)
    }
}
