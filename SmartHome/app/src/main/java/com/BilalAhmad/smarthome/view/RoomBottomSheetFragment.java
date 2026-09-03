package com.BilalAhmad.smarthome.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.BilalAhmad.smarthome.adapter.ApplianceAdapter;
import com.BilalAhmad.smarthome.databinding.LayoutBottomSheetRoomBinding;
import com.BilalAhmad.smarthome.model.SmartDevice;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class RoomBottomSheetFragment extends BottomSheetDialogFragment {
    private LayoutBottomSheetRoomBinding binding;
    private ApplianceAdapter adapter;
    private final List<SmartDevice> deviceList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = LayoutBottomSheetRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        loadDummySmartDevices(); // Test devices setup

        binding.switchMasterRoom.setOnCheckedChangeListener((btn, isChecked) -> {
            for (SmartDevice device : deviceList) {
                device.setSwitchedOn(isChecked);
            }
            adapter.notifyDataSetChanged();
            updateActiveDevicesCount();
        });

        binding.btnAddAppliance.setOnClickListener(v -> addNewDeviceDynamically());
    }

    private void setupRecyclerView() {
        adapter = new ApplianceAdapter(deviceList, device -> updateActiveDevicesCount());
        binding.rvRoomAppliances.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRoomAppliances.setAdapter(adapter);
    }

    private void loadDummySmartDevices() {
        deviceList.clear();

        SmartDevice fan = new SmartDevice("1", "Ceiling Fan", "Living Room", "Slider", "home/livingroom/fan");
        fan.setSwitchedOn(true);
        fan.setValue(3);

        SmartDevice light = new SmartDevice("2", "Main Light", "Living Room", "Switch", "home/livingroom/light");
        light.setSwitchedOn(true);

        deviceList.add(fan);
        deviceList.add(light);

        adapter.notifyDataSetChanged();
        updateActiveDevicesCount();
    }

    private void addNewDeviceDynamically() {
        int id = deviceList.size() + 1;
        SmartDevice newDev = new SmartDevice(
                String.valueOf(id),
                "New Switch " + id,
                "Living Room",
                "Switch",
                "home/livingroom/switch" + id
        );
        newDev.setSwitchedOn(true);

        deviceList.add(newDev);
        adapter.notifyItemInserted(deviceList.size() - 1);
        binding.rvRoomAppliances.smoothScrollToPosition(deviceList.size() - 1);

        updateActiveDevicesCount();
    }

    private void updateActiveDevicesCount() {
        int count = 0;
        for (SmartDevice d : deviceList) {
            if (d.isSwitchedOn()) count++;
        }
        binding.tvSheetActiveDevices.setText(count + " Active Devices");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


