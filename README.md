---
# Travel Database Manager

## Description
This is a console-based Java application for managing a travel database stored in `db.csv`.  
The user can add, edit, delete records, sort them by date, find trips cheaper than a given price, and calculate the average trip price.

---

## Features
- **print** — Display all records in a table format.
- **add** `<ID;City;Date;Days;Price;Vehicle>` — Add a new record.
- **del** `<ID>` — Delete a record by ID.
- **edit** `<ID;City;Date;Days;Price;Vehicle>` — Edit an existing record (you can leave fields empty to keep their current values).
- **sort** — Sort all records by the trip date (ascending).
- **find** `<price>` — Find all trips cheaper than the given price.
- **avg** — Calculate and display the average trip price.
- **exit** — Exit the program.

---

## Record Format
Each record in the `db.csv` file follows the format:

```
ID;City;Date;Days;Price;Vehicle
```

- `ID` — A 3-digit identifier (e.g., `001`, `123`).
- `City` — City name (the first letter is automatically capitalized).
- `Date` — Trip date in the format `dd/MM/yyyy` (e.g., `01/12/2024`).
- `Days` — Number of days (must be a positive integer).
- `Price` — Trip price (must be a positive number).
- `Vehicle` — Type of transport (`TRAIN`, `PLANE`, `BUS`, or `BOAT`).

---

## Installation and Running
1. Make sure you have JDK 8 or higher installed.
2. Download the project and navigate to the project directory.
3. Compile the project:
   ```bash
   javac Main.java
   ```
4. Run the application:
   ```bash
   java Main
   ```

---

## Example Usage

**Add a trip:**
```
add 101;Riga;15/06/2025;7;350;Plane
```

**Delete a trip by ID:**
```
del 101
```

**Edit a trip:**
```
edit 101;London;;5;;Bus
```
(in this example, only the city, number of days, and vehicle are updated)

**Find trips cheaper than 500:**
```
find 500
```

**Calculate average trip price:**
```
avg
```

---

## Important Notes
- If the input format is incorrect, the program will show an error message.
- After every operation, the program waits for a new command.
- If an invalid command is entered, it will show `wrong command`.

---

## Author
Developer: **German Veideman**

---
