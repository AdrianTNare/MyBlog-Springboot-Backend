package com.sir_ad.myBlog_backend.utiliy;

import com.sir_ad.myBlog_backend.model.Post;
import org.springframework.data.domain.Page;

public class Pager {

    private final Page<Post> postsPage;

    public Pager(Page<Post> postsPage) {
        this.postsPage = postsPage;
    }

    public int getPageIndex(){
        return postsPage.getNumber() + 1;
    }

    public int getPageSize(){
        return postsPage.getSize();
    }

    public boolean getHasNext(){
        return postsPage.hasNext();
    }

    public boolean getHasPrevious(){
        return postsPage.hasPrevious();
    }

    public int getTotalPages(){
        return postsPage.getTotalPages();
    }

    public Long getTotalElements(){
        return postsPage.getTotalElements();
    }

    public Page<Post> getPosts(){
        return postsPage;
    }

    public boolean indexOutOfBounds(){
        return getPageIndex() < 0 || getPageIndex() > getTotalElements() ;
    }
}
