package com.exam.controller;

import com.baidu.aip.face.AipFace;
import com.exam.entity.Users;
import com.exam.service.IUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

@RestController
@CrossOrigin
public class UsersController {

    private static final String APP_ID = "22781017";

    private static final String API_KEY = "ftGT0rYR7wv6bVZKzhXdGMus";

    private static final String SECRET_KEY = "zh8hMUBTwa3tK06wPthD5m2ECdcA4jkh";
    HashMap<String, String> option = new HashMap<String, String>();
    @Autowired
    private IUserService userService;



    @RequestMapping(value = "register",method = RequestMethod.POST)
    public String register(String userName,String faceBase) throws IOException {
        if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(faceBase)) {
            //文件上传的地址
            String upPath = ResourceUtils.getURL("classpath:").getPath()+"static\\photo";
            //用于查看路径是否正确
            System.out.println(upPath);
            // 图片名称
            String fileName = userName+System.currentTimeMillis() + ".png";
            System.out.println(upPath+"\\"+fileName);
            File file = new File(upPath+"\\"+fileName);
            //初始化百度云的AipFace
            AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

            //往自己demo数据库里插入一条用户数据
            Users user = new Users();
            user.setUserName(userName);
            user.setUserPhoto(upPath+"\\"+fileName);
            Users exitUser = userService.selectUserByName(user);
            if(exitUser != null) {
                return "2";
            }
            userService.addUsers(user);


            // 往自己demo服务器里面上传摄像头捕获的图片
            GenerateImage(faceBase, file);
            //向百度云人脸库插入一张人脸
            facesetAddUser(client,faceBase,userName);
        }
        return "1";

    }

    @RequestMapping(value = "facelogin",method = RequestMethod.POST)
    public String login(String faceBase) {
        System.out.println("faceBase====================================="+faceBase);
        String faceData = faceBase;
        //进行人像数据对比
        AipFace client = new AipFace(APP_ID,API_KEY,SECRET_KEY);
        System.out.println("人脸测试1");
        Double num = verifyUser(faceData,client);
        if(num>20) {
            System.out.println("option"+option.get("face_token"));
            return "1"+","+option.get("face_token");
        }else {
            return "2"+","+option.get("face_token");
        }

    }

    /**
     * 人脸比对
     * @param imgBash64 照片转bash64格式
     * @return
     */
    public Double verifyUser(String imgBash64,AipFace client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();

        JSONObject res = client.search(imgBash64, "BASE64", "user_01", options);

        System.out.println(res.toString(2));
        System.out.println(res.getJSONObject("result"));
        System.out.println(res.getJSONObject("result").getJSONArray("user_list"));
        System.out.println(res.getJSONObject("result").get("face_token"));
        Object face = res.getJSONObject("result").get("face_token");
        option.put("face_token", (String) face);
        JSONObject user = (JSONObject) res.getJSONObject("result").getJSONArray("user_list").get(0);
        Double score = (Double) user.get("score");

        return score;
    }


    /**
     *
     * @Title: GenerateImage
     * @Description: 该方法的主要作用：// 对字节数组字符串进行Base64解码并生成图片,并上传至服务器
     * @param  @param imgStr
     * @param  @param imgFilePath
     * @param  @return 设定文件
     * @return  返回类型：boolean
     * @throws
     */
    public boolean GenerateImage(String imgStr, File file) {
        // 图像数据为空
        if (imgStr == null) {
            System.out.println(imgStr);
            return false;
        }else {
            @SuppressWarnings("restriction")
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                // Base64解码
                @SuppressWarnings("restriction")
                byte[] bytes = decoder.decodeBuffer(imgStr);
                for (int i = 0; i < bytes.length; ++i) {
                    if (bytes[i] < 0) {
                        bytes[i] += 256;
                    }
                }
                // 生成jpeg图片
                if(!file.exists()) {
                    file.getParentFile().mkdir();
                    OutputStream out = new FileOutputStream(file);
                    out.write(bytes);
                    out.flush();
                    out.close();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * @Title: facesetAddUser
     * @Description: 该方法的主要作用：人脸注册,给人脸库中注册一个人脸
     * @param  @param client 设定文件
     * @return  返回类型：void
     * @throws
     */
    public boolean facesetAddUser(AipFace client, String faceBase, String username) {
        // 参数为数据库中注册的人脸
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        JSONObject res = client.addUser(faceBase, "BASE64", "user_01", username, options);
        System.out.println(res.toString(2));
        return true;

    }
}
