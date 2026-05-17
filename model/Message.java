package model;

import java.io.Serializable;
/**
  Represents a message between two employees in the system.
  Message contains:
  sender, receiver, text content and read status.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    //Sender of the message
    private Employee sender;
    // Receiver of the message
    private Employee receiver;
    private String content;
    private boolean isRead;
    /**
     Creates a new message.
     @param sender message sender
     @param receiver message receiver
     @param content message text
     */
    public Message(Employee sender, Employee receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.isRead = false;
    }
    //Marks message as read.Updates read status and prints confirmation message.
    public void mark() {
        this.isRead = true;
        System.out.println("Message from " + sender.getFullName() + " marked as read.");
    }

    @Override
    public String toString() {
        return "Message from " + sender.getFullName() + " to " + receiver.getFullName() + 
            ": " + content + " (Read: " + isRead + ")";
    }

    public Employee getSender() { 
        return sender; 
    }

    public Employee getReceiver() { 
        return receiver; 
    }

    public String getContent() { 
        return content; 
    }

    public boolean isRead() { 
        return isRead; 
    }
}