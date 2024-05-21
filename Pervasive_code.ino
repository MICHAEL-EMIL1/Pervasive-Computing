#include <Arduino.h>
#include <WiFi.h>
#include <FirebaseESP32.h>
#include <Keypad.h>
#include <LiquidCrystal.h>

// Provide the token generation process info.
#include <addons/TokenHelper.h>

// Provide the RTDB payload printing info and other helper functions.
#include <addons/RTDBHelper.h>

/* 1. Define the WiFi credentials */
#define WIFI_SSID "Mina"
#define WIFI_PASSWORD "0111mayven222"


/* 2. Define the API Key */
#define API_KEY "AIzaSyAYKmWRohRJD69vLAjoD0To-s5-PfEuoh8"

/* 3. Define the RTDB URL */
#define DATABASE_URL "https://pervasive-computing-f250a-default-rtdb.firebaseio.com/"  // Ensure the correct format

/* 4. Define the user Email and password that already registered or added in your project */
#define USER_EMAIL "micheal.emil313@gmail.com"
#define USER_PASSWORD "01128661192"

// Define Firebase Data object
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

// Temperature sensor pin definition
#define TEMPERATURE_PIN 34  // pin for temperature sensor

// LED pin definition
#define LEDPIN 18  // GPIO pin for LED

#define ROW_NUM     4 // four rows
#define COLUMN_NUM  4 // four columns
char keys[ROW_NUM][COLUMN_NUM] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

byte pin_rows[ROW_NUM]      = {32, 33, 25, 26}; // connect to the row pinouts of the keypad
byte pin_column[COLUMN_NUM] = {14, 27, 12, 13};  // connect to the column pinouts of the keypad

Keypad keypad = Keypad(makeKeymap(keys), pin_rows, pin_column, ROW_NUM, COLUMN_NUM);
//const String password = "147*2580"; // change your password here
String input_password;

unsigned long sendDataPrevMillis = 0;
unsigned long ledCheckPrevMillis = 0;
unsigned long keypadCheckPrevMillis = 0;

const unsigned long dataInterval = 15000;
const unsigned long ledInterval = 5000;
const unsigned long keypadInterval = 100;

// LCD configuration
LiquidCrystal lcd(15, 2, 4, 16, 17, 5);

void setup() {
  Serial.begin(115200);

  // Initialize LCD
  lcd.begin(16, 2);
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Initializing...");

  // Initialize LED pin
  pinMode(LEDPIN, OUTPUT);
  // Initialize TEMPERATURE pin
  pinMode(TEMPERATURE_PIN, INPUT);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Serial.printf("Firebase Client v%s\n\n", FIREBASE_CLIENT_VERSION);

  /* Assign the api key (required) */
  config.api_key = API_KEY;

  /* Assign the user sign in credentials */
  auth.user.email = USER_EMAIL;
  auth.user.password = USER_PASSWORD;

  /* Assign the RTDB URL (required) */
  config.database_url = DATABASE_URL;

  /* Assign the callback function for the long running token generation task */
  config.token_status_callback = tokenStatusCallback; // see addons/TokenHelper.h

  // Comment or pass false value when WiFi reconnection will control by your code or third party library e.g. WiFiManager
  Firebase.reconnectNetwork(true);

  fbdo.setBSSLBufferSize(4096 /* Rx buffer size in bytes from 512 - 16384 */, 1024 /* Tx buffer size in bytes from 512 - 16384 */);

  Firebase.begin(&config, &auth);
  Firebase.setDoubleDigits(5);

  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Initialized");
}

void loop() {
  unsigned long currentMillis = millis();
  //message section

  // Keypad section
  if (currentMillis - keypadCheckPrevMillis >= keypadInterval) {
    keypadCheckPrevMillis = currentMillis;
    char key = keypad.getKey();
    if (key) {
      Serial.print("Key pressed: ");
      Serial.println(key);
      if (key != '#') {
        input_password += key;
        Serial.print("Input password: ");
        Serial.println(input_password);
      } else {
        if (Firebase.getString(fbdo, "/sensors/password/password")) {
          String password = fbdo.stringData();
          Serial.print("Stored password: ");
          Serial.println(password);
          if (password == input_password) {
            Serial.println("The password is correct, ACCESS GRANTED!");
            //lcd code
            if (Firebase.getString(fbdo, "/sensors/password/addedBy")) {
              String addedBy = fbdo.stringData();
              Serial.print("addedBy: ");
              Serial.println(addedBy);

              // Retrieve user data using addedBy value
              String userPath = "/Users/" + addedBy + "/name";
              if (Firebase.getString(fbdo, userPath)) {
                String usernameAddedBy = fbdo.stringData();
                Serial.print("Username: ");
                Serial.println(usernameAddedBy);
                // Print "Hi usernameAddedBy" on the LCD
                lcd.clear();
                lcd.setCursor(0, 0);
                lcd.print("Hi ");
                lcd.print(usernameAddedBy);
                delay(15000);
                lcd.clear();
              } else {
                Serial.print("Failed to get username for user: ");
                Serial.println(addedBy);
                Serial.println(fbdo.errorReason());
              }
            } else {
              Serial.println("Failed to get addedBy field");
              Serial.println(fbdo.errorReason());
            }
          } else {
            Serial.println("The password is incorrect, ACCESS DENIED!");
          }
        } else {
          Serial.println("Failed to get password from Firebase");
          Serial.println(fbdo.errorReason());
        }
        input_password = "";  // Reset the input password
      }
    }
  }

  // Firebase section
  if (Firebase.ready() && (currentMillis - sendDataPrevMillis >= dataInterval || sendDataPrevMillis == 0)) {
    sendDataPrevMillis = currentMillis;

    // Temperature section
    int temperature = analogRead(TEMPERATURE_PIN);
    float milliVolt = temperature * (3300.0  / 4096.0);
    float tempC = milliVolt / 10;
    String temperatureStr = String(tempC);

    if (Firebase.setString(fbdo, "/sensors/temperature", temperatureStr)) {
      Serial.print("Temperature sent to Firebase: ");
      Serial.println(temperatureStr);
    } else {
      Serial.println("Failed to send temperature to Firebase");
      Serial.println(fbdo.errorReason());
    }
  }

  // LED section
  if (Firebase.ready() && (currentMillis - ledCheckPrevMillis >= ledInterval || ledCheckPrevMillis == 0)) {
    ledCheckPrevMillis = currentMillis;

    if (Firebase.getBool(fbdo, "/sensors/led-status")) {
      bool ledStatus = fbdo.boolData();
      digitalWrite(LEDPIN, ledStatus ? HIGH : LOW);
      Serial.print("LED is now: ");
      Serial.println(ledStatus ? "ON" : "OFF");
    } else {
      Serial.println("Failed to get LED status from Firebase");
      Serial.println(fbdo.errorReason());
    }
  }
}
