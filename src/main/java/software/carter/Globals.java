package software.carter;

import java.awt.Color;

public class Globals {
    private static Globals globals;
    public static final Color MATCH_CHAT_COLOR = new Color(249, 149, 67);

    static {
        globals = new Globals();
    }

    public static Globals get() { return globals; }
}
