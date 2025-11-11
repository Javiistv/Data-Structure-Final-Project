
package Logic;
import Items.*;
import Characters.*;
import Misc.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;

public class Game {
    private File save;
    private File archives;
    private ArrayList<NPC> characters;
    private Queue<Task> tasks;
    private Deque<Task> completedTasks;
    private ArrayList<Item> items;


    public File getSave() {
        return save;
    }

    public void setSave(File save) {
        this.save = save;
    }

    public ArrayList<NPC> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<NPC> characters) {
        this.characters = characters;
    }

    public Queue<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Queue<Task> tasks) {
        this.tasks = tasks;
    }

    public Deque<Task> getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(Deque<Task> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }


}
