package com.eon37_dev.tmh.model;

import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Post {
  private final long id;
  private final String sessionId;
  private final boolean anonymous;
  private final String author;
  private final String text;
  private Set<String> likes;
  private final Map<Long, Post> comments;
  private long expire;

  private Post(long id, String sessionId, boolean anonymous, String author, String text, Set<String> likes, Map<Long, Post> comments, long expire) {
    this.id = id;
    this.sessionId = sessionId;
    this.anonymous = anonymous;
    this.author = author;
    this.text = text;
    this.likes = likes;
    this.comments = comments;
    this.expire = expire;
  }

  public long getId() {
    return id;
  }

  public String getSessionId() {
    return sessionId;
  }

  public boolean isAnonymous() {
    return anonymous;
  }

  public String getAuthor() {
    return author;
  }

  public String getText() {
    return text;
  }

  public Set<String> getLikes() {
    return likes;
  }

  public long getExpire() {
    return expire;
  }

  public Map<Long, Post> getComments() {
    return comments;
  }

  public int incrementLikes(String sessionId) {
    if (likes.contains(sessionId)) {
      likes.remove(sessionId);
    } else {
      likes.add(sessionId);
    }

    return likes.size();
  }

  public void incrementExpire() {
    this.expire = System.nanoTime() + TimeUnit.MINUTES.toNanos(2);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private long id;
    private String sessionId;
    private boolean anonymous;
    private String author;
    private String text = "";

    public Builder id(long id) {
      this.id = id;
      return this;
    }

    public Builder sessionId(String sessionId) {
      this.sessionId = sessionId;
      return this;
    }

    public Builder anonymous(boolean anonymous) {
      this.anonymous = anonymous;
      return this;
    }

    public Builder author(String author) {
      this.author = author;
      return this;
    }

    public Builder text(String text) {
      this.text = text;
      return this;
    }

    public Post build(boolean isPost) {
      Assert.isTrue(this.id > 0, "Post should have an id");
      Assert.hasText(this.sessionId, "Post should have a session id");
      Assert.hasText(this.author, "Author must not be empty");
      if (isPost) Assert.isTrue(this.text.length() >= 20, "Text should be at least 20 chars");

      return new Post(
              this.id,
              this.sessionId,
              this.anonymous,
              this.author,
              this.text,
              new HashSet<>(),
              new ConcurrentHashMap<>(),
              System.nanoTime() + TimeUnit.MINUTES.toNanos(2));
    }
  }
}


