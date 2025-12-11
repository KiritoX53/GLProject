package gl.data;

public class Budget {
    private String name;
    private float amount;
    private String description;
    public Budget(String name, float amount, String description) {
        this.name = name;
        this.amount = amount;
        this.description = description;
    }
    
    public Budget() {
        
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

