package com.example.rentx.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rentx.R;
import com.example.rentx.adapters.HomeAdapter;
import com.example.rentx.listeners.ItemListener;
import com.example.rentx.model.Item;
import com.example.rentx.model.User;
import com.example.rentx.screens.DetailsActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment implements ItemListener {

    private RecyclerView topDealRV;
    private HomeAdapter adapter;
    private List<Item> itemList;
    private CircleImageView profileImage;
    private TextView username;
    private DatabaseReference ref;
    private View ProfilePage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        topDealRV = view.findViewById(R.id.top_deal_RV);
        profileImage = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.user_name);


        ref = FirebaseDatabase.getInstance().getReference().child("users");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                User user = snapshot.getValue(User.class);
                if (user!=null){
                    username.setText("Hey "+user.getName());
                    Glide.with(getContext())
                            .load(user.getImage())
                            .centerCrop()
                            .placeholder(R.drawable.ic_account)
                            .into(profileImage);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        itemList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("images")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            itemList.add(new Item(
                               Objects.requireNonNull(dataSnapshot.child("location").getValue()).toString(),
                               Objects.requireNonNull(dataSnapshot.child("price").getValue()).toString(),
                               Objects.requireNonNull(dataSnapshot.child("description").getValue()).toString(),
                               Objects.requireNonNull(dataSnapshot.child("shortDescription").getValue()).toString(),
                               Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString(),
                               Objects.requireNonNull(dataSnapshot.child("Latitude").getValue().toString()),
                               Objects.requireNonNull(dataSnapshot.child("Longitude").getValue().toString())
                            ));

                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

        adapter = new HomeAdapter(getContext(), itemList, this);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topDealRV.setLayoutManager(linearLayoutManager);
        topDealRV.setAdapter(adapter);



    }

    @Override
    public void OnItemPosition(int position) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("price", itemList.get(position).getPrice());
        intent.putExtra("location", itemList.get(position).getLocation());
        intent.putExtra("description", itemList.get(position).getDescription());
        intent.putExtra("shortDescription", itemList.get(position).getShortDescription());
        intent.putExtra("image", itemList.get(position).getImage());
        intent.putExtra("Latitude", itemList.get(position).getLatitude());
        intent.putExtra("Longitude", itemList.get(position).getLongitude());
        startActivity(intent);
    }
}