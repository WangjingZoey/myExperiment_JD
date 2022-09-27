package api;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import neo4j.WKFRec.WkfRecIND;
import org.neo4j.driver.v1.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/")
public class apiService {

    private Driver driver;
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
        Result result = new Result();
        result.setCode(0);
        result.setMsg("上传成功");
        return result;
    }

    @PostMapping("/delEntity")
    public Result delEntity() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("删除成功");
        return result;
    }

    @PostMapping("/train")
    public Result train() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("已启动模型训练");
        return result;
    }

    @PostMapping("/status")
    public JSONObject status() {
        String statusContent = "{" +
                "    \"code\": 0," +
                "    \"msg\": \"获取成功\"," +
                "    \"status\": 2," +
                "    \"report\": {" +
                "        \"accuracy\": -1," +
                "        \"f1Score\": -1," +
                "        \"precision\": -1," +
                "        \"recall\": -1    }," +
                "    \"log\": \"2020-02-03 15:00:47 - 训练结束.\"" +
                "}";
        JSONObject jsonObject = JSON.parseObject(statusContent);
        return(jsonObject);
    }

    @PostMapping("/serving")
    public Result serving() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("发布成功");
        return result;
    }

    @PostMapping(value = "/predict",produces = "application/json;charset=UTF-8")
    public Result predict(@RequestBody Map<String, ArrayList> content) throws IOException {
        System.out.println(content.get("keywords"));
        Map<String,Object> get = WkfRecIND.getInd(content.get("keywords"));
        assert get != null;
        if(get.isEmpty()){
            Result result = new Result();
            result.setCode(1);
            result.setMsg("数据库配置未更新，无法进行查询");
            return result;
        }
        return Result.ok(get);
    }

    @PostMapping("/export")
    public Result exportModel() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("已导出");
        return result;
    }

    @PostMapping("/import")
    public Result importModel() throws Exception {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("导入成功");
        return result;
    }

    @PostMapping(value ="/init_server_config",produces = "application/json;charset=UTF-8")
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
        if(ip.indexOf("bolt://") != 0){
            ip = "bolt://" + ip;
        }
        if(ip.lastIndexOf(":") == ip.length()-1){
            ip = ip.substring(0,ip.length()-1);
        }
        String port = (String) apiService.server.get("port");
        port = port.replace(":","");

        // "bolt://ip:port"
        String uri =  ip +":" + port;
        String username = (String) apiService.server.get("username");
        String password = (String) apiService.server.get("password");

        // Database connection information preservation
        Map<String,String> serverProperties = new HashMap<>();
        serverProperties.put("uri",uri);
        serverProperties.put("username",username);
        serverProperties.put("password",password);
        String filePath = "properties.json";
        File proFile = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(proFile, serverProperties);

        Result result = new Result();
        result.setCode(0);
        result.setMsg("更新配置数据成功");
        System.out.println(server);

        return result;
    }


    public void writeString(File file,String content){

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try {
                //关闭流释放资源
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
