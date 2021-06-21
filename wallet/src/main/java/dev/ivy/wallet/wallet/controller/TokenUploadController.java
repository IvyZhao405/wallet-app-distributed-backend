package dev.ivy.wallet.wallet.controller;

import dev.ivy.wallet.wallet.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@Controller
public class TokenUploadController {

    /**Redis client */
    private final StringRedisTemplate redisTemplate;

    public TokenUploadController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //80bc4c08ac13d7d5e59c8c3ea356d575
    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping("/token")
    public String tokenFileUpload(@RequestParam("merchantsId") String merchantsId,
                                  @RequestParam("couponId") String passTemplateId,
                                  @RequestParam("file") MultipartFile file,
                                  RedirectAttributes redirectAttributes){
        if (null == passTemplateId || file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    "couponId is null or file is empty");
            return "redirect:/uploadStatus";
        }

        try {
            File cur = new File(Constants.TOKEN_DIR + merchantsId);
            if (!cur.exists()) {
                log.info("Create File: {}", cur.mkdir());
            }
            Path path = Paths.get(Constants.TOKEN_DIR, merchantsId, passTemplateId);
            Files.write(path, file.getBytes());
            if (!writeTokenToRedis(path, passTemplateId)) {
                redirectAttributes.addFlashAttribute("message",
                        "write token error");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded '" + file.getOriginalFilename() + "'");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    /**
     * <h2>Write token to redis</h2>
     * @param path {@link Path}
     * @param key redis key
     * @return true/false
     */
    private boolean writeTokenToRedis(Path path, String key) {
        Set<String> tokens;

        try(Stream<String> stream = Files.lines(path)){
            tokens = stream.collect(Collectors.toSet());
        }catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        //only suitable for single machine local host redis. "executePipelined" needs to be changed.
        if (!CollectionUtils.isEmpty(tokens)) {
            redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (String token : tokens) {
                    connection.sAdd(key.getBytes(), token.getBytes());
                }
                return null;
            });
            return true;
        }
        return false;
    }
}
