# qinhetea-api

[![Build Status][build-badge]][build-status]

## usage

```bash
./gradlew tasks

# run as spring boot application
SPRING_PROFILES_ACTIVE=development ./gradlew bootRun

# run all checks
./gradlew check

# package and run
./gradlew build
java -jar build/libs/*.jar --spring.profiles.active=production
```

## references

### spring boot

- <https://spring.io/guides/gs/spring-boot/>
- <https://spring.io/guides/gs/accessing-data-rest/>
- <https://spring.io/guides/topicals/spring-security-architecture/>
- <https://spring.io/guides/tutorials/react-and-spring-data-rest/>
- <https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/>
- <https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/>

### kotlin

- <https://kotlinlang.org/docs/reference/>
- <https://kotlinlang.org/docs/reference/idioms.html>
- <https://kotlinlang.org/docs/reference/null-safety.html>
- <https://kotlinlang.org/docs/reference/using-gradle.html>
- <https://kotlinlang.org/api/latest/jvm/stdlib/index.html>

### weixin

- <https://mp.weixin.qq.com/wiki>
- <https://github.com/wechat-group/weixin-java-tools/wiki>
- <https://github.com/wechat-group/weixin-java-mp-demo-springboot>

[build-badge]: https://img.shields.io/travis/airt/qinhetea-api.svg
[build-status]: https://travis-ci.org/airt/qinhetea-api
