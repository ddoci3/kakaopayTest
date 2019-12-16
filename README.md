- ���� �����ӿ�ũ

  Spring Tool Suite 4  (Version: 4.4.2.RELEASE,   Build Id: 201911201053)

  - DB : MariaDB10.4


- �����ذ���

  - ������ ���� �ذ�
    ���� �׽�Ʈ, Maria DB ���� ���� ������ ���� �ذ��� ����
    build.gradle ������ dependencies �� �Ʒ� ���� �߰�

    	implementation 'org.springframework.boot:spring-boot-starter-web'
    	implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.1'
    	implementation 'org.json:json:20190722'
    	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    	developmentOnly 'org.springframework.boot:spring-boot-devtools'
    	runtimeOnly 'mysql:mysql-connector-java'
    	testImplementation('org.springframework.boot:spring-boot-starter-test') {
    		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
    	}

    ���� build.gradle ���� �� ���콺 ��Ŭ�� > Gradle > Refresh Gradle Project ����

  - ������Ʈ ���� �� MariaDB ����
    application.properties (src/main/resources/) ���Ͽ� �Ʒ� ���� �߰�

      server.port = 8080
      
      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      
      spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Seoul
      spring.datasource.username=root
      spring.datasource.password=root

  - �����ּ� html ������ ����
    index.html ���� �߰� (src/main/resources/templates/)


- ���� �� ������

  - ����
    Spring Tool Suite 4 ��ܸ޴� Project > Build Project �Ǵ� Build Automatically ����

  - ����
    Spring Tool Suite 4�� Package Explorer���� ktest [boot] ���� > ��� �޴� Run > Run As > Spring Boot App ����

  - ������
    �ּ� : http://localhost:8080/

  