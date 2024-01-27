package com.example.cardproject.ui.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cardproject.R;
import com.example.cardproject.adapter.CardAdapter;
import com.example.cardproject.adapter.CartAdapter;
import com.example.cardproject.model.Cart;
import com.example.cardproject.viewmodel.CartViewModel;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;

    private CartViewModel cartViewModel;

    private CartAdapter adapter;

    private Button orderBtn;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        setupRecyclerView(view);
        setupListeners();
        setupObservers();
    }

    private void initViewModel() {
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void setupRecyclerView(View view) {
        adapter = new CartAdapter(requireContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setupListeners() {
        orderBtn = (Button) getView().findViewById(R.id.orderBtn);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartViewModel.order();
            }
        });
    }

    private void setupObservers() {
        cartViewModel.cartListLiveData.observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                toggleRecyclerView(carts.isEmpty());
                adapter.setCartList(carts);
            }
        });
    }

    private void toggleRecyclerView(boolean empty) {
        if (empty) {
            getView().findViewById(R.id.cartTV).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.cartRecyclerView).setVisibility(View.GONE);
            getView().findViewById(R.id.orderBtn).setEnabled(false);
        } else {
            getView().findViewById(R.id.cartTV).setVisibility(View.GONE);
            getView().findViewById(R.id.cartRecyclerView).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.orderBtn).setEnabled(true);
        }
    }
}