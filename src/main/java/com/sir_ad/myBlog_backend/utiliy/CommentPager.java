package com.sir_ad.myBlog_backend.utiliy;

import com.sir_ad.myBlog_backend.model.Comment;
import org.springframework.data.domain.Page;

// only for development testing
public class CommentPager {

    private final Page<Comment> commentsPage;

    public CommentPager(Page<Comment> commentsPage) {
        this.commentsPage = commentsPage;
    }

    public int getPageIndex(){
        return commentsPage.getNumber() + 1;
    }

    public int getPageSize(){
        return commentsPage.getSize();
    }

    public boolean getHasNext(){
        return commentsPage.hasNext();
    }

    public boolean getHasPrevious(){
        return commentsPage.hasPrevious();
    }

    public int getTotalPages(){
        return commentsPage.getTotalPages();
    }

    public Long getTotalElements(){
        return commentsPage.getTotalElements();
    }

    public Page<Comment> getComments(){
        return commentsPage;
    }

    public boolean indexOutOfBounds(){
        return getPageIndex() < 0 || getPageIndex() > getTotalElements() ;
    }
}
