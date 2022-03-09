package software.carter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Log {
    private Path logFile;
    private ArrayList<Message> loggedMessages;

    class StartEnd {
        int start;
        int end;

        StartEnd(int... i) {
            this.start = i[0];
            this.end = i[1];
        }
    }

    public Log() {
        logFile = Path.of("log.txt");
        loggedMessages = new ArrayList<>();
    }

    private String filterInput(String input) {
        return input.replace("|", "I");
    }

    private ArrayList<Message> seperateMessages(String input) {
        ArrayList<Message> messages = new ArrayList<>();

        if (input.length() <= 1)
            return messages;
        
        int prevIndex = -1;
        ArrayList<Integer> closingBraces = new ArrayList<>();

        for (;;) {
            prevIndex = input.indexOf("]:", prevIndex + 1);
            if (prevIndex < 0)
                break;
            
            closingBraces.add(prevIndex);
        }

        int _prevIndex = -1;
        ArrayList<Integer> startingBraces = new ArrayList<>();

        for (;;) {
            _prevIndex = input.indexOf("[", _prevIndex + 1);
            if (_prevIndex < 0)
                break;

            startingBraces.add(_prevIndex);
        }


        for (int q = 0; q < startingBraces.size(); q++) {
            Message message = new Message();
            try {
                message.setSender(input.substring(startingBraces.get(q) + 1, closingBraces.get(q)));
                
                String msgBody;
                if (q + 1 >= startingBraces.size()) {
                    msgBody = input.substring(closingBraces.get(q) + 3, input.length());
                } else {
                    msgBody = input.substring(closingBraces.get(q) + 3, startingBraces.get(q+1));
                }

                message.setMessage(msgBody.replaceAll("\n", " ").strip());
                messages.add(message);
            } catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
       }

        return messages;
    }

    public void processInput(String input) {
        try (var writer = Files.newBufferedWriter(this.logFile, StandardCharsets.UTF_8, StandardOpenOption.APPEND))
        {
            ArrayList<Message> messages = this.seperateMessages(this.filterInput(input));

            for (Message msg: messages) {

                // This is horribly inefficient.
                for (Message logged : this.loggedMessages) {
                    if (logged.equals(msg))
                        msg.markAsDuplicate();
                }

                if (!msg.isDuplicate()) {
                    String writeStr = "";
                    writeStr += String.format("[%s]", msg.getSender());
                    for (int i = 0; i <= 12 - msg.getSender().length(); i++)
                        writeStr += " ";
                    writeStr += String.format("-> %s\n", msg.getMessage());

                    writer.write(writeStr);
                    this.loggedMessages.add(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}