node {
  stage 'Checkout'
  checkout scm
  stage 'Build and unit test'
  env.PATH = "${tool 'Maven'}/bin:${env.PATH}"
  sh 'mvn -B clean package'
  stage 'Create docker images'
  sh 'mvn -B -f web clean package -Pdocker'
}
