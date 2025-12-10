package gl.data;

public class Transaction {
    private float cost;
    private String date;
    private String name;
    private String desciption;
    public Transaction(float cost, String date, String name, String desciption) {
        this.cost = cost;
        this.date = date;
        this.name = name;
        this.desciption = desciption;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }


}
