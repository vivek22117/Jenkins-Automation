pipeline {
    agent any

    parameters {
        string(name: 'VPC_NTWRK', defaultValue: 'vpc-subnet-network-by-vivek', description: 'Name of VPC Created')
        string(name: 'NACL', defaultValue: 'vpc-subnet-nacl-by-vivek', description: 'worspace to use in Terraform')
        string(name: 'ROUTEONE', defaultValue: 'vpc-internet-routing-by-vivek', description: 'worspace to use in Terraform')
        string(name: 'ROUTETWO', defaultValue: 'vpc-private-routing-by-vivek', description: 'worspace to use in Terraform')
        string(name: 'JENKINS', defaultValue: 'ec2-jenkins-master-by-vivek', description: 'worspace to use in Terraform')
        string(name: 'JSERVER', defaultValue: 'ec2-web-server-asg', description: 'RDS for Web Server')
        string(name: 'REGION', defaultValue: 'us-east-1', description: 'RDS for Web Server')
    }
    stages {
        stage('delete-all-stacks') {
            steps {
                script {
                    try {
                        sh "aws cloudformation delete-stack --stack-name ${params.JSERVER} --region ${params.REGION}"
                        sh "aws cloudformation --region ${params.REGION} wait stack-delete-complete --stack-name ${params.JSERVER}"
                        sh "aws cloudformation delete-stack --stack-name ${params.JENKINS} --region ${params.REGION}"
                        sh "aws cloudformation --region ${params.REGION} wait stack-delete-complete --stack-name ${params.JENKINS}"
                        sh "aws cloudformation delete-stack --stack-name ${params.ROUTETWO} --region ${params.REGION}"
                        sh "aws cloudformation --region ${params.REGION} wait stack-delete-complete --stack-name ${params.ROUTETWO}"
                        sh "aws cloudformation delete-stack --stack-name ${params.ROUTEONE} --region ${params.REGION}"
                        sh "aws cloudformation --region ${params.REGION} wait stack-delete-complete --stack-name ${params.ROUTEONE}"
                        sh "aws cloudformation delete-stack --stack-name ${params.NACL} --region ${params.REGION}"
                        sh "aws cloudformation --region ${params.REGION} wait stack-delete-complete --stack-name ${params.NACL}"
                        sh "aws cloudformation delete-stack --stack-name ${params.VPC_NTWRK} --region ${params.REGION}"
                        sh "aws cloudformation --region ${params.REGION} wait stack-delete-complete --stack-name ${params.VPC_NTWRK}"
                    } catch (err) {
                        sh "echo Exception occured"

                    }
                    sh "echo Finished create/update/delete successfully!"
                }
            }
        }
    }
}