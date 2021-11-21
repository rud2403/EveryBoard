import Vue from 'vue'
import Router from 'vue-router'
import store from '../store';

// 라우터할 페이지 임포트
import Main from '@/views/Main'
import LoginPage from '@/views/LoginPage'
import SignUpPage from '@/views/SignUpPage'
import FreeBoard from '@/views/board/FreeBoard'
import PopularBoard from '@/views/board/PopularBoard'
import MyPage from '@/views/user/MyPage'
import MyReply from '@/components/user/MyReply'
import MyBoard from '@/components/user/MyBoard'
import EditInfo from '@/components/user/EditInfo'
import DeleteUser from '@/components/user/DeleteUser'
import BoardForm from '@/views/board/BoardForm'
import BoardDetail from '@/views/board/BoardDetail'
import PasswordCerti from '@/components/user/PasswordCerti'
import UserEdit from '@/components/user/UserEdit'
import DeletePasswordCerti from '@/components/user/DeletePasswordCerti'
import AdminPage from '@/views/user/AdminPage'
import ManageUser from '@/components/user/ManageUser'

Vue.use(Router)

export default new Router({
    mode: 'history', // history 모드는 자연스러운 url 가능, 지정하지 않으면 해시(#)기호로 url 사용
    routes: [
        {
            path: "/", // 경로
            name: "Main", // 해당 경로의 이름 
            component: Main,
            redirect: { name: "PopularBoard"},
            children: [
                {
                    path: 'freeBoard',
                    name: 'FreeBoard',
                    component: FreeBoard,
                },
                {
                  path: 'popularBoard',
                  name: 'PopularBoard',
                  component: PopularBoard,
                },
                {
                    path: 'write',
                    name: 'BoardForm',
                    component: BoardForm,
                },
                {
                    path: 'board/:num',
                    name: 'BoardDetail',
                    component: BoardDetail,
                }
            ]
        },
        {
            path: "/signUp",
            name: "SignUpPage",
            component: SignUpPage
        },
        {
            path: "/login",
            name: "LoginPage",
            component: LoginPage
        },
        {
            path: '/myPage/:id',
            name: 'MyPage',
            component: MyPage,
            children: [
                {
                    path: 'myBoard',
                    name: 'MyBoard',
                    component: MyBoard,
                  },
                  {
                    path: 'myReply',
                    name: 'MyReply',
                    component: MyReply,
                  },
                  {
                    path: 'editInfo',
                    name: 'EditInfo',
                    component: EditInfo,
                    children: [
                        {
                            path: 'passwordCerti',
                            name: 'PasswordCerti',
                            component: PasswordCerti,
                        },
                        {
                            path: 'userEdit',
                            name: 'UserEdit',
                            component: UserEdit,
                        },
                    ]
                  },
                  {
                    path: 'deleteUser',
                    name: 'DeleteUser',
                    component: DeleteUser,
                    children: [
                        {
                            path: 'deletePasswordCerti',
                            name: 'DeletePasswordCerti',
                            component: DeletePasswordCerti,
                        },
                    ]
                  }
            ],
            beforeEnter: (to, from, next) => {
                if(store.state.isLogin){
                next();
                } else {
                    alert('로그인이 필요한 작업입니다.')
                }
            },
              
          },
        {
            path: '/adminPage',
            name: 'AdminPage',
            component: AdminPage,
            children: [
                {
                    path: 'manageUser',
                    name: 'ManageUser',
                    component: ManageUser, 
                }
            ],
            beforeEnter: (to, from, next) => {
                if(store.state.role === 'ADMIN'){
                    next();
                } else {
                    console.log('관리자만 접근할 수 있습니다.')
                    next({name: 'Home'});
                }
            },
        },
    ]
})