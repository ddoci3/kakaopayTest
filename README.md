- 개발 프레임워크

  Spring Tool Suite 4  (Version: 4.4.2.RELEASE,   Build Id: 201911201053)

  - DB : MariaDB10.4


- 문제해결방법

  - 의존성 문제 해결
    유닛 테스트, Maria DB 연동 등의 의존성 문제 해결을 위해
    build.gradle 파일의 dependencies 에 아래 내용 추가

    	implementation 'org.springframework.boot:spring-boot-starter-web'
    	implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.1'
    	implementation 'org.json:json:20190722'
    	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    	developmentOnly 'org.springframework.boot:spring-boot-devtools'
    	runtimeOnly 'mysql:mysql-connector-java'
    	testImplementation('org.springframework.boot:spring-boot-starter-test') {
    		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    	}

    이후 build.gradle 선택 후 마우스 우클릭 > Gradle > Refresh Gradle Project 선택

  - 서버포트 설정 및 MariaDB 연동
    application.properties (src/main/resources/) 파일에 아래 내용 추가

      server.port = 8080
      
      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      
      spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul
      spring.datasource.username=root
      spring.datasource.password=root

  - 접속주소 html 페이지 연결
    index.html 파일 추가 (src/main/resources/templates/)


- 빌드 및 실행방법

  - 빌드
    Spring Tool Suite 4 상단메뉴 Project > Build Project 또는 Build Automatically 선택

  - 실행
    Spring Tool Suite 4의 Package Explorer에서 ktest [boot] 선택 > 상단 메뉴 Run > Run As > Spring Boot App 선택

  - 웹접속
    주소 : http://localhost:8080/

  