# CORS Configuration in the Library Project

## Overview

Cross-Origin Resource Sharing (CORS) is a security feature implemented by web browsers that restricts web pages from making requests to a different domain than the one that served the original page. This is known as the Same-Origin Policy. CORS provides a way for servers to indicate which origins are permitted to read information from the server.

In the Library project, CORS is configured using Spring Security's CORS support, which allows the backend API to specify which frontend applications are allowed to access it.

## Current Configuration

The CORS configuration is defined in the `SecurityConfig.java` file, specifically in the `corsConfigurationSource()` method:

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("http://localhost:4200");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

### Current Settings

- **Allowed Origins**: Only `http://localhost:4200` (typically an Angular frontend running locally)
- **Allowed Methods**: All HTTP methods (`*`)
- **Allowed Headers**: All headers (`*`)
- **Allow Credentials**: `true` (allows cookies and authentication headers to be sent with requests)
- **Path Pattern**: All endpoints (`/**`)

## Modifying CORS Settings for Different Environments

The current CORS configuration is hardcoded in the `SecurityConfig.java` file and does not change based on the active profile. This means that even when running in QA or production environments, the application will only accept requests from `http://localhost:4200`, which is not suitable for those environments.

### Option 1: Environment-Specific Configuration in Code

You can modify the `corsConfigurationSource()` method to read the allowed origins from the active profile:

```java
@Value("${cors.allowed-origins}")
private String[] allowedOrigins;

@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    for (String origin : allowedOrigins) {
        configuration.addAllowedOrigin(origin);
    }
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

Then, add the following properties to each environment-specific properties file:

#### application-dev.properties
```properties
cors.allowed-origins=http://localhost:4200
```

#### application-qa.properties
```properties
cors.allowed-origins=https://qa.library-app.example.com
```

#### application-cloud.properties (Production)
```properties
cors.allowed-origins=https://library-app.example.com
```

### Option 2: Conditional Configuration Based on Active Profile

Alternatively, you can use Spring's `@Profile` annotation to create different CORS configurations for each environment:

```java
@Bean
@Profile("dev")
public CorsConfigurationSource corsConfigurationSourceDev() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("http://localhost:4200");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

@Bean
@Profile("qa")
public CorsConfigurationSource corsConfigurationSourceQA() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("https://qa.library-app.example.com");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}

@Bean
@Profile("cloud") // Production
public CorsConfigurationSource corsConfigurationSourceProd() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin("https://library-app.example.com");
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

## Security Considerations

When configuring CORS, keep the following security considerations in mind:

1. **Restrict Allowed Origins**: Only allow trusted domains to access your API. Avoid using wildcards (`*`) for origins in production environments.

2. **Restrict Allowed Methods**: If your API only needs to support certain HTTP methods (e.g., GET, POST), specify only those methods instead of using the wildcard (`*`).

3. **Restrict Allowed Headers**: If possible, specify only the headers your application needs instead of using the wildcard (`*`).

4. **Consider Disabling Credentials**: If your API doesn't require cookies or authentication headers, consider setting `setAllowCredentials(false)`.

5. **Restrict Path Patterns**: If only certain endpoints need to be accessible from other origins, specify those endpoints instead of using the wildcard (`/**`).

## Testing CORS Configuration

To test if your CORS configuration is working correctly, you can use the following methods:

1. **Browser Developer Tools**: Open the Network tab in your browser's developer tools and check if the CORS headers are present in the response.

2. **curl**: Use the following command to test if your API accepts requests from a specific origin:

```bash
curl -H "Origin: http://example.com" -H "Access-Control-Request-Method: GET" -H "Access-Control-Request-Headers: X-Requested-With" -X OPTIONS --verbose https://your-api-url.com/endpoint
```

3. **Postman**: Set the `Origin` header in your request and check if the response includes the appropriate CORS headers.

## Conclusion

CORS is an important security feature that controls which domains can access your API. In the Library project, CORS is configured in the `SecurityConfig.java` file and currently only allows requests from `http://localhost:4200`. To make the application work in QA and production environments, you need to modify the CORS configuration to allow requests from the appropriate domains.