package com.lms.controller;

import com.main.lms.controller.LmsController;
import com.main.lms.repository.DatabaseManager;
import com.main.lms.service.LmsService;
import com.main.lms.service.SearchDatabase;
import com.main.lms.service.UpdateDatabase;
import com.main.lms.ui.GUIApp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LmsControllerTest {
    private LmsService model;
    private GUIApp view;
    LmsController controller;
    @BeforeEach
    void Setup() throws Exception {
        view = new GUIApp();
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.openDatabase("test");
        UpdateDatabase updateDatabase = new UpdateDatabase(databaseManager);
        SearchDatabase searchDatabase = new SearchDatabase(databaseManager);
        model = new LmsService(updateDatabase, searchDatabase);
        controller = new LmsController(model, view);
    }

    @DisplayName("Test action set")
    @Test
    void TestCommandPattern() throws Exception {
        controller.SetAction(1);
        Assertions.assertEquals(view.GetActionListener().getClass().getSimpleName(), "ExecuteAddUser");
    }

}