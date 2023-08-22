package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializerStraregy {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeWithException(r.getContactType().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = r.getSectionType();
//            dos.writeInt(sections.size());    // redundant code
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                writeSection(dos, r, entry.getKey());
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume r = new Resume(uuid, fullName);

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                r.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
//            int sectionsSize = dis.readInt();  // redundant code
            for (SectionType sectionType : SectionType.values()) {
                readSection(dis, r, sectionType);
            }
            return r;
        }
    }

    private void writeStringSection(Resume r, DataOutputStream dos, SectionType sectionType) throws IOException {
        dos.writeUTF(((TextSection) r.getSection(sectionType)).getText());
    }

    private void writeListSection(Resume r, DataOutputStream dos, SectionType sectionType) throws IOException {
        ListSection listSection = ((ListSection) r.getSection(sectionType));
        writeWithException(listSection.getList(), dos, dos::writeUTF);
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumer<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.apply(t);
        }
    }

    private <T> List<T> readListSection(DataInputStream dis, CustomSupplier<T> supplier) throws IOException {
        int size = dis.readInt();
        List<T> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
                arrayList.add(supplier.get());
        }
        return arrayList;
    }

    private void writeSection(DataOutputStream dos, Resume r, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> writeStringSection(r, dos, sectionType);
            case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(r, dos, sectionType);
            case EXPERIENCE, EDUCATION -> {
                List<Organization> orgList = getOrganizationList(r, sectionType);
                writeWithException(orgList, dos, element -> {
                    dos.writeUTF(element.getName());
                    dos.writeUTF(element.getWebSite());
                    List<Organization.Period> prdList = element.getPeriods();
                    writeWithException(prdList, dos, element2 -> {
                        dos.writeUTF(element2.getStartDate().toString());
                        dos.writeUTF(element2.getEndDate().toString());
                        dos.writeUTF(element2.getTitle());
                        dos.writeUTF(element2.getDescription());
                    });
                });
            }
        }
    }

    private void readSection(DataInputStream dis, Resume r, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> r.addSection(sectionType, new TextSection(dis.readUTF()));
            case ACHIEVEMENT, QUALIFICATIONS -> r.addSection(sectionType, new ListSection(readListSection(dis, dis::readUTF)));
            case EXPERIENCE, EDUCATION -> {
                List<Organization> orgList = readListSection(dis, () -> new Organization(dis.readUTF(), dis.readUTF(),
                        readListSection(dis, () -> new Organization.Period(LocalDate.parse(dis.readUTF(), formatter),
                                LocalDate.parse(dis.readUTF(), formatter), dis.readUTF(), dis.readUTF()))));
                r.addSection(sectionType, new OrganizationSection(orgList));
            }
        }
    }

    private List<Organization> getOrganizationList(Resume r, SectionType sectionType) {
        return ((OrganizationSection) r.getSection(sectionType)).getList();
    }

    @FunctionalInterface
    interface CustomConsumer<T> {
        void apply(T t) throws IOException;
    }

    @FunctionalInterface
    interface CustomSupplier <T> {
        T get() throws IOException;
    }
}
