/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper.server;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import org.junit.Test;

/**
 * TODO
 */
public class MinesweeperServerTest {
    
    // TODO
	private static final String LOCALHOST = "127.0.0.1";
	private static final int PORT = 8080;
	private static final int MAX_CONNECTION_ATTEMPTS = 10;
	
	
	public static Thread startMinesweeperServer() {
		final String [] args = new String [] {
				"--debug",
				"--port", Integer.toString(PORT),
				
		};
		
		Thread serverThread  = new Thread(()-> MinesweeperServer.main(args));
		serverThread.start();
		return serverThread;
	}
    
	private static Socket connectToMinesweeperServer(Thread server) throws IOException {
        int attempts = 0;
        while (true) {
            try {
                Socket socket = new Socket(LOCALHOST, PORT);
                socket.setSoTimeout(3000);
                return socket;
            } catch (ConnectException ce) {
                if ( ! server.isAlive()) {
                    throw new IOException("Server thread not running");
                }
                if (++attempts > MAX_CONNECTION_ATTEMPTS) {
                    throw new IOException("Exceeded max connection attempts", ce);
                }
                try { Thread.sleep(attempts * 10); } catch (InterruptedException ie) { }
            }
        }
    }
	
	//@Test
//	public void test() {
//		Thread thread = this.startMinesweeperServer();
//		Socket socket = this.connectToMinesweeperServer(thread);
//		
//		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//	}
	
	
}
