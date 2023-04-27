package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR_UUID = Comparator.comparing(Resume::getUuid);
    @Override
    protected void insertElement(Resume r, int index) {
        int insertIdx = -index - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
        storage[insertIdx] = r;
    }

//    Ia. реализация компаратора через вложенный статический класс:
//    private static final ResumeComparator RESUME_COMPARATOR= new ResumeComparator();


    //    II. реализация через интерфейс Comparable, реализующегося через анонимный класс:
//    private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<>() {  // idea предлагает перевести в лямбду

//    III. (вынес Компаратопв AbstractStorage)
//    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());     //  уже лямбда

    @Override
    protected void fillDeletedElement(int index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKeyResume = new Resume(uuid, "");
        if (size == 0) {
            return -1;
        } else {
//            I. II. реализация компаратора через вложенный статический кдасс и интерфейс Comparable,
//            реализующегося через анонимный класс:
            return Arrays.binarySearch(storage, 0, size, searchKeyResume, RESUME_COMPARATOR_UUID);
        }
    }

//            Ib. реализация компаратора через вложенный статический кдасс:
//    private static class ResumeComparator implements Comparator<Resume> {
//        @Override
//        public int compare(Resume o1, Resume o2) {
//            return o1.getUuid().compareTo(o2.getUuid());
//        }
//    }
}