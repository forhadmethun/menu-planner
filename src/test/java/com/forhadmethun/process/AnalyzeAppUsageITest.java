package com.forhadmethun.process;

import com.forhadmethun.MenuPlannerApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyzeAppUsageITest extends MenuPlannerApplicationTests {

    @Autowired
    AnalyzeAppUsage appUsage;

    @Test
    void testActiveAnalyze() throws IOException {
        List<String> active = appUsage.analyze("active", "2022-01-10", "2022-01-16");
        assertEquals(3, active.size());
    }

    @Test
    void testSuperActiveAnalyze() throws IOException {
        List<String> active = appUsage.analyze("superactive", "2022-01-01", "2022-01-16");
        assertEquals(2, active.size());
    }

    @Test
    void testBoredAnalyze() throws IOException {
        List<String> active = appUsage.analyze("bored", "2022-01-14", "2022-01-16");
        assertEquals(2, active.size());
    }

}
