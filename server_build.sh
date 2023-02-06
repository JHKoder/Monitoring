jarRoot="./build/libs/Monitoring-0.1.0-RELEASE.jar"
serverId="jeonghun.kang.dev"
serverIp="34.168.204.241"
serverRoot=":server"

sh `pwd`/gradlew build
scp -i gcp_rsa_key ${jarRoot} ${serverId}@${serverIp}${serverRoot}
