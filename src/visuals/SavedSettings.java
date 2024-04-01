package visuals;

import buildings.Building;
import debuffs.Debuffs;
import units.Unit;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedSettings implements Serializable {
    SettingsMenu settingsMenu;
    ArrayList<Building> availableBuildings;
    ArrayList<Building> buildingsOnField ;

    ArrayList<Unit> unitTechArrayList ;
    ArrayList<Unit> availableUnits;
    ArrayList<Debuffs> placedDebuffsBuffer;
    ArrayList<Building> upgratebleBuildingsOnField;
     int hpBuff;
     int armorBuff;
     double distanceOfWalkBuff;
     double debuffBuff;
     int damageBuff;
     boolean isAcademyOnField;
     boolean isMarketOnField;
     int workshopcounter;

     public SavedSettings(SettingsMenu settingsMenu, ArrayList<Building> availableBuildings, ArrayList<Building> buildingsOnField, ArrayList<Unit> unitTechArrayList,
                          ArrayList<Unit> availableUnits, ArrayList<Debuffs> placedDebuffsBuffer,ArrayList<Building> upgratebleBuildingsOnField,int hpBuff, int armorBuff, double distanceOfWalkBuff, double debuffBuff, int damageBuff, boolean isAcademyOnField, boolean isMarketOnField,
                          int workshopcounter){
         this.settingsMenu=settingsMenu;
         this.availableBuildings=availableBuildings;
         this.buildingsOnField=buildingsOnField;
         this.unitTechArrayList=unitTechArrayList;
         this.availableUnits =availableUnits;
         this.placedDebuffsBuffer=placedDebuffsBuffer;
         this.upgratebleBuildingsOnField=upgratebleBuildingsOnField;
         this.hpBuff=hpBuff;
         this.armorBuff = armorBuff;
         this.distanceOfWalkBuff=distanceOfWalkBuff;
         this.damageBuff=damageBuff;
         this.debuffBuff=debuffBuff;
         this.isAcademyOnField=isAcademyOnField;
         this.isMarketOnField=isMarketOnField;
         this.workshopcounter=workshopcounter;
     }
}
