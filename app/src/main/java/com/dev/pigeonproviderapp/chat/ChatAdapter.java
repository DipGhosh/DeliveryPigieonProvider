package com.dev.pigeonproviderapp.chat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.dev.pigeonproviderapp.R;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

  public List<UserChatModel> mData;
  private LayoutInflater mInflater;

  // data is passed into the constructor
  public ChatAdapter(Context context, List<UserChatModel> data) {
    this.mInflater = LayoutInflater.from(context);
    this.mData = data;
  }

  // inflates the row layout from xml when needed
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view;
    if (viewType == 1) {
      view = mInflater.inflate(R.layout.my_message, parent, false);
    } else {
      view = mInflater.inflate(R.layout.their_message, parent, false);
    }

    return new ViewHolder(view);
  }

  // binds the data to the TextView in each row
  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    String msg = mData.get(position).getMessage();
    holder.myTextView.setText(msg);
  }

  // total number of rows
  @Override
  public int getItemCount() {
    return mData.size();
  }

  @Override
  public int getItemViewType(int position) {
      if (mData.get(position).getUserchat() == 1) {
          return 1;
      } else {
          return 2;
      }
  }

  // stores and recycles views as they are scrolled off screen
  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView myTextView;

    ViewHolder(View itemView) {
      super(itemView);
      myTextView = itemView.findViewById(R.id.message_body);
    }

  }

}
