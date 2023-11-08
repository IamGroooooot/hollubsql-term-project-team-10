package com.lms.controller;

import com.lms.service.LmsService;
import com.lms.ui.GUIApp;

public class LmsController {
    private LmsService model;
    private GUIApp view;

    public LmsController(LmsService model, GUIApp view) {
        this.model = model;
        this.view = view;
    }

    private void InitView() {

    }

}
