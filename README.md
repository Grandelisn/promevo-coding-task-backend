# Promevo Coding Task Back-end

This project serves as the backend "vertical slice" for the gPanel file/label management application, bridging a Material UI frontend with Google Cloud services.

---

## 🚀 Features

* **Google Gmail API Integration**: Full CRUD capability interacting directly with Google's workspace ecosystem.
* **Secure Credential Architecture**: Zero-risk configuration that loads sensitive Google OAuth/Service Account credentials strictly from local files or environment variables.
* **Spring Web Middleware**: Acts as a decoupled translation layer mapping frontend actions to authenticated Google API calls.
* **Maven Ecosystem**: Standardized build structure with the Maven wrapper (`mvnw`) for immediate, environment-agnostic setup.

---

## 🛠️ Prerequisites

Before running the application, ensure you have:

* **Java Development Kit (JDK)**: Version 17
* **Maven**: Version 3.8+ (Optional, if using the included Maven wrapper)

---

## 🔒 Security & Configuration

1. Download your credentials JSON file from the Google Cloud Console or run the application, you should be prompted to login to a google account.
