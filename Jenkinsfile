pipeline {
    agent any
    
    tools {
        maven 'maven'
        jdk 'JDK21'  // Make sure this matches the name you gave in Global Tool Configuration
    }

    stages {
		stage('Verify JAVA_HOME') {
            steps {
                bat 'echo %JAVA_HOME%'
                bat 'java -version'
            }
        }
	stage('Verify MAVEN_HOME') {
            steps {
                bat 'echo %MAVEN_HOME%\bin'  // Print the Java home directory
                bat 'mvn -v'     // Print the Java version being used
            }
	}
	// Stage to check Maven dependency tree
        stage('Check Dependencies') {
            steps {
                echo 'Checking Maven dependencies...'
                bat 'mvn dependency:tree'  // This will print the Maven dependency tree
            }
        }
        // Clone repository stage
        stage('Checkout') {
            steps {
                // Checkout the code from your version control system (GitHub)
                checkout scm
            }
        }

        // Install dependencies using Maven (or Gradle if you're using that)
        stage('Build') {
            steps {
                echo 'Building the project...'
                // Build the project using Maven
                bat 'mvn clean install'
            }
        }

        // Run the Selenium test cases
        stage('Run Tests') {
            steps {
                echo 'Running tests...'
                // Execute the tests using Maven
                 bat 'mvn test'
            }

            // Post action for collecting results
            post {
                always {
                    // Archive test results (for TestNG)
                    echo 'Publishing test results...'
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        // Generate HTML Report if using TestNG or Allure
        stage('Publish Report') {
            steps {
                echo 'Publishing HTML report...'
                // If you're using Allure for reporting
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    // Post actions for cleaning up or other actions after the pipeline run
    post {
        always {
            // You can clean up workspace or do any other post steps here
            cleanWs()
        }
        failure {
            echo "Build failed!"
        }
        success {
            echo "Build succeeded!"
        }
    }
}
