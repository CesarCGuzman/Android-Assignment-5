package edu.uncc.assignment05;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    String name;
    String ageGroup;
    Mood mood;

    public User() {
    }

    public User(String name, String ageGroup, Mood mood) {
        this.name = name;
        this.ageGroup = ageGroup;
        this.mood = mood;
    }

    /* ----- Compare via Name ----- */
    public static class UserNameComparator implements Comparator<User> {
        private final boolean ascending;
        public UserNameComparator(boolean ascending) {
            this.ascending = ascending;
        }
        @Override
        public int compare(User user1, User user2) {
            String name1 = user1.getName();
            String name2 = user2.getName();
            int result = name1.compareTo(name2);

            return ascending ? result : -result;
        }
    }

    /* ----- Compare via Age Group ----- */

    /*
       I got this from ChatGPT so I'll explain what is happening here
       This is a map that maps the age group to an integers, that way we can give them a weight and
       compare them better, since they're strings
    */
    private static final Map<String, Integer> AGE_GROUP_ORDER = new HashMap<>();

    static {
        AGE_GROUP_ORDER.put("Under 12 years old", 1);
        AGE_GROUP_ORDER.put("12-17 years old", 2);
        AGE_GROUP_ORDER.put("18-24 years old", 3);
        AGE_GROUP_ORDER.put("25-34 years old", 4);
        AGE_GROUP_ORDER.put("35-44 years old", 5);
        AGE_GROUP_ORDER.put("45-54 years old", 6);
        AGE_GROUP_ORDER.put("55-64 years old", 7);
        AGE_GROUP_ORDER.put("65-74 years old", 8);
        AGE_GROUP_ORDER.put("75 years or older", 9);
        // Add more age groups and their order as needed - Comment made by GPT
    }

    // This function returns the order of the age group
    // If the age group is not in the map, it returns the max value
    private int getAgeGroupOrder() {
        return AGE_GROUP_ORDER.getOrDefault(ageGroup, Integer.MAX_VALUE);
    }

    // Comparator for sorting by age group
    public static class AgeGroupComparator implements Comparator<User> {

        // This is the sorting direction, if we get true, we sort ascending, otherwise we sort descending
        private final boolean ascending;

        // This is just a constructor for the ascending thing
        public AgeGroupComparator(boolean ascending) {
            this.ascending = ascending;
        }

        /* This is the compare function, it takes two users and compares them
        based on their age group which is also based on the order/weight we gave them */
        @Override
        public int compare(User user1, User user2) {
            // Get the order of the age group for each user
            int order1 = user1.getAgeGroupOrder();
            int order2 = user2.getAgeGroupOrder();

            // Compare the orders
            int result = Integer.compare(order1, order2);

            // Adjust the result based on the sorting direction and return it
            return ascending ? result : -result;
        }
    }

    /* ----- Compare via Mood ----- */

    /*
       Now using what we learned from mapping something with a Map,
       we can do the same thing with the moods!
     */
    private static final Map<String, Integer> MOOD_ORDER = new HashMap<>();

    static {
        // Give each mood name a weight/int value, this way we can sort them how we want
        MOOD_ORDER.put("Very Good", 1);
        MOOD_ORDER.put("Good", 2);
        MOOD_ORDER.put("Ok", 3);
        MOOD_ORDER.put("Sad", 4);
        MOOD_ORDER.put("Not Well", 5);
    }

    // Here's where we get the order of the moods,
    // so it orders the default moods we provided above
    private int getMoodOrder() {
        return MOOD_ORDER.getOrDefault(mood.name, Integer.MAX_VALUE);
    }

    // Now we can compare the moods
    public static class MoodNameComparator implements Comparator<User> {
        // Sorting direction, if we get true, we sort ascending, otherwise we sort descending
        private final boolean ascending;

        // Constructor for the sorting direction
        public MoodNameComparator(boolean ascending) {
            this.ascending = ascending;
        }

        // Compare function, takes two users and compares their moods
        @Override
        public int compare(User user1, User user2) {
            /* Get the order of the moods for each user, basically the weight of the mood
            so that we can actually compare them in the way we want to. */
            int mood1 = user1.getMoodOrder();
            int mood2 = user2.getMoodOrder();

            // Compare the moods and put it in the result
            int result = Integer.compare(mood1, mood2);

            // return it if ascending is true, otherwise return the negative of it
            return ascending ? result : -result;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }
}
