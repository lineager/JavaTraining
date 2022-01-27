package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.Group;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {
    @Parameter(names = "-c", description = "Group count")
    public int count;

    @Parameter(names = "-f", description = "Target file")
    public String file;

    @Parameter(names = "-d", description = "Data format")
    public String format;

    public static void main(String[] args) throws IOException {
        GroupDataGenerator groupDataGenerator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(groupDataGenerator);
        try {
            jCommander.parse(args);
        }catch (ParameterException p){
            jCommander.usage();
            return;
        }
        groupDataGenerator.run();
    }

    private void run() throws IOException {
        List<Group> groups = generateGroups(count);
        if (format.equals("csv")){
            saveAsCsv(groups, new File(file));
        }else if(format.equals("xml")){
            getSaveAsXml(groups, new File(file));
        }else if(format.equals("json")){
            getSaveAsJson(groups, new File(file));
        }else {
            System.out.println("Неопознанный формат");
        }
    }

    private void getSaveAsJson(List<Group> groups, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(groups);
        try (Writer writer = new FileWriter(file)){
            writer.write(json);
        }
    }

    private void getSaveAsXml(List<Group> groups, File file) throws IOException {
        XStream xStream = new XStream();
        xStream.processAnnotations(Group.class);
        String xml = xStream.toXML(groups);
        try (Writer writer = new FileWriter(file)){
            writer.write(xml);
        }
    }

    private List<Group> generateGroups(int count) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            groups.add(new Group().withName(String.format("test %s", i))
                    .withHeader(String.format("header %s", i)).withFooter(String.format("footer %s", i)));
        }
        return groups;
    }

    private void saveAsCsv(List<Group> groups, File file) throws IOException {
       try( Writer writer = new FileWriter(file)) {
           for (Group group : groups) {
               writer.write(String.format("%s; %s; %s\n", group.getName(), group.getHeader(), group.getFooter()));
           }
       }
    }
}
