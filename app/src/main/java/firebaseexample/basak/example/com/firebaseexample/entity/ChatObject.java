package firebaseexample.basak.example.com.firebaseexample.entity;


public class ChatObject {

    private String id;

    private String sender;

    private String message;

    public ChatObject(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public ChatObject(String id, String sender, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
