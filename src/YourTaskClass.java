public class YourTaskClass extends Task implements Comparable<YourTaskClass> {
    private String taskName;
    private String taskDescription;
    private String taskDate;
    private int intTaskDate;

    public YourTaskClass(String taskName, String taskDescription, String taskDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
    }
    public void setIntTaskDate() {
        try {
            String[] date = taskDate.split(" - ");
            int day = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int year = Integer.parseInt(date[2]);

            intTaskDate = (year * 10000) + (month * 100) + day;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskDate() {
        return taskDate;
    }

    @Override
    public String toString() {
        return taskName;
    }

    public int compareTo(YourTaskClass o) {
        return Integer.compare(this.getIntTaskDate(), o.getIntTaskDate());
    }

    public int getIntTaskDate() {
        return intTaskDate;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
        setIntTaskDate();
    }
}