import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected String by;
    protected LocalDateTime time;

    public Deadline(String description, String by) {
        super(description);
        by = by.replaceAll("by ","");

        /*by = by.replaceAll("/","");*/
        this.description = description;
        this.by = by;
        Task.actions += 1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime myDateObj = LocalDateTime.parse(by, formatter);
        this.time = myDateObj;
    }


    @Override
    public String toString() {
        String timeStr = this.time.format(DateTimeFormatter.ofPattern("HH:mm, MMM dd yyyy"));
        return "[D]" + super.toString() + " (by:" + timeStr + ")";
    }
    @Override
    public String toSaveString() {
        String timeStr = this.time.format(DateTimeFormatter.ofPattern("HH:mm, MMM dd yyyy"));
        return String.format("deadline || %s || %s || %s", super.toSaveString(), this.description, timeStr);
    }
}
