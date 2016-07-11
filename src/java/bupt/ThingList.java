/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bupt;

/**
 *
 * @author holysjl
 */
public class ThingList {
    private int extraMoney;
    private boolean value1;
    private boolean value2;
    private boolean value3;
    private boolean value4;
    private boolean value5;
    
    public ThingList(){}
    
    public int getExtraMoney() {
        return extraMoney;
    }

    public void setExtraMoney(int extraMoney) {
        this.extraMoney = extraMoney;
    }
    
    public void addExtra(boolean value,int _money)
    {
        if (value == true) {
            extraMoney += _money;
        }
    }
    public boolean getValue1() {
        return value1;
    }

    public void setValue1(boolean value1) {
        this.value1 = value1;
    }

    public boolean getValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    public boolean getValue3() {
        return value3;
    }

    public void setValue3(boolean value3) {
        this.value3 = value3;
    }

    public boolean getValue4() {
        return value4;
    }

    public void setValue4(boolean value4) {
        this.value4 = value4;
    }

    public boolean getValue5() {
        return value5;
    }

    public void setValue5(boolean value5) {
        this.value5 = value5;
    }
    
}
