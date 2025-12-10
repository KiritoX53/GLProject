package gl.data;

public class Goal {
    private String name;
    private String description;
    private boolean achieved;

    public Goal(String name, String description) {
        this.name = name;
        this.description = description;
        this.achieved = false;
    }

    public Goal(){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }
}
