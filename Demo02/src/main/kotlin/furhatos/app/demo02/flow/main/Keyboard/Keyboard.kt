/*import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener

class GlobalKeyListenerExample : NativeKeyListener {
    fun nativeKeyPressed(e: NativeKeyEvent) {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()))
        if (e.getKeyCode() === NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook()
            } catch (nativeHookException: NativeHookException) {
                nativeHookException.printStackTrace()
            }
        }
    }

    fun nativeKeyReleased(e: NativeKeyEvent) {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()))
    }

    fun nativeKeyTyped(e: NativeKeyEvent) {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            try {
                GlobalScreen.registerNativeHook()
            } catch (ex: NativeHookException) {
                System.err.println("There was a problem registering the native hook.")
                System.err.println(ex.getMessage())
                System.exit(1)
            }
            GlobalScreen.addNativeKeyListener(GlobalKeyListenerExample())
        }
    }
}
*/
