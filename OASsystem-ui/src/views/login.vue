<template>
	<div class="login">
		<div class="login_left">
			<el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
			<el-form-item prop="username">
				<div class="sub-title">用户名</div>
				<el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="请输入用户名或手机号">
					<svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
				</el-input>
			</el-form-item>
			<el-form-item prop="password">
				<div class="sub-title">密码</div>
				<el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="请输入登录密码" @keyup.enter.native="handleLogin">
					<svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
				</el-input>
			</el-form-item>
			<el-form-item prop="code">
				<el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%" @keyup.enter.native="handleLogin">
					<svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
				</el-input>
				<div class="login-code">
					<img :src="codeUrl" @click="getCode" />
				</div>
			</el-form-item>
			<el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
			<el-form-item style="width:100%;">
				<el-button :loading="loading" size="medium" type="primary" style="width:100%;" @click.native.prevent="handleLogin">
					<span v-if="!loading">登 录</span>
					<span v-else>登 录 中...</span>
				</el-button>
			</el-form-item>
		</el-form>
		<!--  底部  -->
		<div class="el-login-footer">
			<span>©2020 xuanheng   (冀)-经营性-2017-0020  冀公网安备11000002000001号    冀ICP证030173号</span>
		</div>
	</div>
		<div class="login_right">

		</div>
	</div>
</template>

<script>
	import { getCodeImg } from "@/api/login";
	import Cookies from "js-cookie";
	import { encrypt, decrypt } from '@/utils/jsencrypt'
	import getMd5 from "@/assets/js/md5.min";

	export default {
		name: "Login",
		data() {
			return {
				codeUrl: "",
				cookiePassword: "",
				loginForm: {
					username: "",
					password: "",
					rememberMe: false,
					code: "",
					uuid: ""
				},
				loginRules: {
					username: [{
						required: true,
						trigger: "blur",
						message: "用户名不能为空"
					}],
					password: [{
						required: true,
						trigger: "blur",
						message: "密码不能为空"
					}],
					code: [{
						required: false,
						trigger: "change",
						message: "验证码不能为空"
					}]
				},
				loading: false,
				redirect: undefined
			};
		},
		watch: {
			$route: {
				handler: function(route) {
					this.redirect = route.query && route.query.redirect;
				},
				immediate: true
			}
		},
		created() {
			this.getCode();
			this.getCookie();
		},
		methods: {
			getCode() {
				getCodeImg().then(res => {
					this.codeUrl = "data:image/gif;base64," + res.img;
					this.loginForm.uuid = res.uuid;
				});
			},
			getCookie() {
				const username = Cookies.get("username");
				const password = Cookies.get("password");
				const rememberMe = Cookies.get('rememberMe')
				this.loginForm = {
					username: username === undefined ? this.loginForm.username : username,
					password: password === undefined ? this.loginForm.password : decrypt(password),
					rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
				};
			},
			handleLogin() {
				this.$refs.loginForm.validate(valid => {
					if(valid) {
						this.loading = true;
						if(this.loginForm.rememberMe) {
							Cookies.set("username", this.loginForm.username, {
								expires: 30
							});
							Cookies.set("password", encrypt(this.loginForm.password), {
								expires: 30
							});
							Cookies.set('rememberMe', this.loginForm.rememberMe, {
								expires: 30
							});
						} else {
							Cookies.remove("username");
							Cookies.remove("password");
							Cookies.remove('rememberMe');
						}

						// 密码使用md5加密传输
						this.loginForm.password = getMd5(this.loginForm.password);

						this.$store
							.dispatch("Login", this.loginForm)
							.then(() => {
								// this.$router.push({ path: this.redirect || "/" });// 切换不同用户导致404
								this.$router.push({
									path: "/index"
								});
							})
							.catch(() => {
								this.loginForm.password = '';
								this.loading = false;
								this.getCode();
							});
					}
				});
			}
		}
	};
</script>

<style rel="stylesheet/scss" scoped="scoped" lang="scss">
	.login{
		display: flex;
		height: 100%;
		background: #fff;
	}
	.login_right{
		/*display: flex;
		justify-content: center;
		align-items: center;*/
		width:60%;
		height: 100%;
		background-image: url("../assets/image/login_right.png");
		background-size:100% 100%;
	}
	.login_left{
		width:40%;
		height: 100%;
		background-image: url("../assets/image/login_left.png");
		background-size:100% 100%;
	}
	.title {
		margin: 0px auto 30px auto;
		text-align: center;
		color: #707070;
	}

	.login-form {
		border-radius: 6px;
		/*background: #ffffff;*/
		width: 411px;
		box-sizing: border-box;
		margin:0 auto;
		margin-top: 30%;
		padding: 25px 25px 5px 25px;
		.el-input {
			height: 38px;
			input {
				height: 38px;
			}
		}
		.input-icon {
			height: 39px;
			width: 14px;
			margin-left: 2px;
		}
	}
	.el-input__inner{
		border-radius: 0;
		border:none;
		border-bottom: 1px solid #E5E5E5;
	}
	.login-tip {
		font-size: 13px;
		text-allogin-backgroundign: center;
		color: #bfbfbf;
	}

	.login-code {
		width: 33%;
		height: 38px;
		float: right;
		img {
			cursor: pointer;
			vertical-align: middle;
		}
	}
	/*按钮颜色*/
	.el-button--primary{
		background-color: #BA0015;
		border-color:#BA0015;
	}
	.el-login-footer {
		height: 40px;
		line-height: 40px;
		position: fixed;
		text-align: center;
		bottom: 0;
		width: 40%;
		color: #B2B2B2;
		font-family: Arial;
		font-size: 12px;
		letter-spacing: 1px;
	}
</style>
