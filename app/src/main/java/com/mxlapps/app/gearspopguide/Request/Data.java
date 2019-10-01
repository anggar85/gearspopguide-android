
package com.mxlapps.app.gearspopguide.Request;

import com.mxlapps.app.gearspopguide.Model.CommentsModel;
import com.mxlapps.app.gearspopguide.Model.DeckModel;
import com.mxlapps.app.gearspopguide.Model.PinModel;
import com.mxlapps.app.gearspopguide.Model.UserModel;

import java.util.ArrayList;

public class Data {

    private PinModel pin;
    private ArrayList<PinModel> pins;
    private ArrayList<DeckModel> decks;
    private ArrayList<CommentsModel> deckComments;
    private CommentsModel comment;
    private String msg;
    private boolean error;
    private UserModel user;
    private DeckModel deck;

    public CommentsModel getComment() {
        return comment;
    }

    public void setComment(CommentsModel comment) {
        this.comment = comment;
    }

    public ArrayList<CommentsModel> getDeckComments() {
        return deckComments;
    }

    public void setDeckComments(ArrayList<CommentsModel> deckComments) {
        this.deckComments = deckComments;
    }

    public ArrayList<DeckModel> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<DeckModel> decks) {
        this.decks = decks;
    }

    public DeckModel getDeck() {
        return deck;
    }

    public void setDeck(DeckModel deck) {
        this.deck = deck;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ArrayList<PinModel> getPins() {
        return pins;
    }

    public void setPins(ArrayList<PinModel> pins) {
        this.pins = pins;
    }

    public PinModel getPin() {
        return pin;
    }

    public void setPin(PinModel pin) {
        this.pin = pin;
    }


}
