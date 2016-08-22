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

  sh 'PORT_MAPPING="8080" PROFILE="' + profile + '" docker-compose up -d'

  sh "docker port ${env.BRANCH_NAME}_app_1"
}

@NonCPS
def profileFor(branchName) {
  branchName ==~ /^master$/ ? 'production' : 'integration'
}
