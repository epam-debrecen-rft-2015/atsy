node {
  stage 'Checkout'
  checkout scm

  stage 'Build and unit test'
  env.PATH = "${tool 'Maven'}/bin:${env.PATH}"
  sh 'mvn -B clean install -DskipITs'

  stage 'Create docker images'
  sh 'mvn -B -f web clean package -Pdocker'

  stage 'Startup environment'

  echo "Branch is ${env.BRANCH_NAME}"

  def profile = profileFor(env.BRANCH_NAME)

  echo "Selected profile is ${profile}"

  def portMapping = determinePortMapping(env.BRANCH_NAME)

  echo "Selected port mapping is ${portMapping}"

  sh 'PORT_MAPPING="' + portMapping + '" PROFILE="' + profile + '" docker-compose up -d'

  sh "docker port ${env.BRANCH_NAME}_app_1"
}

@NonCPS
def profileFor(branchName) {
  branchName ==~ /^master$/ ? 'production' : 'integration'
}

@NonCPS
def determinePortMapping(branchName) {
  def existingPort = sh (
    script: "docker port ${branchName}_app_1 | cut -d: -f2",
    returnStdout: true
  )
  echo "Existing port detection: ${existingPort}"
  return (existingPort != null && !existingPort.trim().isEmpty()) ? (existingPort.trim() + ':8080') : '8080'
}
