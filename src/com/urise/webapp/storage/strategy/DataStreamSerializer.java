package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.ResumeTestData.resumeFill;
import static com.urise.webapp.model.SectionType.*;

public class DataStreamSerializer implements SerializerStraregy {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContactType();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
//            sections

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

//            sections

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
        for (int i = 0; i < size; i++) {
            dos.writeBoolean(true);
            dos.writeUTF(arrayList.get(i));
        }
    }

    private void readListSection(DataInputStream dis, Resume r, SectionType sectionType) throws IOException {
        int size = dis.readInt();
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (dis.readBoolean()) {
                arrayList.add(dis.readUTF());
            }
        }
        r.addSection(sectionType, new ListSection(arrayList));
    }

    private void writeSection(DataOutputStream dos, Resume r, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> writeStringSection(r, dos, sectionType);
            case ACHIEVEMENT, QUALIFICATIONS -> writeListSection(r, dos, sectionType);
            case EXPERIENCE -> {
                List<Organization> orgList = getOrganizationList(r, EXPERIENCE);
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
            case PERSONAL -> r.addSection(PERSONAL, new TextSection(dis.readUTF()));
            case OBJECTIVE -> r.addSection(OBJECTIVE, new TextSection(dis.readUTF()));
            case ACHIEVEMENT -> readListSection(dis, r, ACHIEVEMENT);
            case QUALIFICATIONS -> readListSection(dis, r, QUALIFICATIONS);
            case EXPERIENCE -> {
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
                r.addSection(EXPERIENCE, new OrganizationSection(orgList));
            }
        }
    }

    private List<Organization> getOrganizationList(Resume r, SectionType sectionType) {
        return ((OrganizationSection) r.getSection(sectionType)).getList();
    }

    public static void main(String[] args) throws IOException {
        Resume r = resumeFill("uuid1", "Григорий Кислин");
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("/home/miux/Java/basejava/storage/resumeFile"));
        DataInputStream dis = new DataInputStream(new FileInputStream("/home/miux/Java/basejava/storage/resumeFile"));
        DataStreamSerializer dss = new DataStreamSerializer();
        dss.doWrite(r, dos);
        Resume r1 = dss.doRead(dis);
        System.out.println(r1);
    }
}
