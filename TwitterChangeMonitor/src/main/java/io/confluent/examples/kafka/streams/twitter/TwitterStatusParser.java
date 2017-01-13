package io.confluent.examples.kafka.streams.twitter;

import io.confluent.examples.kafka.connect.twitter.User;
import org.apache.kafka.streams.KeyValue;
import io.confluent.examples.kafka.connect.twitter.Status;
import io.confluent.examples.kafka.twitter.avro.TwitterChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chris on 10/17/16.
 */
public class TwitterStatusParser {
  private static Logger log = LoggerFactory.getLogger(io.confluent.examples.kafka.streams.twitter.TwitterStatusParser.class);

//  public static KeyValue<String, String> getRawStatus(User user, Status status) {
//    return new KeyValue<String, String>(status.getChannel(), status.getStatus());
//
//  }

  public static KeyValue<String, TwitterChange> parseStatus(String id , Status status) {
    try {
      TwitterChange change = parseStatus(status.getText());

      change.setCreatedAt(status.getCreatedAt());
      change.setUsername(status.getUser().getName());
      change.setLocation(status.getUser().getLocation());
      change.setTimeZone(status.getUser().getTimeZone());
      change.setLang(status.getUser().getLang());
      change.setFollowersCount(status.getUser().getFollowersCount());

      return new KeyValue<String, TwitterChange>(id, change);
    } catch (IllegalStateException e) {
      return new KeyValue<>(null, null);
    }


  }

  public static TwitterChange parseStatus(String text) {

//    String pattern ="\\[\\[(.*)\\]\\]\\s(.*)\\s(.*)\\s\\*\\s(.*)\\s\\*\\s\\(([\\+|\\-].\\d*)\\)\\s?(.*)?";
//    Pattern twitPattern = Pattern.compile(pattern);
//    Matcher matcher = wikiPattern.matcher(text);
//
//    matcher.matches();
    TwitterChange change = new TwitterChange();
    change.setText(text);


//    change.setWikipage(matcher.group(1));
//    change.setDiffurl(matcher.group(3));
//    change.setUsername(matcher.group(4));
//    change.setBytechange(Integer.parseInt(matcher.group(5)));
//    change.setCommitmessage(matcher.group(6));
////    Set Flags
//    change.setIsnew(matcher.group(2).contains("N"));
//    change.setIsminor(matcher.group(2).contains("M"));
//    change.setIsunpatrolled(matcher.group(2).contains("!"));
//    change.setIsbot(matcher.group(2).contains("B"));

    return change;

  }
}
