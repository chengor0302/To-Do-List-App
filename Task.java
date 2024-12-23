import java.time.LocalDate;

public class Task{
private String title;
private String description;
private LocalDate dueDate;
private boolean isCompleted;

public Task(String title, String description, LocalDate dueDate, boolean isCompleted){
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.isCompleted = isCompleted;
}

public String getTitle(){
    return title;
}

public String getDescription(){
    return description;
}

public LocalDate getDueDate(){
    return dueDate;
}

public boolean getTaskCompletedStatus(){
    return isCompleted;
}

public void setTitle(String title){
    this.title = title;
}

public void setDescription(String description){
    this.description = description;
}

public void setDueDate(LocalDate dueDate){
    this.dueDate = dueDate;
}

public void setCompletedStatus(boolean isCompleted){
    this.isCompleted = isCompleted;
}

@Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
