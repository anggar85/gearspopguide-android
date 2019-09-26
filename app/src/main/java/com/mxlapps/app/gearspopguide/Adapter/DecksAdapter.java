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

import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DecksAdapter extends RecyclerView.Adapter<DecksAdapter.HeroViewHolder> {

    private ArrayList<DeckModel> deckModels;
    private OnItemClickListener mlistener;
    Context ctx;
    int modo = 1;


    public void SetOnItemClickListener (OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }


    public interface OnItemClickListener {
        void onDeckCardClick(int position);
    }



    public DecksAdapter(ArrayList<DeckModel> deckModels, Context context, int modo) {
        this.deckModels = deckModels;
        this.ctx = context;
        this.modo = modo;
    }

    @NonNull
    @Override
    public DecksAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck, parent, false);
        return new HeroViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DecksAdapter.HeroViewHolder holder, int position) {
        DeckModel deck = deckModels.get(position);
        holder.name.setText(deck.getName());
        holder.author.setText("by: "+deck.getAuthor());
        holder.cost.setText("Cost: "+deck.getCost());
        holder.votes.setText("Votes: "+String.valueOf(deck.getVotes()));


        Picasso.get().load(deck.getPin1()).into(holder.pin1);
        Picasso.get().load(deck.getPin2()).into(holder.pin2);
        Picasso.get().load(deck.getPin3()).into(holder.pin3);
        Picasso.get().load(deck.getPin4()).into(holder.pin4);
        Picasso.get().load(deck.getPin5()).into(holder.pin5);
        Picasso.get().load(deck.getPin6()).into(holder.pin6);
        Picasso.get().load(deck.getPin7()).into(holder.pin7);
        Picasso.get().load(deck.getPin8()).into(holder.pin8);
    }


    @Override
    public int getItemCount() {
        return deckModels.size();
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView author;
        TextView cost;
        TextView votes;
        ImageView pin1;
        ImageView pin2;
        ImageView pin3;
        ImageView pin4;
        ImageView pin5;
        ImageView pin6;
        ImageView pin7;
        ImageView pin8;
        CardView cardView_pin_item;

        public HeroViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            pin1 = itemView.findViewById(R.id.imageView_pin1);
            pin2 = itemView.findViewById(R.id.imageView_pin2);
            pin3 = itemView.findViewById(R.id.imageView_pin3);
            pin4 = itemView.findViewById(R.id.imageView_pin4);
            pin5 = itemView.findViewById(R.id.imageView_pin5);
            pin6 = itemView.findViewById(R.id.imageView_pin6);
            pin7 = itemView.findViewById(R.id.imageView_pin7);
            pin8 = itemView.findViewById(R.id.imageView_pin8);
            name = itemView.findViewById(R.id.textView_coment);
            author = itemView.findViewById(R.id.textView_user);
            cost = itemView.findViewById(R.id.textView_cost);
            votes = itemView.findViewById(R.id.textView_votes);
            cardView_pin_item = itemView.findViewById(R.id.cardView_section_item);

            cardView_pin_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onDeckCardClick(getAdapterPosition());
                    }
                }
            });
        }
    }

}
