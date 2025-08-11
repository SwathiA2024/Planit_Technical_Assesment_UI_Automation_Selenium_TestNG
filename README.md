# Planit Technical Assessment – UI Automation

Automated UI test suite for [Jupiter Toys](http://jupiter.cloud.planittesting.com) as part of the **Planit Technical Assessment**.


## 💡 Key Highlights

- **Cross-browser support:** Tested and verified on **Google Chrome** and **Mozilla Firefox**.
- **Scalable design:** Page Object Model (POM) for maintainability and easy test expansion.
- **Reusable utilities:** Common methods for waits, assertions, and element interactions.
- **Robust reporting:** Extent Reports with step-level details and embedded screenshots.
- **Parameterization:** Easily change browser and headless mode via Maven command line.
- **High reliability:** Test Case 2 executed 5 times consecutively with a 100% pass rate.
-  **CI-ready:** Created a Jenkinfile configuration and added to the project.

## 🛠 Design Decisions
**POM Structure**: Separates page locators using PageFactory to get rid of abundent declarations, actions, and test logic for easier maintenance.
**TestNG**: Chosen for flexible test configuration using, grouping, and suite execution .
**Extent Reports**: Business-friendly HTML reporting with screenshots.
**Maven Surefire Plugin**: Handles parameterized suite execution and CI integration.
**Parameterization**: Allows switching browsers and headless mode without code changes.

## Test Cases

### **Test Case 1 – Contact Page Error Validation**
1. Navigate to Contact page from Home.
2. Submit without filling mandatory fields.
3. Verify all error messages are displayed.
4. Fill mandatory fields and confirm errors disappear.

### **Test Case 2 – Contact Page Successful Submission**
1. Navigate to Contact page from Home.
2. Fill all mandatory fields.
3. Submit form.
4. Validate success message appears.
5. Executed 5 consecutive times to ensure 100% reliability.

### **Test Case 3 – Cart Verification**
1. Add **2 Stuffed Frogs**, **5 Fluffy Bunnies**, and **3 Valentine Bears**.
2. Go to Cart page.
3. Verify:
   - Unit price for each product is correct.
   - Subtotal for each product is correct.
   - Cart total equals the sum of subtotals.

## 📂 Project Structure
PlanitProject/
│
├── src/
│ ├── test/java/... # Page objects & utilities
│ ├── test/java/... # Test classes
│ └── test/resources/ # testng.xml & test data
│
├── test-output/ # reports
├── pom.xml # Maven configuration
├── Jenkinsfile # CI pipeline script
└── README.md

## ▶️ Running Tests Locally

**Pre-requisites:**
- Java 17 installed
- Maven 3.9.x installed
- Chrome & Firefox browsers installed

**Run on Chrome (headless):**
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dbrowser=chrome -Dheadless=true

**Run on Chrome (headed):**
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dbrowser=chrome -Dheadless=false

**Run on FireFox (headless):**
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dbrowser=firefox -Dheadless=true

**Run on FireFox (headed):**
mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -Dbrowser=firefox -Dheadless=false

🤖 Running in Jenkins
This project includes a Jenkinsfile for CI execution.
Steps:
Create a Pipeline job in Jenkins.
Point it to this GitHub repository.
Ensure JDK 17 and Maven 3.9.x are configured in Manage Jenkins → Tools.
Run the pipeline.

## Extent Report:
Path: test-output/ExtentReport.html
Includes step-level logs, screenshots on failure, and visual pass/fail summaries.
Scrrenshots attached on report for clear visibility project /Screenshots folder

## 🌐 Browser Support
The test suite is designed to run on both **Google Chrome** and **Mozilla Firefox**.  
You can choose the browser at runtime using the `-Dbrowser` parameter or testng is configured with parameters and runs for both browsers when run.

IDE result: Testcases 14 (chrome and firefox)
<img width="920" height="473" alt="image" src="https://github.com/user-attachments/assets/13193a07-736c-4be8-9da0-506528d29b6d" />

Extent Report:
<img width="940" height="469" alt="image" src="https://github.com/user-attachments/assets/32329c77-39f0-43c6-a576-5a627980825c" />
<img width="955" height="462" alt="image" src="https://github.com/user-attachments/assets/b5675a56-4b1d-4af6-ba7a-c7ee75200dc7" />

Jenkins run:
<img width="948" height="464" alt="image" src="https://github.com/user-attachments/assets/a35c27ed-eed6-4345-a098-b796746e9732" />

👤 Author
Swathi Chowdary Vejendla
Automation Test Engineer - Candidate  – Planit
