package furhatos.app.demo02vergleich.flow.main

import furhat.libraries.standard.AutomaticHeadMovements
import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.demo02.flow.main.Greeting
import furhatos.app.demo02.flow.main.user
import furhatos.flow.kotlin.*
import furhatos.records.User

val Idle: State = state {


    onEntry {
        if (users.count == 1) {
            goto(Greeting)
        } else if (users.count > 1) {
            furhat.attendClosestUser()
            //Furhat schaut bei einer Gruppe bestehend aus mehr als zwei Usern den von ihm aus gesehen nächsten user an
            goto(Greeting)
        } else {
            furhat.attendNobody()
            /** Set value to avoid AutomaticHeadMovements to interfere with other head movements defined in a gesture. We assume gestures are not longer than 3000 ms **/
            AutomaticHeadMovements.autoHeadMovementDelay(3000)

            onUserEnter {
                furhat.attend(it)
                goto(Greeting)
            }
        }
    }
}


/*


val Idle: State = state {



        init {
            when {
                users.count > 0 -> {
                    furhat.attend(users.random)
                    goto(Greeting)
                }
                //Falls Skill Virtuell läuft:
                users.count == 0 && furhat.isVirtual() -> goto(Greeting) // if the skill is run on virtual furhat, ignore if there are no users and start anyway.
                users.count == 0 && !furhat.isVirtual() -> furhat.say("I can't see anyone. Step closer please. ")
            }
            when {
                users.count < 1 -> {
                    include(AutomaticHeadMovements.RandomHeadMovements(repetitionPeriod = 5000..10000))
                }

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



 */