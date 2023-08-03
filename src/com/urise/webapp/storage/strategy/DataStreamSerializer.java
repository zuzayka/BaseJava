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

            writeStringSection(r, dos, PERSONAL);
            writeStringSection(r, dos, OBJECTIVE);
            writeListSection(r, dos, ACHIEVEMENT);
            writeListSection(r, dos, QUALIFICATIONS);
            if (r.getSection(EXPERIENCE) != null) {
                dos.writeBoolean(true);
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
            } else {
                dos.writeBoolean(false);
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

            readStringSection(dis, r, PERSONAL);
            readStringSection(dis, r, OBJECTIVE);
            readListSection(dis, r, ACHIEVEMENT);
            readListSection(dis, r, QUALIFICATIONS);
            if (dis.readBoolean()) {
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

            } return r;
        }
    }

    private void writeStringSection(Resume r, DataOutputStream dos, SectionType sectionType) throws IOException {
        if (r.getSection(sectionType) != null) {
            dos.writeBoolean(true);
            dos.writeUTF(((TextSection) r.getSection(sectionType)).getText());
        } else {
            dos.writeBoolean(false);
        }
    }

    private void readStringSection(DataInputStream dis, Resume r, SectionType sectionType) throws IOException {
        if (dis.readBoolean()) {
            r.addSection(sectionType, new TextSection(dis.readUTF()));
        }
    }

    private void writeListSection(Resume r, DataOutputStream dos, SectionType sectionType) throws IOException {
        if (r.getSection(sectionType) != null) {
            dos.writeBoolean(true);
            ListSection listSection = ((ListSection) r.getSection(sectionType));
            ArrayList<String> arrayList = (ArrayList) listSection.getList();
            int size = arrayList.size();
            dos.writeInt(arrayList.size());
            for (int i = 0; i < size; i++) {
                dos.writeBoolean(true);
                dos.writeUTF(arrayList.get(i));
            }
        } else {
            dos.writeBoolean(false);
        }
    }

    private void readListSection(DataInputStream dis, Resume r, SectionType sectionType) throws IOException {
        int size;
        if (dis.readBoolean()) {
            size = dis.readInt();
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                if (dis.readBoolean()) {
                    arrayList.add(dis.readUTF());
                }
            }
            r.addSection(sectionType, new ListSection(arrayList));
        }
    }

    private List<Organization> getOrganizationList(Resume r, SectionType sectionType) {
        List<Organization> ol = ((OrganizationSection) r.getSection(sectionType)).getList();
        return ol;
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
