package furhatos.app.demo02.flow


import furhatos.app.demo02.flow.main.Greeting
import furhatos.app.demo02.setting.distanceToEngage
import furhatos.app.demo02.setting.maxNumberOfUsers
import furhatos.app.demo02vergleich.flow.main.Idle
import furhatos.autobehavior.setDefaultMicroexpression
import furhatos.event.responses.ResponseFace
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.AcapelaVoice
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.util.Gender
import furhatos.util.Language
import javax.swing.Action.NAME
import javax.xml.stream.events.Characters

val Init : State = state() {
    init {

            dialogLogger.startSession()
            furhat.setVoice(Language.GERMAN, Gender.FEMALE, false)
            furhat.setInputLanguage(Language.GERMAN)
            furhat.voice = Voice("Vicki")
            furhat.voice = PollyVoice.Vicki()
            furhat.character = "Isabel"
            furhat.setDefaultMicroexpression(blinking = true, facialMovements = true, eyeMovements = false)

        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        /** start the interaction */
        goto(Idle)
    }
}
