package com.basicSpringBootRestAPI.util

import org.springframework.http.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

class MicroServiceUtils {
    static <T> T jsonPutForObject(String url, final Object body, Class<T> clazz, Map headers = [:]) {
        return jsonRequestForObject(url, HttpMethod.PUT, body, clazz, headers)
    }

    static <T> T jsonDeleteForObject(String url, Class<T> clazz, Map headers = [:]) {
        return jsonRequestForObject(url, HttpMethod.DELETE, null, clazz, headers)
    }

    static <T> T jsonPostForObject(String url, final Object body, Class<T> clazz, Map headers = [:]) {
        return jsonRequestForObject(url, HttpMethod.POST, body, clazz, headers)
    }

    static <T> T jsonGetForObject(String url, Class<T> clazz, Map headers = [:]) {
        return jsonRequestForObject(url, HttpMethod.GET, null, clazz, headers)
    }

    static <T> T jsonRequestForObject(String url, HttpMethod method,
                                      final Object body, Class<T> clazz, Map headers = [:]) {
        println("INFO: MicroServiceUtils -> URL : ${url}")
        println("INFO: MicroServiceUtils -> METHOD : ${method}")
        println("INFO: MicroServiceUtils -> BODY : ${body}")
        println("INFO: MicroServiceUtils -> HEADER : ${headers}")

        T responseBody = null;

        RestTemplate restTemplate = new RestTemplate()
        HttpHeaders httpHeader = new HttpHeaders()

        httpHeader.setContentType(MediaType.APPLICATION_JSON)
        if (headers.size() != 0) {
            addHeadersToRequest(headers, httpHeader)
        }
        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeader)
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, method, httpEntity, clazz)
            if (response.getStatusCode() == HttpStatus.OK) {
                responseBody = response.getBody()
            }
        } catch (HttpClientErrorException e) {
            println("ERROR: MicroServiceUtils -> HttpClientErrorException occur : ${e + " : " + e.getResponseBodyAsString()}")
        } catch (Exception e) {
            println("ERROR: MicroServiceUtils -> Exception occur : ${e}")
        }
        println("INFO: MicroServiceUtils -> RESPONSE : ${responseBody}")
        return responseBody
    }

    static void addHeadersToRequest(Map map, HttpHeaders httpHeaders) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            httpHeaders.add(entry.key, entry.value)
        }
    }
}
