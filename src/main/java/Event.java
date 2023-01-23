public class Event extends Task {
    protected String from;
    protected String to;
    protected String date;

    public Event(String description, String date)  {
        super(description);
        String[] fromandto = date.split("-");
        this.from =  fromandto[0];
        this.from = from.replaceAll("from", "");
        this.date = date;
        this.to = fromandto[1];
        this.to = to.replaceAll("to","");
        Task.actions += 1;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from:" + from + "to:" + to + ")";
    }
    @Override
    public String toSaveString() {
        return String.format("event || %s || %s || %s ", super.toSaveString(), this.description, this.date);
    }

}
