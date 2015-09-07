/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 *
 * @author CRE0260
 */
public class SondaWorkloadEmptySlot {
    private String system;
    private int countEmptySlot;

    public SondaWorkloadEmptySlot(String system, int countEmptySlot) {
        this.system = system;
        this.countEmptySlot = countEmptySlot;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public int getCountEmptySlot() {
        return countEmptySlot;
    }

    public void setCountEmptySlot(int countEmptySlot) {
        this.countEmptySlot = countEmptySlot;
    }
 
    
}
