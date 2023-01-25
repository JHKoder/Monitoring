jarRoot="build/libs/"
jarName="Monitoring-0.1.0-RELEASE.jar"
serverIp="192.168.1.4"
serverRoot=":server"

sh `pwd`/gradlew build
ssh ${serverIp} <<EOF
${jarRoot}${jarName} ${serverIp}${serverRoot}
fuser -k 8080/tcp
java -jar server/${jarName}
EOF
