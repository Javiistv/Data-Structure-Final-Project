package Characters;

import Items.*;
import java.util.ArrayList;
import Misc.*;
import javafx.scene.image.Image;

public class Monster extends NPC {

    protected Weapon actualWeapon;
    protected int attack;
    protected int life;
    protected int actualLife;
    protected int defense;
    protected ArrayList<Item> loot;
    protected int exp;
    protected String encounter;
    protected int money;

    public Monster(Weapon actualWeapon, int attack,int defense, String name, String sprite, int life, int actualLife,
            int exp,int money, String encounter) {
        super(name, sprite);
        setActualWeapon(actualWeapon);
        setAttack(attack);
        setDefense(defense);
        setActualLife(actualLife);
        setLife(life);
        this.loot = new ArrayList<>();
        setExp(exp);
        setEncounter(encounter);
        setMoney(money);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    
    public String getEncounter() {
        return encounter;
    }

    public void setEncounter(String encounter) {
        this.encounter = encounter;
    }
    
    

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getActualLife() {
        return actualLife;
    }

    public void setActualLife(int actualLife) {
        this.actualLife = actualLife;
    }

    public Weapon getActualWeapon() {
        return actualWeapon;
    }

    public void setActualWeapon(Weapon actualWeapon) {
        this.actualWeapon = actualWeapon;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public ArrayList<Item> getLoot() {
        return loot;
    }

    public void setLoot(ArrayList<Item> loot) {
        this.loot = loot;
    }

    public ArrayList<String> getDialogue() {
        return dialogue;
    }

    public void setDialogue(ArrayList<String> dialogue) {
        this.dialogue = dialogue;
    }

}
