package com.lms.controller;

import com.lms.repository.DatabaseManager;
import com.lms.service.LmsService;
import com.lms.service.SearchDatabase;
import com.lms.service.UpdateDatabase;
import com.lms.ui.GUIApp;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

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