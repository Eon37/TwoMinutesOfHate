package com.eon37_dev.tmh.controllers;

import com.eon37_dev.tmh.dto.CommentDto;
import com.eon37_dev.tmh.dto.DtoMapper;
import com.eon37_dev.tmh.dto.NewCommentDto;
import com.eon37_dev.tmh.model.ModelAndViewUtils;
import com.eon37_dev.tmh.model.Post;
import com.eon37_dev.tmh.dto.PostDto;
import com.eon37_dev.tmh.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping(path = {"/", "/api/posts"})
  public ModelAndView getAll(HttpServletRequest request, HttpServletResponse response) {
    List<PostDto> postDtos = DtoMapper.mapPostListFilterBySession(request.getSession().getId(), postService.getPosts());

    return ModelAndViewUtils.buildView("index", Map.of("posts", postDtos), request);
  }

  @PostMapping(path = "/api/posts/create")
  public ModelAndView newPost(RedirectAttributes redirectAttributes, HttpServletRequest request,
                           @CookieValue(value = "theme", required = false) String theme,
                           @RequestParam(name = "text") String text,
                           @RequestParam(name = "anonymous", defaultValue = "false") boolean anonymous) {
    postService.newPost(request.getSession().getId(), text, anonymous);
    List<PostDto> postDtos = DtoMapper.mapPostListFilterBySession(request.getSession().getId(), postService.getPosts());

    return ModelAndViewUtils.buildRedirect("/", Map.of("posts", postDtos), redirectAttributes, request);
  }

  @ResponseBody
  @PostMapping(path = "/api/posts/{id}/like-async")
  public Integer likePostAsync(HttpSession httpSession, @PathVariable(name = "id") String id) {
    return postService.likePost(httpSession.getId(), Long.parseLong(id));
  }

  @ResponseBody
  @PostMapping(path = "/api/posts/{id}/comments/{commentId}/like-async")
  public Integer likeCommentAsync(HttpSession httpSession,
                                  @PathVariable(name = "id") String id,
                                  @PathVariable(name = "commentId") String commentId) {
    return postService.likeComment(httpSession.getId(), Long.parseLong(id), Long.parseLong(commentId));
  }

  @ResponseBody
  @PostMapping(path = "/api/posts/{id}/comments")
  public CommentDto commentPostAsync(HttpSession httpSession,
                                     @PathVariable(name = "id") String id,
                                     @RequestBody NewCommentDto commentDto) {
    Map<Long, Post> comment = postService.newComment(httpSession.getId(), Long.parseLong(id), commentDto.getComment());
    return DtoMapper.mapComments(comment).iterator().next();
  }
}
