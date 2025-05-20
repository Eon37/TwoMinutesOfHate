package com.eon37_dev.tmh.dto;

import com.eon37_dev.tmh.model.Post;

import java.util.*;

public class PostDto {
  private final Long id;
  private final Boolean anonymous;
  private final String author;
  private final String text;
  private final int likes;
  private List<CommentDto> comments;

  public PostDto(Long id, Boolean anonymous, String author, String text, int likes, List<CommentDto> comments) {
    this.id = id;
    this.anonymous = anonymous;
    this.author = author;
    this.text = text;
    this.likes = likes;
    this.comments = comments;
  }

//  public PostDto withComments(Map<Long, Post> comments) {
//    this.comments = comments.entrySet().stream()
//            .sorted(Map.Entry.comparingByKey())
//            .map(postEntry -> new CommentDto(
//                    postEntry.getKey(),
//                    new PostDto(
//                            postEntry.getValue().getId(),
//                            postEntry.getValue().isAnonymous(),
//                            postEntry.getValue().getAuthor(),
//                            postEntry.getValue().getText(),
//                            postEntry.getValue().getLikes().size())))
//            .toList();
//    return this;
//  }

  public Long getId() {
    return id;
  }

  public Boolean getAnonymous() {
    return anonymous;
  }

  public String getAuthor() {
    return author;
  }

  public String getText() {
    return text;
  }

  public int getLikes() {
    return likes;
  }

  public List<CommentDto> getComments() {
    return comments;
  }
}
