# ===Private Excel File Reader ===

```java

package com.org.ss.techno;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@EnableAsync
@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/spreadsheets.readonly");
    private static final String CREDENTIALS_FILE_PATH = "D:\\ss-techno-it-solutions\\src\\main\\resources\\credentials.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        File credentialsFile = new File(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(credentialsFile));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        logger.info("Starting spring boot started..");
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        String spreadsheetId = "1hwvHbRargzmbErRYGU2cjxf4PR8GTOI-e1R9VqOVQgY";
        String range = "Hard Problems";  // Give the sheet name here
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("=======Number==========");
            for (List<Object> row : values) {
                System.out.println(row.size() == 4 ? row.get(0) : "");
            }
            System.out.println("=======Topic==========");

            for (List<Object> row : values) {
                System.out.println(row.size() == 4 ? row.get(1) : "");
            }
            System.out.println("=======Question==========");

            for (List<Object> row : values) {
                System.out.println(row.size() == 4 ? row.get(2) : "");
            }
            System.out.println("=======Difficulty==========");
            for (List<Object> row : values) {
                System.out.println(row.size() == 4 ? row.get(3) : "");
            }


        }
        logger.info("Starting spring boot completed");
    }
}

```
# ====credentials.json =====
```json
{"installed":{"client_id":"99262103412-o2ao7fbfra605o1856n3llmk4qvgah4u.apps.googleusercontent.com","project_id":"sheet-reader-451720","auth_uri":"https://accounts.google.com/o/oauth2/auth","token_uri":"https://oauth2.googleapis.com/token","auth_provider_x509_cert_url":"https://www.googleapis.com/oauth2/v1/certs","client_secret":"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","redirect_uris":["http://localhost"]}}

```
# ======pom.xml =====

```xml
        <dependency>
            <groupId>com.google.api-client</groupId>
            <artifactId>google-api-client</artifactId>
            <version>1.33.2</version>
        </dependency>

        <dependency>
            <groupId>com.google.apis</groupId>
            <artifactId>google-api-services-sheets</artifactId>
            <version>v4-rev612-1.25.0</version>
        </dependency>

        <dependency>
            <groupId>com.google.oauth-client</groupId>
            <artifactId>google-oauth-client-jetty</artifactId>
            <version>1.34.1</version>
        </dependency>

        <dependency>
            <groupId>com.google.http-client</groupId>
            <artifactId>google-http-client-gson</artifactId>
            <version>1.39.2</version>
        </dependency>

```

# =====Steps for google account=====
## Step-by-Step Guide: Setting Up Google Sheets API with OAuth 2.0

### 1. Create a Google Cloud Project

1. **Open Google Cloud Console**  
   - Visit [console.cloud.google.com](https://console.cloud.google.com/).

2. **Create a New Project**  
   - Click the **project drop-down** at the top of the page.
   - Select **New Project**.
   - **Enter a Project Name:** e.g., "SheetReader-Project".
   - (Optional) Select an organization if applicable.
   - Click **Create**.
   - **Note your Project ID** for later use.

---

### 2. Enable the Google Sheets API

1. **Navigate to APIs & Services**  
   - In the left-hand menu, click **APIs & Services**.

2. **Enable the API**  
   - Click **+ ENABLE APIS AND SERVICES**.
   - Search for **Google Sheets API**.
   - Click **Google Sheets API** from the search results.
   - Click **Enable**.

---

### 3. Obtain OAuth 2.0 Credentials

#### Option A: Using OAuth 2.0 Client ID (User-Based Access)

1. **Go to Credentials Section**  
   - In the Cloud Console, click **APIs & Services > Credentials**.

2. **Create OAuth Client ID**  
   - Click **+ CREATE CREDENTIALS**.
   - Choose **OAuth client ID**.

3. **Configure the Consent Screen (if required)**  
   - Select **External** (for personal use) or **Internal** (for organization use).
   - Fill out required fields (**App name**, **Support email**, **Developer contact**).
   - Click **Save and Continue**.

4. **Create OAuth Client ID**  
   - Select **Desktop app** as the Application type.
   - Click **Create**.
   - **Download the Credentials** (`credentials.json`).

---

### 4. Add Test Users (For Testing Mode)

1. **Navigate to OAuth Consent Screen or audiance screen**  
   - In the left sidebar, click **APIs & Services > OAuth consent screen**.

2. **Locate Test Users Section**  
   - Scroll down to the **Test users** section.

3. **Add Test Users**  
   - Click **Add Users**.
   - Enter your email (e.g., `nawalesagar0@gmail.com`).
   - Click **Save**.

4. **Confirm Status**  
   - Ensure your email appears in the test user list.
   - Now, you can complete OAuth login from your Java application.

---

### 5. Final Steps for Java Integration

- **Place `credentials.json`** in your project’s resources folder.
- Follow [Google Sheets API Java Quickstart](https://developers.google.com/sheets/api/quickstart/java) for implementation.
- Adjust permissions if access is denied.

---

### Troubleshooting

- If you see **Error 403: Access Denied**, ensure your email is listed as a test user.
- If you encounter **OAuth brand creation issues**, refresh and retry.
- If Google API version issues occur, ensure dependencies match your library versions.

---

By following these steps, you can set up and access the Google Sheets API in your Java application.

================================
