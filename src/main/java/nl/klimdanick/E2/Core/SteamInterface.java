package nl.klimdanick.E2.Core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;
import com.codedisaster.steamworks.SteamFriends;
import com.codedisaster.steamworks.SteamFriendsCallback;
import com.codedisaster.steamworks.SteamID;
import com.codedisaster.steamworks.SteamResult;
import com.codedisaster.steamworks.SteamUser;

import nl.klimdanick.E2.Core.Debugging.DebugPanel;

public class SteamInterface {
	
	public SteamUser steamUser;
	public SteamFriends steamFriends;
	private Thread callbackThread;

    public SteamInterface() throws SteamException, IOException {
    	System.out.println("Working directory: " + new File(".").getAbsolutePath());
    	InputStream in = getClass().getResourceAsStream("/resources/steam_appid.txt");
    	Files.copy(in, Paths.get("steam_appid.txt"), StandardCopyOption.REPLACE_EXISTING);
    	
    	System.setProperty("org.lwjgl.librarypath", new File("steamworks4j").getAbsolutePath());
    	System.setProperty("java.library.path", new File("steamworks4j").getAbsolutePath());
    	
    	SteamAPI.loadLibraries();
        // Initialize Steam API
    	if (!SteamAPI.init()) {
    	    System.err.println("Steam initialization failed!");
    	} else {
    	    System.out.println("Steam initialized successfully!");
    	}
    	
    	// Create a SteamFriends instance
       steamFriends = new SteamFriends((SteamFriendsCallback) new SteamFriendsCallback() {
            @Override public void onSetPersonaNameResponse(boolean success, boolean localSuccess, SteamResult result) {}
            @Override public void onPersonaStateChange(SteamID steamID, SteamFriends.PersonaChange change) {}
            @Override public void onGameOverlayActivated(boolean active) {}
            @Override public void onGameLobbyJoinRequested(SteamID lobbyID, SteamID friendID) {}
            @Override public void onAvatarImageLoaded(SteamID steamID, int image, int width, int height) {}
            @Override public void onFriendRichPresenceUpdate(SteamID steamIDFriend, int appID) {}
            @Override public void onGameRichPresenceJoinRequested(SteamID steamIDFriend, String connect) {}
			@Override public void onGameServerChangeRequested(String server, String password) {}
        });
       
       // Example: set Rich Presence values
       steamFriends.setRichPresence("status", "Developing with Elemental Engine!");
       steamFriends.setRichPresence("steam_display", "#status");

        // Get the local user's SteamID
        steamUser = new SteamUser(null);
        SteamID mySteamID = steamUser.getSteamID();

        // Fetch and print the username
        String personaName = steamFriends.getFriendPersonaName(mySteamID);
        DebugPanel.rows.put("Username", personaName);
        
//        System.out.println("Logged in as: " + steamUser.getSteamID());

        // Run a basic loop
        callbackThread = new Thread() {
        	public void run() {
		        while (true) {
		            SteamAPI.runCallbacks();
		            try {
		                Thread.sleep(100);
		            } catch (InterruptedException e) {
		                break;
		            }
		        }
        	}
        };
        callbackThread.start();        
    }
    
    public void shutdown() {
        if (callbackThread != null && callbackThread.isAlive()) {
            callbackThread.interrupt();
        }
        SteamAPI.shutdown();
        System.out.println("Steam API shut down.");
    }
}
