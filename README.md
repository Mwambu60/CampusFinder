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
| Full Name | Student Number |
|-----------|----------------|
| Katendi Kamenga | 2410432 |
| Racheal Chilufya | 2410503 |
| Mwambu Austin Mulonda | 2410275 |
| Joseph Chilando | 2410127 |
| John Chirwa | 2410663 |
