# Non Functional Property / Quality Attributes

Non Functional Property (or Quality attributes) of a software system are constraint on the manner in which the system implements and delivers its functionality.

In our case, we have identified the following non-functional properties:

## Quality Attributes Overview

### Operational Properties

1. **Availability**
    1. Fault tolerance to ensure system availability.
    2. Automatic recovery of the system in case of failure.
    3. Possibility of redundancy of critical components to ensure system availability.
2. **Scalability & Performance**:
    1. Ability of **horizontal scalability** to handle increasing operational load.
3. **Deployability**:
    1. Automated deployment of new software releases.
4. **Reliability & Continuity**:
    1. Restore the parts of the system that were affected by a failure.
5. **Observability**:
    1. Monitoring of system performance.
    2. Graphical representation of system performance.

### Structural Properties

1. **Extensibility**:
    1. Clearly defined architecture to allow the addition of new features.
2. **Maintainability**:
    1. Artifact modularity.
    2. Well-structured and understandable source code.
3. **Testability**:
    1. Automated testing.
    2. Unit testing.
    3. Integration testing.
    4. Acceptance testing.

### Security Properties

1. Authentication of users to verify their identity.
2. Authorization of users to access resources according to established rules.
3. Encryption to ensure confidentiality of user passwords.

## Quality Attributes Scenarios

Also for some of these non-functional property, we have identified some scenarios that will be used to evaluate the quality of the system:

### Availability Scenario

#### Fault Tolerance
- **Stimulus:** A server node becomes unavailable.
- **Stimulus Source:** Hardware failure.
- **Response:** The system detects the failure and reroutes traffic to available nodes.
- **Response Measure:** The system reroutes traffic in less than 5 seconds without service interruption.
- **Environment:** Normal runtime operations.
- **Artifact:** Server management system.

#### Automatic Recovery
- **Stimulus:** An unexpected outage occurs.
- **Stimulus Source:** Software malfunction.
- **Response:** The system automatically performs a failover and restores service.
- **Response Measure:** Service is restored within 10 seconds.
- **Environment:** Normal runtime operations.
- **Artifact:** Entire system.

### Deployability Scenario

#### Deployment Automation
- **Stimulus:** A new software release is ready for deployment.
- **Stimulus Source:** Development team.
- **Response:** The system automatically deploys the new release to the production environment.
- **Response Measure:** Deployment is completed within 30 minutes since the release is ready without manual intervention.
- **Environment:** Production environment.
- **Artifact:** Deployment system.

### Scalability and Performance Scenario

#### Horizontal Scalability
- **Stimulus:** Sudden increase in the number of users.
- **Stimulus Source:** Advertised event bringing new users to the platform.
- **Response:** The system dynamically adds computing resources to handle the increased load.
- **Response Measure:** No negative impact on performance with a 100% increase in users.
- **Environment:** Normal runtime operations.
- **Artifact:** Server management system.

### Reliability and Continuity Scenario

#### System Restoration
- **Stimulus:** Part of the system fails.
- **Stimulus Source:** Network issue causing service disruption.
- **Response:** The system isolates and repairs the affected part, restoring functionality.
- **Response Measure:** Service is restored within 5 minutes.
- **Environment:** Normal runtime operations.
- **Artifact:** Entire system.

### Observability Scenario

#### Performance Monitoring
- **Stimulus:** A new software release is deployed.
- **Stimulus Source:** Development team.
- **Response:** The system monitors performance metrics in real-time and generates reports.
- **Response Measure:** Performance reports are generated within 2 minutes of deployment.
- **Environment:** Production environment.
- **Artifact:** Monitoring system.

### Extensibility Scenario

#### Feature Addition
- **Stimulus:** A new feature is requested by the product team.
- **Stimulus Source:** Product team.
- **Response:** The system supports the integration of the new feature with minimal changes to existing code.
- **Response Measure:** Time required to add the new feature is less than 2 weeks.
- **Environment:** Development environment.
- **Artifact:** System architecture.

### Maintainability Scenario

#### Code Maintenance
- **Stimulus:** A bug is reported in an existing feature.
- **Stimulus Source:** End user.
- **Response:** The development team fixes the bug with localized changes.
- **Response Measure:** The bug is resolved within 24 hours.
- **Environment:** Development and testing environment.
- **Artifact:** Source code.

### Testability Scenario

#### Automated Testing
- **Stimulus:** A new feature is completed.
- **Stimulus Source:** Development team.
- **Response:** The system runs a suite of automated tests.
- **Response Measure:** 95% of tests must pass without errors.
- **Environment:** Testing environment.
- **Artifact:** Test suite.

### Security Scenario

#### User Authentication
- **Stimulus:** A user attempts to log into the system.
- **Stimulus Source:** User login request.
- **Response:** The system verifies the user's identity using secure credentials.
- **Response Measure:** Authentication is completed within 2 seconds.
- **Environment:** Normal runtime operations.
- **Artifact:** Authentication module.


#### Data Encryption
- **Stimulus:** A user submits a password for login.
- **Stimulus Source:** User login request.
- **Response:** The system encrypts the password before storing it.
- **Response Measure:** The password is encrypted using an advanced encryption algorithm.
- **Environment:** Normal runtime operations.
- **Artifact:** Credential management system.
