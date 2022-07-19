package furhatos.app.demo02.flow.main


import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes


val Taxidriverdialogue : State = state() {

    onEntry {
        furhat.ask (
            "Sind Sie hier um einen Kunden abzuholen?"
        )
    }
    onResponse<Yes> {
        if (user == null){
            //User wird gesetzt, wenn der vorherige user gleich null ist und ein anderer, bspw. der Patient geantortet hat
            user = users.getUser(it.userId)
        }
//Mit der Funktion stellefrage wird die Frage nach den Namen des Kunden platziert
        stellefrage(user!!, this.furhat, "Bitte geben Sie den Vornamen des Kunden ein", "vorname")
        stellefrage(user!!, this.furhat, "Bitte geben Sie den Nachnamen des Kunden ein", "name")

// der Name des Kunden wird mit den Eingabewerten des fields vorname sowie name gesetzt, der value beschreibt die Zusammensetzung des fields fullname
        //TODO ISt hier nciht eigentlich der user, der Kunde der wohlmöglich noch an der Dialyse hängt und gar nicht der user ist?
        //Eine Änderung könnte aber wohlmöglich noch zu dem Problem führen, dass man in der ReadExcel
        // zwischen einem Patienten und einem Taxifahrer unterscheiden müsste
        user!!.put("fullname", "${user!!.get("name")}, ${user!!.get("vorname")}")

        goto(Taxidriverdialogue01)
    }

    onResponse<No> {
        furhat.say("Okay, melden sie sich bitte an unserer Rezeption an. Es war mir eine Ehre mit Ihnen zu reden.")
        furhat.gesture(Gestures.BigSmile(1.0, 2.0))
    }

}






