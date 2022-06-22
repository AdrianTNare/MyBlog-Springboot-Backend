package com.sir_ad.myBlog_backend.utiliy;

import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;

public class
UserPager {

    private final Page<User> usersPage;

    public UserPager(Page<User> usersPage) {
        this.usersPage = usersPage;
    }

    public int getPageIndex(){
        return usersPage.getNumber() + 1;
    }

    public int getPageSize(){
        return usersPage.getSize();
    }

    public boolean getHasNext(){
        return usersPage.hasNext();
    }

    public boolean getHasPrevious(){
        return usersPage.hasPrevious();
    }

    public int getTotalPages(){
        return usersPage.getTotalPages();
    }

    public Long getTotalElements(){
        return usersPage.getTotalElements();
    }

    public Page<User> getUsers(){
        return usersPage;
    }

    public boolean indexOutOfBounds(){
        return getPageIndex() < 0 || getPageIndex() > getTotalElements() ;
    }
}
