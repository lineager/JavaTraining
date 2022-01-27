package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Group;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names = "-c", description = "Contact count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        ContactDataGenerator contactDataGenerator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(contactDataGenerator);
        try {
            jCommander.parse(args);
        } catch (ParameterException p) {
            jCommander.usage();
            return;
        }
        contactDataGenerator.run();
    }

    private void run() throws IOException {
        List<Contact> contacts = generateContact(count);
        if (format.equals("csv")) {
            saveAsCsv(contacts, new File(file));
        } else if (format.equals("xml")) {
            getSaveAsXml(contacts, new File(file));
        } else if (format.equals("json")) {
            getSaveAsJson(contacts, new File(file));
        } else {
            System.out.println("Неопознанный формат");
        }
    }

    private void getSaveAsJson(List<Contact> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        try(Writer writer = new FileWriter(file)){
            writer.write(json);
        }
    }

    private void getSaveAsXml(List<Contact> contacts, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(Group.class);
        String xml = xStream.toXML(contacts);
       try( Writer writer = new FileWriter(file)){
           writer.write(xml);
       }
    }


    private List<Contact> generateContact(int count) {
        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            contacts.add(new Contact().withName(String.format("name %s", i))
                    .withSurname(String.format("surname %s", i))
                    .withLastName(String.format("lastname %s", i))
                    .withEmail(String.format("email %s", i)));
        }
        return contacts;
    }

    private void saveAsCsv(List<Contact> contacts, File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            for (Contact contact : contacts) {
                writer.write(String.format("%s; %s; %s; %s\n", contact.getName(), contact.getSurname(), contact.getLastName(), contact.getEmail()));
            }
        }
    }
}
