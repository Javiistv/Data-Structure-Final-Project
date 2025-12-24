package Items;

public class Fist extends Weapon {

    public Fist(String info, String name, String id, int attack, int lifeSpan,
            String effect) {
        super(info, name, id, attack, lifeSpan, effect);
        setType("fist");
    }

}
