//* A small ticket desk program that puts the earlier files to work.
// Nothing from the earlier files is explained again here - a short note
// points back when an old idea shows up. Only the NEW idea (a key
// derivation function) gets a full explanation.

//& A record with a check (ModernTypes.java), and an enum for the price group.
record Ticket(String name, int age) {
    Ticket {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        name = name.strip();    // tidy the name before it is stored
    }
}

enum Group { CHILD, ADULT, SENIOR }

//& The desk name is pinned on a noticeboard (Concurrency.java).
static final ScopedValue<String> DESK = ScopedValue.newInstance();

Group groupFor(int age) {
    if (age < 13)  return Group.CHILD;
    if (age >= 65) return Group.SENIOR;
    return Group.ADULT;
}

String priceFor(Group group) {
    return switch (group) {
        case CHILD  -> "$5";
        case ADULT  -> "$12";
        case SENIOR -> "$8";
    };
}

//! The new idea: a key derivation function, or KDF

// A KDF takes one weak secret and makes a strong key from it. The same
// input always gives the same output, but nobody can work backwards from
// the output to the input. Real programs use one to turn a password into a proper key for locking (encrypting) data.

// The pieces used below:
//*   - "ikm"   the secret you start with
//*   - "salt"  extra text mixed in, so two programs with the same secret still end up with different keys
//*  - "info"  a label saying what the new key is for
//*  - 16      how many bytes long the key should be
//*  - "HKDF-SHA256"  the name of the recipe we choose

String receiptCode(Ticket ticket) throws GeneralSecurityException {
    var recipe = HKDFParameterSpec.ofExtract()
            .addIKM(("demo-secret-" + ticket.name()).getBytes(StandardCharsets.UTF_8))
            .addSalt("ticket-desk-salt".getBytes(StandardCharsets.UTF_8))
            .thenExpand("receipt code".getBytes(StandardCharsets.UTF_8), 16);

    var key = KDF.getInstance("HKDF-SHA256").deriveKey("AES", recipe);

    //& Bytes look like random symbols when printed. HexFormat writes them
    // with 0-9 and a-f. We keep the first 8 characters so the code is short.
    return HexFormat.of().formatHex(key.getEncoded()).substring(0, 8);
}

void printReceipt(Ticket ticket, String code) {
    IO.println("  %s | %s | %s | code %s".formatted(
            DESK.get(), ticket.name(), priceFor(groupFor(ticket.age())), code));
}

void main() throws Exception {
    IO.println("Welcome to the ticket desk");

    String name = IO.readln("Name (Enter = Ada): ");
    if (name == null || name.isBlank()) {
        name = "Ada";
    }

    //& If the age is not a number, we say so and use 25 (Basics.java).
    String ageText = IO.readln("Age (Enter = 25): ");
    int age = 25;
    if (ageText != null && !ageText.isBlank()) {
        try {
            age = Integer.parseInt(ageText.strip());
        } catch (NumberFormatException _) {
            IO.println("That was not a number, so we will use 25.");
        }
    }

    var tickets = List.of(new Ticket(name, age), new Ticket("Grace", 37), new Ticket("Tim", 8));

    //& One virtual thread per ticket makes the codes at the same time
    // (Concurrency.java). Each Future is a ticket for a code not ready yet.
    var codes = new ArrayList<Future<String>>();
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        for (Ticket ticket : tickets) {
            codes.add(executor.submit(() -> receiptCode(ticket)));
        }
    }

    //& Print the receipts while "Desk 7" is pinned on the noticeboard.
    IO.println("\nReceipts");
    ScopedValue.where(DESK, "Desk 7").run(() -> {
        for (int i = 0; i < tickets.size(); i++) {
            try {
                printReceipt(tickets.get(i), codes.get(i).get());
            } catch (Exception e) {
                IO.println("  Could not make a receipt: " + e.getMessage());
            }
        }
    });

    //& Both ends of a list, and the stream belt (ModernTypes.java, Basics.java).
    var names = tickets.stream().map(Ticket::name).toList();
    IO.println("\nFirst in line: " + names.getFirst() + ", last in line: " + names.getLast());

    IO.println("\nThat is the end of the tour.");
}