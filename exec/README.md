## 개발 환경

### Front-end

- **Framework** : Vue.js
- **지원 환경** : Web
- **담당자** : `권예빈`,`김보민`

<br>

### Back-end

- **Framework** : Spring boot 
- **Database** : MySQL
- **담당자** :  `김윤빈`, `정동원`

<br>

## ⚙ IDE

- Java : jdk11 (11.0.12)
- Docker : 20.10.7
- node.js : 14.17.3
- maven : Apache Maven 3.6.0
- gradle: 6.8.3
- openvidu : 2.19

<br>

## 😋 설치 가이드

- Frontend

```bash
$ cd frontend
$ npm i
$ npm run serve
```

- Backend

```bash
# API server 
$ cd Backend
$ gradle wrap # gradle wrapper 없을 경우 실행
$ ./gradlew clean build
$ sudo java -jar build/libs/ssafy-fifth-web-common-project-1.0-SNAPSHOT.jar

# openvidu KMS
$ docker run --rm -p8888:8888/tcp -p5000-5050:5000-5050/udp -e KMS_MIN_PORT=5000 -e KMS_MAX_PORT=5050 kurento/kurento-media-server:latest

# openvidu-server
$ cd ..
# /openvidu
$ sudo mvn package -DskipTests
$ cd openvidu-server/target 
$ java -jar openvidu-server-2.19.0.jar

```

<br>

## 🐤 Server Description

- port (nginx)

  - | 443               | server default(https)                                        |
    | ----------------- | ------------------------------------------------------------ |
    | **80**            | server default(http) (redirect to 443)                       |
    | **5443**          | openvidu (Spring boot running)                               |
    | **8080**          | REST API (Spring boot running) (reverse proxy)               |
    | **3478**          | used by TURN server to resolve clients IPs.                  |
    | **40000 - 57000** | used by Kurento Media Server to establish media connections. |
    | **57001 - 65535** | used by TURN server to establish relayed media connections.  |

