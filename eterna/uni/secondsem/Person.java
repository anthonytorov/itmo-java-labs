package eterna.uni.secondsem;

import java.util.HashSet;

public class Person implements Comparable<Person> {
    private Integer id;
    public Integer get_id() { return id; }

    private String name;
    public String get_name() { return name; } 

    public Person(String _name) {
        id = PersonIdPool.getNextId();
        name = _name;
    }
    
    public void copyDataFrom(Person other) {
        name = new String(other.name);
    }

    @Override
    public String toString() {
        return String.format(
            "Person(%d, %s)",
            id,
            name
        );
    }

    @Override
    public int compareTo(Person o) {
        return id.compareTo(o.id);
    }

    private static class PersonIdPool {
        private static final HashSet<Integer> OCCUPIED_IDS = new HashSet<Integer>();
        private static Integer lastId = 1;

        public static Integer getNextId() {
            while (!OCCUPIED_IDS.add(lastId)) { lastId++; }
            return lastId;
        }   
    }
}
