package com.dev.pigeonproviderapp.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dev.pigeonproviderapp.R;
import com.dev.pigeonproviderapp.chat.ChatAdapter;
import com.dev.pigeonproviderapp.chat.ChatModel;
import com.dev.pigeonproviderapp.chat.UserChatModel;
import com.dev.pigeonproviderapp.chat.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;


public class SupportFrag extends Fragment {

  // Chat node in firebase DB
  public static final String MESSAGES_CHATS = "ChatsList";
  public static final String MESSAGES_USERS = "UsersList";

  // Firebase instance variables
  private FirebaseAuth mFirebaseAuth;
  private FirebaseUser mFirebaseUser;
  private DatabaseReference mFirebaseDatabaseReference;
  private ImageView mSendButton;
  private RecyclerView mMessageRecyclerView;
  private LinearLayoutManager mLinearLayoutManager;
  private EditText mMessageEditText;

  private String adminEmail = "admin@pigeon.in";
  private String uid = "";
  private Users adminUser;

  private List<UserChatModel> data = new ArrayList<>();


  public SupportFrag() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_support, container, false);

    mMessageRecyclerView = view.findViewById(R.id.mMessageRecyclerView);
    mMessageEditText = view.findViewById(R.id.mMessageEditText);
    mSendButton = view.findViewById(R.id.mSendButton);

    // Initialize Firebase Auth
    mFirebaseAuth = FirebaseAuth.getInstance();
    mFirebaseUser = mFirebaseAuth.getCurrentUser();

    if (mFirebaseUser == null) {
      Toast.makeText(getActivity(),"Please restart app to automatically sign in chat section",Toast.LENGTH_LONG).show();
    }

    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    mLinearLayoutManager.setStackFromEnd(true);
    mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

    ChatAdapter customAdapter = new ChatAdapter(getActivity(), data);
    mMessageRecyclerView.setAdapter(customAdapter);

    // New child entries
    mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(MESSAGES_USERS);
    ref.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
          adminUser = userSnapshot.getValue(Users.class);
          if (adminEmail.equals(adminUser.getEmail())) {
            Log.i("chat", "email: " + adminUser.getEmail() + " uid: " + adminUser.getUid());
            break;
          }
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

    DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference(MESSAGES_CHATS)
        .child(mFirebaseAuth.getUid());
    chatRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

        data.clear();

        for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
          ChatModel chatModel = chatSnapshot.getValue(ChatModel.class);

          if (chatModel.getSenderUid().equals(mFirebaseAuth.getUid())) {
            UserChatModel listUserChatModel = new UserChatModel(chatModel.getSenderUid(),
                chatModel.getMessage(), 1);
            data.add(listUserChatModel);
          } else {
            UserChatModel listUserChatModel = new UserChatModel(chatModel.getSenderUid(),
                chatModel.getMessage(), 2);
            data.add(listUserChatModel);
          }
          Log.i("chat", "message: " + chatModel.getMessage());
        }

        customAdapter.notifyDataSetChanged();
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

    mMessageEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() > 0) {
          mSendButton.setEnabled(true);
        } else {
          mSendButton.setEnabled(false);
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {
      }
    });

    mSendButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if (adminUser != null && mFirebaseAuth != null) {
          ChatModel chatModel = new ChatModel(mFirebaseAuth.getUid(),
              mMessageEditText.getText().toString());
          mFirebaseDatabaseReference.child(MESSAGES_CHATS).child(mFirebaseAuth.getUid()).push()
              .setValue(chatModel);
          mFirebaseDatabaseReference.child(MESSAGES_CHATS).child(adminUser.getUid()).push()
              .setValue(chatModel);
          mMessageEditText.setText("");
        }

      }
    });

    customAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);

        int friendlyMessageCount = customAdapter.getItemCount();
        int lastVisiblePosition =
            mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        // If the recycler view is initially being loaded or the
        // user is at the bottom of the list, scroll to the bottom
        // of the list to show the newly added message.

        if (lastVisiblePosition == -1 ||
            (positionStart >= (friendlyMessageCount - 1) &&
                lastVisiblePosition == (positionStart - 1))) {
          mMessageRecyclerView.scrollToPosition(positionStart);
        }

      }
    });

    // Inflate the layout for this fra
    return view;

  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onResume() {
    super.onResume();
  }

}