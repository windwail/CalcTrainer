package windwail.ru.calctrainer;

/**
 * Created by icetusk on 11.12.17.
 */

public class Quiz {

    int x;
    int y;

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Quiz() {
    }

    public Quiz(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
