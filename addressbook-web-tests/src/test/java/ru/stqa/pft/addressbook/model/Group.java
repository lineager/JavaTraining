package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("group")
@Entity
@Table(name = "group_list")
public class Group {
    @XStreamOmitField
    @Id
    @Column(name = "group_id")
    private int id = Integer.MAX_VALUE;
    @Expose
    @Column(name = "group_name")
    private  String name;
    @Expose
    @Column(name = "group_header")
    @Type(type = "text")
    private  String header;

    @Expose
    @Column(name = "group_footer")
    @Type(type = "text")
    private  String footer;

    @ManyToMany(mappedBy = "groups")
    private Set<Contact> contact = new HashSet<>();

    public Group withName(String name) {
        this.name = name;
        return this;
    }

    public Group withHeader(String header) {
        this.header = header;
        return this;
    }

    public Group withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public Group withId(int id) {
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHeader() {
        return header;
    }

    public String getFooter() {
        return footer;
    }

    public Contacts getContact() {
        return new Contacts(contact);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name) && Objects.equals(header, group.header) && Objects.equals(footer, group.footer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, header, footer);
    }


}
