# tour-web-app

**Generate RSA Keys for JWT**

You can generate them using OpenSSL. We will put them in the src/main/resources/jwt folder.

```java
cd src/main/resources/jwt
openssl genpkey -algorithm RSA -out app.key -outform PEM
openssl rsa -pubout -in app.key -out app.pub
```

**And add the keys to the application.yaml:**

```java
jwt:
  private-key: classpath:jwt/app.key
  public-key: classpath:jwt/app.pub
  ttl: 15m
```
