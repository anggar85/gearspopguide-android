package com.mxlapps.app.gearspopguide.Adapter;

import android.content.Context;
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
//        holder.textView_pin_name.setText(hero.getName());
        Picasso.get().load(hero.getSmallImage()).into(holder.smallImage);
    }


    @Override
    public int getItemCount() {
        return pinModels.size();
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView textView_tier;
//        TextView textView_pin_name;
        ImageView smallImage;
        ImageView imageView_holdertenporal;
        CardView cardView_pin_item;

        public HeroViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);


            smallImage = itemView.findViewById(R.id.imageView_hero_image);
//            textView_pin_name = itemView.findViewById(R.id.textView_pin_name);
            textView_tier = itemView.findViewById(R.id.textView_tier);
            imageView_holdertenporal = itemView.findViewById(R.id.imageView_holdertenporal);
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


}
