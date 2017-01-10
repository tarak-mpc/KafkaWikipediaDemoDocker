package org.cmatta.kafka.streams.wikipedia;

import org.cmatta.kafka.streams.wikipedia.avro.WikipediaChange;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by chris on 10/31/16.
 */
public class WikipediaMessageParserTest {
  Logger log = LoggerFactory.getLogger(WikipediaMessageParserTest.class);
  private List<String> messages;


  @Test
  public void testParseMessageWithAllFields() throws Exception {
    String message = "[[Page Title]] MBN! https://en.wikipedia.org/w/index.php?diff=747145198&oldid=747144021 * Username * (+17) This is the commit message.";
    WikipediaChange change = WikipediaMessageParser.parseMessage(message);

    assertEquals("Page Title", change.getWikipage());
    assertEquals(true, change.getIsbot());
    assertEquals(true, change.getIsminor());
    assertEquals(true, change.getIsnew());
    assertEquals(true, change.getIsunpatrolled());
    assertEquals(true, change.getDiffurl().startsWith("https://"));
    assertEquals("Username", change.getUsername());
    assertEquals(17, (int) change.getBytechange());
    assertEquals("This is the commit message.", change.getCommitmessage());

  }

  @Test
  public void testParseMessageWithoutCommit() throws Exception {
    String message = "[[Page Title]] MBN! https://en.wikipedia.org/w/index.php?diff=747145198&oldid=747144021 * Username * (+17)";
    WikipediaChange change = WikipediaMessageParser.parseMessage(message);

    assertEquals("", change.getCommitmessage());
  }

  @Test
  public void testParseMessageWithNegativeByte() throws Exception {
    String message = "[[Page Title]] MBN! https://en.wikipedia.org/w/index.php?diff=747145198&oldid=747144021 * Username * (-17)";
    WikipediaChange change = WikipediaMessageParser.parseMessage(message);

    assertEquals(-17, (int) change.getBytechange());
  }

}