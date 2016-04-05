package utility;

/**
 * Used to print text to System.out for the purpose of debugging
 *
 * Set global variable isDebug to turnOff/On printing
 *
 * How to use specific mutes: Ex. "mute all messages from Queen" ->
 *
 * a. your println("queen: does something") or print("queen: does something
 * else") should contain the word queen
 *
 * b. set global variable muteQueen = true;
 *
 * @author Tobias Jacobsen
 */
public class Debug {

    public static boolean isDebug;
    public static boolean muteSystem;
    public static boolean muteQueen;
    public static boolean muteCarrier;
    public static boolean muteScout;
    public static boolean muteWarrior;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_MAGNETA = "\u001B[35m";

    /**
     * Prints a line of text to the console
     *
     * @param message
     */
    public static void println(Object message) {
        format(message, true);
    }

    /**
     * Prints text to the console. Use multiple print() to construct a sentence
     *
     * @param message
     */
    public static void print(Object message) {
        format(message, false);
    }

    private static void format(Object message, boolean isPrintline) {
        if (isDebug) {
            String m = message.toString().toLowerCase();

            if (m.contains("queen:") && !muteQueen) {
                m = ANSI_RED + m + ANSI_RESET;
                SystemOut(m, isPrintline);
            } else if (m.contains("carrier:") && !muteCarrier) {
                m = ANSI_GREEN + m + ANSI_RESET;
                SystemOut(m, isPrintline);
            } else if (m.contains("scout:") && !muteScout) {
                m = ANSI_BLUE + m + ANSI_RESET;
                SystemOut(m, isPrintline);
            } else if (m.contains("warrior:") && !muteWarrior) {
                m = ANSI_YELLOW + m + ANSI_RESET;
                SystemOut(m, isPrintline);
            } else if (m.contains("system:") && !muteWarrior) {
                m = ANSI_MAGNETA + m + ANSI_RESET;
                SystemOut(m, isPrintline);
            } else {
                SystemOut(m, isPrintline);
            }
        }
    }

    private static void SystemOut(String message, boolean isPrintline) {
        if (isPrintline) {
            System.out.println(message);
        } else {
            System.out.print(message);
        }
    }
}
