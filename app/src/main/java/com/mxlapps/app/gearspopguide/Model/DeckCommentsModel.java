package com.mxlapps.app.gearspopguide.Model;

public class DeckCommentsModel {

    private Integer id;
    private String usuario;
    private String comment;
    private Integer deck_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(Integer deck_id) {
        this.deck_id = deck_id;
    }
}
