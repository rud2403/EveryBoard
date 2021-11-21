import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import router from '../router'

Vue.use(Vuex)

export default new Vuex.Store({
  
  state: {
      // 유저 정보
      id: "",
      email: "",
      name: "",
      role: "",
      isLogin: false,

      // 게시글 정보
      num: "",
      title: "",
      author: "",
      readcount: "",
      likecount: "",
      regdate: "",
  },
  mutations: {
    loginSuccess(state, payload){
        state.id = payload.id;
        state.email = payload.email;
        state.name = payload.name;
        state.role = payload.role;
        state.isLogin = true;
    },
    logout(state){
        state.id = "";
        state.email = "";
        state.name = "";
        state.role = "";
        state.isLogin = false;
        localStorage.removeItem("access_token")
    },
    setBoardInfo(state, payload) {
      state.num= payload.num;
      state.title= payload.title;
      state.author= payload.author;
      state.readcount= payload.readcount;
      state.likecount= payload.likecount;
      state.regdate= payload.regdate;
    },
    changMemberInfo(state, payload) {
      state.email = payload.email;
      state.name = payload.name;
    },
    deleteMemberInfo(state) {
      state.id = "";
      state.email = "";
      state.name = "";
      state.role = "";
      state.isLogin = false;
      localStorage.removeItem("access_token")
    }
  },
  actions: {
    // 로그인 액션 시작
    login({dispatch}, payload) {
        axios
            // 로그인 시도
            .post("/api/login", {
              email: payload.email,
              password: payload.password,
            })
            // 아이디 비밀번호 일치
            .then((res) => {
                let token = res.data.token;
                // 로컬호스트에 토큰 저장
                localStorage.setItem("access_token", token);
                dispatch('getMemberInfo')
            })
            // 아이디 비밀번호 불일치
            .catch((err) => {
              console.log(err);
              alert('이메일 또는 비밀번호를 확인해 주세요.');
            })
            .finally(() => {});
    },
    // 멤버 정보가져오는 액션 시작
    getMemberInfo({commit}) {
      let token = localStorage.getItem("access_token")
      let config = {
        headers: {
        access_token: token,
        },
    };
      axios
        .get("/get/subject", config)
        .then((response) => {
            // 유저 정보를 가져온다
            commit('loginSuccess',response.data)
            router.push({ name: "Main" })
        })
        .catch((error) => {
            console.log(error);
        });
    },
    // 정보 변경하는 액션 시작1
    changMemberInfo({dispatch}, payload){
      // 정보 변경하는 axios
      axios
        .post("/api/user/changMemberInfo", {
          id: payload.id,
          email: payload.email,
          name: payload.name,
        })
        .then((response) => {
            // 유저 정보를 가져온다
            console.log(response);
            alert('토큰 가져오기 완료');
            // 로컬호스트에 토큰 저장
            let token = response.data.token;
            localStorage.setItem("access_token", token);
            dispatch('changeMember')
        })
        .catch((error) => {
            console.log(error);
        });
    },
    // 멤버 정보 변경하는 액션 시작2
    changeMember({commit}) {
      let token = localStorage.getItem("access_token")
      let config = {
        headers: {
        access_token: token,
        },
    };
      axios
        .get("/get/subject", config)
        .then((response) => {
            // 유저 정보를 가져온다
            commit('changMemberInfo',response.data);
        })
        .catch((error) => {
            console.log(error);
        });
    },
    // 회원 탈퇴 액션 시작
    deleteMemberInfo({commit}, payload) {
      axios
        .post("/api/user/deleteMemberInfo", {
          id: payload.id,
        })
        .then((response) => {
            console.log(response);
            commit('deleteMemberInfo')
        })
        .catch((error) => {
            console.log(error);
        });
    }
  }
})