package com.lms.service.impl;

import com.lms.controller.LmsController;
import com.lms.service.LmsService;
import com.lms.vo.LmsVo;
import com.lms.vo.UserVo;

import java.util.Observer;

public class LmsServiceImpl implements LmsService {
    private LmsController lmsController = new LmsController();

    @Override
    public void borrowBook(Observer observer, LmsVo lmsVo, UserVo userVo) {
        //TODO 구현
    }

    @Override
    public void addUser(Observer observer, UserVo vo) {
        //TODO 구현
    }

    @Override
    public void returnBook(Observer observer, LmsVo lmsVo, UserVo userVo) {
        //TODO 구현
    }

    @Override
    public void addBook(Observer observer, LmsVo vo) {
        //TODO 구현
    }

    @Override
    public void searchBook(Observer observer, LmsVo vo) {
        //TODO 구현
    }
}
