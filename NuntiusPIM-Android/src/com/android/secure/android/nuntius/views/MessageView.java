package com.android.secure.android.nuntius.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.secure.android.nuntius.R;


/**
 *	Message construct for one item of the conversation list
 */
public class MessageView extends RelativeLayout {

	// context of the view
	private Context context;

	// view of the sent or received image
	private ImageView messageImageView;
	// view of the sent or received text
	private TextView messageTextView;

	
	/**
	 * constructor for initiating important variables and views
	 * @param context
	 */
	public MessageView(Context context) {
		super(context);

		this.context = context;

		setProperties();
	}

	
	/*
	 * initiates the layout and contained views
	 */
	private void setProperties() {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.list_item, this);

		messageTextView = (TextView) findViewById(R.id.list_item_text_view);
		messageImageView = (ImageView) findViewById(R.id.list_item_image_view);
	}
	
	
	/*
	 * @return the message text
	 */
	public String getText() {
		return messageTextView.getText().toString();
	}
	
	/*
	 * set the received or sent message text
	 * @param received or sent message text
	 */
	public void setText(String text) {
		messageTextView.setText(text);
	}
	
	
	/*
	 * @return the received or sent image
	 */
	public Drawable getImage() {
		return messageImageView.getDrawable();
	}
	
	/*
	 * sets the received or sent image
	 * @param received or sent image
	 */
	public void setImage(Drawable image) {
		messageImageView.setBackgroundDrawable(image);
	}
}
