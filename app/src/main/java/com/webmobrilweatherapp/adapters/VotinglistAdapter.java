package com.webmobrilweatherapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webmobrilweatherapp.model.VotinglistModel;
import com.webmobrilweatherapp.R;

import java.util.List;

public class VotinglistAdapter extends RecyclerView.Adapter<VotinglistAdapter.ViewHolder> {
    private List<VotinglistModel> userList;

    public VotinglistAdapter(List<VotinglistModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.votinglistrecycle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int voterImage = userList.get(position).getVoter_image();
        String voter_name = userList.get(position).getVoter_name();
        String tempeture = userList.get(position).getTempeture_in_degree();
        String speed = userList.get(position).getSpeed();
        String typeOfSpeed = userList.get(position).getTypeofspeed();
        String voterLocation = userList.get(position).getVoter_location();
        int cloudImage = userList.get(position).getCloudy_Image();
        holder.setData(voterImage, voter_name, tempeture, speed, typeOfSpeed, voterLocation, cloudImage);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView voter_Image_;
        private TextView voter_Name_;
        private TextView temputure_;
        private TextView speed_;
        private TextView typeOfSpeed_;
        private TextView voter_Location_;
        private ImageView cloud_Image_;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            voter_Image_ = itemView.findViewById(R.id.votinglistimage);
            voter_Name_ = itemView.findViewById(R.id.votername);
            temputure_ = itemView.findViewById(R.id.temperatureindegree);
            speed_ = itemView.findViewById(R.id.tvspeed);
            typeOfSpeed_ = itemView.findViewById(R.id.typeofspeed);
            voter_Location_ = itemView.findViewById(R.id.locationname);
            cloud_Image_ = itemView.findViewById(R.id.cloudyicon);

        }

        public void setData(int voterImage, String voter_name, String tempeture, String speed, String typeOfSpeed, String voterLocation, int cloudImage) {
            voter_Image_.setImageResource(voterImage);
            voter_Name_.setText(voter_name);
            temputure_.setText(tempeture);
            speed_.setText(speed);
            typeOfSpeed_.setText(typeOfSpeed);
            voter_Location_.setText(voterLocation);
            cloud_Image_.setImageResource(cloudImage);
        }
    }
}
