package com.BilalAhmad.smarthome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.BilalAhmad.smarthome.databinding.ItemApplianceCardBinding;
import com.BilalAhmad.smarthome.model.SmartDevice;

import java.util.List;

public class ApplianceAdapter extends RecyclerView.Adapter<ApplianceAdapter.DeviceViewHolder> {

    private final List<SmartDevice> deviceList;
    private final OnDeviceChangeListener listener;

    public interface OnDeviceChangeListener {
        void onDeviceChanged(SmartDevice device);
    }

    public ApplianceAdapter(List<SmartDevice> deviceList, OnDeviceChangeListener listener) {
        this.deviceList = deviceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemApplianceCardBinding binding = ItemApplianceCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new DeviceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        holder.bind(deviceList.get(position));
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        private final ItemApplianceCardBinding binding;

        public DeviceViewHolder(ItemApplianceCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SmartDevice device) {
            binding.tvApplianceName.setText(device.getName());

            binding.switchAppliance.setOnCheckedChangeListener(null);
            binding.switchAppliance.setChecked(device.isSwitchedOn());

            // Check if device type is Slider or Switch
            boolean isSlider = "Slider".equalsIgnoreCase(device.getDeviceType());

            if (isSlider && device.isSwitchedOn()) {
                binding.sliderApplianceControl.setVisibility(View.VISIBLE);
                binding.sliderApplianceControl.setValue((float) device.getValue());
            } else {
                binding.sliderApplianceControl.setVisibility(View.GONE);
            }

            updateStatusText(device);

            // Switch Toggle Listener
            binding.switchAppliance.setOnCheckedChangeListener((btn, isChecked) -> {
                device.setSwitchedOn(isChecked);
                binding.sliderApplianceControl.setVisibility((isSlider && isChecked) ? View.VISIBLE : View.GONE);
                updateStatusText(device);
                if (listener != null) listener.onDeviceChanged(device);
            });

            // Slider Change Listener
            binding.sliderApplianceControl.addOnChangeListener((slider, value, fromUser) -> {
                if (fromUser) {
                    device.setValue((int) value);
                    updateStatusText(device);
                    if (listener != null) listener.onDeviceChanged(device);
                }
            });
        }

        private void updateStatusText(SmartDevice device) {
            if (!device.isSwitchedOn()) {
                binding.tvApplianceStatus.setText("OFF");
            } else if ("Slider".equalsIgnoreCase(device.getDeviceType())) {
                binding.tvApplianceStatus.setText("ON • Speed " + device.getValue());
            } else {
                binding.tvApplianceStatus.setText("ON");
            }
        }
    }
}