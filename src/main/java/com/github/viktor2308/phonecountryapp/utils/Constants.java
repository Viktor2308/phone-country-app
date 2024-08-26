package com.github.viktor2308.phonecountryapp.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    /**
     * Regular expression to remove all characters except digits, commas, and parentheses.
     * This pattern is used to clean up phone code strings.
     */
    public static final String REMOVE_NON_NUMERIC_AND_SPECIAL_CHARS = "[^\\d,()]";

    /**
     * Regular expression to split a string by commas, ignoring commas within parentheses.
     * This pattern is used to split phone code strings while preserving grouped suffixes.
     */
    public static final String SPLIT_IGNORING_COMMAS_IN_PARENTHESES = ",(?![^()]*\\))";
}
