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
        List<String> arrayList = (ArrayList) listSection.getList();
        int size = arrayList.size();
        dos.writeInt(arrayList.size());
        for (String s : arrayList) {
            dos.writeUTF(s);
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumer<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.apply(t);
        }
    }

    private void readListSection(DataInputStream dis, Resume r, SectionType sectionType) throws IOException {
        int size = dis.readInt();
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
                arrayList.add(dis.readUTF());
        }
        r.addSection(sectionType, new ListSection(arrayList));
    }

    private void writeSection(DataOutputStream dos, Resume r, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> writeStringSection(r, dos, sectionType);
            case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(r, dos, sectionType);
            case EXPERIENCE, EDUCATION -> {
                List<Organization> orgList = getOrganizationList(r, sectionType);
                int orgListSize = orgList.size();
                dos.writeInt(orgListSize);
                for (Organization org : orgList) {
                    dos.writeUTF(org.getName());
                    dos.writeUTF(org.getWebSite());
                    List<Organization.Period> prdList = org.getPeriods();
                    int prdListSize = prdList.size();
                    dos.writeInt(prdListSize);
                    for (Organization.Period op : prdList) {
                        dos.writeUTF(op.getStartDate().toString());
                        dos.writeUTF(op.getEndDate().toString());
                        dos.writeUTF(op.getTitle());
                        dos.writeUTF(op.getDescription());
                    }
                }
            }
        }
    }

    private void readSection(DataInputStream dis, Resume r, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> r.addSection(sectionType, new TextSection(dis.readUTF()));
            case ACHIEVEMENT, QUALIFICATIONS -> readListSection(dis, r, sectionType);
            case EXPERIENCE, EDUCATION -> {
                List<Organization> orgList = new ArrayList<>();
                int orgListSize = dis.readInt();
                for (int i = 0; i < orgListSize; i++) {
                    String name = dis.readUTF();
                    String webSite = dis.readUTF();
                    List<Organization.Period> prdList = new ArrayList<>();
                    int prdListSize = dis.readInt();
                    for (int j = 0; j < prdListSize; j++) {
                        LocalDate startDate = LocalDate.parse(dis.readUTF(), formatter);
                        LocalDate endDate = LocalDate.parse(dis.readUTF(), formatter);
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        Organization.Period prd = new Organization.Period(startDate, endDate, title, description);
                        prdList.add(prd);
                    }
                    Organization org = new Organization(name, webSite, prdList);
                    orgList.add(org);
                }
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
}
