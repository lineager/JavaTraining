package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class Contact {
    @XStreamOmitField
    @Id
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;

    @Expose
    @Column(name = "firstname")
    private  String name;

    @Expose
    @Column(name = "middlename")
    private  String surname;
    @Expose
    @Column(name = "lastName")
    private  String lastName;



    @Column(name = "home")
    @Type(type = "text")
    private String homePhone;

    @Column(name = "mobile")
    @Type(type = "text")
    private String mobilePhone;

    @Column(name = "work")
    @Type(type = "text")
    private String workPhone;

    @Expose
    @Transient
    private String homePhone2;

    @Transient
    private String allPhones;

    @Transient
    private String allEmails;

    @Expose
    @Column(name = "email")
    @Type(type = "text")
    private String email;

    @Type(type = "text")
    private String email2;

    @Type(type = "text")
    private String email3;

    @Column(name = "photo")
    @Type(type = "text")
    private String photo;

    @Expose
    @Column(name = "address")
    @Type(type = "text")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "address_in_groups" , joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<Group> groups = new HashSet<>();


    public Contact withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
    }

    public Contact withName(String name) {
        this.name = name;
        return this;
    }

    public Contact withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Contact withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Contact withId(int id) {
        this.id = id;
        return this;
    }

    public Contact withHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public Contact withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public Contact withWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public Contact withHomePhone2(String fax) {
        this.homePhone2 = fax;
        return this;
    }

    public Contact withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public Contact withEmail(String email) {
        this.email = email;
        return this;
    }

    public Contact withEmail2(String email2) {
        this.email2 = email2;
        return this;
    }

    public Contact withEmail3(String email3) {
        this.email3 = email3;
        return this;
    }


    public Contact withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public Contact withAddress(String address) {
        this.address = address;
        return this;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getHomePhone2() {
        return homePhone2;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public String getEmail() {
        return email;
    }

    public String getEmail2() {
        return email2;
    }

    public String getEmail3() {
        return email3;
    }

    public String getAllEmails() {
        return allEmails;
    }

    public File getPhoto() {
        return  new File(photo);
    }

    public String getAddress() {
        return address;
    }

    public Groups getGroups() {
        return new Groups(groups);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id && Objects.equals(name, contact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Contact inGroup(Group group) {
        groups.add(group);
        return this;
    }
}
