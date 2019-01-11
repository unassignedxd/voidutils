package com.github.unassignedxd.voidutils.main.util.infusion;

import com.github.unassignedxd.voidutils.api.infusion.modifier.IVoidModifier;

/**
 * This is the infusion itself, what stores all the values from the modifiers that went into the system.
 */
public class VoidInfusion {

    protected double voidAmount;
    protected double powerAmount;
    protected double depletionAmount;
    protected int energyUse;

    public VoidInfusion(double voidAmount, double powerAmount, double depletionAmount, int energyUse) {
        this.voidAmount = voidAmount;
        this.powerAmount = powerAmount;
        this.depletionAmount = depletionAmount;
        this.energyUse = energyUse;
    }

    public VoidInfusion(IVoidModifier... modifiers){
        double tempVoid = 0;
        double tempPower = 0;
        double tempDepletion = 0;
        int tempEnergyUse = 0;

        for(IVoidModifier modifier : modifiers){
            if(modifier.getVoidAmount() + tempVoid < 0) {
                tempVoid = 0;
            }else { tempVoid += modifier.getVoidAmount(); }

            if(modifier.getPowerAmount() + tempPower < 0) {
                tempPower = 0;
            }else { tempPower += modifier.getPowerAmount(); }

            if(modifier.getDepletionAmount() + tempDepletion < 0) {
                tempDepletion = 0;
            }else { tempDepletion += modifier.getDepletionAmount(); }

            if(modifier.getEnergyUse() + tempEnergyUse < 0) {
                tempEnergyUse = 0;
            }else { tempEnergyUse += modifier.getEnergyUse(); }

        }

        this.voidAmount = tempVoid;
        this.powerAmount = tempPower;
        this.depletionAmount = tempDepletion;
        this.energyUse = tempEnergyUse;
    }

    public boolean isUseless() { return (this.voidAmount == 0 && this.powerAmount == 0 && this.depletionAmount == 0); }

    public double getVoidAmount() {
        return voidAmount;
    }

    public double getPowerAmount() {
        return powerAmount;
    }

    public double getDepletionAmount() {
        return depletionAmount;
    }

    public int getEnergyUse() {
        return energyUse;
    }

    public void setDepletionAmount(double depletionAmount) {
        this.depletionAmount = depletionAmount;
    }

    public void setEnergyUse(int energyUse) {
        this.energyUse = energyUse;
    }

    public void setPowerAmount(double powerAmount) {
        this.powerAmount = powerAmount;
    }

    public void setVoidAmount(double voidAmount) {
        this.voidAmount = voidAmount;
    }
}
