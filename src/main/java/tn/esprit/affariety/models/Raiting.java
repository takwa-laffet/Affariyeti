
package tn.esprit.affariety.models;
public class Raiting {
    private int user_id;
    private int product_id;
    private int value;
    private int id;

    public Raiting() {
    }

    public Raiting(int user_id, int product_id, int value, int id) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.value = value;
        this.id = id;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
