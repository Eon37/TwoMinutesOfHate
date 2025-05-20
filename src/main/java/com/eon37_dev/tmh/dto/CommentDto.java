package com.eon37_dev.tmh.dto;

public class CommentDto {
  private Long id;
  private PostDto comment;

  public CommentDto(Long id, PostDto comment) {
    this.id = id;
    this.comment = comment;
  }

  public Long getId() {
    return id;
  }

  public PostDto getComment() {
    return comment;
  }

  public void setComment(PostDto comment) {
    this.comment = comment;
  }
}
