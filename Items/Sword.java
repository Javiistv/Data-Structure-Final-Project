package Items;

public class Sword extends Weapon {

    public Sword(String info, String name, String id, int attack, int lifeSpan,
            String effect) {
        super(info, name, id, attack, lifeSpan, effect);
        setType("sword");
    }
}
