package org.cmatta.kafka.streams.wikipedia;

import org.apache.kafka.streams.KeyValue;
import org.cmatta.kafka.connect.irc.Message;
import org.cmatta.kafka.streams.wikipedia.avro.WikipediaChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chris on 10/17/16.
 */
public class WikipediaMessageParser {
  private static Logger log = LoggerFactory.getLogger(WikipediaMessageParser.class);

  public static KeyValue<String, String> getRawMessage(String key, Message message) {
    return new KeyValue<String, String>(message.getChannel(), message.getMessage());

  }

  public static KeyValue<String, WikipediaChange> parseMessage(String channel, Message message) {
    try {
      WikipediaChange change = parseMessage(message.getMessage());
      change.setCreatedat(message.getCreatedat());
      return new KeyValue<String, WikipediaChange>(change.getWikipage(), change);
    } catch (IllegalStateException e) {
      return new KeyValue<>(null, null);
    }


  }

  public static WikipediaChange parseMessage(String message) {

    String pattern ="\\[\\[(.*)\\]\\]\\s(.*)\\s(.*)\\s\\*\\s(.*)\\s\\*\\s\\(([\\+|\\-].\\d*)\\)\\s?(.*)?";
    Pattern wikiPattern = Pattern.compile(pattern);
    Matcher matcher = wikiPattern.matcher(message);

    matcher.matches();
    WikipediaChange change = new WikipediaChange();

    change.setWikipage(matcher.group(1));
    change.setDiffurl(matcher.group(3));
    change.setUsername(matcher.group(4));
    change.setBytechange(Integer.parseInt(matcher.group(5)));
    change.setCommitmessage(matcher.group(6));
//    Set Flags
    change.setIsnew(matcher.group(2).contains("N"));
    change.setIsminor(matcher.group(2).contains("M"));
    change.setIsunpatrolled(matcher.group(2).contains("!"));
    change.setIsbot(matcher.group(2).contains("B"));

    return change;

  }
}
