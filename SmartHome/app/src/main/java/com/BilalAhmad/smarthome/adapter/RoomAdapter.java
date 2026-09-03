package com.BilalAhmad.smarthome.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.BilalAhmad.smarthome.databinding.ItemRoomCardBinding;
import com.BilalAhmad.smarthome.model.Room;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private final List<Room> roomList;
    private final OnRoomClickListener listener;
    public interface OnRoomClickListener {
        void onRoomClick(Room room);
    }

    public RoomAdapter(List<Room> roomList, OnRoomClickListener listener) {
        this.roomList = roomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRoomCardBinding binding = ItemRoomCardBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new RoomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.bind(roomList.get(position));
    }

    @Override
    public int getItemCount() {
        return roomList != null ? roomList.size() : 0;
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        private final ItemRoomCardBinding binding;

        public RoomViewHolder(ItemRoomCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Room room) {

            binding.tvRoomName.setText(room.getRoomName());
            binding.tvRoomStatus.setText(room.getRoomStatus());

            binding.switchMasterRoom.setOnCheckedChangeListener(null);
            binding.switchMasterRoom.setChecked(room.isMasterOn());

            binding.switchMasterRoom.setOnCheckedChangeListener((buttonView, isChecked) -> {
                room.setMasterOn(isChecked);
            });

            binding.cardRoomContainer.setOnClickListener(v -> {
                if (listener != null) listener.onRoomClick(room);
            });
        }
    }
}
