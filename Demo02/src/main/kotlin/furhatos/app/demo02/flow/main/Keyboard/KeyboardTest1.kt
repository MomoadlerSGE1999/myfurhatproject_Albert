import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class GlobalKeyListenerExample : NativeKeyListener {
    //leerer String der befüllt werden soll
    var text: String = ""
    constructor(text: String) {
        this.text = text
    }

    override fun nativeKeyPressed(e: NativeKeyEvent) {
        //Definition der Regeln wie der Keyboardlistener zu arbeiten hat
        //Wird Enter gedrückt, wird der Keyboardlistener unregistered, also beendet
        if (e.keyCode == NativeKeyEvent.VC_ENTER) {
            try {
                GlobalScreen.unregisterNativeHook()
            } catch (nativeHookException: NativeHookException) {
                nativeHookException.printStackTrace()
            }
            //Der bis zum Drücken von enter eingegebene text wird geprinted
        println(text)
        }
        else {
            //Hier wird solange ein Char der Variable text hinzugefügt, bis das Abbrcuhkriterium (Enter) erfüllt ist
            println(e.keyCode)
            println(NativeKeyEvent.getKeyText(e.getKeyCode()))
            val value = NativeKeyEvent.getKeyText(e.getKeyCode())
            println(value.length)
            //Zwei Bedingungen, einmal darf die länge nur 1 sein, so wird umgangen, dass Strings wie "Umschalt" o. Ä. dem
            // Text hinzugefügt werden, also nur einzelne bcuhstaben (Chars)
            if (value.length == 1 && Character.isLetter(value.single())){
                //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()))
                //Operator muss aneinander stehen: text+=
                //Der Variable Text wird immer ein weiterer Buchstabe hinzugefügt, solange bis der Keyboardlistener
                // durch die erste Bedingung unterbrochen wird
                text += NativeKeyEvent.getKeyText(e.getKeyCode())

            }
            else if (e.keyCode == NativeKeyEvent.VC_SPACE){
                text+=" "
            }
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        //println("Key Released: " + NativeKeyEvent.getKeyText(e.keyCode))
    }

    override fun nativeKeyTyped(e: NativeKeyEvent) {
        //println("Key Typed: " + NativeKeyEvent.getKeyText(e.keyCode))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var text2 = ""
            try {
                GlobalScreen.registerNativeHook()
            } catch (ex: NativeHookException) {
                System.err.println("There was a problem registering the native hook.")
                System.err.println(ex.message)
                System.exit(1)
            }
            GlobalScreen.addNativeKeyListener(GlobalKeyListenerExample(text2))
            println(text2)
        }
    }
}