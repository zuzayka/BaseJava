import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        for (int i = 0; i < 10000; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                System.out.println(storage[i].uuid);
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume dummy = new Resume();
        dummy.uuid = "This uuid is not in the Resume";
        for (int i = 0; i < 10000; i++) {
            try {
                if (storage[i].uuid.equals(uuid)) {
                    return storage[i];
                }
            } catch (NullPointerException e) {
                break;
            }
        }
        return dummy;
    }

    void delete(String uuid) {
        for (int i = 0; i < 10000; i++) {

            if (uuid.equals(storage[i].uuid)) {
                System.arraycopy(storage, i + 1, storage, i, 9999 - i);
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int i = 0;
        while (i < 10000) {
            try {
                storage[i].uuid.equals(null);
            } catch (NullPointerException e) {
                break;
            }
            i++;
        }
        return i;
    }
}
