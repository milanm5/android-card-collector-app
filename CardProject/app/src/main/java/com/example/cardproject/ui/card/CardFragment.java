package com.example.cardproject.ui.card;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cardproject.R;
import com.example.cardproject.adapter.CardAdapter;
import com.example.cardproject.model.CardModel;
import com.example.cardproject.util.Constants;
import com.example.cardproject.util.OperationResult;
import com.example.cardproject.viewmodel.CardViewModel;


public class CardFragment extends Fragment implements CardAdapter.CardAdapterListener, SensorEventListener {


    private RecyclerView recyclerView;

    private CardViewModel cardViewModel;

    private EditText cardSearch;

    private Button searchBtn;

    private CardAdapter adapter;

    private SensorManager sensorManager;

    private Sensor sensorShake;

    private SensorEventListener sensorEventListener;

    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        setupRecyclerView(view);
        insertData();
        setupListeners(view);
        initSensorManager();
        setupObservers();


    }

    private void initViewModel() {
        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
    }

    private void setupRecyclerView(View view) {
        adapter = new CardAdapter(requireContext(), this);
        recyclerView = (RecyclerView) view.findViewById(R.id.cardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(CardModel cardModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.FAVORITE_TAG, cardModel);
        Navigation.findNavController(getView()).navigate(R.id.action_cardFragment_to_cardDetailsFragment, bundle);
    }

    private void insertData() {
        cardViewModel.insertData();
    }

    private void setupListeners(View view) {
        cardSearch = (EditText) view.findViewById(R.id.searchCardET);
        searchBtn = (Button) view.findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewModel.search(cardSearch.getText().toString());
                cardSearch.setText("");
            }
        });


        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event != null) {
                    float x_accl = event.values[0];
                    float y_accl = event.values[1];
                    float z_accl = event.values[2];

                    float floatSum = Math.abs(x_accl) + Math.abs(y_accl) + Math.abs(z_accl);

                    if (floatSum > 20) {
                        cardViewModel.getRandomCard();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

    }

    private void initSensorManager() {
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorShake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(sensorEventListener, sensorShake, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void setupObservers() {
        cardViewModel.cardResult.observe(getViewLifecycleOwner(), new Observer<OperationResult>() {
            @Override
            public void onChanged(OperationResult operationResult) {
                switch (operationResult.status) {
                    case SUCCESS:
                        adapter.setCardList(operationResult.data);
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), operationResult.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        cardViewModel.randomCardResult.observe(getViewLifecycleOwner(), new Observer<OperationResult>() {
            @Override
            public void onChanged(OperationResult operationResult) {
                switch (operationResult.status) {
                    case SUCCESS:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.FAVORITE_TAG, operationResult.data.get(0));
                        Navigation.findNavController(getView()).navigate(R.id.action_cardFragment_to_cardDetailsFragment, bundle);
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), operationResult.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}