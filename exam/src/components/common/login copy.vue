<!-- 用户登录界面 -->
<template>
  <div id="login">
    <img class="nav" src="../../assets/img/nav.png" />
    <div class="bg"></div>
    <el-row class="main-container">
      <el-col :lg="8" :xs="16" :md="10" :span="10">
        <div class="top">
          <span class="title">在线考试系统</span>
        </div>
        <div class="bottom">
          <div class="container">
            <div>
              <video
                id="video"
                width="300px"
                preload
                autoplay
                loop
                muted
              ></video>
              <canvas
                id="canvas"
                width="250"
                height="250"
                style="position: absolute; top: 0; left: 0"
              ></canvas>
            </div>
            <el-button type="primary" class="row-login" @click="facelogin()"
              >视频验证</el-button
            >
             <el-button type="primary" class="row-login" @click="facezclogin()"
              >视频注册</el-button
            >
            <el-form
              :label-position="labelPosition"
              label-width="80px"
              :model="formLabelAlign"
            >
              <el-form-item label="用户名">
                <el-input
                  v-model.number="formLabelAlign.username"
                  placeholder="请输入用户名"
                ></el-input>
              </el-form-item>
              <el-form-item label="密码">
                <el-input
                  v-model="formLabelAlign.password"
                  placeholder="请输入密码"
                  type="password"
                ></el-input>
              </el-form-item>
              <div class="submit">
                <el-button type="primary" class="row-login" @click="login()"
                  >用户/管理员登录</el-button
                >
              </div>
              <div class="submit">
                <el-button
                  type="primary"
                  class="row-login2"
                  @click="createStudent()"
                  >用户注册</el-button
                >
              </div>
            </el-form>
          </div>
        </div>
      </el-col>
    </el-row>

    <div class="add">
      <el-dialog
        title="提示"
        :visible="dialogVisible2"
        append-to-body
        width="30%"
      >
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="姓名">
            <el-input v-model="form.studentName"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="form.sex"></el-input>
          </el-form-item>
          <el-form-item label="学院">
            <el-input v-model="form.institute"></el-input>
          </el-form-item>
          <el-form-item label="所属专业">
            <el-input v-model="form.major"></el-input>
          </el-form-item>
          <el-form-item label="年级">
            <el-input v-model="form.grade"></el-input>
          </el-form-item>
          <el-form-item label="班级">
            <el-input v-model="form.clazz"></el-input>
          </el-form-item>
          <el-form-item label="电话号码">
            <el-input v-model="form.tel"></el-input>
          </el-form-item>
          <el-form-item label="学号">
            <el-input v-model="form.cardId"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.pwd"></el-input>
          </el-form-item>
          <el-form-item label="人脸验证">
            <div>
              <video
                id="video"
                width="300px"
                preload
                autoplay  
                loop
                muted
              ></video>
              <canvas
                id="canvas"
                width="250"
                height="250"
                style="position: absolute; top: 0; left: 0"
              ></canvas>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit()">立即添加</el-button>
            <el-button type="text" @click="cancel()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import store from "@/vuex/store";
import { mapState } from "vuex";
export default {
  store,
  name: "login",
  data() {
    return {
      role: 2,
      labelPosition: "left",
      formLabelAlign: {},
      dialogVisible2: false,
      form: {
        //添加用户表单数据初始化
        studentName: null,
        grade: null,
        major: null,
        clazz: null,
        institute: null,
        tel: null,
        email: null,
        pwd: null,
        cardId: null,
        sex: null,
        role: 2,
      },
    };
  },
  methods: {
    //用户登录请求后台处理
    login() {
      console.log("登录操作执行-------");
      this.$axios({
        url: `/api/login`,
        method: "post",
        data: {
          ...this.formLabelAlign,
        },
      }).then((res) => {
        let resData = res.data.data;
        if (resData != null) {
          switch (resData.role) {
            case "0": //管理员
              this.$cookies.set("cname", resData.adminName);
              this.$cookies.set("cid", resData.adminId);
              this.$cookies.set("role", 0);
              this.$router.push({ path: "/index" }); //跳转到首页
              break;
            case "1": //教师
              this.$cookies.set("cname", resData.teacherName);
              this.$cookies.set("cid", resData.teacherId);
              this.$cookies.set("role", 1);
              this.$router.push({ path: "/index" }); //跳转到教师用户
              break;
            case "2": //学生
              this.$cookies.set("cname", resData.studentName);
              this.$cookies.set("cid", resData.studentId);
              this.$router.push({ path: "/student" });
              break;
          }
        }
        if (resData == null) {
          //错误提示
          this.$message({
            showClose: true,
            type: "error",
            message: "用户名或者密码错误",
          });
        }
      });
    },
        facezclogin() {
      // 点击按钮登录事件
      // 点击按钮时拿到登陆者面部信息
      this.userContext.drawImage(video, 0, 0, 450, 300);
      var userImgSrc = document.getElementById("canvas").toDataURL("img/png");
      console.log(userImgSrc);
      // 拿到bash64格式的照片信息
      var faceBase = userImgSrc.split(",")[1];
      // console.log(faceBase)
      var qs = require("qs");
      // axios异步请求
      // console.log(faceBase);
      // let data = new FormData();
      // data.append('faceBase',faceBase);
      this.$axios
        .post(
          "/api/register",
          qs.stringify({
            faceBase: faceBase,
            userName:'admin'
          })
        )
        .then((response) => {
          var data1 = response.data;
          var str = data1.substring(0, 1);

          if (str == 2) return this.$message.error("登录失败，请重新验证人脸");
          this.$message.success("注册成功！！，点击确认跳转至登录页面");
          this.$router.push("/student");
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    facelogin() {
      // 点击按钮登录事件
      // 点击按钮时拿到登陆者面部信息
      this.userContext.drawImage(video, 0, 0, 450, 300);
      var userImgSrc = document.getElementById("canvas").toDataURL("img/png");
      console.log(userImgSrc);
      // 拿到bash64格式的照片信息
      var faceBase = userImgSrc.split(",")[1];
      // console.log(faceBase)
      var qs = require("qs");
      // axios异步请求
      // console.log(faceBase);
      // let data = new FormData();
      // data.append('faceBase',faceBase);
      this.$axios
        .post(
          "/api/facelogin",
          qs.stringify({
            faceBase: faceBase,
          })
        )
        .then((response) => {
          var data1 = response.data;
          var str = data1.substring(0, 1);

          if (str == 2) return this.$message.error("登录失败，请重新验证人脸");
          this.$message.success("人脸验证成功，祝您考试愉快");
          this.$router.push("/student");
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    clickTag(key) {
      this.role = key;
    },
    diaoyong() {
      /** 调用摄像头，获取媒体视频流**/
      var video = document.getElementById("video");
      var canvas = document.getElementById("canvas");
      // // 返回画布二维画图环境
      this.userContext = canvas.getContext("2d");
      var getUserMedia =
        // 浏览器兼容,表示在火狐、Google、IE等浏览器都可正常支持
        navigator.getUserMedia ||
        navigator.webkitGetUserMedia ||
        navigator.mozGetUserMedia ||
        navigator.msGetUserMedia;
      // getUserMedia.call(要调用的对象，约束条件，调用成功的函数，调用失败的函数)
      getUserMedia.call(
        window.navigator,
        { video: true, audio: false },
        function (localMediaStream) {
          // 获取摄像头捕捉的视频流
          video.srcObject = localMediaStream;
        },
        function (e) {
          console.log("获取摄像头失败！！");
        }
      );
    },

    clickTag(key) {
      this.role = key;
    },
    createStudent() {
      this.dialogVisible2 = true;
      this.form = {};
    },

    onSubmit() {
      //数据提交
      this.$axios({
        url: "/api/student",
        method: "post",
        data: {
          ...this.form,
        },
      }).then((res) => {
        if (res.data.code == 200) {
          this.$message({
            message: "用户注册成功",
            type: "success",
          });
        }
        this.dialogVisible2 = false; // 关闭弹窗
      });
    },
    cancel() {
      //取消按钮
      this.form = {};
      this.dialogVisible2 = false; // 关闭弹窗
    },
  },
  computed: mapState(["userInfo"]),
  mounted() {
    this.diaoyong();
  },
};
</script>

<style lang="scss" scoped>
.remind {
  border-radius: 4px;
  padding: 10px 20px;
  display: flex;
  position: fixed;
  right: 20px;
  bottom: 50%;
  flex-direction: column;
  color: #606266;
  background-color: #fff;
  border-left: 4px solid #409eff;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
.container {
  margin-top: 50px;
  margin-bottom: 32px;
}
.container .el-radio-group {
  margin: 30px 0px;
}
a:link {
  color: #ff962a;
  text-decoration: none;
}
#login {
  font-size: 14px;
  color: #04468b;
  background-color: #fff;
}
img {
  width: 100%;
  height: 100%;
}
#login .bg {
  position: fixed;
  top: 68px;
  left: 0;
  width: 100%;
  overflow-y: auto;
  height: 100%;
  background: url("../../assets/img/bg1.png") center top / cover no-repeat;
}
#login .main-container {
  margin-right: 100px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
#login .main-container .top {
  margin-top: 100px;
  font-size: 30px;
  color: 000;
  display: flex;
  justify-content: center;
}
#login .top .icon-kaoshi {
  font-size: 80px;
}
#login .top .title {
  margin-top: 20px;
}
#login .bottom {
  display: flex;
  justify-content: center;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
#login .bottom .title {
  text-align: center;
  font-size: 30px;
}
.bottom .container .title {
  margin: 30px 0px;
}
.bottom .submit .row-login {
  width: 100%;
  background-color: #04468b;
  border-color: #04468b;
  margin: 5px 0px 10px 0px;
  padding: 15px 20px;
}
.bottom .submit .row-login2 {
  width: 100%;
  background-color: #04468b;
  border-color: #04468b;
  margin: 5px 0px 10px 0px;
  padding: 15px 20px;
}
.bottom .submit {
  display: flex;
  justify-content: center;
}
.footer {
  margin-top: 50px;
  text-align: center;
}
.footer .msg1 {
  font-size: 18px;
  color: #fff;
  margin-bottom: 15px;
}
.footer .msg2 {
  font-size: 14px;
  color: #e3e3e3;
  margin-top: 70px;
}
.bottom .options {
  margin-bottom: 40px;
  color: #ff962a;
  display: flex;
  justify-content: space-between;
}
.bottom .options > a {
  color: #ff962a;
}
.bottom .options .register span:nth-child(1) {
  color: #8c8c8c;
}
.add {
  padding: 0px 40px;
  width: 400px;
}
</style>
