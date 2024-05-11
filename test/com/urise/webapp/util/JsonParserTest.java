package com.urise.webapp.util;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

public class JsonParserTest  {
    Resume r1 = ResumeTestData.resumeFill("uuid10", "Qu Qu");
    @Test
    public void write() throws Exception {
        AbstractSection ts1 = new TextSection("Objective1");
        String json = JsonParser.write(ts1, AbstractSection.class);
//        System.out.println(ts1 + ":" + json);
        AbstractSection ts2 = JsonParser.read(json, AbstractSection.class);
//        System.out.println(ts2);
        Assert.assertEquals(ts1, ts2);
    }

    @Test
    public void read() throws Exception {
        String json = JsonParser.write(r1);
        System.out.println(json);
        Resume r = JsonParser.read(json, Resume.class);
        Assert.assertEquals(r1, r);
    }

//    public static void main(String[] args) {
//        System.out.println(R1);
//    }
}