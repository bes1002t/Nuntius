package com.android.secure.android.nuntius.views;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.secure.android.nuntius.R;
import com.android.secure.android.nuntius.network.TCPClient;


/**
 * Conversation activity, handles a conversation between two clients
 */
public class ConversationActivity extends Activity {
	
	// TCP client which handle the communication between a server and the client application
	private TCPClient mTcpClient;

	// List to display the conversation
	private ListView conversationList;
	// Adapter which initiates the Layout of every list item 
	private ConversationAdapter conversationAdapter;
	// stores the conversation message text
	private ArrayList<String> messageArray;

	// button for send a message
	private Button messageSendButton;
	// form to type a message
	private EditText messageEditText;


	/**
	 * onCreate sets the conversation layout and call important methods
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// sets the conversation layout
		setContentView(R.layout.activity_start);

		
		// initiation of GUI components
		initGUI();

		// add listener to views
		initSend();

		// connect to the server
		connect();
	}
	
	
	/*
	 * initiates the GUI of the conversation layout
	 */
	private void initGUI() {
		messageArray = new ArrayList<String>();

		messageEditText = (EditText) findViewById(R.id.editText);
		messageSendButton = (Button) findViewById(R.id.send_button);

		conversationList = (ListView) findViewById(R.id.list);
		
		conversationAdapter = new ConversationAdapter(this, messageArray);
		conversationList.setAdapter(conversationAdapter);
	}


	/*
	 * sets all listeners which will be called if a message was sent
	 */
	private void initSend() {
		messageSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String message = messageEditText.getText().toString();

				// add the text to the arrayList
				messageArray.add("c: " + message);

				// sends the message to the server
				if (mTcpClient != null) {
					mTcpClient.sendMessage(message);
				}

				// refresh the list
				conversationAdapter.notifyDataSetChanged();
				messageEditText.setText("");
			}
		});
	}


	/*
	 * create a asyncronous tcp client to handle the communication between the cliet application and a server
	 */
	private void connect() {
		new AsyncTask<String, String, TCPClient>() {
			
			@Override
			protected TCPClient doInBackground(String... message) {
				// create a new TPC Client
				mTcpClient = new TCPClient(new TCPClient.OnMessageReceived() {
					@Override
					public void messageReceived(String message) {
						publishProgress(message);
					}
				});
				mTcpClient.run();

				return null;
			}


			@Override
			protected void onProgressUpdate(String... values) {
				super.onProgressUpdate(values);

				// in the arrayList we add the messaged received from server
				messageArray.add(values[0]);
				// notify the adapter that the data set has changed. This means that new message received
				// from server was added to the list
				conversationAdapter.notifyDataSetChanged();
			}

		};

	}
}