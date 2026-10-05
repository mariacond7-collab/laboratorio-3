import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class AddressBook {

    private HashMap<String, Contact> contacts;
    private static final String FILE_NAME = "contacts.dat";

    public AddressBook() {
        contacts = new HashMap<>();
    }

    public void addContact(Contact contact) {
        if (contacts.containsKey(contact.getEmail())) {
            System.out.println("A contact with this email already exists.");
        } else {
            contacts.put(contact.getEmail(), contact);
            System.out.println("Contact added successfully.");
            storeContacts();
        }
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("The address book is empty.");
            return;
        }

        for (Contact contact : contacts.values()) {
            System.out.println(contact.print());
        }
    }

    public void searchContact(String email) {
        if (contacts.containsKey(email)) {
            System.out.println(contacts.get(email).print());
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void deleteContact(String email) {
        if (contacts.containsKey(email)) {
            contacts.remove(email);
            System.out.println("Contact deleted.");
            storeContacts();
        } else {
            System.out.println("No contact found with the provided email.");
        }
    }

    public void storeContacts() {
        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            output.writeObject(contacts);

        } catch (IOException e) {
            System.out.println("Error saving contacts.");
        }
    }

    @SuppressWarnings("unchecked")
    public void loadContacts() {
        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            contacts = (HashMap<String, Contact>) input.readObject();

        } catch (FileNotFoundException e) {
            contacts = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading contacts.");
            contacts = new HashMap<>();
        }
    }
}
