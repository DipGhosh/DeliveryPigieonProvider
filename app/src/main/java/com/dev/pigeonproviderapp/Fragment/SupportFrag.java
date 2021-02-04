package com.dev.pigeonproviderapp.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dev.pigeonproviderapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class SupportFrag extends Fragment {

  private RecyclerView recyclerView;
  private EditText editText;
  private ImageView send;

  private LinearLayoutManager mLinearLayoutManager;

  private String DB_CHILD_USER = "users";
  private String DB_CHILD_Chats = "Chats";


  // Firebase instance variables
  private FirebaseAuth mFirebaseAuth;
  private FirebaseUser mFirebaseUser;
  private DatabaseReference mFirebaseDatabaseReference;


  public SupportFrag() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_support, container, false);

    recyclerView = view.findViewById(R.id.messages_view);
    editText = view.findViewById(R.id.editText);
    send = view.findViewById(R.id.send);

    mFirebaseAuth = FirebaseAuth.getInstance();

    mLinearLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLinearLayoutManager);

    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().trim().length() > 0) {
          send.setEnabled(true);
        } else {
          send.setEnabled(false);
        }
      }

      @Override
      public void afterTextChanged(Editable editable) {
      }
    });

    send.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Send messages on click.
      }
    });

    return view;

  }
}