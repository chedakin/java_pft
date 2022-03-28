package sc.stqa.pft.addressbook.Tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.Contacts;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactCreationTest extends TestBase {
    @DataProvider
    public Iterator<Object[]> validContactsFromCSV() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")))) {
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(";");
                list.add(new Object[]{new ContactData().withFirstname(split[0]).withLastname(split[1])
                        .withAddress(split[2]).withEmail(split[3]).withMobile(split[4])
                        .withHomePhone(split[5]).withWorkPhone(split[6])});
                line = reader.readLine();
            }
        }
        return list.iterator();
    }

    // НЕ РАБОТАЕТ!!!!
    @DataProvider
    public Iterator<Object[]> validContactsFromXML() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.processAnnotations(ContactData.class);
            //В следующей строке вылетает без ошибок просто skip
            List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
            return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromGSON() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            System.out.println(json);
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
            return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJackson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper ();
            List<ContactData> contacts = mapper.readValue(json, new TypeReference<List<ContactData>>(){});
            return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
        }
    }


    @Test(dataProvider = "validContactsFromJackson")
    public void testContactCreation(ContactData contact) throws Exception {
        app.goTo().homePage();

        Contacts before = app.contact().all();
        app.goTo().addNew();
        File photo = new File("src/test/resources/123.png");
        app.contact().create(contact.withPhoto(photo).withGroup("group4"), true);

        assertThat(app.contact().count()-1, equalTo(before.size()));
        Contacts after = app.contact().all();
        contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt());
        assertThat(after, equalTo(before.withAdded(contact.withAllPhones(mergePhones(contact)))));
    }

    public String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobile(), contact.getWorkPhone())
                .stream().filter((s) -> s != null)
                .filter((s) -> ! s.equals(""))
                .map(ContactPhoneTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone){
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    @Test(enabled = false)
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/123.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }

}
