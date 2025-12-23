package Misc;

public abstract class Classes {

    protected String description;
    protected boolean unlocked;
    protected String id;

    public Classes(String description, boolean unlocked, String id) {
        setDescription(description);
        setUnlocked(unlocked);
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
