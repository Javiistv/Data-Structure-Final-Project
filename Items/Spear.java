
package Items;

public class Spear extends Weapon{
     public Spear(String info, String name, String id, int attack, int lifeSpan,
            String effect){
        super(info, name, id, attack, lifeSpan, effect);
        setType("spear");
     }
}
