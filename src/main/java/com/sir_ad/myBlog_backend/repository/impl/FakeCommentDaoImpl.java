package com.sir_ad.myBlog_backend.repository.impl;

import com.sir_ad.myBlog_backend.model.Comment;
import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.repository.CommentDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FakeCommentDaoImpl implements CommentDao {
    private static List<Comment> DB = new ArrayList<>();

    @Override
    public Optional<Comment> addComment(UUID id, Comment comment) {
        DB.add(new Comment(id, comment.getBody(), comment.getUser(), comment.getPost()));
        return findById(id);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentsToPage(DB, pageable);
    }

    @Override
    public Page<Comment> findByPost(Post post, Pageable pageable) {
        List<Comment> userComments = DB.stream()
                .filter(comment -> comment.getPost().equals(post))
                .collect(Collectors.toList());
        return commentsToPage(userComments, pageable);
    }

    @Override
    public Page<Comment> findByUser(User user, Pageable pageable) {
        List<Comment> userComments = DB.stream()
                .filter(comment -> comment.getUser().equals(user))
                .collect(Collectors.toList());
        return commentsToPage(userComments, pageable);
    }

    @Override
    public Optional<Comment> findById(UUID id) {
        return DB.stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deleteComment(UUID id) {
        Optional<Comment> optionalComment = findById(id);
        if(optionalComment.isEmpty()){
            return 0;
        }
        DB.remove(optionalComment.get());
        return 1;
    }

    @Override
    public Optional<Comment> updateComment(UUID id, Comment comment) {
        return findById(id)
                .map(item -> {
                    Comment updatedComment = null;
                    int itemIndex = DB.indexOf(item);
                    if(itemIndex >=0 ) {
                        DB.set(itemIndex, new Comment(id, comment.getBody(), comment.getUser(), comment.getPost()));
                        updatedComment =  findById(id).orElse(null);
                    }
                    return updatedComment;
                });
    }

    Page<Comment> commentsToPage(List<Comment> commentList, Pageable pageable){
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), commentList.size());
        if(start > commentList.size()){
            return new PageImpl<>(new ArrayList<>(), pageable, commentList.size());
        }
        return new PageImpl<>(commentList.subList(start, end), pageable, commentList.size());
    }
}
