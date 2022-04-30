package me.ddtincoming.hexicon.capabilities;

public class MobDataFactory implements IMobData {

    private boolean gen = false;

    public MobDataFactory() {
    }


    @Override
    public boolean getGen() {
        return gen;
    }




    @Override
    public void setGen(boolean amount) {this.gen = amount;}



}
