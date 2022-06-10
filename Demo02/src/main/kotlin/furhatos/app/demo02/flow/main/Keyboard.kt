/*
package furhatos.app.demo02.flow.main
import furhatos.app.demo02.flow.Parent
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import java.util.Scanner
val Keyboardtipein : State = state(Parent){

    onEntry {

        furhat.say("Please type in your sur name")
            val userInputvor = readLine()
            furhat.say("Please type in your last name")
            val userInputnach = readLine()

            fun getName(vorname: String? = userInputvor, name:String? = userInputnach): String = "$userInputvor $userInputnach"

            furhat.say("This is the name that our system has now ")
            val vollername: String = getName()

        furhat.say(vollername)

        println(vollername)

    }
}

*/