package com.android.secure.android.nuntius.network;

import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
 
/**
 * TCP client which handles the communication between a server and a client application  
 */
public class TCPClient {

    private String serverMessage;
    public static final String SERVERIP = ""; //your computer IP address
    public static final int SERVERPORT = 8888; 
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    private PrintWriter out;
    private OutputStream outStream;
    private BufferedReader in;
 
    
    /**
     *  Constructor of the class, initiates the listener
     */
    public TCPClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }
 
    
    /*
     * Sends the message from the client to the server
     * @param message text entered by client
     */
    public void sendMessage(String message) {  	
        if (out != null && !out.checkError()) {
        	message = "string;;" + message;
        		
            out.println(message);
            out.flush();
        }
    }
    
    
    /*
     * Sends a file from the client to the server
     * @param path to the file
     * @throws IOException if there occurs an error
     */
    public void sendFIle(String path) throws IOException {
    	File f = new File(path);
    	
    	byte [] buffer = new byte[(int)f.length()];
    	FileInputStream fis = new FileInputStream(f);
    	BufferedInputStream bis = new BufferedInputStream(fis);
    	bis.read(buffer,0,buffer.length);
    	
    	outStream.write(buffer,0,buffer.length);
    	outStream.flush();
    }
    
 
    /*
     * start the client
     */
    public void run() {
        mRun = true;
 
        createSocket(1);
    }
    
    
    /*
     * stop the client
     */
    public void stopClient(){
        mRun = false;
    }
    
    
    /*
     * create the socket for a connection to the server
     */
    private void createSocket(int times) {
    	try {
    		//here you must put your computer's IP address.
    		InetAddress serverAddr = InetAddress.getByName(SERVERIP);

    		Log.e("TCP Client", "C: Connecting...");

    		//create a socket to make the connection with the server
    		Socket socket = new Socket();
    		InetSocketAddress inetSocket = new InetSocketAddress(serverAddr, SERVERPORT);
    		socket.connect(inetSocket, 8000);
    		
    		listen(socket);
    	} catch (Exception e) {
    		Log.e("TCP", "C: Error", e);
    		
    		if(times < 5) {
    			createSocket(times + 1);
    		}
    	}
    }
    
    
    private void listen(Socket socket) {
    	try {
            //send the message to the server
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            outStream = socket.getOutputStream();

            Log.e("TCP Client", "C: Sent.");

            //receive the message which the server sends back
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //in this while the client listens for the messages sent by the server
            while (mRun) {
                serverMessage = in.readLine();

                if (serverMessage != null && mMessageListener != null) {
                    //call the method messageReceived from MyActivity class
                    mMessageListener.messageReceived(serverMessage);
                }
                
                serverMessage = null;
            }

            Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
        } catch (Exception e) {
            Log.e("TCP", "S: Error", e);
        } finally {
            //the socket must be closed. It is not possible to reconnect to this socket
            // after it is closed, which means a new socket instance has to be created.
            try {
				socket.close();
			} catch (IOException e) {
				Log.e("TCP", "S: Error", e);
			}
        }
    }
    
    
    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}