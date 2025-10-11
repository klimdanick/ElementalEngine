package Examples.Mario;

import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;

public class TestSteam {
    public static void main(String[] args) throws SteamException {
        // 1) MANUALLY load the Steamworks SDK DLL (adjust path)
        //    Use the absolute path to a known-good steam_api64.dll from the SDK.
        try {
            System.out.println("Loading steam_api64.dll manually...");
            System.load("E://ElementalDev-workspace - Copy/ElementalEngine/src/main/resources/steam/steam_api64.dll"); // <-- change this
            System.out.println("Loaded steam_api64.dll");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            System.err.println("Failed to load steam_api64.dll manually.");
            return;
        }

        // 2) Let steamworks4j extract/load its own helper library
        try {
            SteamAPI.loadLibraries(); // will extract steamworks4j64.dll into temp and attempt to load it
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println("SteamAPI.loadLibraries() failed.");
            return;
        }

        // 3) initialize
        boolean ok = SteamAPI.init();
        System.out.println("Steam init ok? " + ok);

        if (ok) {
            // run callbacks a little then shutdown
            for (int i = 0; i < 20; i++) {
                SteamAPI.runCallbacks();
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
            SteamAPI.shutdown();
        }
    }
}
