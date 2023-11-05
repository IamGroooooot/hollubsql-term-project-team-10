package com.lms.service;

import com.lms.vo.LmsVo;
import com.lms.vo.UserVo;

import java.util.Observer;

public interface LmsService {
    /**
        도서 대출
     */
    public void borrowBook(Observer observer, LmsVo lmsVo, UserVo userVo);

    /**
     사용자 추가
     */
    public void addUser(Observer observer, UserVo vo);

    /**
     도서 반납
     */
    public void returnBook(Observer observer, LmsVo lmsVo, UserVo userVo);

    /**
     도서 추가
     */
    public void addBook(Observer observer, LmsVo vo);

    /**
     도서 검색
     */
    public void searchBook(Observer observer, LmsVo vo);

}
