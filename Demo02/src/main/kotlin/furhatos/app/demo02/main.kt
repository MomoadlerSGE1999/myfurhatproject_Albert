package furhatos.app.demo02

import furhatos.app.demo02.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class Demo02Skill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}