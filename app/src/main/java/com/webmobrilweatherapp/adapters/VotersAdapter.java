package com.webmobrilweatherapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.webmobrilweatherapp.model.SearchProfileModel;
import com.webmobrilweatherapp.R;
import com.webmobrilweatherapp.activities.ViewPagerActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class VotersAdapter extends RecyclerView.Adapter<VotersAdapter.Myviewholder> {
    Context context;
    private List<SearchProfileModel> profileModelsdata;

    public VotersAdapter(Context context, List<SearchProfileModel> profileModelsdata) {
        this.context = context;
        this.profileModelsdata = profileModelsdata;
    }

    @NotNull
    @Override
    public VotersAdapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profilesearchrecycle, parent, false);
        VotersAdapter.Myviewholder myviewholder = new VotersAdapter.Myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull VotersAdapter.Myviewholder holder, int position) {

        holder.instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPagerActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileModelsdata.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView imgsearchlogopic, imguseraddline;
        TextView Christian, Liveinneapolis;
        ConstraintLayout instagram;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            imgsearchlogopic = itemView.findViewById(R.id.imgsearchlogopic);
            imguseraddline = itemView.findViewById(R.id.imguseraddline);
            Christian = itemView.findViewById(R.id.Christian);
            Liveinneapolis = itemView.findViewById(R.id.Liveinneapolis);
            instagram = itemView.findViewById(R.id.instagram);

        }

    }
}
