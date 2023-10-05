package org.example.app.service;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.app.entity.Contact;

import java.lang.reflect.Parameter;
import java.net.URI;
import java.util.*;

@Path("/api/v1.0/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class ContactService {

    private static final List<Contact> contacts;

    static {
        contacts = new ArrayList<>();
        contacts.add(new Contact(1, "Bob", "333 767-9836"));
        contacts.add(new Contact(2, "Marry", "343 645-7767"));
        contacts.add(new Contact(3, "Tom", "444 767-2322"));
        contacts.add(new Contact(4, "Kris", "121 666-0987"));
        contacts.add(new Contact(5, "Barbara", "345 656-777"));
        contacts.add(new Contact(6, "Tadeusz", "343 565-1191"));
    }

    @GET
    public List<Contact> getContacts() {
        return contacts;
    }

    @GET
    @Path("{id: [0-9]+}")
    public Contact getContact(@PathParam("id") int id) {
        Contact contact = new Contact(id, null, null);
        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));
        if (index >= 0)
            return contacts.get(index);
        else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createContact(Contact contact) {
        if (Objects.isNull(contact.getId()))
            throw new WebApplicationException(Response.Status.BAD_REQUEST);

        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

        if (index < 0) {
            contacts.add(contact);
            return Response
                    .status(Response.Status.CREATED)
                    .location(URI.create(String.format("/api/v1.0/contacts/%s", contact.getId())))
                    .build();
        } else
            throw new WebApplicationException(Response.Status.CONFLICT);
    }

    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateContact(@PathParam("id") int id, Contact contact) {
        contact.setId(id);
        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

        if (index >= 0) {
            Contact updateContact = contacts.get(index);
            updateContact.setPhone(contact.getPhone());
            contacts.set(index, updateContact);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }


    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteContact(@PathParam("id") int id) {
        Contact contact = new Contact(id, null, null);
        int index = Collections.binarySearch(contacts, contact, Comparator.comparing(Contact::getId));

        if (index >= 0) {
            contacts.remove(index);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        } else
            throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
}
