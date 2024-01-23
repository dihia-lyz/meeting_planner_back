node("dihia-ci"){
    stage("checkout"){
        echo "start checkout"
        echo "start checkout 2"
        checkout scmGit(branches: [[name: '*/main']], extensions: [],
        userRemoteConfigs: [[url: 'https://github.com/dihia-lyz/meeting_planner_back.git']])
    }

    stage("unit tests"){
        echo "start tests"
        sh "chmod 777 mvnw"
        sh "./mvnw test"
    }

    stage("build"){
        echo "start build"
        sh "chmod 777 mvnw"
        sh "./mvnw clean package -DskipTests=true"
    }

//     stage("build docker image"){
//         "sudo docker build -t "
//     }
}