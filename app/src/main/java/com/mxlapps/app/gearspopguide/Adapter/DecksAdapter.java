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
        holder.author.setText(deck.getAuthor());
        holder.cost.setText("Cost: "+deck.getCost());
        holder.votes.setText("Votes: "+String.valueOf(deck.getVotes()));

//        Picasso.get().load(deck.getSmallImage()).into(holder.smallImage);
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
        ImageView smallImage;
        CardView cardView_pin_item;

        public HeroViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            smallImage = itemView.findViewById(R.id.imageView_hero_image);
            name = itemView.findViewById(R.id.textView_namedeck);
            author = itemView.findViewById(R.id.textView_author);
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
