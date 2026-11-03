/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.notepadapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests verifying text parsing and metric calculation rules.
 * 
 * @author Thembelani
 */
public class NotepadLogicTest {

    @Test
    public void testWordCountCalculation() {
        String sampleText = "The quick brown fox jumps over the lazy dog";
        int words = sampleText.trim().split("\\s+").length;
        assertEquals(9, words, "Word count should accurately identify 9 words.");
    }

    @Test
    public void testEmptyTextWordCount() {
        String emptyText = "";
        int words = emptyText.trim().isEmpty() ? 0 : emptyText.trim().split("\\s+").length;
        assertEquals(0, words, "Empty string must yield 0 words.");
    }

    @Test
    public void testNonSpaceCharacterCount() {
        String sampleText = "Hello World";
        int nonSpaceCount = sampleText.replaceAll("\\s+", "").length();
        assertEquals(10, nonSpaceCount, "Character count excluding spaces should equal 10.");
    }
}

