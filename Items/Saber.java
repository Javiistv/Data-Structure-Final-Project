package Items;

public class Saber extends Sword {

    public Saber(String info, String name, String id, int attack, int lifeSpan,
            String effect) {
        super(info, name, id, attack, lifeSpan, effect);
        setType("saber");

    }

}
