package com.mxlapps.app.gearspopguide.Utils;

public class Filters {

    public class FilterGameLevel{
        public Boolean early        = false;
        public Boolean mid          = false;
        public Boolean late         = false;
    }

    public class FilterClasses {
        public Boolean agility      = false;
        public Boolean intelligence = false;
        public Boolean strength     = false;
    }

    public class FilterRarity {
        public Boolean legendary    = false;
        public Boolean ascend       = false;
        public Boolean common       = false;
    }

    public class FilterRaceName {
        public Boolean wilders      = false;
        public Boolean maulers      = false;
        public Boolean lightbearers = false;
        public Boolean hypogean     = false;
        public Boolean celestial    = false;
        public Boolean graveborn    = false;
    }

    public class FilterRole {
        public Boolean tank         = false;
        public Boolean support      = false;
        public Boolean damage_dealer= false;
        public Boolean solo_carry   = false;
    }


    public FilterGameLevel filterGameLevel;
    public FilterClasses filterClasses;
    public FilterRaceName filterRaceName;
    public FilterRole filterRole;
    public FilterRarity filterRarity;

    public Filters()
    {
        filterGameLevel = new FilterGameLevel();
        filterRaceName  = new FilterRaceName();
        filterClasses   = new FilterClasses();
        filterRole      = new FilterRole();
        filterRarity    = new FilterRarity();
    }
}
