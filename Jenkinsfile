node {
  stage 'Checkout'
  checkout scm
  stage 'Build and unit test'
  env.PATH = "${tool 'Maven'}/bin:${env.PATH}"
  sh 'mvn clean package'
}
