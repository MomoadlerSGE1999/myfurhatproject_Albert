package furhatos.app.demo02vergleich.flow.main

import Greetingname
import furhat.libraries.standard.AutomaticHeadMovements
import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.demo02.flow.Init
import furhatos.app.demo02.flow.main.Greeting
import furhatos.app.demo02.flow.main.user
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Greeting
import furhatos.records.User
import khttp.get


val Idle: State = state {


    init {
        when {
            users.count > 0 -> {
                furhat.attendClosestUser()
                goto(Greetingname)
            }
        }
    }
    onUserEnter {
        furhat.ledStrip.solid(java.awt.Color.BLUE)
        if(users.count > 0){
            furhat.attendClosestUser()
            goto(Greetingname)
        }
        else {
            furhat.attendNobody()
        }
    }
    onUserLeave {
        goto(Init)
    }
}

    /*init {
        furhat.ledStrip.solid(java.awt.Color.WHITE)
        furhat.attend(users.other)
        if (users.count > 0) {
                furhat.attend(users.random)
                goto(Greetingname)
            }
        else{
            delay(5000)
            reentry()
        }
    }

    onEntry {
            furhat.attendClosestUser()
            furhat.ledStrip.solid(java.awt.Color.BLUE)
            furhat.attend(users.current)
            goto(Greetingname)
        }
    onUserLeave {
        furhat.attend(users.other)
        furhat.attend(users.current)
        goto(Greetingname)
    }


    onUserEnter(instant = true) {
        furhat.ledStrip.solid(java.awt.Color.RED)
        furhat.attend(it)
        goto(Greetingname)
    }
}

/*
val Idle: State = state {


    init {
        /** Set value to avoid AutomaticHeadMovements to interfere with other head movements defined in a gesture. We assume gestures are not longer than 3000 ms **/
        AutomaticHeadMovements.autoHeadMovementDelay(3000)

        onUserEnter {
            furhat.attend(it)
            goto(Greeting)
        }
        onUserAttend {
            furhat.attend(it)
            furhat.say("Hallo du schaust mich nun an")
            goto(Greeting)
        }
        onUserLeave {
            reentry()
        }


       /* if (users.count == 1) {
            goto(Greeting)
        } else if (users.count > 1) {
            furhat.attendClosestUser()
            //Furhat schaut bei einer Gruppe bestehend aus mehr als zwei Usern den von ihm aus gesehen nächsten user an
            goto(Greeting)
        } else {
            furhat.attendNobody()

        }
*/
    }



    }

     */


     */