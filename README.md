# CampusFinder — ready-to-run HTML/CSS/Java demo

A responsive student accommodation finder for Zambia, with ZMW monthly prices, sample property cards, search filters, a landlord listing form and a small Java HTTP API.

## Requirements
- Java JDK 17 or newer (includes `javac`).
- Python 3 for serving the frontend locally (or another static web server).
- Internet for the remote Google Fonts and sample Unsplash property images.

Check installation:
```bash
java -version
javac -version
python --version
```

## Folder structure
```text
campusfinder-ready/
├── index.html
├── styles.css
├── app.js
├── README.md
└── java/src/campusfinder/
    ├── ListingFilter.java
    ├── StudentUser.java
    ├── LandlordUser.java
    ├── Listing.java
    └── CampusFinderServer.java
```

## Run on Windows
1. Extract the ZIP.
2. Open PowerShell in the extracted `campusfinder-ready` folder.
3. Compile Java:
```powershell
javac -d out java\src\campusfinder\*.java
```
4. Start the API (keep this terminal open):
```powershell
java -cp out campusfinder.CampusFinderServer
```
5. Open a second PowerShell window in the same project folder and run:
```powershell
py -m http.server 5500
```
If `py` is unavailable, use `python -m http.server 5500`.
6. Open `http://localhost:5500` in your browser.

## Run on macOS / Linux
From the project folder, compile:
```bash
javac -d out java/src/campusfinder/*.java
```
Start the API:
```bash
java -cp out campusfinder.CampusFinderServer
```
In another terminal:
```bash
python3 -m http.server 5500
```
Open `http://localhost:5500`.

## API endpoints
- `GET http://localhost:8080/api/health` — health check.
- `GET http://localhost:8080/api/listings` — all sample properties.
- `GET /api/listings?location=UNZA&minPrice=1000&maxPrice=4500&type=Room` — filtered properties.
- `POST /api/listings` — add a listing using URL-encoded form fields: `landlordName`, `title`, `location`, `price`, `type`, `description`, `phone`.

The JavaScript frontend (`app.js`) calls this API. The search form filters by campus/location, price range and property type. The landlord form submits new listings.

## What each Java class does
- `ListingFilter`: filters listings by location, minimum/maximum price and type.
- `StudentUser`: student data model with saved listing IDs.
- `LandlordUser`: landlord data model with submitted listings.
- `Listing`: accommodation/property data model and JSON output.
- `CampusFinderServer`: starts the API, seeds sample data and handles GET/POST requests.

## Important limitations before real deployment
This is a teaching/demo starter, not a production rental platform:
- Listings and landlord submissions are held in memory and disappear when the Java process stops.
- StudentUser and LandlordUser are data models only; there is no login, password handling or account persistence.
- Sample homes, contacts and prices are illustrative. They are not verified available properties.
- The demo API accepts landlord submissions without authentication. Do not expose it publicly with real data as-is.
- GitHub Pages can host the static frontend, but cannot run the Java server. Deploy the Java API separately (for example to a Java-capable host), then replace `API` at the top of `app.js` with your deployed API base URL and restrict CORS to your site domain.
- Before launch, add a database, authentication/authorization, input validation, landlord verification, privacy safeguards and HTTPS.

## Troubleshooting
**`javac` not found:** Install a JDK 17+ and reopen the terminal.

**Frontend says Java service is offline:** Keep the Java server terminal running, serve the page using Python's local web server, and open `http://localhost:5500` (not a `file://` URL).

**Port 8080 already in use:** Stop the other service or change the port in `CampusFinderServer.java` and the `API` constant in `app.js` to the same value.

---

# Object-oriented programming (OOP) implementation

This version explicitly demonstrates all four OOP principles in the Java backend: **abstraction, inheritance, encapsulation and polymorphism**.

## 1. Abstraction — `User.java`

`User` is an **abstract class**. It represents the common idea of a CampusFinder user without representing one specific account type. It stores the shared `name` and declares two abstract methods:

- `getRole()` — each concrete user type must identify its role.
- `getContactInfo()` — each concrete user type must provide its own contact detail.

An abstract class cannot be instantiated directly, so `new User(...)` is not allowed. The server and other code can work with the general `User` type without needing to know every subclass detail. The abstract methods define what a user must provide, while the subclasses decide how.

**Where to find it:** `java/src/campusfinder/User.java`, especially the `abstract class User` declaration and the two `abstract` methods.

## 2. Inheritance — `StudentUser.java` and `LandlordUser.java`

Both concrete user classes use `extends User`:

- `StudentUser extends User` inherits the common `name` field and `getName()` method. It adds an email address and saved listing IDs.
- `LandlordUser extends User` inherits the same common identity behaviour. It adds a phone number and a collection of property listings.

Each subclass calls `super(name)` in its constructor. This runs the parent constructor so the shared name is initialized in one place instead of being duplicated in both subclasses.

**Where to find it:** the class declarations and constructors in `StudentUser.java` and `LandlordUser.java`.

## 3. Encapsulation — private fields and controlled methods

Encapsulation means keeping an object's data protected inside the class and exposing controlled operations to other code.

Examples in this project:

- `User.name` is `private final`; outside classes read it through `getName()`.
- `LandlordUser.phone` and its `listings` collection are private. A landlord adds a listing through `addListing()`. `getListings()` returns an unmodifiable view, not a list that callers can directly edit.
- `StudentUser.email` and `savedListingIds` are private. Saved IDs are changed through `saveListing()` and `removeSavedListing()`. The getter returns an unmodifiable view.
- `Listing` also keeps its fields private and exposes getters.

**Where to find it:** the `private` fields, constructors, getters and controlled update methods in `User.java`, `StudentUser.java`, `LandlordUser.java` and `Listing.java`.

## 4. Polymorphism — overridden methods called through `User`

Polymorphism means one parent type can refer to objects of different child types, and an overridden method behaves according to the object's actual type at runtime.

The server demonstrates this in `CampusFinderServer.java`:

```java
User student = new StudentUser("Demo Student", "student@example.com");
User landlord = new LandlordUser("Demo Landlord", "0977000000");
printUserProfile(student);
printUserProfile(landlord);
```

Both variables have the declared type `User`, and both are passed to the same method:

```java
private static void printUserProfile(User user) {
    System.out.println(user.getProfileSummary());
}
```

`getProfileSummary()` calls `getRole()` and `getContactInfo()`. Those methods are overridden in each subclass. For the student object, Java executes `StudentUser.getRole()` and `StudentUser.getContactInfo()`; for the landlord object, it executes the `LandlordUser` versions. This is **runtime polymorphism** (dynamic method dispatch).

The server also calls `printUserProfile(landlord)` when a landlord submits a property. The startup demonstration prints one student and one landlord profile so the behaviour is visible immediately in the console. The demo student is only an OOP demonstration object; student registration and saved-listing functionality are still not connected to the web interface or persistent storage.

**Where to find it:** `@Override` methods in `StudentUser.java` and `LandlordUser.java`, and `printUserProfile(User user)` plus `demonstrateOopConcepts()` in `CampusFinderServer.java`.

## How the classes relate

```text
                  User (abstract)
              name, getName(), profile
                /              \\
               /                \\
       StudentUser            LandlordUser
       email                   phone
       saved listing IDs       property listings
       getRole()               getRole()
       getContactInfo()        getContactInfo()
```

`Listing` remains a separate model class. `LandlordUser` has a list of `Listing` objects; this is a **has-a relationship (composition/aggregation)**, not inheritance.

## Run the project

Requirements: **JDK 11 or newer** and Python 3 (only needed to serve the frontend). The backend uses Java's built-in `HttpServer`; no third-party Java libraries are required.

### Windows

1. Extract the ZIP file.
2. Open Command Prompt in the extracted `CampusFind` folder.
3. Run `run-backend.bat`.
4. Open a second Command Prompt in the same folder and run `run-frontend.bat`.
5. Open `http://localhost:5500` in your browser.

### macOS / Linux

1. Extract the ZIP file and open a terminal in the `CampusFind` folder.
2. Run `chmod +x run-backend.sh run-frontend.sh` once.
3. In one terminal run `./run-backend.sh`.
4. In a second terminal run `./run-frontend.sh`.
5. Open `http://localhost:5500` in your browser.

The backend API runs at `http://localhost:8080` by default. If port 8080 is already in use, start it with `java -Dcampusfinder.port=18080 -cp out campusfinder.CampusFinderServer` and update the API base URL in `app.js` to `http://localhost:18080/api`. To check it, open `http://localhost:8080/api/health`; it should return `{"status":"ok"}`. The startup console also prints the two OOP demonstration profiles.

### Manual compile and run

From the project root:

```bash
javac -d out java/src/campusfinder/*.java
java -cp out campusfinder.CampusFinderServer
```

Then, in a second terminal:

```bash
python -m http.server 5500
```

Open `http://localhost:5500`.

## Important project scope

The Java changes add and demonstrate the four OOP principles without changing the existing listing API or frontend workflow. Data is still held in memory and resets when the backend restarts. There is no real student sign-up/login, database, or persistent favourites feature yet. The `StudentUser` class models saved listing IDs, but the current website does not call it.
