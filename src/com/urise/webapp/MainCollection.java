package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MainCollection {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULL_NAME_1 = "Petrov Petr";
    private static final String FULL_NAME_2 = "Ivanov Ivan";
    private static final String FULL_NAME_3 = "Saidova Zulykha";
    private static final String FULL_NAME_4 = "Petrova Maria";
    public static final Resume R1 = new Resume(UUID_1, FULL_NAME_1);
    public static final Resume R2 = new Resume(UUID_2, FULL_NAME_2);
    public static final Resume R3 = new Resume(UUID_3, FULL_NAME_3);
    public static final Resume R4 = new Resume(UUID_4, FULL_NAME_4);
    public static void main(String[] args) {
        Resume resume = new Resume();
        Collection<Resume> collection = new ArrayList<>();
        collection.add(R1);
        collection.add(R2);
        collection.add(R3);
        collection.add(R4);

        for (Resume r : collection) {
            System.out.println(r);

            if (Objects.equals(r.getUuid(), UUID_1)) {
                //collection.remove(r);
            }
        }

        Iterator<Resume> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), UUID_1)) {
                iterator.remove();
            }
        }
        System.out.println(collection.toString());

        Map<String, Resume> map = new HashMap<>();
        map.put(UUID_1,R1);
        map.put(UUID_2,R2);
        map.put(UUID_3,R3);

        for (String uuid : map.keySet()) {
            System.out.println(map.get(uuid));
        }

        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
