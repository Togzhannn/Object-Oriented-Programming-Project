package model;

public class Message {

    private Employee sender;
    private Employee receiver;
    private String content;
    private boolean isRead;

    public Message(Employee sender, Employee receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.isRead = false;
    }

    public void mark() {
        this.isRead = true;
        System.out.println("Message from " + sender.getFullName() + " marked as read.");
    }

    @Override
    public String toString() {
        return "Message{from='" + sender.getFullName() + "', to='" + receiver.getFullName()
                + "', content='" + content + "', isRead=" + isRead + "}";
    }

    public Employee getSender()   { return sender; }
    public Employee getReceiver() { return receiver; }
    public String getContent()    { return content; }
    public boolean isRead()       { return isRead; }
}
