package com.may.libraryMVC.model.constant;

public enum BookCategory {
    CLASSIC("Classic"), EDUCATION_BOOK("Education Book"), FANTASY("Fantasy"), THEATRE("Theatre"), ADVENTURE(
            "Adventure"), ROMANCE("Romance"),
    CONTEMPORARY("Contemporary"), DYSTOPIAN("Dystopian"), MYSTERY("Mystery"), HORROR("Horror"), THRILLER("Thriller"),
    PARANORMAL("Paranormal"), HISTORICAL_FICTION("Historical fiction"),
    SCIENCE_FICTION("Science Fiction"), MEMOIR("Memoir"), COOKING("Cooking"), ART("Art"),
    SELF_HELP_PERSONAL("Self-help / Personal"), DEVELOPMENT("Development"), MOTIVATIONAL("Motivational"), HEALTH(
            "Health"), HISTORY("History"), TRAVEL("Travel"),
    GUIDE_HOW_TO("Guide / How-to"), FAMILIES_AND_RELATIONSHIPS("Families & Relationships"), HUMOR("Humor"), CHILDREN(
            "Children’s");

    private final String displayValue;

    BookCategory(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
