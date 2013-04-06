package com.android.secure.android.nuntius.views;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Adapter to set the layout and content of a conversation list item
 */
public class ConversationAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> listItems;
	private Holder holder;

	
	/**
	 * Constructor, initiates all variables 
	 * 
	 * @param context
	 * @param arrayList which contains the all messages of the conversation
	 */
	public ConversationAdapter(Context context, ArrayList<String> arrayList) {
		this.context = context;
		this.listItems = arrayList;
	}


	/**
	 * Holder pattern to store a view for efficient listview using
	 */
	static class Holder {
		MessageView messageItem;
	}


	/*
	 * @returns the number of list items
	 */
	@Override
	public int getCount() {
		return listItems.size();
	}


	/*
	 * @returns the item from a specific position
	 */
	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}


	/*
	 * @returns the position id of a list item
	 */
	@Override
	public long getItemId(int position) {
		return 0;
	}


	/*
	 * If the view is null, the a item will be created and stored inside the holder
	 * else the item will reused.
	 * Set the message text to the view
	 * @returns a initiated message item 
	 */
	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {

		// if the view is null, initiate it with a new item
		// if is not null then reuse it
		if (view == null) {		
			holder = new Holder();
			holder.messageItem = new MessageView(context);
			holder.messageItem.setTag(holder);
		} else {
			holder = (Holder) view.getTag();       	
		}

		String stringItem = listItems.get(position);

		if (stringItem != null) {
			//set the text of the message
			holder.messageItem.setText(stringItem);
		}

		return holder.messageItem;
	}
}