package sc.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import sc.stqa.pft.addressbook.models.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names = "-c", description = "Contact Count")
    public int count;

    @Parameter(names = "-f", description = "Target File")
    public String file;

    @Parameter(names = "-d", description = "Data Format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try{
            jCommander.parse(args);
        } catch(ParameterException ex){
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> contacts = generateContact(count);
        if( format.equals("csv")){
            saveAsCsv(contacts, new File(file));
        } else if (format.equals("xml")){
            saveAsXml(contacts, new File(file));
        } else if (format.equals("json")){
            saveAsJson(contacts, new File(file));
        } else {
            System.out.println("unrecognized format " + format);
        }
    }

    private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(json);
        }

    }

    private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(ContactData.class);
        String xml = xStream.toXML(contacts);
        try (Writer writer = new FileWriter(file)) {
            writer.write(xml);
        }
    }

    private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            for (ContactData contact : contacts) {
                writer.write(String.format("%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getLastname()
                        , contact.getAddress(), contact.getEmail(), contact.getMobile()
                        , contact.getHomePhone(), contact.getWorkPhone()));
            }
        }
    }

    private List<ContactData> generateContact(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for(int i = 0; i < count; i++){
            contacts.add(new ContactData().withFirstname(String.format("TestFirstName %s", i))
                    .withLastname(String.format("testLastName %S", i))
                    .withAddress(String.format("TestAddress %s", i))
                    .withEmail(String.format("Test@email.%s", i))
                    .withMobile(String.format("123%s", i))
                    .withHomePhone(String.format("456%s", i))
                    .withWorkPhone(String.format("789%s", i)));
        }
        return contacts;
    }
}
