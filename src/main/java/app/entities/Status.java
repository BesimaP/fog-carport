package app.entities;

// The three statuses in the status table. The id must match status_id in the database.
public enum Status {
    INQUIRY(1, "Forespørgsel"),
    OFFER_SENT(2, "Tilbud sendt"),
    PAID(3, "Betalt");

    private final int id;
    private final String displayName;

    Status(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Used by the mappers when reading status_id from the database
    public static Status fromId(int id) {
        for (Status status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown status id: " + id);
    }
}
