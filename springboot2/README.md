# Build a springboot project

## Step 1

### Spring Initializr:

* Lombok + Spring Boot DevTools + Spring Configuration Processor
* MySQL Driver
* Spring Web
* MyBatis Framework

### Connect to DB:

* IDEA可以直接连DB，最右边Maven上面

### Configuration:

* application.properties -> application.yml
* modify `application.yml`

### Add Dependencies:

* [mvn repository](https://mvnrepository.com/)
* add `Junit 4` 或者用自带的 `Junit 5` 也行

## Step 2

### Implement the Backend Logic (Examples):

* structure:

  ```
  ├── src
  │   ├── main
  │   │   ├── java
  │   │   │   └── com.example.springboot2
  │   │   │       ├── controller
  │   │   │       │   └── (Controller classes)
  │   │   │       ├── service
  │   │   │       │   └── (Service classes)
  │   │   │       ├── mapper
  │   │   │       │   └── (Mapper interfaces)
  │   │   │       ├── model
  │   │   │       │   └── (Entity/Model classes)
  │   │   │       ├── utils
  │   │   │       ├── constant
  │   │   │       └── App.java
  │   │   └── resources
  │   │       ├── mapper
  │   │       │   └── (Mapper xmls)
  │   │       └── application.yml
  │   └── test
  │       └── java
  │           └── com.example.springboot2
  │               └── (Test classes)
  └── pom.xml
  ```

### See implementations of `User`:

* `User.java`:
  * Long id;
* `UserMapper.java`:
  * @Mapper
  * 右键 generate Test
* `UserService.java`:
  * Insert & Update: return int
  * Delete: Not really delete; = Update
* `UserServiceImpl.java`:
  * @Service
* `UserController.java`
* `UserMapper.xml`
* `UserMapperTest.java`:
  * Test `UserMapper.java`
  * Junit 4:

  ```java
  import org.junit.Test;
  
  @SpringBootTest
  @RunWith(SpringRunner.class)
  public class UserMapperTest {}
  ```

  * Junit 5 (默认是这个，想必这个好用吧):

  ```java
  import org.junit.jupiter.api.Test;
  
  @SpringBootTest
  public class UserMapperTest {}
  ```

## Tips
* MyBatisX: 自动生成
* GenerateAllSetter: 
  * 自动生成所有setter方法
  * 选定user，alt + enter -> Generate all setter with default value

* 一般前后端都要做校验，因为用户可以不通过前端直接发请求
* Apache commons Lang: `StringUtils.isAnyBlank`
* java正则表达式处理特殊字符
* 注意优化性能，比如查询sql的语句可以放在所有if的最后执行
* 密码加密：`DigestUtils.md5DigestAsHex`

* 登录后返回的用户信息要**脱敏**，即不返回 passwd & isDelete
* @slf4j: lombok提供的日志工具，使用 `log.info` 记录日志
* **逻辑删除**: MyBatis plus支持，配置即可
* Auto filling Java call arguments:
  * 自动填充java的参数
* UserRegisterRequest 继承 Serializable
* **IDEA自带的测试请求工具**: 
  * Tools -> HTTP Client -> Create request
  * 点方法左边绿色的球也可以
* @DeleteMapping不建议，推荐@PostMapping("/delete")
* constant: 可以使用interface，其属性默认是public static
* session超时时间: 加在 `application.yml`
* Impl -> Interface:
  * @Override
  * 在这个注解上 alt + enter -> Pull method to ...
* 修改变量名: Refactor/Rename修改所有，不然还要改挨个改
* 后端请求地址全部 + '/api'

```yml
servlet:
  context-path: /api
```

* F12 -> x-real-url 可以看到代理到的url