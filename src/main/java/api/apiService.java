package api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import neo4j.WKFRec.WkfRecIND;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/kg/")
public class apiService {

    public static Map<String, Object> server = new HashMap<>();

    @GetMapping("/definition")
    public JSONObject definition() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("definition.json");
        byte[] bdata = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
        String data = new String(bdata, StandardCharsets.UTF_8);
        JSONObject info = JSONObject.parseObject(data);
        return info;
    }

    @PostMapping("/addEntity")
    public Result addEntity() {
        return Result.ok("上传成功");
    }


    @PostMapping("/delEntity")
    public Result delEntity() {
        return Result.ok("删除成功");
    }

    @PostMapping("/train")
    public Result train() {
        return Result.ok("已启动模型训练");
    }

    @PostMapping("/status")
    public JSONObject status() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("status.json");
        byte[] bdata = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
        String data = new String(bdata, StandardCharsets.UTF_8);
        JSONObject info = JSONObject.parseObject(data);
        return info;
    }

    @PostMapping("/serving")
    public Result serving() {
        return Result.ok("发布成功");
    }

    @PostMapping(value = "/predict", produces = "application/json;charset=UTF-8")
    public Result predict(@RequestBody Map<String, ArrayList> content) throws IOException {
        ArrayList keywords = content.get("keywords");
        Map<String, Object> get = WkfRecIND.getInd(keywords);
        assert get != null;
        if (get == null) {
            return Result.error("数据库配置无效，请更新");
        }
        return Result.ok(get);
    }

    @PostMapping("/export")
    public Result exportModel() {
        return Result.ok("已导出");

    }

    @PostMapping("/import")
    public Result importModel() {
        return Result.ok("导入成功");
    }

    @PostMapping(value = "/init_server_config", produces = "application/json;charset=UTF-8")
    public Result init_server_config(@RequestBody JSONObject relateServer) throws Exception {
        server.clear();
        JSONArray relateServerJsonArray = relateServer.getJSONArray("relate_server");
        Map<String, Object> relateServerMap = (Map<String, Object>) relateServerJsonArray.get(0);
        List serverListArray = (List) relateServerMap.get("server_list");
        Map<String, Object> serverList = (Map<String, Object>) serverListArray.get(0);
        Map<String, Object> serverConfig = (Map<String, Object>) serverList.get("server_config");
        server = serverConfig;

        // Get the uri information and check the splice ":"
        String ip = (String) apiService.server.get("ip");
        ip = ip.toLowerCase().trim();
        if (ip.indexOf("bolt://") != 0) {
            ip = "bolt://" + ip;
        }
        if (ip.lastIndexOf(":") == ip.length() - 1) {
            ip = ip.substring(0, ip.length() - 1);
        }
        String port = (String) apiService.server.get("port");
        port = port.replace(":", "");

        // "bolt://ip:port"
        String uri = ip + ":" + port;
        String username = (String) apiService.server.get("username");
        String password = (String) apiService.server.get("password");

        // Database connection information preservation
        Map<String, Object> serverProperties = new HashMap<>();
        serverProperties.put("uri", uri);
        serverProperties.put("username", username);
        serverProperties.put("password", password);
//        JSONObject json = new JSONObject(serverProperties);

        String filePath = "properties.json";
        File proFile = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(proFile, serverProperties);

        System.out.println(server);

        return Result.ok("更新配置数据成功");
    }


    public void writeString(File file, String content) {

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流释放资源
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
