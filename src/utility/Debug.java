package utility;

/**
 *
 * @author Tobias Jacobsen
 */
public class Debug {

    public static boolean isDebug;

    public static void println(Object message) {
        if (isDebug) {
            System.out.println(message + "");
        }
    }

    public static void print(Object message) {
        if (isDebug) {
            System.out.print(message + "");
        }
    }
}
