package com.mxlapps.app.gearspopguide.Utils;

import com.mxlapps.app.gearspopguide.Model.PinModel;

import java.util.ArrayList;

public class FilterAndSort {

    private static final String TAG = "filtronchilo";

    public static ArrayList<PinModel> filterHeroes(ArrayList<PinModel> heroList, Filters filters) {
        ArrayList<PinModel> heroesListFiltered = new ArrayList<>();
        ArrayList<Integer> heroesToRemove = new ArrayList<Integer>();

////        if (heroList != null){
////            //Heroes to filter
////            for(PinModel hero : heroList) {
////                String rarity = hero.getRarity();
////                if((rarity.compareToIgnoreCase("Legendary+")  == 0 && filters.filterRarity.legendary ||
////                        rarity.compareToIgnoreCase("Ascended")  == 0 && filters.filterRarity.ascend ||
////                        rarity.compareToIgnoreCase("Common")  == 0 && filters.filterRarity.common ) ||
////                        (!filters.filterRarity.legendary && !filters.filterRarity.ascend && !filters.filterRarity.common)
////                )
////                {
////                    heroesListFiltered.add(hero);
////                }
////            }
////
////            for (PinModel hero: heroesListFiltered){
////                String classe = hero.getClasse();
////
////                if (
////                        (
////                                classe.compareToIgnoreCase("Agility")       ==  0 && !filters.filterClasses.agility ||
////                                        classe.compareToIgnoreCase("Intelligence")  ==  0 && !filters.filterClasses.intelligence ||
////                                        classe.compareToIgnoreCase("Strength")      ==  0 && !filters.filterClasses.strength
////                        )
////                                &&
////                                !(!filters.filterClasses.agility && !filters.filterClasses.intelligence && !filters.filterClasses.strength))
////                {
////                    heroesToRemove.add(heroesListFiltered.indexOf(hero));
////                }
////            }
////
////            //Removing Heroes
////            Collections.reverse(heroesToRemove);
////            for(Integer hero : heroesToRemove) {
////                heroesListFiltered.remove(hero.intValue());
////            }
////            heroesToRemove.clear();
////
////            for (PinModel hero: heroesListFiltered){
////                String race_name = hero.getRace_name().toUpperCase();
////                if (
////                        (
////                                race_name.compareToIgnoreCase("CELESTIAL")      ==  0 && !filters.filterRaceName.celestial      ||
////                                        race_name.compareToIgnoreCase("WILDER")        ==  0 && !filters.filterRaceName.wilders        ||
////                                        race_name.compareToIgnoreCase("MAULER")        ==  0 && !filters.filterRaceName.maulers        ||
////                                        race_name.compareToIgnoreCase("HYPOGEAN")       ==  0 && !filters.filterRaceName.hypogean       ||
////                                        race_name.compareToIgnoreCase("GRAVEBORN")      ==  0 && !filters.filterRaceName.graveborn      ||
////                                        race_name.compareToIgnoreCase("LIGHTBEARER")   ==  0 && !filters.filterRaceName.lightbearers
////                        )
////                                &&
////                                !(
////                                        !filters.filterRaceName.celestial   &&
////                                                !filters.filterRaceName.wilders     &&
////                                                !filters.filterRaceName.maulers     &&
////                                                !filters.filterRaceName.hypogean    &&
////                                                !filters.filterRaceName.graveborn   &&
////                                                !filters.filterRaceName.lightbearers
////                                )
////                )
////                {
////                    heroesToRemove.add(heroesListFiltered.indexOf(hero));
////                }
////            }
////
////            //Removing Heroes
////            Collections.reverse(heroesToRemove);
////            for(Integer hero : heroesToRemove) {
////                heroesListFiltered.remove(hero.intValue());
////            }
////            heroesToRemove.clear();
//
//            return heroesListFiltered;
//
//        }else{
            return heroesListFiltered;
//        }
    }


}
