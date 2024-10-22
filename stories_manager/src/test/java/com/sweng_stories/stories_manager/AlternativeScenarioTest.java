package com.sweng_stories.stories_manager.domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlternativeScenarioTest {

    @Test
    void testAlternativeConstructorAndGetters() {
        List<String> items = Arrays.asList("item1", "item2");
        Alternative alternative = new Alternative("Go through the door", "with-items", items, 2L);

        assertEquals("Go through the door", alternative.getText());
        assertEquals("with-items", alternative.getType());
        assertEquals(items, alternative.getItems());
        assertEquals(2L, alternative.getNextScenarioId());
    }

    @Test
    void testAlternativeSetters() {
        Alternative alternative = new Alternative();
        alternative.setText("Go left");
        alternative.setType("without-items");
        alternative.setItems(Arrays.asList("key", "map"));
        alternative.setNextScenarioId(1L);

        assertEquals("Go left", alternative.getText());
        assertEquals("without-items", alternative.getType());
        assertEquals(Arrays.asList("key", "map"), alternative.getItems());
        assertEquals(1L, alternative.getNextScenarioId());
    }

    @Test
    void testScenarioConstructorAndGetters() {
        List<Alternative> alternatives = new ArrayList<>();
        alternatives.add(new Alternative("Go right", "without-items", null, 3L));

        Scenario scenario = new Scenario(1L, "You are in a forest", null, null, alternatives);

        assertEquals(1L, scenario.getId());
        assertEquals("You are in a forest", scenario.getDescrizione());
        assertTrue(scenario.getIndovinelli().isEmpty());
        assertTrue(scenario.getOggetti().isEmpty());
        assertEquals(alternatives, scenario.getAlternatives());
    }

    @Test
    void testScenarioSetters() {
        Scenario scenario = new Scenario();
        scenario.setId(2L);
        scenario.setDescrizione("You are in a cave");

        List<Alternative> alternatives = Arrays.asList(new Alternative("Climb the wall", "with-items", Arrays.asList("rope"), 4L));
        scenario.setAlternatives(alternatives);

        assertEquals(2L, scenario.getId());
        assertEquals("You are in a cave", scenario.getDescrizione());
        assertEquals(alternatives, scenario.getAlternatives());
    }

    @Test
    void testAddAndRemoveIndovinello() {
        Scenario scenario = new Scenario();
        Indovinello indovinello = new Indovinello("What has keys but can't open locks?", "A piano");
        scenario.addIndovinello(indovinello);

        assertEquals(1, scenario.getIndovinelli().size());
        assertEquals(indovinello, scenario.getIndovinelli().get(0));

        scenario.removeIndovinello(indovinello);
        assertTrue(scenario.getIndovinelli().isEmpty());
    }
}
