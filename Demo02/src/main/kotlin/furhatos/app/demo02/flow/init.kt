package furhatos.app.demo02.flow


import NummerErfragen
import furhatos.app.demo02.flow.main.Greeting
import furhatos.app.demo02.setting.distanceToEngage
import furhatos.app.demo02.setting.maxNumberOfUsers
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.AcapelaVoice
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.util.Gender
import furhatos.util.Language

val Init : State = state() {
    init {
        furhat.run {
            dialogLogger.startSession()
            setVoice(Language.GERMAN, Gender.FEMALE, false)
            setInputLanguage(Language.GERMAN)
        }
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        /** start the interaction */
        goto(Idle)
    }
}
