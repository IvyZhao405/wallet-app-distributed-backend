package dev.ivyzhao.wallet.merchants.controller;

import com.alibaba.fastjson.JSON;
import dev.ivyzhao.wallet.merchants.service.IMerchantsServ;
import dev.ivyzhao.wallet.merchants.vo.CreateMerchantsRequest;
import dev.ivyzhao.wallet.merchants.vo.PassTemplate;
import dev.ivyzhao.wallet.merchants.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>Merchant service Controller</h1>
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantsCtl {

    /** Merchant Service Interface */
    private final IMerchantsServ merchantsServ;

    @Autowired
    public MerchantsCtl(IMerchantsServ merchantsServ) {
        this.merchantsServ = merchantsServ;
    }

    @ResponseBody
    @PostMapping("/create")
    public Response createMerchants(@RequestBody CreateMerchantsRequest request) {
        log.info("CreateMerchants: {}", JSON.toJSONString(request));
        return merchantsServ.createMerchants(request);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Response buildMerchantsInfo(@PathVariable Integer id) {
        log.info("BuildMerchantsInfo: {}", id);
        return merchantsServ.buildMerchantsInfoById(id);
    }

    @ResponseBody
    @PostMapping("/drop")
    public Response dropPassTemplate(PassTemplate passTemplate) {
        log.info("DropPassTemplate: {}", passTemplate);
        return merchantsServ.dropPassTemplate(passTemplate);
    }
}
