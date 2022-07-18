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
        if (e.keyCode == NativeKeyEvent.VC_ENTER) {
            try {
                GlobalScreen.unregisterNativeHook()
            } catch (nativeHookException: NativeHookException) {
                nativeHookException.printStackTrace()
            }
        println(text)
        }
        else {
            println(e.keyCode)
            println(NativeKeyEvent.getKeyText(e.getKeyCode()))
            val value = NativeKeyEvent.getKeyText(e.getKeyCode())
            println(value.length)
            if (value.length == 1 && Character.isLetter(value.single())){
                //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()))
                //Operator muss aneinander stehen: text+=
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