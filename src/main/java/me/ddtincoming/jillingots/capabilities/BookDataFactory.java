package me.ddtincoming.jillingots.capabilities;

public class BookDataFactory implements IBookData {

    private boolean isSpellbook = false;

    public BookDataFactory() {
    }


    @Override
    public boolean getIsSpellbook() {
        return isSpellbook;
    }




    @Override
    public void setIsSpellbook(boolean x) {this.isSpellbook = x;}



}
