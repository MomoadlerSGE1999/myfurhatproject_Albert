import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class GlobalKeyListenerExample : NativeKeyListener {
    override fun nativeKeyPressed(e: NativeKeyEvent) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()))
        if (e.getKeyCode() === NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook()
            } catch (nativeHookException: NativeHookException) {
                nativeHookException.printStackTrace()
            }
        }
    }

    override fun nativeKeyReleased(e: NativeKeyEvent) {
        //mit for schleife, append, add, Stringbuilder?
        //for (i in 1..35) {
        //Forschleife für stringinput der liefert dann von der forschleife aus immer einen char an eine variable die nicht in der schleife ist
            System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()))
            var stringInput = NativeKeyEvent.getKeyText(e.getKeyCode())
        var fullstring: String = stringInput
            System.out.println("hallo" + stringInput)

    }

    override fun nativeKeyTyped(e: NativeKeyEvent) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            try {
                GlobalScreen.registerNativeHook()
            } catch (ex: NativeHookException) {
                System.err.println("There was a problem registering the native hook.")
                System.exit(1)
            }
            GlobalScreen.addNativeKeyListener(GlobalKeyListenerExample())
        }
    }

}

