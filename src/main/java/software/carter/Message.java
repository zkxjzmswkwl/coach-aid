package software.carter;

public class Message {
    private String sender;
    private String message;
    private String previousMessage;
    private String nextMessage;
    private boolean duplicate;

    public Message(String input) {
        if (!input.contains("[") || !input.contains("]")) {
            this.sender = "nil";
            this.message = "nil";
        } else {
            this.sender = input.split("\\[")[1].split("\\]")[0];
            this.message = input.substring(input.indexOf("]:") + 3, input.length());
        }
    }

    public Message() {
        this.duplicate = false;
    }

    public String getSender()   { return this.sender;           }
    public String getMessage()  { return this.message;          }
    public String getPrevious() { return this.previousMessage;  }
    public String getNext()     { return this.nextMessage;      }

    public boolean isDuplicate() { return this.duplicate;   }

    public Message setSender(String input) {
        this.sender = input;
        return this;
    }

    public Message setMessage(String input) {
        this.message = input.replace("\n", " ");
        return this;
    }

    public Message setPrevious(String input) {
        this.previousMessage = input;
        return this;
    }

    public Message setNext(String input) {
        this.nextMessage = input;
        return this;
    }

    public Message markAsDuplicate() {
        this.duplicate = true;
        return this;
    }

    public boolean equals(Message m) {
        if (this.sender.equalsIgnoreCase(m.getSender()) && this.message.equalsIgnoreCase(m.getMessage())) {
            return true;
        } 
        return false;
    }
}
