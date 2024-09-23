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
	stage('Verify Maven and Java') {
		steps {
			bat 'mvn -version'  // Print Maven version
			bat 'java -version' // Print Java version
		}
	}

	// Stage to check Maven dependency tree
        stage('Check Dependencies') {
            steps {
                echo 'Checking Maven dependencies...'
                bat 'mvn dependency:tree -Dverbose'  // This will print the Maven dependency tre
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
                bat 'mvn clean install -U'
            }
        }

        // Run the Selenium test cases
        stage('Run Tests') {
            steps {
                echo 'Running tests...'
                // Execute the tests using Maven
                 bat 'mvn clean test -X'
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
