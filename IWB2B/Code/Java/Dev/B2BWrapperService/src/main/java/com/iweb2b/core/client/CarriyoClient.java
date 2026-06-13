//package com.iweb2b.core.client;
//
//import com.iweb2b.core.model.carriyo.CarriyoStatusUpdateRequest;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@FeignClient(name = "carriyo", url = "")
//public interface CarriyoClient {
//
//    @PostMapping("/{carrier}/update-status")
//    void updateStatus(@PathVariable("carrier") String carrier, @RequestBody CarriyoStatusUpdateRequest request);
//}