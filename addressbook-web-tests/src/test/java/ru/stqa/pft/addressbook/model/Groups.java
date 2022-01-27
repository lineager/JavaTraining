package ru.stqa.pft.addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Groups extends ForwardingSet<Group> {

    private Set<Group> delegate;

    public Groups(Groups groups) {
        this.delegate = new HashSet<>(groups.delegate);
    }

    public Groups() {
        this.delegate = new HashSet<>();
    }

    public Groups(Collection<Group> groups){
        this.delegate = new HashSet<>(groups);
    }

    @Override
    protected Set<Group> delegate() {
        return delegate;
    }

    public Groups withAdded(Group group) {
        Groups groups = new Groups(this);
        groups.add(group);
        return groups;
    }

    public Groups withOut(Group group) {
        Groups groups = new Groups(this);
        groups.remove(group);
        return groups;
    }
}
