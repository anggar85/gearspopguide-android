package com.mxlapps.app.gearspopguide.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PinsDeckAdapter extends RecyclerView.Adapter<PinsDeckAdapter.HeroViewHolder> {

    private ArrayList<PinModel> pinModels;
    private ArrayList<PinModel> pinModelsFull;
    private OnItemClickListener mlistener;
    Context ctx;
    int modo = 1;


    public void SetOnItemClickListener (OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }


    public interface OnItemClickListener {
        void onHeroCardClick(int position);
    }



    public PinsDeckAdapter(ArrayList<PinModel> pinModels, Context context, int modo) {
        this.pinModels = pinModels;
        this.ctx = context;
        this.modo = modo;
        pinModelsFull = new ArrayList<>(pinModels);
    }

    @NonNull
    @Override
    public PinsDeckAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hero_deck, parent, false);
        return new HeroViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final PinsDeckAdapter.HeroViewHolder holder, int position) {

        PinModel hero = pinModels.get(position);
        holder.textView_pin_cost.setText(String.valueOf(hero.getCost()));
        holder.textView_pin_role.setText(hero.getRole());
        holder.textView_pin_type.setText(hero.getType());
        Picasso.get().load(hero.getSmallImage()).into(holder.smallImage);

        pintaRole(hero.getRole(), holder);
        pintaType(hero.getType(), holder);
    }



    @Override
    public int getItemCount() {
        return pinModels.size();
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView textView_pin_cost;
        TextView textView_pin_role;
        TextView textView_pin_type;
        ImageView smallImage;
        CardView cardView_pin_item;

        public HeroViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);


            smallImage = itemView.findViewById(R.id.imageView_hero_image);
            textView_pin_cost = itemView.findViewById(R.id.textView_pin_cost);
            textView_pin_role = itemView.findViewById(R.id.textView_pin_role);
            textView_pin_type = itemView.findViewById(R.id.textView_pin_type);
            cardView_pin_item = itemView.findViewById(R.id.cardView_section_item);

            cardView_pin_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onHeroCardClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void pintaRole(String role, HeroViewHolder holder){
        int color;
        switch (role){
            case "Tank":
                color = Color.parseColor("#a64d79");
                holder.textView_pin_role.setBackgroundColor(color);
                break;
            case "Threat":
                color = Color.parseColor("#260d8a");
                holder.textView_pin_role.setBackgroundColor(color);
                break;
            case "Scout":
                color = Color.parseColor("#b314c9");
                holder.textView_pin_role.setBackgroundColor(color);
                break;
            case "Removal":
                color = Color.parseColor("#76a21a");
                holder.textView_pin_role.setBackgroundColor(color);
                break;
            case "Utility":
                color = Color.parseColor("#5c5c5c");
                holder.textView_pin_role.setBackgroundColor(color);
                break;
            case "Bruiser":
                color = Color.parseColor("#df3030");
                holder.textView_pin_role.setBackgroundColor(color);
                break;
        }
    }

    public void pintaType(String type, HeroViewHolder holder){
        int color;
        switch (type){
            case "Common":
                color = Color.parseColor("#32cd32");
                holder.textView_pin_type.setBackgroundColor(color);
                break;
            case "Rare":
                color = Color.parseColor("#bb88bb");
                holder.textView_pin_type.setBackgroundColor(color);
                break;
            case "Epic":
                color = Color.parseColor("#C51162");
                holder.textView_pin_type.setBackgroundColor(color);
                break;
            case "Legendary":
                color = Color.parseColor("#ffae42");
                holder.textView_pin_type.setBackgroundColor(color);
                break;
        }
    }


}
